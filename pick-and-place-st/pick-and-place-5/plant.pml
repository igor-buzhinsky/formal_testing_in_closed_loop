#define vcyl_maxpos 2

d_step {
    prev_carrying_wp = carrying_wp;
    if
    :: (carrying_wp && !(suction_on)) -> 
        carrying_wp = false;
    :: (((!(carrying_wp) && vcyl_extended) && suction_on) && ((((((((((((((((((((((((((((((((total_hcyl_pos == 1) && wp[0]) || ((total_hcyl_pos == 2) && wp[1])) || ((total_hcyl_pos == 3) && wp[2])) || ((total_hcyl_pos == 4) && wp[3])) || ((total_hcyl_pos == 5) && wp[4])) || ((total_hcyl_pos == 6) && wp[5])) || ((total_hcyl_pos == 7) && wp[6])) || ((total_hcyl_pos == 8) && wp[7])) || ((total_hcyl_pos == 9) && wp[8])) || ((total_hcyl_pos == 10) && wp[9])) || ((total_hcyl_pos == 11) && wp[10])) || ((total_hcyl_pos == 12) && wp[11])) || ((total_hcyl_pos == 13) && wp[12])) || ((total_hcyl_pos == 14) && wp[13])) || ((total_hcyl_pos == 15) && wp[14])) || ((total_hcyl_pos == 16) && wp[15])) || ((total_hcyl_pos == 17) && wp[16])) || ((total_hcyl_pos == 18) && wp[17])) || ((total_hcyl_pos == 19) && wp[18])) || ((total_hcyl_pos == 20) && wp[19])) || ((total_hcyl_pos == 21) && wp[20])) || ((total_hcyl_pos == 22) && wp[21])) || ((total_hcyl_pos == 23) && wp[22])) || ((total_hcyl_pos == 24) && wp[23])) || ((total_hcyl_pos == 25) && wp[24])) || ((total_hcyl_pos == 26) && wp[25])) || ((total_hcyl_pos == 27) && wp[26])) || ((total_hcyl_pos == 28) && wp[27])) || ((total_hcyl_pos == 29) && wp[28])) || ((total_hcyl_pos == 30) && wp[29])) || ((total_hcyl_pos == 31) && wp[30]))) -> 
        carrying_wp = true;
    :: else -> ;
    fi
    wp_output = (((prev_carrying_wp && !(carrying_wp)) && (total_hcyl_pos == 0)) && vcyl_extended);
    wp[0] = (adding_wp[0] || (wp[0] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 1)))));
    wp[1] = (adding_wp[1] || (wp[1] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 2)))));
    wp[2] = (adding_wp[2] || (wp[2] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 3)))));
    wp[3] = (adding_wp[3] || (wp[3] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 4)))));
    wp[4] = (adding_wp[4] || (wp[4] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 5)))));
    wp[5] = (adding_wp[5] || (wp[5] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 6)))));
    wp[6] = (adding_wp[6] || (wp[6] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 7)))));
    wp[7] = (adding_wp[7] || (wp[7] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 8)))));
    wp[8] = (adding_wp[8] || (wp[8] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 9)))));
    wp[9] = (adding_wp[9] || (wp[9] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 10)))));
    wp[10] = (adding_wp[10] || (wp[10] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 11)))));
    wp[11] = (adding_wp[11] || (wp[11] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 12)))));
    wp[12] = (adding_wp[12] || (wp[12] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 13)))));
    wp[13] = (adding_wp[13] || (wp[13] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 14)))));
    wp[14] = (adding_wp[14] || (wp[14] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 15)))));
    wp[15] = (adding_wp[15] || (wp[15] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 16)))));
    wp[16] = (adding_wp[16] || (wp[16] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 17)))));
    wp[17] = (adding_wp[17] || (wp[17] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 18)))));
    wp[18] = (adding_wp[18] || (wp[18] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 19)))));
    wp[19] = (adding_wp[19] || (wp[19] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 20)))));
    wp[20] = (adding_wp[20] || (wp[20] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 21)))));
    wp[21] = (adding_wp[21] || (wp[21] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 22)))));
    wp[22] = (adding_wp[22] || (wp[22] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 23)))));
    wp[23] = (adding_wp[23] || (wp[23] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 24)))));
    wp[24] = (adding_wp[24] || (wp[24] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 25)))));
    wp[25] = (adding_wp[25] || (wp[25] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 26)))));
    wp[26] = (adding_wp[26] || (wp[26] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 27)))));
    wp[27] = (adding_wp[27] || (wp[27] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 28)))));
    wp[28] = (adding_wp[28] || (wp[28] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 29)))));
    wp[29] = (adding_wp[29] || (wp[29] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 30)))));
    wp[30] = (adding_wp[30] || (wp[30] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 31)))));
    if
    :: hcyl_extend[0] -> 
        hcyl_pos[0] = (hcyl_pos[0] + 1);
    :: else -> 
        hcyl_pos[0] = (hcyl_pos[0] - 1);
    fi
    if
    :: (hcyl_pos[0] > 16) -> 
        hcyl_pos[0] = 16;
    :: (hcyl_pos[0] < 0) -> 
        hcyl_pos[0] = 0;
    :: else -> ;
    fi
    hcyl_retracted[0] = (hcyl_pos[0] == 0);
    hcyl_extended[0] = (hcyl_pos[0] == 16);
    if
    :: hcyl_extend[1] -> 
        hcyl_pos[1] = (hcyl_pos[1] + 1);
    :: else -> 
        hcyl_pos[1] = (hcyl_pos[1] - 1);
    fi
    if
    :: (hcyl_pos[1] > 8) -> 
        hcyl_pos[1] = 8;
    :: (hcyl_pos[1] < 0) -> 
        hcyl_pos[1] = 0;
    :: else -> ;
    fi
    hcyl_retracted[1] = (hcyl_pos[1] == 0);
    hcyl_extended[1] = (hcyl_pos[1] == 8);
    if
    :: hcyl_extend[2] -> 
        hcyl_pos[2] = (hcyl_pos[2] + 1);
    :: else -> 
        hcyl_pos[2] = (hcyl_pos[2] - 1);
    fi
    if
    :: (hcyl_pos[2] > 4) -> 
        hcyl_pos[2] = 4;
    :: (hcyl_pos[2] < 0) -> 
        hcyl_pos[2] = 0;
    :: else -> ;
    fi
    hcyl_retracted[2] = (hcyl_pos[2] == 0);
    hcyl_extended[2] = (hcyl_pos[2] == 4);
    if
    :: hcyl_extend[3] -> 
        hcyl_pos[3] = (hcyl_pos[3] + 1);
    :: else -> 
        hcyl_pos[3] = (hcyl_pos[3] - 1);
    fi
    if
    :: (hcyl_pos[3] > 2) -> 
        hcyl_pos[3] = 2;
    :: (hcyl_pos[3] < 0) -> 
        hcyl_pos[3] = 0;
    :: else -> ;
    fi
    hcyl_retracted[3] = (hcyl_pos[3] == 0);
    hcyl_extended[3] = (hcyl_pos[3] == 2);
    if
    :: hcyl_extend[4] -> 
        hcyl_pos[4] = (hcyl_pos[4] + 1);
    :: else -> 
        hcyl_pos[4] = (hcyl_pos[4] - 1);
    fi
    if
    :: (hcyl_pos[4] > 1) -> 
        hcyl_pos[4] = 1;
    :: (hcyl_pos[4] < 0) -> 
        hcyl_pos[4] = 0;
    :: else -> ;
    fi
    hcyl_retracted[4] = (hcyl_pos[4] == 0);
    hcyl_extended[4] = (hcyl_pos[4] == 1);
    total_hcyl_pos = ((((hcyl_pos[0] + hcyl_pos[1]) + hcyl_pos[2]) + hcyl_pos[3]) + hcyl_pos[4]);
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
