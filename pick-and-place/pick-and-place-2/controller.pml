// Controller execution
d_step {
    bool hcyl_target_wp0 = wp[0] && hcyl_retracted[0] && hcyl_extended[1];
    bool hcyl_target_wp1 = wp[1] && hcyl_extended[0] && hcyl_retracted[1];
    bool hcyl_target_wp2 = wp[2] && hcyl_extended[0] && hcyl_extended[1];

    if
    :: state == s_v_retract_output && vcyl_retracted && (wp[0] || wp[1] || wp[2]) -> state = s_h_extend;
    :: state == s_h_extend && (hcyl_target_wp0 || hcyl_target_wp1 || hcyl_target_wp2) -> state = s_v_extend;
    :: state == s_v_extend && vcyl_extended -> state = s_suction_on;
    :: state == s_suction_on -> state = s_v_retract;
    :: state == s_v_retract && vcyl_retracted -> state = s_h_retract;
    :: state == s_h_retract && hcyl_retracted[0] && hcyl_retracted[1] -> state = s_v_extend_output;
    :: state == s_v_extend_output && vcyl_extended -> state = s_suction_off;
    :: state == s_suction_off -> state = s_v_retract_output;
    :: else -> ;
    fi

    int minimum_wp = (wp[0] -> 1 : (wp[1] -> 2 : (wp[2] -> 3 : 0)));

    if
    :: state == s_suction_on -> suction_on = 1;
    :: state == s_suction_off -> suction_on = 0;
    :: state == s_h_extend ->
        hcyl_extend[0] = (minimum_wp / 2) % 2;
        hcyl_extend[1] = (minimum_wp / 1) % 2;
    :: state == s_h_retract ->
        hcyl_extend[0] = 0;
        hcyl_extend[1] = 0;
    :: state == s_v_extend || state == s_v_extend_output -> vcyl_extend = 1;
    :: state == s_v_retract || state == s_v_retract_output -> vcyl_extend = 0;
    :: else -> ;
    fi
}