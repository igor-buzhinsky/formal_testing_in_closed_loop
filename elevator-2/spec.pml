// plant
ltl pos0_1 { X( []((elevator_pos == 0) && !(!down && up) -> X(elevator_pos == 0)) ) }
ltl pos0_2 { X( []((elevator_pos == 0) && !down && up -> X(elevator_pos == 1)) ) }
ltl pos1_1 { X( []((elevator_pos == 1) && down && !up -> X(elevator_pos == 0)) ) }
ltl pos1_2 { X( []((elevator_pos == 1) && !down && up -> X(elevator_pos == 2)) ) }
ltl pos1_3 { X( []((elevator_pos == 1) && (down == up) -> X(elevator_pos == 1)) ) }
ltl pos2_1 { X( []((elevator_pos == 2) && down && !up -> X(elevator_pos == 1)) ) }
ltl pos2_2 { X( []((elevator_pos == 2) && !down && up -> X(elevator_pos == 3)) ) }
ltl pos2_3 { X( []((elevator_pos == 2) && (down == up) -> X(elevator_pos == 2)) ) }
ltl pos3_1 { X( []((elevator_pos == 3) && down && !up -> X(elevator_pos == 2)) ) }
ltl pos3_2 { X( []((elevator_pos == 3) && !(down && !up) -> X(elevator_pos == 3)) ) }

ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 3)) ) }

ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }

ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }

ltl door0_delay { X( [](!door_open[0] -> X(!user_floor_button[0] && !user_cabin_button[0] && door_open[0] -> X(!user_floor_button[0] && !user_cabin_button[0] -> door_open[0] && X(!user_floor_button[0] && !user_cabin_button[0] -> door_open[0] && X(!user_floor_button[0] && !user_cabin_button[0] -> !door_open[0]))))) ) }
ltl door1_delay { X( [](!door_open[1] -> X(!user_floor_button[1] && !user_cabin_button[1] && door_open[1] -> X(!user_floor_button[1] && !user_cabin_button[1] -> door_open[1] && X(!user_floor_button[1] && !user_cabin_button[1] -> door_open[1] && X(!user_floor_button[1] && !user_cabin_button[1] -> !door_open[1]))))) ) }

// open-loop
ltl phi06 { X( []!(up && down) ) }

// closed-loop
ltl phi04_1 { X( []<>!down ) }
ltl phi04_2 { X( []<>!up ) }
ltl phi15 { X( [](!on_floor[0] && !on_floor[1] -> door_closed[0] && door_closed[1]) ) }

ltl cl0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] || user_floor_button[1] || user_cabin_button[1])) ) }
ltl cl1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] || user_floor_button[0] || user_cabin_button[0])) ) }

ltl phi11_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0]) ) } // false
ltl phi11_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1]) ) } // false
