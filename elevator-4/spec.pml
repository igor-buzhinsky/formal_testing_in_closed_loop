// plant
ltl pos0_1 { X( []((elevator_pos == 0) && down && !up -> X(elevator_pos == 0)) ) }
ltl pos0_2 { X( []((elevator_pos == 0) && !down && up -> X(elevator_pos == 1)) ) }
ltl pos0_3 { X( []((elevator_pos == 0) && (down == up) -> X(elevator_pos == 0)) ) }
ltl pos1_1 { X( []((elevator_pos == 1) && down && !up -> X(elevator_pos == 0)) ) }
ltl pos1_2 { X( []((elevator_pos == 1) && !down && up -> X(elevator_pos == 2)) ) }
ltl pos1_3 { X( []((elevator_pos == 1) && (down == up) -> X(elevator_pos == 1)) ) }
ltl pos2_1 { X( []((elevator_pos == 2) && down && !up -> X(elevator_pos == 1)) ) }
ltl pos2_2 { X( []((elevator_pos == 2) && !down && up -> X(elevator_pos == 3)) ) }
ltl pos2_3 { X( []((elevator_pos == 2) && (down == up) -> X(elevator_pos == 2)) ) }
ltl pos3_1 { X( []((elevator_pos == 3) && down && !up -> X(elevator_pos == 2)) ) }
ltl pos3_2 { X( []((elevator_pos == 3) && !down && up -> X(elevator_pos == 4)) ) }
ltl pos3_3 { X( []((elevator_pos == 3) && (down == up) -> X(elevator_pos == 3)) ) }
ltl pos4_1 { X( []((elevator_pos == 4) && down && !up -> X(elevator_pos == 3)) ) }
ltl pos4_2 { X( []((elevator_pos == 4) && !down && up -> X(elevator_pos == 5)) ) }
ltl pos4_3 { X( []((elevator_pos == 4) && (down == up) -> X(elevator_pos == 4)) ) }
ltl pos5_1 { X( []((elevator_pos == 5) && down && !up -> X(elevator_pos == 4)) ) }
ltl pos5_2 { X( []((elevator_pos == 5) && !down && up -> X(elevator_pos == 6)) ) }
ltl pos5_3 { X( []((elevator_pos == 5) && (down == up) -> X(elevator_pos == 5)) ) }
ltl pos6_1 { X( []((elevator_pos == 6) && down && !up -> X(elevator_pos == 5)) ) }
ltl pos6_2 { X( []((elevator_pos == 6) && !down && up -> X(elevator_pos == 6)) ) }
ltl pos6_3 { X( []((elevator_pos == 6) && (down == up) -> X(elevator_pos == 6)) ) }

ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 2)) ) }
ltl floor2 { X( [] (on_floor[2] <-> (elevator_pos == 4)) ) }
ltl floor3 { X( [] (on_floor[3] <-> (elevator_pos == 6)) ) }

ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }
ltl door2_open { X( []<>!(open[2] && !door_open[2]) ) }
ltl door3_open { X( []<>!(open[3] && !door_open[3]) ) }

ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }
ltl door2_close { X( []<>!(!open[2] && !door_closed[2]) ) }
ltl door3_close { X( []<>!(!open[3] && !door_closed[3]) ) }

// open-loop
ltl phi06 { X( []!(up && down) ) }

// closed-loop
ltl phi04_1 { X( []<>!down ) }
ltl phi04_2 { X( []<>!up ) }
ltl phi15 { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] -> door_closed[0] && door_closed[1] && door_closed[2] && door_closed[3]) ) }

ltl cl0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl cl1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl cl2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>(on_floor[2] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl cl3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>(on_floor[3] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2])) ) }

ltl phi11_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0]) ) } // false
ltl phi11_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1]) ) } // false
ltl phi11_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>on_floor[2]) ) } // false
ltl phi11_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>on_floor[3]) ) } // false
