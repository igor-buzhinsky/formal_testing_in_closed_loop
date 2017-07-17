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
ltl pos22_2 { X( []((elevator_pos == 22) && !down && up -> X(elevator_pos == 23)) ) }
ltl pos22_3 { X( []((elevator_pos == 22) && (down == up) -> X(elevator_pos == 22)) ) }
ltl pos23_1 { X( []((elevator_pos == 23) && down && !up -> X(elevator_pos == 22)) ) }
ltl pos23_2 { X( []((elevator_pos == 23) && !down && up -> X(elevator_pos == 24)) ) }
ltl pos23_3 { X( []((elevator_pos == 23) && (down == up) -> X(elevator_pos == 23)) ) }
ltl pos24_1 { X( []((elevator_pos == 24) && down && !up -> X(elevator_pos == 23)) ) }
ltl pos24_2 { X( []((elevator_pos == 24) && !down && up -> X(elevator_pos == 25)) ) }
ltl pos24_3 { X( []((elevator_pos == 24) && (down == up) -> X(elevator_pos == 24)) ) }
ltl pos25_1 { X( []((elevator_pos == 25) && down && !up -> X(elevator_pos == 24)) ) }
ltl pos25_2 { X( []((elevator_pos == 25) && !down && up -> X(elevator_pos == 26)) ) }
ltl pos25_3 { X( []((elevator_pos == 25) && (down == up) -> X(elevator_pos == 25)) ) }
ltl pos26_1 { X( []((elevator_pos == 26) && down && !up -> X(elevator_pos == 25)) ) }
ltl pos26_2 { X( []((elevator_pos == 26) && !down && up -> X(elevator_pos == 27)) ) }
ltl pos26_3 { X( []((elevator_pos == 26) && (down == up) -> X(elevator_pos == 26)) ) }
ltl pos27_1 { X( []((elevator_pos == 27) && down && !up -> X(elevator_pos == 26)) ) }
ltl pos27_2 { X( []((elevator_pos == 27) && !down && up -> X(elevator_pos == 28)) ) }
ltl pos27_3 { X( []((elevator_pos == 27) && (down == up) -> X(elevator_pos == 27)) ) }
ltl pos28_1 { X( []((elevator_pos == 28) && down && !up -> X(elevator_pos == 27)) ) }
ltl pos28_2 { X( []((elevator_pos == 28) && !down && up -> X(elevator_pos == 29)) ) }
ltl pos28_3 { X( []((elevator_pos == 28) && (down == up) -> X(elevator_pos == 28)) ) }
ltl pos29_1 { X( []((elevator_pos == 29) && down && !up -> X(elevator_pos == 28)) ) }
ltl pos29_2 { X( []((elevator_pos == 29) && !down && up -> X(elevator_pos == 30)) ) }
ltl pos29_3 { X( []((elevator_pos == 29) && (down == up) -> X(elevator_pos == 29)) ) }
ltl pos30_1 { X( []((elevator_pos == 30) && down && !up -> X(elevator_pos == 29)) ) }
ltl pos30_2 { X( []((elevator_pos == 30) && !down && up -> X(elevator_pos == 31)) ) }
ltl pos30_3 { X( []((elevator_pos == 30) && (down == up) -> X(elevator_pos == 30)) ) }
ltl pos31_1 { X( []((elevator_pos == 31) && down && !up -> X(elevator_pos == 30)) ) }
ltl pos31_2 { X( []((elevator_pos == 31) && !down && up -> X(elevator_pos == 32)) ) }
ltl pos31_3 { X( []((elevator_pos == 31) && (down == up) -> X(elevator_pos == 31)) ) }
ltl pos32_1 { X( []((elevator_pos == 32) && down && !up -> X(elevator_pos == 31)) ) }
ltl pos32_2 { X( []((elevator_pos == 32) && !down && up -> X(elevator_pos == 33)) ) }
ltl pos32_3 { X( []((elevator_pos == 32) && (down == up) -> X(elevator_pos == 32)) ) }
ltl pos33_1 { X( []((elevator_pos == 33) && down && !up -> X(elevator_pos == 32)) ) }
ltl pos33_2 { X( []((elevator_pos == 33) && !down && up -> X(elevator_pos == 34)) ) }
ltl pos33_3 { X( []((elevator_pos == 33) && (down == up) -> X(elevator_pos == 33)) ) }
ltl pos34_1 { X( []((elevator_pos == 34) && down && !up -> X(elevator_pos == 33)) ) }
ltl pos34_2 { X( []((elevator_pos == 34) && !down && up -> X(elevator_pos == 35)) ) }
ltl pos34_3 { X( []((elevator_pos == 34) && (down == up) -> X(elevator_pos == 34)) ) }
ltl pos35_1 { X( []((elevator_pos == 35) && down && !up -> X(elevator_pos == 34)) ) }
ltl pos35_2 { X( []((elevator_pos == 35) && !down && up -> X(elevator_pos == 36)) ) }
ltl pos35_3 { X( []((elevator_pos == 35) && (down == up) -> X(elevator_pos == 35)) ) }
ltl pos36_1 { X( []((elevator_pos == 36) && down && !up -> X(elevator_pos == 35)) ) }
ltl pos36_2 { X( []((elevator_pos == 36) && !down && up -> X(elevator_pos == 37)) ) }
ltl pos36_3 { X( []((elevator_pos == 36) && (down == up) -> X(elevator_pos == 36)) ) }
ltl pos37_1 { X( []((elevator_pos == 37) && down && !up -> X(elevator_pos == 36)) ) }
ltl pos37_2 { X( []((elevator_pos == 37) && !down && up -> X(elevator_pos == 38)) ) }
ltl pos37_3 { X( []((elevator_pos == 37) && (down == up) -> X(elevator_pos == 37)) ) }
ltl pos38_1 { X( []((elevator_pos == 38) && down && !up -> X(elevator_pos == 37)) ) }
ltl pos38_2 { X( []((elevator_pos == 38) && !down && up -> X(elevator_pos == 39)) ) }
ltl pos38_3 { X( []((elevator_pos == 38) && (down == up) -> X(elevator_pos == 38)) ) }
ltl pos39_1 { X( []((elevator_pos == 39) && down && !up -> X(elevator_pos == 38)) ) }
ltl pos39_2 { X( []((elevator_pos == 39) && !down && up -> X(elevator_pos == 40)) ) }
ltl pos39_3 { X( []((elevator_pos == 39) && (down == up) -> X(elevator_pos == 39)) ) }
ltl pos40_1 { X( []((elevator_pos == 40) && down && !up -> X(elevator_pos == 39)) ) }
ltl pos40_2 { X( []((elevator_pos == 40) && !down && up -> X(elevator_pos == 41)) ) }
ltl pos40_3 { X( []((elevator_pos == 40) && (down == up) -> X(elevator_pos == 40)) ) }
ltl pos41_1 { X( []((elevator_pos == 41) && down && !up -> X(elevator_pos == 40)) ) }
ltl pos41_2 { X( []((elevator_pos == 41) && !down && up -> X(elevator_pos == 42)) ) }
ltl pos41_3 { X( []((elevator_pos == 41) && (down == up) -> X(elevator_pos == 41)) ) }
ltl pos42_1 { X( []((elevator_pos == 42) && down && !up -> X(elevator_pos == 41)) ) }
ltl pos42_2 { X( []((elevator_pos == 42) && !down && up -> X(elevator_pos == 43)) ) }
ltl pos42_3 { X( []((elevator_pos == 42) && (down == up) -> X(elevator_pos == 42)) ) }
ltl pos43_1 { X( []((elevator_pos == 43) && down && !up -> X(elevator_pos == 42)) ) }
ltl pos43_2 { X( []((elevator_pos == 43) && !down && up -> X(elevator_pos == 44)) ) }
ltl pos43_3 { X( []((elevator_pos == 43) && (down == up) -> X(elevator_pos == 43)) ) }
ltl pos44_1 { X( []((elevator_pos == 44) && down && !up -> X(elevator_pos == 43)) ) }
ltl pos44_2 { X( []((elevator_pos == 44) && !down && up -> X(elevator_pos == 45)) ) }
ltl pos44_3 { X( []((elevator_pos == 44) && (down == up) -> X(elevator_pos == 44)) ) }
ltl pos45_1 { X( []((elevator_pos == 45) && down && !up -> X(elevator_pos == 44)) ) }
ltl pos45_2 { X( []((elevator_pos == 45) && !(down && !up) -> X(elevator_pos == 45)) ) }

