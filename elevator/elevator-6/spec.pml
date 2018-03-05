// plant
// ltl pos0_1 { X( []((elevator_pos == 0) && !(!down && up) -> X(elevator_pos == 0)) ) }
// ltl pos0_2 { X( []((elevator_pos == 0) && !down && up -> X(elevator_pos == 1)) ) }
// ltl pos1_1 { X( []((elevator_pos == 1) && down && !up -> X(elevator_pos == 0)) ) }
// ltl pos1_2 { X( []((elevator_pos == 1) && !down && up -> X(elevator_pos == 2)) ) }
// ltl pos1_3 { X( []((elevator_pos == 1) && (down == up) -> X(elevator_pos == 1)) ) }
// ltl pos2_1 { X( []((elevator_pos == 2) && down && !up -> X(elevator_pos == 1)) ) }
// ltl pos2_2 { X( []((elevator_pos == 2) && !down && up -> X(elevator_pos == 3)) ) }
// ltl pos2_3 { X( []((elevator_pos == 2) && (down == up) -> X(elevator_pos == 2)) ) }
// ltl pos3_1 { X( []((elevator_pos == 3) && down && !up -> X(elevator_pos == 2)) ) }
// ltl pos3_2 { X( []((elevator_pos == 3) && !down && up -> X(elevator_pos == 4)) ) }
// ltl pos3_3 { X( []((elevator_pos == 3) && (down == up) -> X(elevator_pos == 3)) ) }
// ltl pos4_1 { X( []((elevator_pos == 4) && down && !up -> X(elevator_pos == 3)) ) }
// ltl pos4_2 { X( []((elevator_pos == 4) && !down && up -> X(elevator_pos == 5)) ) }
// ltl pos4_3 { X( []((elevator_pos == 4) && (down == up) -> X(elevator_pos == 4)) ) }
// ltl pos5_1 { X( []((elevator_pos == 5) && down && !up -> X(elevator_pos == 4)) ) }
// ltl pos5_2 { X( []((elevator_pos == 5) && !down && up -> X(elevator_pos == 6)) ) }
// ltl pos5_3 { X( []((elevator_pos == 5) && (down == up) -> X(elevator_pos == 5)) ) }
// ltl pos6_1 { X( []((elevator_pos == 6) && down && !up -> X(elevator_pos == 5)) ) }
// ltl pos6_2 { X( []((elevator_pos == 6) && !down && up -> X(elevator_pos == 7)) ) }
// ltl pos6_3 { X( []((elevator_pos == 6) && (down == up) -> X(elevator_pos == 6)) ) }
// ltl pos7_1 { X( []((elevator_pos == 7) && down && !up -> X(elevator_pos == 6)) ) }
// ltl pos7_2 { X( []((elevator_pos == 7) && !down && up -> X(elevator_pos == 8)) ) }
// ltl pos7_3 { X( []((elevator_pos == 7) && (down == up) -> X(elevator_pos == 7)) ) }
// ltl pos8_1 { X( []((elevator_pos == 8) && down && !up -> X(elevator_pos == 7)) ) }
// ltl pos8_2 { X( []((elevator_pos == 8) && !down && up -> X(elevator_pos == 9)) ) }
// ltl pos8_3 { X( []((elevator_pos == 8) && (down == up) -> X(elevator_pos == 8)) ) }
// ltl pos9_1 { X( []((elevator_pos == 9) && down && !up -> X(elevator_pos == 8)) ) }
// ltl pos9_2 { X( []((elevator_pos == 9) && !down && up -> X(elevator_pos == 10)) ) }
// ltl pos9_3 { X( []((elevator_pos == 9) && (down == up) -> X(elevator_pos == 9)) ) }
// ltl pos10_1 { X( []((elevator_pos == 10) && down && !up -> X(elevator_pos == 9)) ) }
// ltl pos10_2 { X( []((elevator_pos == 10) && !down && up -> X(elevator_pos == 11)) ) }
// ltl pos10_3 { X( []((elevator_pos == 10) && (down == up) -> X(elevator_pos == 10)) ) }
// ltl pos11_1 { X( []((elevator_pos == 11) && down && !up -> X(elevator_pos == 10)) ) }
// ltl pos11_2 { X( []((elevator_pos == 11) && !down && up -> X(elevator_pos == 12)) ) }
// ltl pos11_3 { X( []((elevator_pos == 11) && (down == up) -> X(elevator_pos == 11)) ) }
// ltl pos12_1 { X( []((elevator_pos == 12) && down && !up -> X(elevator_pos == 11)) ) }
// ltl pos12_2 { X( []((elevator_pos == 12) && !down && up -> X(elevator_pos == 13)) ) }
// ltl pos12_3 { X( []((elevator_pos == 12) && (down == up) -> X(elevator_pos == 12)) ) }
// ltl pos13_1 { X( []((elevator_pos == 13) && down && !up -> X(elevator_pos == 12)) ) }
// ltl pos13_2 { X( []((elevator_pos == 13) && !down && up -> X(elevator_pos == 14)) ) }
// ltl pos13_3 { X( []((elevator_pos == 13) && (down == up) -> X(elevator_pos == 13)) ) }
// ltl pos14_1 { X( []((elevator_pos == 14) && down && !up -> X(elevator_pos == 13)) ) }
// ltl pos14_2 { X( []((elevator_pos == 14) && !down && up -> X(elevator_pos == 15)) ) }
// ltl pos14_3 { X( []((elevator_pos == 14) && (down == up) -> X(elevator_pos == 14)) ) }
// ltl pos15_1 { X( []((elevator_pos == 15) && down && !up -> X(elevator_pos == 14)) ) }
// ltl pos15_2 { X( []((elevator_pos == 15) && !(down && !up) -> X(elevator_pos == 15)) ) }

// ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
// ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 3)) ) }
// ltl floor2 { X( [] (on_floor[2] <-> (elevator_pos == 6)) ) }
// ltl floor3 { X( [] (on_floor[3] <-> (elevator_pos == 9)) ) }
// ltl floor4 { X( [] (on_floor[4] <-> (elevator_pos == 12)) ) }
// ltl floor5 { X( [] (on_floor[5] <-> (elevator_pos == 15)) ) }

// ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
// ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }
// ltl door2_open { X( []<>!(open[2] && !door_open[2]) ) }
// ltl door3_open { X( []<>!(open[3] && !door_open[3]) ) }
// ltl door4_open { X( []<>!(open[4] && !door_open[4]) ) }
// ltl door5_open { X( []<>!(open[5] && !door_open[5]) ) }

// ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
// ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }
// ltl door2_close { X( []<>!(!open[2] && !door_closed[2]) ) }
// ltl door3_close { X( []<>!(!open[3] && !door_closed[3]) ) }
// ltl door4_close { X( []<>!(!open[4] && !door_closed[4]) ) }
// ltl door5_close { X( []<>!(!open[5] && !door_closed[5]) ) }

// open-loop
// ltl no_up_and_down_MUST_BE_TRUE { X( []!(up && down) ) }

// closed-loop
// ltl no_infinite_down_MUST_BE_TRUE { X( []<>!down ) }
// ltl no_infinite_up_MUST_BE_TRUE { X( []<>!up ) }

ltl door0_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[0]) ) }
ltl door1_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[1]) ) }
ltl door2_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[2]) ) }
ltl door3_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[3]) ) }
ltl door4_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[4]) ) }
ltl door5_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_closed[5]) ) }

ltl door0_delay_2steps_MUST_BE_TRUE { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(door_open[0] && X(!door_open[0]))))) ) }
ltl door1_delay_2steps_MUST_BE_TRUE { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(door_open[1] && X(!door_open[1]))))) ) }
ltl door2_delay_2steps_MUST_BE_TRUE { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(door_open[2] && X(!door_open[2]))))) ) }
ltl door3_delay_2steps_MUST_BE_TRUE { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(door_open[3] && X(!door_open[3]))))) ) }
ltl door4_delay_2steps_MUST_BE_TRUE { X( [](!door_open[4] -> X(door_open[4] -> X(door_open[4] && X(door_open[4] && X(!door_open[4]))))) ) }
ltl door5_delay_2steps_MUST_BE_TRUE { X( [](!door_open[5] -> X(door_open[5] -> X(door_open[5] && X(door_open[5] && X(!door_open[5]))))) ) }

