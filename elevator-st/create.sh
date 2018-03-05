#!/bin/bash
posperfloor=3

from=2
to=3

plantcomment="// "

for ((floors = from; floors <= to; floors++)); do
    floorstimes=$((floors * posperfloor))
    fm1=$((floors - 1))
    dir="elevator-st-$floors"
    mkdir -p $dir

    echo -n > $dir/header.smv
    echo -n > $dir/header.pml
    echo -n > $dir/controller.pml
    echo -n > $dir/plant.pml
    echo -n > $dir/controller.smv
    echo -n > $dir/plant.smv

    # 1 floor  - pos : 0..0
    # 2 floors - pos : 0..posperfloor
    # 3 floors - pos : 0..2*posperfloor
    # k floors - pos : 0..(k - 1)*posperfloor
    cat elevator-st.conf | sed "s/__1/$floors/g; s/__2/$((floorstimes - posperfloor))/g" > tmp
    mv tmp $dir/elevator-st.conf

    # Promela specs
    echo "// plant" >> tmp
    echo ${plantcomment}"ltl pos0_1 { X( []((elevator_pos == 0) && !(!down && up) -> X(elevator_pos == 0)) ) }" >> tmp
    echo ${plantcomment}"ltl pos0_2 { X( []((elevator_pos == 0) && !down && up -> X(elevator_pos == 1)) ) }" >> tmp
    for ((i = 1; i < $floorstimes - $posperfloor; i++)); do
        echo ${plantcomment}"ltl pos${i}_1 { X( []((elevator_pos == $i) && down && !up -> X(elevator_pos == $((i - 1)))) ) }" >> tmp
        echo ${plantcomment}"ltl pos${i}_2 { X( []((elevator_pos == $i) && !down && up -> X(elevator_pos == $((i + 1)))) ) }" >> tmp
        echo ${plantcomment}"ltl pos${i}_3 { X( []((elevator_pos == $i) && (down == up) -> X(elevator_pos == $i)) ) }" >> tmp
    done
    echo ${plantcomment}"ltl pos$((floorstimes - posperfloor))_1 { X( []((elevator_pos == $((floorstimes - posperfloor))) && down && !up -> X(elevator_pos == $((floorstimes - posperfloor - 1)))) ) }" >> tmp
    echo ${plantcomment}"ltl pos$((floorstimes - posperfloor))_2 { X( []((elevator_pos == $((floorstimes - posperfloor))) && !(down && !up) -> X(elevator_pos == $((floorstimes - posperfloor)))) ) }" >> tmp

    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo ${plantcomment}"ltl floor$i { X( [] (on_floor[$i] <-> (elevator_pos == $((posperfloor * i)))) ) }" >> tmp
    done

    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo ${plantcomment}"ltl door${i}_open { X( []<>!(open[$i] && !door_open[$i]) ) }" >> tmp
    done

    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo ${plantcomment}"ltl door${i}_close { X( []<>!(!open[$i] && !door_closed[$i]) ) }" >> tmp
    done
    
    echo >> tmp
    echo "// open-loop" >> tmp
    echo "// ltl no_up_and_down_MUST_BE_TRUE { X( []!(up && down) ) }" >> tmp

    echo >> tmp
    echo "// closed-loop" >> tmp
    echo "// ltl no_infinite_down_MUST_BE_TRUE { X( []<>!down ) }" >> tmp
    echo "// ltl no_infinite_up_MUST_BE_TRUE { X( []<>!up ) }" >> tmp
    
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_closed_when_between_floors_MUST_BE_TRUE { X( []($(for ((i = 0; i < $floors - 1; i++)); do echo -n "!on_floor[$i] && "; done)!on_floor[$fm1] -> door_closed[$i]) ) }" >> tmp
    done
    
    # door closing delay - 2 step - true
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_delay_2steps_MUST_BE_TRUE { X( [](!door_open[$i] -> X(door_open[$i] -> X(door_open[$i] && X(door_open[$i] && X(!door_open[$i]))))) ) }" >> tmp
    done
    
    # door reopening - 2 steps - true
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_reopen_2steps_MUST_BE_TRUE { X( [](door_open[$i] -> X(!door_open[$i] && (user_floor_button[$i] || user_cabin_button[$i]) -> X(X(door_open[$i]))))) }" >> tmp
    done
    
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl floor_reached_single_call_${i}_MUST_BE_TRUE { X( []((user_floor_button[$i] || user_cabin_button[$i]) -> <>(on_floor[$i] && door_open[$i]$(for ((j = 0; j < $i; j++)); do echo -n " || user_floor_button[$j] || user_cabin_button[$j]"; done)$(for ((j = $i + 1; j < $floors; j++)); do echo -n " || user_floor_button[$j] || user_cabin_button[$j]"; done))) ) }" >> tmp
    done
    
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_open_when_between_floors_MUST_BE_FALSE { X( []($(for ((i = 0; i < $floors - 1; i++)); do echo -n "!on_floor[$i] && "; done)!on_floor[$fm1] -> door_open[$i]) ) }" >> tmp
    done
    
    # door closing delay - 1 step - false
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_delay_1step_MUST_BE_FALSE { X( [](!door_open[$i] -> X(door_open[$i] -> X(door_open[$i] && X(!door_open[$i])))) ) }" >> tmp
    done
    
    # door reopening - 1 step - false
    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl door${i}_reopen_1step_MUST_BE_FALSE { X( [](door_open[$i] -> X(!door_open[$i] && (user_floor_button[$i] || user_cabin_button[$i]) -> X(door_open[$i])))) }" >> tmp
    done

    echo >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "ltl floor_reached_multiple_calls_${i}_MUST_BE_FALSE { X( []((user_floor_button[$i] || user_cabin_button[$i]) -> <>on_floor[$i] && door_open[$i]) ) }" >> tmp
    done

    cat tmp | sed 's/<>/AF /g; s/\[\]/AG /g; s/X(/AX(/g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /CTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec.smv
    cat tmp | sed 's/<>/F /g; s/\[\]/G /g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /LTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec-ltl.smv
    mv tmp $dir/spec.pml
    
    # Structured text without loops: plant model
    echo -n > tmp
    echo "VAR_INPUT" >> tmp
    echo "    user_floor_button: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    user_cabin_button: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    up: BOOL;" >> tmp
    echo "    down: BOOL;" >> tmp
    echo "    open: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_OUTPUT" >> tmp
    echo "    on_floor: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    door_closed: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    door_open: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    button: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    call: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR" >> tmp
    echo "    elevator_pos: INT;" >> tmp
    echo "    door_state: ARRAY[0..$((floors - 1))] OF INT;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_CONSTANT" >> tmp
    echo "    // door_state enumeration" >> tmp
    echo "    d_closed: INT := 0;" >> tmp
    echo "    d_opening: INT := 1;" >> tmp
    echo "    d_open: INT := 2;" >> tmp
    echo "    d_closing: INT := 3;" >> tmp
    echo "END_VAR" >> tmp

    echo >> tmp
    echo "// plant" >> tmp
    echo "IF up THEN" >> tmp
    echo "    elevator_pos := elevator_pos + 1;" >> tmp
    echo "END_IF" >> tmp
    echo "IF down THEN" >> tmp
    echo "    elevator_pos := elevator_pos - 1;" >> tmp
    echo "END_IF" >> tmp
    echo "IF elevator_pos > $((posperfloor * (floors - 1))) THEN" >> tmp
    echo "    elevator_pos := $((posperfloor * (floors - 1)));" >> tmp
    echo "END_IF" >> tmp
    echo "IF elevator_pos < 0 THEN" >> tmp
    echo "    elevator_pos := 0;" >> tmp
    echo "END_IF" >> tmp
    
    for ((floor = 0; floor < $floors; floor++)); do
        echo "on_floor[$floor] := elevator_pos = $((posperfloor * floor));" >> tmp
        
        echo "IF open[$floor] THEN" >> tmp
        echo "    IF door_state[$floor] = d_closed OR door_state[$floor] = d_closing THEN" >> tmp
        echo "        door_state[$floor] := d_opening;" >> tmp
        echo "    ELSE" >> tmp
        echo "        door_state[$floor] := d_open;" >> tmp
        echo "    END_IF" >> tmp
        echo "ELSE" >> tmp
        echo "    IF door_state[$floor] = d_open OR door_state[$floor] = d_opening THEN" >> tmp
        echo "        door_state[$floor] := d_closing;" >> tmp
        echo "    ELSE" >> tmp
        echo "        door_state[$floor] := d_closed;" >> tmp
        echo "    END_IF" >> tmp
        echo "END_IF" >> tmp
        
        echo "door_closed[$floor] := door_state[$floor] = d_closed;" >> tmp
        echo "door_open[$floor] := door_state[$floor] = d_open;" >> tmp
        
        echo "IF on_floor[$floor] AND door_open[$floor] THEN" >> tmp
        echo "    button[$floor] := FALSE;" >> tmp
        echo "    call[$floor] := FALSE;" >> tmp
        echo "ELSE" >> tmp
        echo "    button[$floor] := button[$floor] OR user_floor_button[$floor];" >> tmp
        echo "    call[$floor] := call[$floor] OR user_cabin_button[$floor];" >> tmp
        echo "END_IF" >> tmp
    done
    mv tmp $dir/plant-st.txt
    
    # Structured text without loops: controller model
    echo -n > tmp
    echo "VAR_INPUT" >> tmp
    echo "    on_floor: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    door_closed: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    door_open: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    button: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    call: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_OUTPUT" >> tmp
    echo "    open: ARRAY[0..$((floors - 1))] OF BOOL;" >> tmp
    echo "    up: BOOL;" >> tmp
    echo "    down: BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR" >> tmp
    echo "    door_timer: INT;" >> tmp
    echo "    // controller temporary variables" >> tmp
    echo "    on_some_floor: BOOL;" >> tmp
    echo "    is_requested: BOOL;" >> tmp
    echo "    timer_set: BOOL;" >> tmp
    echo "    need_stop: BOOL;" >> tmp
    echo "END_VAR" >> tmp
    echo "VAR_CONSTANT" >> tmp
    echo "    // door_state enumeration" >> tmp
    echo "    d_closed: INT := 0;" >> tmp
    echo "    d_opening: INT := 1;" >> tmp
    echo "    d_open: INT := 2;" >> tmp
    echo "    d_closing: INT := 3;" >> tmp
    echo "END_VAR" >> tmp
    
    echo >> tmp
    echo "// controller" >> tmp
    echo "on_some_floor := FALSE;" >> tmp
    echo "is_requested := FALSE;" >> tmp
    echo "timer_set := FALSE;" >> tmp
    
    for ((floor = 0; floor < $floors; floor++)); do
        echo "IF NOT open[$floor] AND on_floor[$floor] AND (button[$floor] OR call[$floor]) THEN" >> tmp
        echo "    door_timer := 4;" >> tmp
        echo "    timer_set := TRUE;" >> tmp
        echo "END_IF" >> tmp
    done
    echo "IF door_timer > 0 AND NOT timer_set THEN" >> tmp
    echo "    door_timer := door_timer - 1;" >> tmp
    echo "END_IF" >> tmp
    
    for ((floor = 0; floor < $floors; floor++)); do
        echo "on_some_floor := on_some_floor OR on_floor[$floor];" >> tmp
        echo "is_requested := is_requested OR button[$floor] OR call[$floor];" >> tmp
        echo "open[$floor] := open[$floor] AND NOT door_open[$floor];" >> tmp
    done
    
    echo "need_stop := FALSE;" >> tmp
    
    echo "IF NOT on_some_floor THEN" >> tmp
    echo "    ;" >> tmp
    echo "ELSIF NOT is_requested THEN" >> tmp
    echo "    up := FALSE;" >> tmp
    echo "    down := FALSE;" >> tmp
    echo "ELSE" >> tmp
    for ((floor = 0; floor < $floors; floor++)); do
        echo "    need_stop := need_stop OR NOT door_closed[$floor] OR on_floor[$floor] AND (button[$floor] OR call[$floor]);" >> tmp
        echo "    open[$floor] := open[$floor] OR on_floor[$floor] AND (button[$floor] OR call[$floor]);" >> tmp
    done
    echo "    IF need_stop THEN" >> tmp
    echo "        up := FALSE;" >> tmp
    echo "        down := FALSE;" >> tmp
    echo "    ELSE" >> tmp
    for ((floor = 0; floor < $floors; floor++)); do
        for ((floor_cur = 0; floor_cur < $floor; floor_cur++)); do
            echo "        IF on_floor[$floor_cur] AND (button[$floor] OR call[$floor]) THEN" >> tmp
            echo "            up := TRUE;" >> tmp
            echo "            down := FALSE;" >> tmp
            echo "        END_IF" >> tmp
        done
        for ((floor_cur = $floor + 1; floor_cur < $floors; floor_cur++)); do
            echo "        IF on_floor[$floor_cur] AND (button[$floor] OR call[$floor]) THEN" >> tmp
            echo "            up := FALSE;" >> tmp
            echo "            down := TRUE;" >> tmp
            echo "        END_IF" >> tmp
        done
    done
    echo "    END_IF" >> tmp
    echo "END_IF" >> tmp
    
    for ((floor = 0; floor < $floors; floor++)); do
        echo "open[$floor] := open[$floor] OR door_open[$floor] AND door_timer > 0;" >> tmp
    done

    mv tmp $dir/controller-st.txt
done
