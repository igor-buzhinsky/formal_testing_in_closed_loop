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
ltl pos6_2 { X( []((elevator_pos == 6) && !down && up -> X(elevator_pos == 7)) ) }
ltl pos6_3 { X( []((elevator_pos == 6) && (down == up) -> X(elevator_pos == 6)) ) }
ltl pos7_1 { X( []((elevator_pos == 7) && down && !up -> X(elevator_pos == 6)) ) }
ltl pos7_2 { X( []((elevator_pos == 7) && !down && up -> X(elevator_pos == 8)) ) }
ltl pos7_3 { X( []((elevator_pos == 7) && (down == up) -> X(elevator_pos == 7)) ) }
ltl pos8_1 { X( []((elevator_pos == 8) && down && !up -> X(elevator_pos == 7)) ) }
ltl pos8_2 { X( []((elevator_pos == 8) && !down && up -> X(elevator_pos == 9)) ) }
ltl pos8_3 { X( []((elevator_pos == 8) && (down == up) -> X(elevator_pos == 8)) ) }
ltl pos9_1 { X( []((elevator_pos == 9) && down && !up -> X(elevator_pos == 8)) ) }
ltl pos9_2 { X( []((elevator_pos == 9) && !down && up -> X(elevator_pos == 10)) ) }
ltl pos9_3 { X( []((elevator_pos == 9) && (down == up) -> X(elevator_pos == 9)) ) }
ltl pos10_1 { X( []((elevator_pos == 10) && down && !up -> X(elevator_pos == 9)) ) }
ltl pos10_2 { X( []((elevator_pos == 10) && !down && up -> X(elevator_pos == 11)) ) }
ltl pos10_3 { X( []((elevator_pos == 10) && (down == up) -> X(elevator_pos == 10)) ) }
ltl pos11_1 { X( []((elevator_pos == 11) && down && !up -> X(elevator_pos == 10)) ) }
ltl pos11_2 { X( []((elevator_pos == 11) && !down && up -> X(elevator_pos == 12)) ) }
ltl pos11_3 { X( []((elevator_pos == 11) && (down == up) -> X(elevator_pos == 11)) ) }
ltl pos12_1 { X( []((elevator_pos == 12) && down && !up -> X(elevator_pos == 11)) ) }
ltl pos12_2 { X( []((elevator_pos == 12) && !down && up -> X(elevator_pos == 13)) ) }
ltl pos12_3 { X( []((elevator_pos == 12) && (down == up) -> X(elevator_pos == 12)) ) }
ltl pos13_1 { X( []((elevator_pos == 13) && down && !up -> X(elevator_pos == 12)) ) }
ltl pos13_2 { X( []((elevator_pos == 13) && !down && up -> X(elevator_pos == 14)) ) }
ltl pos13_3 { X( []((elevator_pos == 13) && (down == up) -> X(elevator_pos == 13)) ) }
ltl pos14_1 { X( []((elevator_pos == 14) && down && !up -> X(elevator_pos == 13)) ) }
ltl pos14_2 { X( []((elevator_pos == 14) && !down && up -> X(elevator_pos == 15)) ) }
ltl pos14_3 { X( []((elevator_pos == 14) && (down == up) -> X(elevator_pos == 14)) ) }
ltl pos15_1 { X( []((elevator_pos == 15) && down && !up -> X(elevator_pos == 14)) ) }
ltl pos15_2 { X( []((elevator_pos == 15) && !down && up -> X(elevator_pos == 16)) ) }
ltl pos15_3 { X( []((elevator_pos == 15) && (down == up) -> X(elevator_pos == 15)) ) }
ltl pos16_1 { X( []((elevator_pos == 16) && down && !up -> X(elevator_pos == 15)) ) }
ltl pos16_2 { X( []((elevator_pos == 16) && !down && up -> X(elevator_pos == 17)) ) }
ltl pos16_3 { X( []((elevator_pos == 16) && (down == up) -> X(elevator_pos == 16)) ) }
ltl pos17_1 { X( []((elevator_pos == 17) && down && !up -> X(elevator_pos == 16)) ) }
ltl pos17_2 { X( []((elevator_pos == 17) && !down && up -> X(elevator_pos == 18)) ) }
ltl pos17_3 { X( []((elevator_pos == 17) && (down == up) -> X(elevator_pos == 17)) ) }
ltl pos18_1 { X( []((elevator_pos == 18) && down && !up -> X(elevator_pos == 17)) ) }
ltl pos18_2 { X( []((elevator_pos == 18) && !down && up -> X(elevator_pos == 19)) ) }
ltl pos18_3 { X( []((elevator_pos == 18) && (down == up) -> X(elevator_pos == 18)) ) }
ltl pos19_1 { X( []((elevator_pos == 19) && down && !up -> X(elevator_pos == 18)) ) }
ltl pos19_2 { X( []((elevator_pos == 19) && !down && up -> X(elevator_pos == 20)) ) }
ltl pos19_3 { X( []((elevator_pos == 19) && (down == up) -> X(elevator_pos == 19)) ) }
ltl pos20_1 { X( []((elevator_pos == 20) && down && !up -> X(elevator_pos == 19)) ) }
ltl pos20_2 { X( []((elevator_pos == 20) && !down && up -> X(elevator_pos == 21)) ) }
ltl pos20_3 { X( []((elevator_pos == 20) && (down == up) -> X(elevator_pos == 20)) ) }
ltl pos21_1 { X( []((elevator_pos == 21) && down && !up -> X(elevator_pos == 20)) ) }
ltl pos21_2 { X( []((elevator_pos == 21) && !down && up -> X(elevator_pos == 22)) ) }
ltl pos21_3 { X( []((elevator_pos == 21) && (down == up) -> X(elevator_pos == 21)) ) }
ltl pos22_1 { X( []((elevator_pos == 22) && down && !up -> X(elevator_pos == 21)) ) }
ltl pos22_2 { X( []((elevator_pos == 22) && !down && up -> X(elevator_pos == 22)) ) }
ltl pos22_3 { X( []((elevator_pos == 22) && (down == up) -> X(elevator_pos == 22)) ) }

ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 2)) ) }
ltl floor2 { X( [] (on_floor[2] <-> (elevator_pos == 4)) ) }
ltl floor3 { X( [] (on_floor[3] <-> (elevator_pos == 6)) ) }
ltl floor4 { X( [] (on_floor[4] <-> (elevator_pos == 8)) ) }
ltl floor5 { X( [] (on_floor[5] <-> (elevator_pos == 10)) ) }
ltl floor6 { X( [] (on_floor[6] <-> (elevator_pos == 12)) ) }
ltl floor7 { X( [] (on_floor[7] <-> (elevator_pos == 14)) ) }
ltl floor8 { X( [] (on_floor[8] <-> (elevator_pos == 16)) ) }
ltl floor9 { X( [] (on_floor[9] <-> (elevator_pos == 18)) ) }
ltl floor10 { X( [] (on_floor[10] <-> (elevator_pos == 20)) ) }
ltl floor11 { X( [] (on_floor[11] <-> (elevator_pos == 22)) ) }