ltl door0_reopen_2steps_MUST_BE_TRUE { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(X(door_open[0]))))) }
ltl door1_reopen_2steps_MUST_BE_TRUE { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(X(door_open[1]))))) }
ltl door2_reopen_2steps_MUST_BE_TRUE { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(X(door_open[2]))))) }
ltl door3_reopen_2steps_MUST_BE_TRUE { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(X(door_open[3]))))) }
ltl door4_reopen_2steps_MUST_BE_TRUE { X( [](door_open[4] -> X(!door_open[4] && (user_floor_button[4] || user_cabin_button[4]) -> X(X(door_open[4]))))) }
ltl door5_reopen_2steps_MUST_BE_TRUE { X( [](door_open[5] -> X(!door_open[5] && (user_floor_button[5] || user_cabin_button[5]) -> X(X(door_open[5]))))) }

ltl floor_reached_single_call_0_MUST_BE_TRUE { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] && door_open[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5])) ) }
ltl floor_reached_single_call_1_MUST_BE_TRUE { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] && door_open[1] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5])) ) }
ltl floor_reached_single_call_2_MUST_BE_TRUE { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>(on_floor[2] && door_open[2] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5])) ) }
ltl floor_reached_single_call_3_MUST_BE_TRUE { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>(on_floor[3] && door_open[3] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5])) ) }
ltl floor_reached_single_call_4_MUST_BE_TRUE { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>(on_floor[4] && door_open[4] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[5] || user_cabin_button[5])) ) }
ltl floor_reached_single_call_5_MUST_BE_TRUE { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>(on_floor[5] && door_open[5] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4])) ) }

ltl door0_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[0]) ) }
ltl door1_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[1]) ) }
ltl door2_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[2]) ) }
ltl door3_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[3]) ) }
ltl door4_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[4]) ) }
ltl door5_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] -> door_open[5]) ) }

ltl door0_delay_1step_MUST_BE_FALSE { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(!door_open[0])))) ) }
ltl door1_delay_1step_MUST_BE_FALSE { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(!door_open[1])))) ) }
ltl door2_delay_1step_MUST_BE_FALSE { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(!door_open[2])))) ) }
ltl door3_delay_1step_MUST_BE_FALSE { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(!door_open[3])))) ) }
ltl door4_delay_1step_MUST_BE_FALSE { X( [](!door_open[4] -> X(door_open[4] -> X(door_open[4] && X(!door_open[4])))) ) }
ltl door5_delay_1step_MUST_BE_FALSE { X( [](!door_open[5] -> X(door_open[5] -> X(door_open[5] && X(!door_open[5])))) ) }

ltl door0_reopen_1step_MUST_BE_FALSE { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(door_open[0])))) }
ltl door1_reopen_1step_MUST_BE_FALSE { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(door_open[1])))) }
ltl door2_reopen_1step_MUST_BE_FALSE { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(door_open[2])))) }
ltl door3_reopen_1step_MUST_BE_FALSE { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(door_open[3])))) }
ltl door4_reopen_1step_MUST_BE_FALSE { X( [](door_open[4] -> X(!door_open[4] && (user_floor_button[4] || user_cabin_button[4]) -> X(door_open[4])))) }
ltl door5_reopen_1step_MUST_BE_FALSE { X( [](door_open[5] -> X(!door_open[5] && (user_floor_button[5] || user_cabin_button[5]) -> X(door_open[5])))) }

ltl floor_reached_multiple_calls_0_MUST_BE_FALSE { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0] && door_open[0]) ) }
ltl floor_reached_multiple_calls_1_MUST_BE_FALSE { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1] && door_open[1]) ) }
ltl floor_reached_multiple_calls_2_MUST_BE_FALSE { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>on_floor[2] && door_open[2]) ) }
ltl floor_reached_multiple_calls_3_MUST_BE_FALSE { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>on_floor[3] && door_open[3]) ) }
ltl floor_reached_multiple_calls_4_MUST_BE_FALSE { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>on_floor[4] && door_open[4]) ) }
ltl floor_reached_multiple_calls_5_MUST_BE_FALSE { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>on_floor[5] && door_open[5]) ) }