ltl floor0 { X( [] (on_floor[0] <-> (elevator_pos == 0)) ) }
ltl floor1 { X( [] (on_floor[1] <-> (elevator_pos == 3)) ) }
ltl floor2 { X( [] (on_floor[2] <-> (elevator_pos == 6)) ) }
ltl floor3 { X( [] (on_floor[3] <-> (elevator_pos == 9)) ) }
ltl floor4 { X( [] (on_floor[4] <-> (elevator_pos == 12)) ) }
ltl floor5 { X( [] (on_floor[5] <-> (elevator_pos == 15)) ) }
ltl floor6 { X( [] (on_floor[6] <-> (elevator_pos == 18)) ) }
ltl floor7 { X( [] (on_floor[7] <-> (elevator_pos == 21)) ) }
ltl floor8 { X( [] (on_floor[8] <-> (elevator_pos == 24)) ) }
ltl floor9 { X( [] (on_floor[9] <-> (elevator_pos == 27)) ) }
ltl floor10 { X( [] (on_floor[10] <-> (elevator_pos == 30)) ) }
ltl floor11 { X( [] (on_floor[11] <-> (elevator_pos == 33)) ) }
ltl floor12 { X( [] (on_floor[12] <-> (elevator_pos == 36)) ) }
ltl floor13 { X( [] (on_floor[13] <-> (elevator_pos == 39)) ) }
ltl floor14 { X( [] (on_floor[14] <-> (elevator_pos == 42)) ) }
ltl floor15 { X( [] (on_floor[15] <-> (elevator_pos == 45)) ) }

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
ltl door12_open { X( []<>!(open[12] && !door_open[12]) ) }
ltl door13_open { X( []<>!(open[13] && !door_open[13]) ) }
ltl door14_open { X( []<>!(open[14] && !door_open[14]) ) }
ltl door15_open { X( []<>!(open[15] && !door_open[15]) ) }

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
ltl door12_close { X( []<>!(!open[12] && !door_closed[12]) ) }
ltl door13_close { X( []<>!(!open[13] && !door_closed[13]) ) }
ltl door14_close { X( []<>!(!open[14] && !door_closed[14]) ) }
ltl door15_close { X( []<>!(!open[15] && !door_closed[15]) ) }

