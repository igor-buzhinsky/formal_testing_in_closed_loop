#define vcyl_maxpos 2

d_step {
    prev_carrying_wp = carrying_wp;
    if
    :: (carrying_wp && !(suction_on)) -> 
        carrying_wp = false;
    :: (((!(carrying_wp) && vcyl_extended) && suction_on) && ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((total_hcyl_pos == 1) && wp[0]) || ((total_hcyl_pos == 2) && wp[1])) || ((total_hcyl_pos == 3) && wp[2])) || ((total_hcyl_pos == 4) && wp[3])) || ((total_hcyl_pos == 5) && wp[4])) || ((total_hcyl_pos == 6) && wp[5])) || ((total_hcyl_pos == 7) && wp[6])) || ((total_hcyl_pos == 8) && wp[7])) || ((total_hcyl_pos == 9) && wp[8])) || ((total_hcyl_pos == 10) && wp[9])) || ((total_hcyl_pos == 11) && wp[10])) || ((total_hcyl_pos == 12) && wp[11])) || ((total_hcyl_pos == 13) && wp[12])) || ((total_hcyl_pos == 14) && wp[13])) || ((total_hcyl_pos == 15) && wp[14])) || ((total_hcyl_pos == 16) && wp[15])) || ((total_hcyl_pos == 17) && wp[16])) || ((total_hcyl_pos == 18) && wp[17])) || ((total_hcyl_pos == 19) && wp[18])) || ((total_hcyl_pos == 20) && wp[19])) || ((total_hcyl_pos == 21) && wp[20])) || ((total_hcyl_pos == 22) && wp[21])) || ((total_hcyl_pos == 23) && wp[22])) || ((total_hcyl_pos == 24) && wp[23])) || ((total_hcyl_pos == 25) && wp[24])) || ((total_hcyl_pos == 26) && wp[25])) || ((total_hcyl_pos == 27) && wp[26])) || ((total_hcyl_pos == 28) && wp[27])) || ((total_hcyl_pos == 29) && wp[28])) || ((total_hcyl_pos == 30) && wp[29])) || ((total_hcyl_pos == 31) && wp[30])) || ((total_hcyl_pos == 32) && wp[31])) || ((total_hcyl_pos == 33) && wp[32])) || ((total_hcyl_pos == 34) && wp[33])) || ((total_hcyl_pos == 35) && wp[34])) || ((total_hcyl_pos == 36) && wp[35])) || ((total_hcyl_pos == 37) && wp[36])) || ((total_hcyl_pos == 38) && wp[37])) || ((total_hcyl_pos == 39) && wp[38])) || ((total_hcyl_pos == 40) && wp[39])) || ((total_hcyl_pos == 41) && wp[40])) || ((total_hcyl_pos == 42) && wp[41])) || ((total_hcyl_pos == 43) && wp[42])) || ((total_hcyl_pos == 44) && wp[43])) || ((total_hcyl_pos == 45) && wp[44])) || ((total_hcyl_pos == 46) && wp[45])) || ((total_hcyl_pos == 47) && wp[46])) || ((total_hcyl_pos == 48) && wp[47])) || ((total_hcyl_pos == 49) && wp[48])) || ((total_hcyl_pos == 50) && wp[49])) || ((total_hcyl_pos == 51) && wp[50])) || ((total_hcyl_pos == 52) && wp[51])) || ((total_hcyl_pos == 53) && wp[52])) || ((total_hcyl_pos == 54) && wp[53])) || ((total_hcyl_pos == 55) && wp[54])) || ((total_hcyl_pos == 56) && wp[55])) || ((total_hcyl_pos == 57) && wp[56])) || ((total_hcyl_pos == 58) && wp[57])) || ((total_hcyl_pos == 59) && wp[58])) || ((total_hcyl_pos == 60) && wp[59])) || ((total_hcyl_pos == 61) && wp[60])) || ((total_hcyl_pos == 62) && wp[61])) || ((total_hcyl_pos == 63) && wp[62]))) -> 
        carrying_wp = true;
    :: else -> ;
    fi
    wp_output = (((prev_carrying_wp && !(carrying_wp)) && (total_hcyl_pos == 0)) && vcyl_extended);
    wp[0] = (adding_wp[0] || (wp[0] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 1)))));
    wp[1] = (adding_wp[1] || (wp[1] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 2)))));
    wp[2] = (adding_wp[2] || (wp[2] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 3)))));
    wp[3] = (adding_wp[3] || (wp[3] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 4)))));
    wp[4] = (adding_wp[4] || (wp[4] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 5)))));
    wp[5] = (adding_wp[5] || (wp[5] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 6)))));
    wp[6] = (adding_wp[6] || (wp[6] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 7)))));
    wp[7] = (adding_wp[7] || (wp[7] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 8)))));
    wp[8] = (adding_wp[8] || (wp[8] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 9)))));
    wp[9] = (adding_wp[9] || (wp[9] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 10)))));
    wp[10] = (adding_wp[10] || (wp[10] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 11)))));
    wp[11] = (adding_wp[11] || (wp[11] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 12)))));
    wp[12] = (adding_wp[12] || (wp[12] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 13)))));
    wp[13] = (adding_wp[13] || (wp[13] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 14)))));
    wp[14] = (adding_wp[14] || (wp[14] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 15)))));
    wp[15] = (adding_wp[15] || (wp[15] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 16)))));
    wp[16] = (adding_wp[16] || (wp[16] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 17)))));
    wp[17] = (adding_wp[17] || (wp[17] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 18)))));
    wp[18] = (adding_wp[18] || (wp[18] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 19)))));
    wp[19] = (adding_wp[19] || (wp[19] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 20)))));
    wp[20] = (adding_wp[20] || (wp[20] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 21)))));
    wp[21] = (adding_wp[21] || (wp[21] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 22)))));
    wp[22] = (adding_wp[22] || (wp[22] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 23)))));
    wp[23] = (adding_wp[23] || (wp[23] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 24)))));
    wp[24] = (adding_wp[24] || (wp[24] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 25)))));
    wp[25] = (adding_wp[25] || (wp[25] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 26)))));
    wp[26] = (adding_wp[26] || (wp[26] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 27)))));
    wp[27] = (adding_wp[27] || (wp[27] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 28)))));
    wp[28] = (adding_wp[28] || (wp[28] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 29)))));
    wp[29] = (adding_wp[29] || (wp[29] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 30)))));
    wp[30] = (adding_wp[30] || (wp[30] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 31)))));
    wp[31] = (adding_wp[31] || (wp[31] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 32)))));
    wp[32] = (adding_wp[32] || (wp[32] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 33)))));
    wp[33] = (adding_wp[33] || (wp[33] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 34)))));
    wp[34] = (adding_wp[34] || (wp[34] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 35)))));
    wp[35] = (adding_wp[35] || (wp[35] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 36)))));
    wp[36] = (adding_wp[36] || (wp[36] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 37)))));
    wp[37] = (adding_wp[37] || (wp[37] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 38)))));
    wp[38] = (adding_wp[38] || (wp[38] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 39)))));
    wp[39] = (adding_wp[39] || (wp[39] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 40)))));
    wp[40] = (adding_wp[40] || (wp[40] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 41)))));
    wp[41] = (adding_wp[41] || (wp[41] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 42)))));
    wp[42] = (adding_wp[42] || (wp[42] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 43)))));
    wp[43] = (adding_wp[43] || (wp[43] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 44)))));
    wp[44] = (adding_wp[44] || (wp[44] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 45)))));
    wp[45] = (adding_wp[45] || (wp[45] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 46)))));
    wp[46] = (adding_wp[46] || (wp[46] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 47)))));
    wp[47] = (adding_wp[47] || (wp[47] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 48)))));
    wp[48] = (adding_wp[48] || (wp[48] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 49)))));
    wp[49] = (adding_wp[49] || (wp[49] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 50)))));
    wp[50] = (adding_wp[50] || (wp[50] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 51)))));
    wp[51] = (adding_wp[51] || (wp[51] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 52)))));
    wp[52] = (adding_wp[52] || (wp[52] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 53)))));
    wp[53] = (adding_wp[53] || (wp[53] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 54)))));
    wp[54] = (adding_wp[54] || (wp[54] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 55)))));
    wp[55] = (adding_wp[55] || (wp[55] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 56)))));
    wp[56] = (adding_wp[56] || (wp[56] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 57)))));
    wp[57] = (adding_wp[57] || (wp[57] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 58)))));
    wp[58] = (adding_wp[58] || (wp[58] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 59)))));
    wp[59] = (adding_wp[59] || (wp[59] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 60)))));
    wp[60] = (adding_wp[60] || (wp[60] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 61)))));
    wp[61] = (adding_wp[61] || (wp[61] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 62)))));
    wp[62] = (adding_wp[62] || (wp[62] && !((((!(prev_carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 63)))));
    if
    :: hcyl_extend[0] -> 
        hcyl_pos[0] = (hcyl_pos[0] + 1);
    :: else -> 
        hcyl_pos[0] = (hcyl_pos[0] - 1);
    fi
    if
    :: (hcyl_pos[0] > 32) -> 
        hcyl_pos[0] = 32;
    :: (hcyl_pos[0] < 0) -> 
        hcyl_pos[0] = 0;
    :: else -> ;
    fi
    hcyl_retracted[0] = (hcyl_pos[0] == 0);
    hcyl_extended[0] = (hcyl_pos[0] == 32);
    if
    :: hcyl_extend[1] -> 
        hcyl_pos[1] = (hcyl_pos[1] + 1);
    :: else -> 
        hcyl_pos[1] = (hcyl_pos[1] - 1);
    fi
    if
    :: (hcyl_pos[1] > 16) -> 
        hcyl_pos[1] = 16;
    :: (hcyl_pos[1] < 0) -> 
        hcyl_pos[1] = 0;
    :: else -> ;
    fi
    hcyl_retracted[1] = (hcyl_pos[1] == 0);
    hcyl_extended[1] = (hcyl_pos[1] == 16);
    if
    :: hcyl_extend[2] -> 
        hcyl_pos[2] = (hcyl_pos[2] + 1);
    :: else -> 
        hcyl_pos[2] = (hcyl_pos[2] - 1);
    fi
    if
    :: (hcyl_pos[2] > 8) -> 
        hcyl_pos[2] = 8;
    :: (hcyl_pos[2] < 0) -> 
        hcyl_pos[2] = 0;
    :: else -> ;
    fi
    hcyl_retracted[2] = (hcyl_pos[2] == 0);
    hcyl_extended[2] = (hcyl_pos[2] == 8);
    if
    :: hcyl_extend[3] -> 
        hcyl_pos[3] = (hcyl_pos[3] + 1);
    :: else -> 
        hcyl_pos[3] = (hcyl_pos[3] - 1);
    fi
    if
    :: (hcyl_pos[3] > 4) -> 
        hcyl_pos[3] = 4;
    :: (hcyl_pos[3] < 0) -> 
        hcyl_pos[3] = 0;
    :: else -> ;
    fi
    hcyl_retracted[3] = (hcyl_pos[3] == 0);
    hcyl_extended[3] = (hcyl_pos[3] == 4);
    if
    :: hcyl_extend[4] -> 
        hcyl_pos[4] = (hcyl_pos[4] + 1);
    :: else -> 
        hcyl_pos[4] = (hcyl_pos[4] - 1);
    fi
    if
    :: (hcyl_pos[4] > 2) -> 
        hcyl_pos[4] = 2;
    :: (hcyl_pos[4] < 0) -> 
        hcyl_pos[4] = 0;
    :: else -> ;
    fi
    hcyl_retracted[4] = (hcyl_pos[4] == 0);
    hcyl_extended[4] = (hcyl_pos[4] == 2);
    if
    :: hcyl_extend[5] -> 
        hcyl_pos[5] = (hcyl_pos[5] + 1);
    :: else -> 
        hcyl_pos[5] = (hcyl_pos[5] - 1);
    fi
    if
    :: (hcyl_pos[5] > 1) -> 
        hcyl_pos[5] = 1;
    :: (hcyl_pos[5] < 0) -> 
        hcyl_pos[5] = 0;
    :: else -> ;
    fi
    hcyl_retracted[5] = (hcyl_pos[5] == 0);
    hcyl_extended[5] = (hcyl_pos[5] == 1);
    total_hcyl_pos = (((((hcyl_pos[0] + hcyl_pos[1]) + hcyl_pos[2]) + hcyl_pos[3]) + hcyl_pos[4]) + hcyl_pos[5]);
    if
    :: vcyl_extend -> 
        vcyl_pos = (vcyl_pos + 1);
    :: else -> 
        vcyl_pos = (vcyl_pos - 1);
    fi
    if
    :: (vcyl_pos > vcyl_maxpos) -> 
        vcyl_pos = vcyl_maxpos;
    :: (vcyl_pos < 0) -> 
        vcyl_pos = 0;
    :: else -> ;
    fi
    vcyl_retracted = (vcyl_pos == 0);
    vcyl_extended = (vcyl_pos == vcyl_maxpos);
}
