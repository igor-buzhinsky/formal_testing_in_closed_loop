// Plant execution
#define VCYL_MAXPOS 2

d_step {
    int i;

    int hpos_sum = 0;
    for (i : 0..(HCYL_NUM - 1)) {
        hpos_sum = hpos_sum + hcyl_pos[i];
    }
    
    bool prev_carrying_wp = carrying_wp;
    
    bool carry_condition = 0;
    for (i : 0..(WP_NUM - 1)) {
        carry_condition = carry_condition || (hpos_sum == i + 1) && wp[i];
    }
    
    if
    :: carrying_wp && !suction_on -> carrying_wp = 0;
    :: !carrying_wp && vcyl_extended && suction_on && carry_condition -> carrying_wp = 1;
    :: else -> ;
    fi
    
    wp_output = prev_carrying_wp && !carrying_wp && (hpos_sum == 0) && vcyl_extended;
    for (i : 0..(WP_NUM - 1)) {
        wp[i] = adding_wp[i] | wp[i] && !(!carrying_wp && vcyl_extended && suction_on && (hpos_sum == i + 1));
    }
    
    for (i : 0..(HCYL_NUM - 1)) {
        int maxpos = 1 << (HCYL_NUM - 1 - i);
        hcyl_pos[i] = hcyl_pos[i] + (hcyl_extend[i] -> 1 : -1);
        hcyl_pos[i] = (hcyl_pos[i] > maxpos -> maxpos : hcyl_pos[i]);
        hcyl_pos[i] = (hcyl_pos[i] < 0 -> 0 : hcyl_pos[i]);
        hcyl_retracted[i] = hcyl_pos[i] == 0;
        hcyl_extended[i] = hcyl_pos[i] == maxpos;
    }
    
    total_hcyl_pos = hpos_sum;
    vcyl_pos = vcyl_pos + (vcyl_extend -> 1 : -1);
    vcyl_pos = (vcyl_pos > VCYL_MAXPOS -> VCYL_MAXPOS : vcyl_pos);
    vcyl_pos = (vcyl_pos < 0 -> 0 : vcyl_pos);
    vcyl_retracted = vcyl_pos == 0;
    vcyl_extended = vcyl_pos == VCYL_MAXPOS;
}