ltl door0_delay_1step { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(!door_open[0])))) ) } // false
ltl door1_delay_1step { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(!door_open[1])))) ) } // false
ltl door2_delay_1step { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(!door_open[2])))) ) } // false
ltl door3_delay_1step { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(!door_open[3])))) ) } // false
ltl door4_delay_1step { X( [](!door_open[4] -> X(door_open[4] -> X(door_open[4] && X(!door_open[4])))) ) } // false
ltl door5_delay_1step { X( [](!door_open[5] -> X(door_open[5] -> X(door_open[5] && X(!door_open[5])))) ) } // false
ltl door6_delay_1step { X( [](!door_open[6] -> X(door_open[6] -> X(door_open[6] && X(!door_open[6])))) ) } // false
ltl door7_delay_1step { X( [](!door_open[7] -> X(door_open[7] -> X(door_open[7] && X(!door_open[7])))) ) } // false
ltl door8_delay_1step { X( [](!door_open[8] -> X(door_open[8] -> X(door_open[8] && X(!door_open[8])))) ) } // false
ltl door9_delay_1step { X( [](!door_open[9] -> X(door_open[9] -> X(door_open[9] && X(!door_open[9])))) ) } // false
ltl door10_delay_1step { X( [](!door_open[10] -> X(door_open[10] -> X(door_open[10] && X(!door_open[10])))) ) } // false
ltl door11_delay_1step { X( [](!door_open[11] -> X(door_open[11] -> X(door_open[11] && X(!door_open[11])))) ) } // false
ltl door12_delay_1step { X( [](!door_open[12] -> X(door_open[12] -> X(door_open[12] && X(!door_open[12])))) ) } // false
ltl door13_delay_1step { X( [](!door_open[13] -> X(door_open[13] -> X(door_open[13] && X(!door_open[13])))) ) } // false
ltl door14_delay_1step { X( [](!door_open[14] -> X(door_open[14] -> X(door_open[14] && X(!door_open[14])))) ) } // false
ltl door15_delay_1step { X( [](!door_open[15] -> X(door_open[15] -> X(door_open[15] && X(!door_open[15])))) ) } // false