ltl door0_open { X( []<>!(open[0] && !door_open[0]) ) }
ltl door1_open { X( []<>!(open[1] && !door_open[1]) ) }
ltl door2_open { X( []<>!(open[2] && !door_open[2]) ) }
ltl door3_open { X( []<>!(open[3] && !door_open[3]) ) }
ltl door4_open { X( []<>!(open[4] && !door_open[4]) ) }
ltl door5_open { X( []<>!(open[5] && !door_open[5]) ) }
ltl door6_open { X( []<>!(open[6] && !door_open[6]) ) }
ltl door7_open { X( []<>!(open[7] && !door_open[7]) ) }
ltl door8_open { X( []<>!(open[8] && !door_open[8]) ) }
ltl door9_open { X( []<>!(open[9] && !door_open[9]) ) }
ltl door10_open { X( []<>!(open[10] && !door_open[10]) ) }
ltl door11_open { X( []<>!(open[11] && !door_open[11]) ) }

ltl door0_close { X( []<>!(!open[0] && !door_closed[0]) ) }
ltl door1_close { X( []<>!(!open[1] && !door_closed[1]) ) }
ltl door2_close { X( []<>!(!open[2] && !door_closed[2]) ) }
ltl door3_close { X( []<>!(!open[3] && !door_closed[3]) ) }
ltl door4_close { X( []<>!(!open[4] && !door_closed[4]) ) }
ltl door5_close { X( []<>!(!open[5] && !door_closed[5]) ) }
ltl door6_close { X( []<>!(!open[6] && !door_closed[6]) ) }
ltl door7_close { X( []<>!(!open[7] && !door_closed[7]) ) }
ltl door8_close { X( []<>!(!open[8] && !door_closed[8]) ) }
ltl door9_close { X( []<>!(!open[9] && !door_closed[9]) ) }
ltl door10_close { X( []<>!(!open[10] && !door_closed[10]) ) }
ltl door11_close { X( []<>!(!open[11] && !door_closed[11]) ) }

