#define vcyl_maxpos 2

d_step {
    prev_carrying_wp = carrying_wp;
    if
    :: (carrying_wp && !(suction_on)) -> 
        carrying_wp = false;
    :: (((!(carrying_wp) && vcyl_extended) && suction_on) && ((((total_hcyl_pos == 1) && wp[0]) || ((total_hcyl_pos == 2) && wp[1])) || ((total_hcyl_pos == 3) && wp[2]))) -> 
        carrying_wp = true;
    :: else -> ;
    fi
    wp_output = (((prev_carrying_wp && !(carrying_wp)) && (total_hcyl_pos == 0)) && vcyl_extended);
    wp[0] = (adding_wp[0] || (wp[0] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 1)))));
    wp[1] = (adding_wp[1] || (wp[1] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 2)))));
    wp[2] = (adding_wp[2] || (wp[2] && !((((!(carrying_wp) && vcyl_extended) && suction_on) && (total_hcyl_pos == 3)))));
    if
    :: hcyl_extend[0] -> 
        hcyl_pos[0] = (hcyl_pos[0] + 1);
    :: else -> 
        hcyl_pos[0] = (hcyl_pos[0] - 1);
    fi
    if
    :: (hcyl_pos[0] > 2) -> 
        hcyl_pos[0] = 2;
    :: (hcyl_pos[0] < 0) -> 
        hcyl_pos[0] = 0;
    :: else -> ;
    fi
    hcyl_retracted[0] = (hcyl_pos[0] == 0);
    hcyl_extended[0] = (hcyl_pos[0] == 2);
    if
    :: hcyl_extend[1] -> 
        hcyl_pos[1] = (hcyl_pos[1] + 1);
    :: else -> 
        hcyl_pos[1] = (hcyl_pos[1] - 1);
    fi
    if
    :: (hcyl_pos[1] > 1) -> 
        hcyl_pos[1] = 1;
    :: (hcyl_pos[1] < 0) -> 
        hcyl_pos[1] = 0;
    :: else -> ;
    fi
    hcyl_retracted[1] = (hcyl_pos[1] == 0);
    hcyl_extended[1] = (hcyl_pos[1] == 1);
    total_hcyl_pos = (hcyl_pos[0] + hcyl_pos[1]);
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