ltl door0_reopen_1step { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(door_open[0])))) } // false
ltl door1_reopen_1step { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(door_open[1])))) } // false
ltl door2_reopen_1step { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(door_open[2])))) } // false
ltl door3_reopen_1step { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(door_open[3])))) } // false
ltl door4_reopen_1step { X( [](door_open[4] -> X(!door_open[4] && (user_floor_button[4] || user_cabin_button[4]) -> X(door_open[4])))) } // false
ltl door5_reopen_1step { X( [](door_open[5] -> X(!door_open[5] && (user_floor_button[5] || user_cabin_button[5]) -> X(door_open[5])))) } // false
ltl door6_reopen_1step { X( [](door_open[6] -> X(!door_open[6] && (user_floor_button[6] || user_cabin_button[6]) -> X(door_open[6])))) } // false
ltl door7_reopen_1step { X( [](door_open[7] -> X(!door_open[7] && (user_floor_button[7] || user_cabin_button[7]) -> X(door_open[7])))) } // false
ltl door8_reopen_1step { X( [](door_open[8] -> X(!door_open[8] && (user_floor_button[8] || user_cabin_button[8]) -> X(door_open[8])))) } // false
ltl door9_reopen_1step { X( [](door_open[9] -> X(!door_open[9] && (user_floor_button[9] || user_cabin_button[9]) -> X(door_open[9])))) } // false
ltl door10_reopen_1step { X( [](door_open[10] -> X(!door_open[10] && (user_floor_button[10] || user_cabin_button[10]) -> X(door_open[10])))) } // false
ltl door11_reopen_1step { X( [](door_open[11] -> X(!door_open[11] && (user_floor_button[11] || user_cabin_button[11]) -> X(door_open[11])))) } // false
ltl door12_reopen_1step { X( [](door_open[12] -> X(!door_open[12] && (user_floor_button[12] || user_cabin_button[12]) -> X(door_open[12])))) } // false
ltl door13_reopen_1step { X( [](door_open[13] -> X(!door_open[13] && (user_floor_button[13] || user_cabin_button[13]) -> X(door_open[13])))) } // false
ltl door14_reopen_1step { X( [](door_open[14] -> X(!door_open[14] && (user_floor_button[14] || user_cabin_button[14]) -> X(door_open[14])))) } // false
ltl door15_reopen_1step { X( [](door_open[15] -> X(!door_open[15] && (user_floor_button[15] || user_cabin_button[15]) -> X(door_open[15])))) } // false

ltl door0_delay_2steps { X( [](!door_open[0] -> X(door_open[0] -> X(door_open[0] && X(door_open[0] && X(!door_open[0]))))) ) }
ltl door1_delay_2steps { X( [](!door_open[1] -> X(door_open[1] -> X(door_open[1] && X(door_open[1] && X(!door_open[1]))))) ) }
ltl door2_delay_2steps { X( [](!door_open[2] -> X(door_open[2] -> X(door_open[2] && X(door_open[2] && X(!door_open[2]))))) ) }
ltl door3_delay_2steps { X( [](!door_open[3] -> X(door_open[3] -> X(door_open[3] && X(door_open[3] && X(!door_open[3]))))) ) }
ltl door4_delay_2steps { X( [](!door_open[4] -> X(door_open[4] -> X(door_open[4] && X(door_open[4] && X(!door_open[4]))))) ) }
ltl door5_delay_2steps { X( [](!door_open[5] -> X(door_open[5] -> X(door_open[5] && X(door_open[5] && X(!door_open[5]))))) ) }
ltl door6_delay_2steps { X( [](!door_open[6] -> X(door_open[6] -> X(door_open[6] && X(door_open[6] && X(!door_open[6]))))) ) }
ltl door7_delay_2steps { X( [](!door_open[7] -> X(door_open[7] -> X(door_open[7] && X(door_open[7] && X(!door_open[7]))))) ) }
ltl door8_delay_2steps { X( [](!door_open[8] -> X(door_open[8] -> X(door_open[8] && X(door_open[8] && X(!door_open[8]))))) ) }
ltl door9_delay_2steps { X( [](!door_open[9] -> X(door_open[9] -> X(door_open[9] && X(door_open[9] && X(!door_open[9]))))) ) }
ltl door10_delay_2steps { X( [](!door_open[10] -> X(door_open[10] -> X(door_open[10] && X(door_open[10] && X(!door_open[10]))))) ) }
ltl door11_delay_2steps { X( [](!door_open[11] -> X(door_open[11] -> X(door_open[11] && X(door_open[11] && X(!door_open[11]))))) ) }
ltl door12_delay_2steps { X( [](!door_open[12] -> X(door_open[12] -> X(door_open[12] && X(door_open[12] && X(!door_open[12]))))) ) }
ltl door13_delay_2steps { X( [](!door_open[13] -> X(door_open[13] -> X(door_open[13] && X(door_open[13] && X(!door_open[13]))))) ) }
ltl door14_delay_2steps { X( [](!door_open[14] -> X(door_open[14] -> X(door_open[14] && X(door_open[14] && X(!door_open[14]))))) ) }
ltl door15_delay_2steps { X( [](!door_open[15] -> X(door_open[15] -> X(door_open[15] && X(door_open[15] && X(!door_open[15]))))) ) }

