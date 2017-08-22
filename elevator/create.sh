#!/bin/bash
posperfloor=3

from=2
to=20

plantcomment="// "

for ((floors = from; floors <= to; floors++)); do
    floorstimes=$((floors * posperfloor))
    fm1=$((floors - 1))
    dir="../elevator-$floors"
    mkdir -p $dir

    cat header | sed "s/__1/$floors/g" | sed "s/__2/$posperfloor/g" > tmp
    cp tmp $dir/header.smv
    cp tmp $dir/header.pml

    cp controller.pml plant.pml $dir

    # 1 floor  - pos : 0..0
    # 2 floors - pos : 0..posperfloor
    # 3 floors - pos : 0..2*posperfloor
    # k floors - pos : 0..(k - 1)*posperfloor
    cat elevator.conf | sed "s/__1/$floors/g; s/__2/$((floorstimes - posperfloor))/g" > tmp
    mv tmp $dir/elevator.conf

    echo "ASSIGN" >> tmp
    echo "    next(elevator_pos) := elevator_pos3;" >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "    next(on_floor[$i]) := next(elevator_pos) = $((posperfloor * i));" >> tmp
    done
    for ((i = 0; i < $floors; i++)); do
        echo "    next(door_state[$i]) := open[$i] ? (door_state[$i] in { d_closed, d_closing } ? d_opening : d_open) : (door_state[$i] in { d_open, d_opening } ? d_closing : d_closed);" >> tmp
    done
    for ((i = 0; i < $floors; i++)); do
        echo "    next(door_closed[$i]) := next(door_state[$i]) = d_closed;" >> tmp
    done
    for ((i = 0; i < $floors; i++)); do
        echo "    next(door_open[$i]) := next(door_state[$i]) = d_open;" >> tmp
    done
    for ((i = 0; i < $floors; i++)); do
        echo "    next(button[$i]) := (next(on_floor[$i]) & next(door_open[$i])) ? FALSE : next(user_floor_button[$i]) ? TRUE : button[$i];" >> tmp
    done
    for ((i = 0; i < $floors; i++)); do
        echo "    next(call[$i]) := (next(on_floor[$i]) & next(door_open[$i])) ? FALSE : next(user_cabin_button[$i]) ? TRUE : call[$i];" >> tmp
    done
    echo "DEFINE" >> tmp
    echo "    elevator_pos1 := elevator_pos + (up ? 1 : 0) - (down ? 1 : 0);" >> tmp
    echo "    elevator_pos2 := elevator_pos1 > $posperfloor * (FLOORS - 1) ? $posperfloor * (FLOORS - 1) : elevator_pos1;" >> tmp
    echo "    elevator_pos3 := elevator_pos2 < 0 ? 0 : elevator_pos2;" >> tmp

    mv tmp $dir/plant.smv

    echo "ASSIGN" >> tmp
    
    echo "    next(door_timer) := case" >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "            !open[$i] & next(on_floor[$i]) & call$i: 4;" >> tmp
    done
    echo "            door_timer > 0: door_timer - 1;" >> tmp
    echo "            TRUE: door_timer;" >> tmp
    echo "        esac;" >> tmp
    
    for ((i = 0; i < $floors; i++)); do
        echo "    next(open[$i]) := open[$i] & !next(door_open[$i]) | next(on_floor[$i]) & call$i | next(door_open[$i]) & next(door_timer) > 0;" >> tmp
    done
    echo "    next(up) := case" >> tmp
    echo "            !is_requested | need_stop: FALSE;" >> tmp
    for ((i = 0; i < $floors - 1; i++)); do
        echo "            next(on_floor[$i]) & ($(for ((j = $i + 1; j < $floors - 1; j++)); do echo -n "call$j | "; done)call$fm1): TRUE;" >> tmp
    done
    for ((i = $floors - 1; i >= 1; i--)); do
        echo "            next(on_floor[$i]) & ($(for ((j = 0; j < $i - 1; j++)); do echo -n "call$j | "; done)call$((i - 1))): FALSE;" >> tmp
    done
    echo "            TRUE: up;" >> tmp
    echo "        esac;" >> tmp
    echo "    next(down) := case" >> tmp
    echo "            !is_requested | need_stop: FALSE;" >> tmp
    for ((i = 0; i < $floors - 1; i++)); do
        echo "            next(on_floor[$i]) & ($(for ((j = $i + 1; j < $floors - 1; j++)); do echo -n "call$j | "; done)call$fm1): FALSE;" >> tmp
    done
    for ((i = $floors - 1; i >= 1; i--)); do
        echo "            next(on_floor[$i]) & ($(for ((j = 0; j < $i - 1; j++)); do echo -n "call$j | "; done)call$((i - 1))): TRUE;" >> tmp
    done
    echo "            TRUE: down;" >> tmp
    echo "        esac;" >> tmp
    echo "DEFINE" >> tmp
    for ((i = 0; i < $floors; i++)); do
        echo "    call$i := next(button[$i]) | next(call[$i]);" >> tmp
    done
    echo "    is_requested := $(for ((i = 0; i < $floors - 1; i++)); do echo -n "call$i | "; done)call$fm1;" >> tmp
    echo "    on_some_floor := $(for ((i = 0; i < $floors - 1; i++)); do echo -n "next(on_floor[$i]) | "; done)next(on_floor[$fm1]);" >> tmp
    echo "    need_stop := $(for ((i = 0; i < $floors - 1; i++)); do echo -n "!next(door_closed[$i]) | next(on_floor[$i]) & call$i | "; done)!next(door_closed[$fm1]) | next(on_floor[$fm1]) & call$fm1;" >> tmp

    mv tmp $dir/controller.smv
    
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
done