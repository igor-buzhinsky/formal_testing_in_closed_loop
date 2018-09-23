#define s_h_extend 0
#define s_v_extend 1
#define s_suction_on 2
#define s_v_retract 3
#define s_h_retract 4
#define s_v_extend_output 5
#define s_suction_off 6
#define s_v_retract_output 7

d_step {
    target_condition = false;
    target_condition = (target_condition || ((wp[0] && hcyl_retracted[0]) && hcyl_extended[1]));
    target_condition = (target_condition || ((wp[1] && hcyl_extended[0]) && hcyl_retracted[1]));
    target_condition = (target_condition || ((wp[2] && hcyl_extended[0]) && hcyl_extended[1]));
    if
    :: (((state == s_v_retract_output) && vcyl_retracted) && ((wp[0] || wp[1]) || wp[2])) -> 
        state = s_h_extend;
    :: ((state == s_h_extend) && target_condition) -> 
        state = s_v_extend;
    :: ((state == s_v_extend) && vcyl_extended) -> 
        state = s_suction_on;
    :: (state == s_suction_on) -> 
        state = s_v_retract;
    :: ((state == s_v_retract) && vcyl_retracted) -> 
        state = s_h_retract;
    :: (((state == s_h_retract) && hcyl_retracted[0]) && hcyl_retracted[1]) -> 
        state = s_v_extend_output;
    :: ((state == s_v_extend_output) && vcyl_extended) -> 
        state = s_suction_off;
    :: (state == s_suction_off) -> 
        state = s_v_retract_output;
    :: else -> ;
    fi
    if
    :: wp[0] -> 
        minimum_wp = 1;
    :: wp[1] -> 
        minimum_wp = 2;
    :: wp[2] -> 
        minimum_wp = 3;
    :: else -> 
        minimum_wp = 0;
    fi
    if
    :: (state == s_suction_on) -> 
        suction_on = true;
    :: (state == s_suction_off) -> 
        suction_on = false;
    :: (state == s_h_extend) -> 
        hcyl_extend[0] = (((minimum_wp / 2) % 2) == 1);
        hcyl_extend[1] = (((minimum_wp / 1) % 2) == 1);
    :: (state == s_h_retract) -> 
        hcyl_extend[0] = false;
        hcyl_extend[1] = false;
    :: ((state == s_v_extend) || (state == s_v_extend_output)) -> 
        vcyl_extend = true;
    :: ((state == s_v_retract) || (state == s_v_retract_output)) -> 
        vcyl_extend = false;
    :: else -> ;
    fi
}