ltl door0_reopen_2steps { X( [](door_open[0] -> X(!door_open[0] && (user_floor_button[0] || user_cabin_button[0]) -> X(X(door_open[0]))))) }
ltl door1_reopen_2steps { X( [](door_open[1] -> X(!door_open[1] && (user_floor_button[1] || user_cabin_button[1]) -> X(X(door_open[1]))))) }
ltl door2_reopen_2steps { X( [](door_open[2] -> X(!door_open[2] && (user_floor_button[2] || user_cabin_button[2]) -> X(X(door_open[2]))))) }
ltl door3_reopen_2steps { X( [](door_open[3] -> X(!door_open[3] && (user_floor_button[3] || user_cabin_button[3]) -> X(X(door_open[3]))))) }
ltl door4_reopen_2steps { X( [](door_open[4] -> X(!door_open[4] && (user_floor_button[4] || user_cabin_button[4]) -> X(X(door_open[4]))))) }
ltl door5_reopen_2steps { X( [](door_open[5] -> X(!door_open[5] && (user_floor_button[5] || user_cabin_button[5]) -> X(X(door_open[5]))))) }
ltl door6_reopen_2steps { X( [](door_open[6] -> X(!door_open[6] && (user_floor_button[6] || user_cabin_button[6]) -> X(X(door_open[6]))))) }
ltl door7_reopen_2steps { X( [](door_open[7] -> X(!door_open[7] && (user_floor_button[7] || user_cabin_button[7]) -> X(X(door_open[7]))))) }
ltl door8_reopen_2steps { X( [](door_open[8] -> X(!door_open[8] && (user_floor_button[8] || user_cabin_button[8]) -> X(X(door_open[8]))))) }
ltl door9_reopen_2steps { X( [](door_open[9] -> X(!door_open[9] && (user_floor_button[9] || user_cabin_button[9]) -> X(X(door_open[9]))))) }
ltl door10_reopen_2steps { X( [](door_open[10] -> X(!door_open[10] && (user_floor_button[10] || user_cabin_button[10]) -> X(X(door_open[10]))))) }
ltl door11_reopen_2steps { X( [](door_open[11] -> X(!door_open[11] && (user_floor_button[11] || user_cabin_button[11]) -> X(X(door_open[11]))))) }
ltl door12_reopen_2steps { X( [](door_open[12] -> X(!door_open[12] && (user_floor_button[12] || user_cabin_button[12]) -> X(X(door_open[12]))))) }
ltl door13_reopen_2steps { X( [](door_open[13] -> X(!door_open[13] && (user_floor_button[13] || user_cabin_button[13]) -> X(X(door_open[13]))))) }
ltl door14_reopen_2steps { X( [](door_open[14] -> X(!door_open[14] && (user_floor_button[14] || user_cabin_button[14]) -> X(X(door_open[14]))))) }
ltl door15_reopen_2steps { X( [](door_open[15] -> X(!door_open[15] && (user_floor_button[15] || user_cabin_button[15]) -> X(X(door_open[15]))))) }

// open-loop
ltl no_up_and_down { X( []!(up && down) ) }

// closed-loop
ltl no_infinite_down { X( []<>!down ) }
ltl no_infinite_up { X( []<>!up ) }
ltl doors_closed_when_between_floors { X( [](!on_floor[0] && !on_floor[1] && !on_floor[2] && !on_floor[3] && !on_floor[4] && !on_floor[5] && !on_floor[6] && !on_floor[7] && !on_floor[8] && !on_floor[9] && !on_floor[10] && !on_floor[11] && !on_floor[12] && !on_floor[13] && !on_floor[14] && !on_floor[15] -> door_closed[0] && door_closed[1] && door_closed[2] && door_closed[3] && door_closed[4] && door_closed[5] && door_closed[6] && door_closed[7] && door_closed[8] && door_closed[9] && door_closed[10] && door_closed[11] && door_closed[12] && door_closed[13] && door_closed[14] && door_closed[15]) ) }

