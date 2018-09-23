#define vcyl_maxpos 2

d_step {
    prev_carrying_wp = carrying_wp;
    if
    :: (carrying_wp && !(suction_on)) -> 
        carrying_wp = false;
    :: (((!(carrying_wp) && vcyl_extended) && suction_on) && ((((((((total_hcyl_pos == 1) && wp[0]) || ((total_hcyl_pos == 2) && wp[1])) || ((total_hcyl_pos == 3) && wp[2])) || ((total_hcyl_pos == 4) && wp[3])) || ((total_hcyl_pos == 5) && wp[4])) || ((total_hcyl_pos == 6) && wp[5])) || ((total_hcyl_pos == 7) && wp[6]))) -> 
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
    if
    :: hcyl_extend[0] -> 
        hcyl_pos[0] = (hcyl_pos[0] + 1);
    :: else -> 
        hcyl_pos[0] = (hcyl_pos[0] - 1);
    fi
    if
    :: (hcyl_pos[0] > 4) -> 
        hcyl_pos[0] = 4;
    :: (hcyl_pos[0] < 0) -> 
        hcyl_pos[0] = 0;
    :: else -> ;
    fi
    hcyl_retracted[0] = (hcyl_pos[0] == 0);
    hcyl_extended[0] = (hcyl_pos[0] == 4);
    if
    :: hcyl_extend[1] -> 
        hcyl_pos[1] = (hcyl_pos[1] + 1);
    :: else -> 
        hcyl_pos[1] = (hcyl_pos[1] - 1);
    fi
    if
    :: (hcyl_pos[1] > 2) -> 
        hcyl_pos[1] = 2;
    :: (hcyl_pos[1] < 0) -> 
        hcyl_pos[1] = 0;
    :: else -> ;
    fi
    hcyl_retracted[1] = (hcyl_pos[1] == 0);
    hcyl_extended[1] = (hcyl_pos[1] == 2);
    if
    :: hcyl_extend[2] -> 
        hcyl_pos[2] = (hcyl_pos[2] + 1);
    :: else -> 
        hcyl_pos[2] = (hcyl_pos[2] - 1);
    fi
    if
    :: (hcyl_pos[2] > 1) -> 
        hcyl_pos[2] = 1;
    :: (hcyl_pos[2] < 0) -> 
        hcyl_pos[2] = 0;
    :: else -> ;
    fi
    hcyl_retracted[2] = (hcyl_pos[2] == 0);
    hcyl_extended[2] = (hcyl_pos[2] == 1);
    total_hcyl_pos = ((hcyl_pos[0] + hcyl_pos[1]) + hcyl_pos[2]);
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