// open-loop
ltl phi06 { X( []!(up && down) ) }

// closed-loop
ltl phi04_1 { X( []<>!down ) }
ltl phi04_2 { X( []<>!up ) }
ltl phi15 { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] && !on_floor[6] && !on_floor[7] && !on_floor[8] && !on_floor[9] && !on_floor[10] && !on_floor[11] -> door_closed[0] && door_closed[1] && door_closed[2] && door_closed[3] && door_closed[4] && door_closed[5] && door_closed[6] && door_closed[7] && door_closed[8] && door_closed[9] && door_closed[10] && door_closed[11]) ) }

ltl cl0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>(on_floor[2] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>(on_floor[3] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl4 { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>(on_floor[4] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl5 { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>(on_floor[5] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl6 { X( []((user_floor_button[6] || user_cabin_button[6]) -> <>(on_floor[6] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl7 { X( []((user_floor_button[7] || user_cabin_button[7]) -> <>(on_floor[7] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl8 { X( []((user_floor_button[8] || user_cabin_button[8]) -> <>(on_floor[8] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl9 { X( []((user_floor_button[9] || user_cabin_button[9]) -> <>(on_floor[9] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl10 { X( []((user_floor_button[10] || user_cabin_button[10]) -> <>(on_floor[10] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[11] || user_cabin_button[11])) ) }
ltl cl11 { X( []((user_floor_button[11] || user_cabin_button[11]) -> <>(on_floor[11] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10])) ) }

ltl phi11_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0]) ) } // false
ltl phi11_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1]) ) } // false
ltl phi11_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>on_floor[2]) ) } // false
ltl phi11_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>on_floor[3]) ) } // false
ltl phi11_4 { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>on_floor[4]) ) } // false
ltl phi11_5 { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>on_floor[5]) ) } // false
ltl phi11_6 { X( []((user_floor_button[6] || user_cabin_button[6]) -> <>on_floor[6]) ) } // false
ltl phi11_7 { X( []((user_floor_button[7] || user_cabin_button[7]) -> <>on_floor[7]) ) } // false
ltl phi11_8 { X( []((user_floor_button[8] || user_cabin_button[8]) -> <>on_floor[8]) ) } // false
ltl phi11_9 { X( []((user_floor_button[9] || user_cabin_button[9]) -> <>on_floor[9]) ) } // false
ltl phi11_10 { X( []((user_floor_button[10] || user_cabin_button[10]) -> <>on_floor[10]) ) } // false
ltl phi11_11 { X( []((user_floor_button[11] || user_cabin_button[11]) -> <>on_floor[11]) ) } // false