ltl floor_reached_single_call_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>(on_floor[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>(on_floor[1] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>(on_floor[2] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>(on_floor[3] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_4 { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>(on_floor[4] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_5 { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>(on_floor[5] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_6 { X( []((user_floor_button[6] || user_cabin_button[6]) -> <>(on_floor[6] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_7 { X( []((user_floor_button[7] || user_cabin_button[7]) -> <>(on_floor[7] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_8 { X( []((user_floor_button[8] || user_cabin_button[8]) -> <>(on_floor[8] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_9 { X( []((user_floor_button[9] || user_cabin_button[9]) -> <>(on_floor[9] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_10 { X( []((user_floor_button[10] || user_cabin_button[10]) -> <>(on_floor[10] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_11 { X( []((user_floor_button[11] || user_cabin_button[11]) -> <>(on_floor[11] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_12 { X( []((user_floor_button[12] || user_cabin_button[12]) -> <>(on_floor[12] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_13 { X( []((user_floor_button[13] || user_cabin_button[13]) -> <>(on_floor[13] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[14] || user_cabin_button[14] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_14 { X( []((user_floor_button[14] || user_cabin_button[14]) -> <>(on_floor[14] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[15] || user_cabin_button[15])) ) }
ltl floor_reached_single_call_15 { X( []((user_floor_button[15] || user_cabin_button[15]) -> <>(on_floor[15] || user_floor_button[0] || user_cabin_button[0] || user_floor_button[1] || user_cabin_button[1] || user_floor_button[2] || user_cabin_button[2] || user_floor_button[3] || user_cabin_button[3] || user_floor_button[4] || user_cabin_button[4] || user_floor_button[5] || user_cabin_button[5] || user_floor_button[6] || user_cabin_button[6] || user_floor_button[7] || user_cabin_button[7] || user_floor_button[8] || user_cabin_button[8] || user_floor_button[9] || user_cabin_button[9] || user_floor_button[10] || user_cabin_button[10] || user_floor_button[11] || user_cabin_button[11] || user_floor_button[12] || user_cabin_button[12] || user_floor_button[13] || user_cabin_button[13] || user_floor_button[14] || user_cabin_button[14])) ) }

ltl floor_reached_multiple_calls_0 { X( []((user_floor_button[0] || user_cabin_button[0]) -> <>on_floor[0]) ) } // false
ltl floor_reached_multiple_calls_1 { X( []((user_floor_button[1] || user_cabin_button[1]) -> <>on_floor[1]) ) } // false
ltl floor_reached_multiple_calls_2 { X( []((user_floor_button[2] || user_cabin_button[2]) -> <>on_floor[2]) ) } // false
ltl floor_reached_multiple_calls_3 { X( []((user_floor_button[3] || user_cabin_button[3]) -> <>on_floor[3]) ) } // false
ltl floor_reached_multiple_calls_4 { X( []((user_floor_button[4] || user_cabin_button[4]) -> <>on_floor[4]) ) } // false
ltl floor_reached_multiple_calls_5 { X( []((user_floor_button[5] || user_cabin_button[5]) -> <>on_floor[5]) ) } // false
ltl floor_reached_multiple_calls_6 { X( []((user_floor_button[6] || user_cabin_button[6]) -> <>on_floor[6]) ) } // false
ltl floor_reached_multiple_calls_7 { X( []((user_floor_button[7] || user_cabin_button[7]) -> <>on_floor[7]) ) } // false
ltl floor_reached_multiple_calls_8 { X( []((user_floor_button[8] || user_cabin_button[8]) -> <>on_floor[8]) ) } // false
ltl floor_reached_multiple_calls_9 { X( []((user_floor_button[9] || user_cabin_button[9]) -> <>on_floor[9]) ) } // false
ltl floor_reached_multiple_calls_10 { X( []((user_floor_button[10] || user_cabin_button[10]) -> <>on_floor[10]) ) } // false
ltl floor_reached_multiple_calls_11 { X( []((user_floor_button[11] || user_cabin_button[11]) -> <>on_floor[11]) ) } // false
ltl floor_reached_multiple_calls_12 { X( []((user_floor_button[12] || user_cabin_button[12]) -> <>on_floor[12]) ) } // false
ltl floor_reached_multiple_calls_13 { X( []((user_floor_button[13] || user_cabin_button[13]) -> <>on_floor[13]) ) } // false
ltl floor_reached_multiple_calls_14 { X( []((user_floor_button[14] || user_cabin_button[14]) -> <>on_floor[14]) ) } // false
ltl floor_reached_multiple_calls_15 { X( []((user_floor_button[15] || user_cabin_button[15]) -> <>on_floor[15]) ) } // false
