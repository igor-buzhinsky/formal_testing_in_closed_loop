// Plant execution
#define HCYL0_MAXPOS 2
#define HCYL1_MAXPOS 1
#define VCYL_MAXPOS 2

d_step {
    int hpos_sum = hcyl_pos[0] + hcyl_pos[1];
    
    bool prev_carrying_wp = carrying_wp;
    if
    :: carrying_wp && !suction_on -> carrying_wp = 0;
    :: !carrying_wp && vcyl_extended && suction_on && ((hpos_sum == 1) && wp[0] || (hpos_sum == 2) && wp[1] || (hpos_sum == 3) && wp[2]) -> carrying_wp = 1;
    :: else -> ;
    fi
    
    wp_output = prev_carrying_wp && !carrying_wp && (hpos_sum == 0) && vcyl_extended;
    wp[0] = adding_wp[0] | wp[0] && !(!carrying_wp && vcyl_extended && suction_on && (hpos_sum == 1));
    wp[1] = adding_wp[1] | wp[1] && !(!carrying_wp && vcyl_extended && suction_on && (hpos_sum == 2));
    wp[2] = adding_wp[2] | wp[2] && !(!carrying_wp && vcyl_extended && suction_on && (hpos_sum == 3));
    
    hcyl_pos[0] = hcyl_pos[0] + (hcyl_extend[0] -> 1 : -1);
    hcyl_pos[0] = (hcyl_pos[0] > HCYL0_MAXPOS -> HCYL0_MAXPOS : hcyl_pos[0]);
    hcyl_pos[0] = (hcyl_pos[0] < 0 -> 0 : hcyl_pos[0]);
    
    hcyl_pos[1] = hcyl_pos[1] + (hcyl_extend[1] -> 1 : -1);
    hcyl_pos[1] = (hcyl_pos[1] > HCYL1_MAXPOS -> HCYL1_MAXPOS : hcyl_pos[1]);
    hcyl_pos[1] = (hcyl_pos[1] < 0 -> 0 : hcyl_pos[1]);
    
    vcyl_pos = vcyl_pos + (vcyl_extend -> 1 : -1);
    vcyl_pos = (vcyl_pos > VCYL_MAXPOS -> VCYL_MAXPOS : vcyl_pos);
    vcyl_pos = (vcyl_pos < 0 -> 0 : vcyl_pos);

    hcyl_retracted[0] = hcyl_pos[0] == 0;
    hcyl_retracted[1] = hcyl_pos[1] == 0;
    vcyl_retracted = vcyl_pos == 0;
    hcyl_extended[0] = hcyl_pos[0] == HCYL0_MAXPOS;
    hcyl_extended[1] = hcyl_pos[1] == HCYL1_MAXPOS;
    vcyl_extended = vcyl_pos == VCYL_MAXPOS;
}