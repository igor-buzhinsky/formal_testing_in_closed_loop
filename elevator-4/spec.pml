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
ltl pos3_2 { X( []((elevator_pos == 3) && !down && up -> X(elevator_pos == 4)) ) }
ltl pos3_3 { X( []((elevator_pos == 3) && (down == up) -> X(elevator_pos == 3)) ) }
ltl pos4_1 { X( []((elevator_pos == 4) && down && !up -> X(elevator_pos == 3)) ) }
ltl pos4_2 { X( []((elevator_pos == 4) && !down && up -> X(elevator_pos == 5)) ) }
ltl pos4_3 { X( []((elevator_pos == 4) && (down == up) -> X(elevator_pos == 4)) ) }
ltl pos5_1 { X( []((elevator_pos == 5) && down && !up -> X(elevator_pos == 4)) ) }
ltl pos5_2 { X( []((elevator_pos == 5) && !down && up -> X(elevator_pos == 6)) ) }
ltl pos5_3 { X( []((elevator_pos == 5) && (down == up) -> X(elevator_pos == 5)) ) }
ltl pos6_1 { X( []((elevator_pos == 6) && down && !up -> X(elevator_pos == 5)) ) }
ltl pos6_2 { X( []((elevator_pos == 6) && !down && up -> X(elevator_pos == 7)) ) }
ltl pos6_3 { X( []((elevator_pos == 6) && (down == up) -> X(elevator_pos == 6)) ) }
ltl pos7_1 { X( []((elevator_pos == 7) && down && !up -> X(elevator_pos == 6)) ) }
ltl pos7_2 { X( []((elevator_pos == 7) && !down && up -> X(elevator_pos == 8)) ) }
ltl pos7_3 { X( []((elevator_pos == 7) && (down == up) -> X(elevator_pos == 7)) ) }
ltl pos8_1 { X( []((elevator_pos == 8) && down && !up -> X(elevator_pos == 7)) ) }
ltl pos8_2 { X( []((elevator_pos == 8) && !down && up -> X(elevator_pos == 9)) ) }
ltl pos8_3 { X( []((elevator_pos == 8) && (down == up) -> X(elevator_pos == 8)) ) }
ltl pos9_1 { X( []((elevator_pos == 9) && down && !up -> X(elevator_pos == 8)) ) }
ltl pos9_2 { X( []((elevator_pos == 9) && !(down && !up) -> X(elevator_pos == 9)) ) }

ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 3)) ) }
ltl floor2 { X( [] (on_floor[2] <-> (elevator_pos == 6)) ) }
ltl floor3 { X( [] (on_floor[3] <-> (elevator_pos == 9)) ) }

ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }
ltl door2_open { X( []<>!(open[2] && !door_open[2]) ) }
ltl door3_open { X( []<>!(open[3] && !door_open[3]) ) }

ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }
ltl door2_close { X( []<>!(!open[2] && !door_closed[2]) ) }
ltl door3_close { X( []<>!(!open[3] && !door_closed[3]) ) }

ltl door0_delay_1step { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(!door_open[0])))) ) } // false
ltl door1_delay_1step { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(!door_open[1])))) ) } // false
ltl door2_delay_1step { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(!door_open[2])))) ) } // false
ltl door3_delay_1step { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(!door_open[3])))) ) } // false

ltl door0_reopen_1step { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(door_open[0])))) } // false
ltl door1_reopen_1step { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(door_open[1])))) } // false
ltl door2_reopen_1step { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(door_open[2])))) } // false
ltl door3_reopen_1step { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(door_open[3])))) } // false

ltl door0_delay_2steps { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(door_open[0] && X(!door_open[0]))))) ) }
ltl door1_delay_2steps { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(door_open[1] && X(!door_open[1]))))) ) }
ltl door2_delay_2steps { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(door_open[2] && X(!door_open[2]))))) ) }
ltl door3_delay_2steps { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(door_open[3] && X(!door_open[3]))))) ) }

ltl door0_reopen_2steps { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(X(door_open[0]))))) }
ltl door1_reopen_2steps { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(X(door_open[1]))))) }
ltl door2_reopen_2steps { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(X(door_open[2]))))) }
ltl door3_reopen_2steps { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(X(door_open[3]))))) }

// open-loop
ltl no_up_and_down { X( []!(up && down) ) }

// closed-loop
ltl no_infinite_down { X( []<>!down ) }
ltl no_infinite_up { X( []<>!up ) }
ltl doors_closed_when_between_floors { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] -> door_closed[0] && door_closed[1] && door_closed[2] && door_closed[3]) ) }

ltl floor_reached_single_call_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl floor_reached_single_call_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl floor_reached_single_call_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>(on_floor[2] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[3] || user_cabin_button[3])) ) }
ltl floor_reached_single_call_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>(on_floor[3] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2])) ) }

ltl floor_reached_multiple_calls_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0]) ) } // false
ltl floor_reached_multiple_calls_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1]) ) } // false
ltl floor_reached_multiple_calls_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>on_floor[2]) ) } // false
ltl floor_reached_multiple_calls_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>on_floor[3]) ) } // false
