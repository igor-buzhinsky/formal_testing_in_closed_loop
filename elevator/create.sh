#!/bin/bash

floors="$1"
floors2=$((floors * 2))
fm1=$((floors - 1))
dir="../elevator-$floors"
mkdir -p $dir

cat header | sed "s/__1/$floors/g" > tmp
cp tmp $dir/header.smv
cp tmp $dir/header.pml

cp controller.pml plant.pml $dir

cat elevator.conf | sed "s/__1/$floors/g; s/__2/$((floors2 - 2))/g" > tmp
mv tmp $dir/elevator.conf

echo "ASSIGN" >> tmp
echo "    next(elevator_pos) := elevator_pos3;" >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "    next(on_floor[$i]) := next(elevator_pos) = 2 * $i;" >> tmp
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
echo "    elevator_pos2 := elevator_pos1 > 2 * (FLOORS - 1) ? 2 * (FLOORS - 1) : elevator_pos1;" >> tmp
echo "    elevator_pos3 := elevator_pos2 < 0 ? 0 : elevator_pos2;" >> tmp

mv tmp $dir/plant.smv

echo "ASSIGN" >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "    next(open[$i]) := open[$i] & !next(door_open[$i]) | next(on_floor[$i]) & call$i;" >> tmp
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
echo "ltl pos0_1 { X( []((elevator_pos == 0) && down && !up -> X(elevator_pos == 0)) ) }" >> tmp
echo "ltl pos0_2 { X( []((elevator_pos == 0) && !down && up -> X(elevator_pos == 1)) ) }" >> tmp
echo "ltl pos0_3 { X( []((elevator_pos == 0) && (down == up) -> X(elevator_pos == 0)) ) }" >> tmp
for ((i = 1; i <= $floors2 - 3; i++)); do
    echo "ltl pos${i}_1 { X( []((elevator_pos == $i) && down && !up -> X(elevator_pos == $((i - 1)))) ) }" >> tmp
    echo "ltl pos${i}_2 { X( []((elevator_pos == $i) && !down && up -> X(elevator_pos == $((i + 1)))) ) }" >> tmp
    echo "ltl pos${i}_3 { X( []((elevator_pos == $i) && (down == up) -> X(elevator_pos == $i)) ) }" >> tmp
done
echo "ltl pos$((floors2 - 2))_1 { X( []((elevator_pos == $((floors2 - 2))) && down && !up -> X(elevator_pos == $((floors2 - 3)))) ) }" >> tmp
echo "ltl pos$((floors2 - 2))_2 { X( []((elevator_pos == $((floors2 - 2))) && !down && up -> X(elevator_pos == $((floors2 - 2)))) ) }" >> tmp
echo "ltl pos$((floors2 - 2))_3 { X( []((elevator_pos == $((floors2 - 2))) && (down == up) -> X(elevator_pos == $((floors2 - 2)))) ) }" >> tmp

echo >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "ltl floor$i { X( [] (on_floor[$i] <-> (elevator_pos == $((2 * i)))) ) }" >> tmp
done

echo >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "ltl door${i}_open { X( []<>!(open[$i] && !door_open[$i]) ) }" >> tmp
done

echo >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "ltl door${i}_close { X( []<>!(!open[$i] && !door_closed[$i]) ) }" >> tmp
done

echo >> tmp
echo "// open-loop" >> tmp
echo "ltl phi06 { X( []!(up && down) ) }" >> tmp

echo >> tmp
echo "// closed-loop" >> tmp
echo "ltl phi04_1 { X( []<>!down ) }" >> tmp
echo "ltl phi04_2 { X( []<>!up ) }" >> tmp
echo "ltl phi15 { X( []($(for ((i = 0; i < $floors - 1; i++)); do echo -n "!on_floor[$i] && "; done)!on_floor[$fm1] -> $(for ((i = 0; i < $floors - 1; i++)); do echo -n "door_closed[$i] && "; done)door_closed[$fm1]) ) }" >> tmp

echo >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "ltl cl$i { X( []((user_floor_button[$i] || user_cabin_button[$i]) -> <>(on_floor[$i]$(for ((j = 0; j < $i; j++)); do echo -n " || user_floor_button[$j] || user_cabin_button[$j]"; done)$(for ((j = $i + 1; j < $floors; j++)); do echo -n " || user_floor_button[$j] || user_cabin_button[$j]"; done))) ) }" >> tmp
done

echo >> tmp
for ((i = 0; i < $floors; i++)); do
    echo "ltl phi11_$i { X( []((user_floor_button[$i] || user_cabin_button[$i]) -> <>on_floor[$i]) ) } // false" >> tmp
done

cat tmp | sed 's/<>/AF /g; s/\[\]/AG /g; s/X(/AX(/g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /CTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec.smv
cat tmp | sed 's/<>/F /g; s/\[\]/G /g; s/||/|/g; s/\&\&/\&/g; s/==/=/g; s/^ltl \w\+ { /LTLSPEC /g; s/}//g; s/\/\//--/g' > $dir/spec-ltl.smv
mv tmp $dir/spec.pml
