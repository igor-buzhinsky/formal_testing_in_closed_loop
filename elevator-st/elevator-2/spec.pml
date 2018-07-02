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
// ltl pos3_2 { X( []((elevator_pos == 3) && !(down && !up) -> X(elevator_pos == 3)) ) }

// ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
// ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 3)) ) }

// ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
// ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }

// ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
// ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }

// open-loop
// ltl no_up_and_down_MUST_BE_TRUE { X( []!(up && down) ) }

// closed-loop
// ltl no_infinite_down_MUST_BE_TRUE { X( []<>!down ) }
// ltl no_infinite_up_MUST_BE_TRUE { X( []<>!up ) }

ltl door0_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] -> door_closed[0]) ) }
ltl door1_closed_when_between_floors_MUST_BE_TRUE { X( [](!on_floor[0] && !on_floor[1] -> door_closed[1]) ) }

ltl door0_delay_2steps_MUST_BE_TRUE { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(door_open[0] && X(!door_open[0]))))) ) }
ltl door1_delay_2steps_MUST_BE_TRUE { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(door_open[1] && X(!door_open[1]))))) ) }

ltl door0_reopen_2steps_MUST_BE_TRUE { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(X(door_open[0]))))) }
ltl door1_reopen_2steps_MUST_BE_TRUE { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(X(door_open[1]))))) }

ltl floor_reached_single_call_0_MUST_BE_TRUE { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] && door_open[0] || user_floor_button[1] || user_cabin_button[1])) ) }
ltl floor_reached_single_call_1_MUST_BE_TRUE { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] && door_open[1] || user_floor_button[0] || user_cabin_button[0])) ) }

ltl door0_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] -> door_open[0]) ) }
ltl door1_open_when_between_floors_MUST_BE_FALSE { X( [](!on_floor[0] && !on_floor[1] -> door_open[1]) ) }

ltl door0_delay_1step_MUST_BE_FALSE { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(!door_open[0])))) ) }
ltl door1_delay_1step_MUST_BE_FALSE { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(!door_open[1])))) ) }

ltl door0_reopen_1step_MUST_BE_FALSE { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(door_open[0])))) }
ltl door1_reopen_1step_MUST_BE_FALSE { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(door_open[1])))) }

ltl floor_reached_multiple_calls_0_MUST_BE_FALSE { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0] && door_open[0]) ) }
ltl floor_reached_multiple_calls_1_MUST_BE_FALSE { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1] && door_open[1]) ) }
