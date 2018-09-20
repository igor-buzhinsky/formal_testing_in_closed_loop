// Controller execution
d_step {
    int i, j;
    
    bool any_wp = 0;
    bool target_condition = 0;
    for (i : 0..(WP_NUM - 1)) {
        any_wp = any_wp || wp[i];
        bool inner_condition = wp[i];
        for (j : 0..(HCYL_NUM - 1)) {
            inner_condition = inner_condition && (((i + 1) >> (HCYL_NUM - j - 1)) % 2 -> hcyl_extended[j] : hcyl_retracted[j]);
        }
        target_condition = target_condition || inner_condition;
    }
    
    bool all_retracted = 1;
    for (i : 0..(HCYL_NUM - 1)) {
        all_retracted = all_retracted && hcyl_retracted[i];
    }

    if
    :: state == s_v_retract_output && vcyl_retracted && any_wp -> state = s_h_extend;
    :: state == s_h_extend && target_condition -> state = s_v_extend;
    :: state == s_v_extend && vcyl_extended -> state = s_suction_on;
    :: state == s_suction_on -> state = s_v_retract;
    :: state == s_v_retract && vcyl_retracted -> state = s_h_retract;
    :: state == s_h_retract && all_retracted -> state = s_v_extend_output;
    :: state == s_v_extend_output && vcyl_extended -> state = s_suction_off;
    :: state == s_suction_off -> state = s_v_retract_output;
    :: else -> ;
    fi

    int minimum_wp = 0;
    for (i : 0..(WP_NUM - 1)) {
        j = WP_NUM - i - 1;
        minimum_wp = (wp[j] -> j + 1 : minimum_wp);
    }
    
    if
    :: state == s_suction_on -> suction_on = 1;
    :: state == s_suction_off -> suction_on = 0;
    :: state == s_h_extend ->
        for (i : 0..(HCYL_NUM - 1)) {
            hcyl_extend[i] = (minimum_wp >> (HCYL_NUM - i - 1)) % 2;
        }
    :: state == s_h_retract ->
        for (i : 0..(HCYL_NUM - 1)) {
            hcyl_extend[i] = 0;
        }
    :: state == s_v_extend || state == s_v_extend_output -> vcyl_extend = 1;
    :: state == s_v_retract || state == s_v_retract_output -> vcyl_extend = 0;
    :: else -> ;
    fi
}