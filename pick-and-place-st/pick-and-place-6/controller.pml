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
    target_condition = (target_condition || ((((((wp[0] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[1] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[2] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[3] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[4] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[5] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[6] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[7] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[8] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[9] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[10] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[11] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[12] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[13] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[14] && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[15] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[16] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[17] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[18] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[19] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[20] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[21] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[22] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[23] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[24] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[25] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[26] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[27] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[28] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[29] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[30] && hcyl_retracted[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[31] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[32] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[33] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[34] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[35] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[36] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[37] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[38] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[39] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[40] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[41] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[42] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[43] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[44] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[45] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[46] && hcyl_extended[0]) && hcyl_retracted[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[47] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[48] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[49] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[50] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[51] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[52] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[53] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[54] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_retracted[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[55] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[56] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[57] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[58] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_retracted[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[59] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[60] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_retracted[4]) && hcyl_extended[5]));
    target_condition = (target_condition || ((((((wp[61] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_retracted[5]));
    target_condition = (target_condition || ((((((wp[62] && hcyl_extended[0]) && hcyl_extended[1]) && hcyl_extended[2]) && hcyl_extended[3]) && hcyl_extended[4]) && hcyl_extended[5]));
    if
    :: (((state == s_v_retract_output) && vcyl_retracted) && ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((wp[0] || wp[1]) || wp[2]) || wp[3]) || wp[4]) || wp[5]) || wp[6]) || wp[7]) || wp[8]) || wp[9]) || wp[10]) || wp[11]) || wp[12]) || wp[13]) || wp[14]) || wp[15]) || wp[16]) || wp[17]) || wp[18]) || wp[19]) || wp[20]) || wp[21]) || wp[22]) || wp[23]) || wp[24]) || wp[25]) || wp[26]) || wp[27]) || wp[28]) || wp[29]) || wp[30]) || wp[31]) || wp[32]) || wp[33]) || wp[34]) || wp[35]) || wp[36]) || wp[37]) || wp[38]) || wp[39]) || wp[40]) || wp[41]) || wp[42]) || wp[43]) || wp[44]) || wp[45]) || wp[46]) || wp[47]) || wp[48]) || wp[49]) || wp[50]) || wp[51]) || wp[52]) || wp[53]) || wp[54]) || wp[55]) || wp[56]) || wp[57]) || wp[58]) || wp[59]) || wp[60]) || wp[61]) || wp[62])) -> 
        state = s_h_extend;
    :: ((state == s_h_extend) && target_condition) -> 
        state = s_v_extend;
    :: ((state == s_v_extend) && vcyl_extended) -> 
        state = s_suction_on;
    :: (state == s_suction_on) -> 
        state = s_v_retract;
    :: ((state == s_v_retract) && vcyl_retracted) -> 
        state = s_h_retract;
    :: (((((((state == s_h_retract) && hcyl_retracted[0]) && hcyl_retracted[1]) && hcyl_retracted[2]) && hcyl_retracted[3]) && hcyl_retracted[4]) && hcyl_retracted[5]) -> 
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
    :: wp[3] -> 
        minimum_wp = 4;
    :: wp[4] -> 
        minimum_wp = 5;
    :: wp[5] -> 
        minimum_wp = 6;
    :: wp[6] -> 
        minimum_wp = 7;
    :: wp[7] -> 
        minimum_wp = 8;
    :: wp[8] -> 
        minimum_wp = 9;
    :: wp[9] -> 
        minimum_wp = 10;
    :: wp[10] -> 
        minimum_wp = 11;
    :: wp[11] -> 
        minimum_wp = 12;
    :: wp[12] -> 
        minimum_wp = 13;
    :: wp[13] -> 
        minimum_wp = 14;
    :: wp[14] -> 
        minimum_wp = 15;
    :: wp[15] -> 
        minimum_wp = 16;
    :: wp[16] -> 
        minimum_wp = 17;
    :: wp[17] -> 
        minimum_wp = 18;
    :: wp[18] -> 
        minimum_wp = 19;
    :: wp[19] -> 
        minimum_wp = 20;
    :: wp[20] -> 
        minimum_wp = 21;
    :: wp[21] -> 
        minimum_wp = 22;
    :: wp[22] -> 
        minimum_wp = 23;
    :: wp[23] -> 
        minimum_wp = 24;
    :: wp[24] -> 
        minimum_wp = 25;
    :: wp[25] -> 
        minimum_wp = 26;
    :: wp[26] -> 
        minimum_wp = 27;
    :: wp[27] -> 
        minimum_wp = 28;
    :: wp[28] -> 
        minimum_wp = 29;
    :: wp[29] -> 
        minimum_wp = 30;
    :: wp[30] -> 
        minimum_wp = 31;
    :: wp[31] -> 
        minimum_wp = 32;
    :: wp[32] -> 
        minimum_wp = 33;
    :: wp[33] -> 
        minimum_wp = 34;
    :: wp[34] -> 
        minimum_wp = 35;
    :: wp[35] -> 
        minimum_wp = 36;
    :: wp[36] -> 
        minimum_wp = 37;
    :: wp[37] -> 
        minimum_wp = 38;
    :: wp[38] -> 
        minimum_wp = 39;
    :: wp[39] -> 
        minimum_wp = 40;
    :: wp[40] -> 
        minimum_wp = 41;
    :: wp[41] -> 
        minimum_wp = 42;
    :: wp[42] -> 
        minimum_wp = 43;
    :: wp[43] -> 
        minimum_wp = 44;
    :: wp[44] -> 
        minimum_wp = 45;
    :: wp[45] -> 
        minimum_wp = 46;
    :: wp[46] -> 
        minimum_wp = 47;
    :: wp[47] -> 
        minimum_wp = 48;
    :: wp[48] -> 
        minimum_wp = 49;
    :: wp[49] -> 
        minimum_wp = 50;
    :: wp[50] -> 
        minimum_wp = 51;
    :: wp[51] -> 
        minimum_wp = 52;
    :: wp[52] -> 
        minimum_wp = 53;
    :: wp[53] -> 
        minimum_wp = 54;
    :: wp[54] -> 
        minimum_wp = 55;
    :: wp[55] -> 
        minimum_wp = 56;
    :: wp[56] -> 
        minimum_wp = 57;
    :: wp[57] -> 
        minimum_wp = 58;
    :: wp[58] -> 
        minimum_wp = 59;
    :: wp[59] -> 
        minimum_wp = 60;
    :: wp[60] -> 
        minimum_wp = 61;
    :: wp[61] -> 
        minimum_wp = 62;
    :: wp[62] -> 
        minimum_wp = 63;
    :: else -> 
        minimum_wp = 0;
    fi
    if
    :: (state == s_suction_on) -> 
        suction_on = true;
    :: (state == s_suction_off) -> 
        suction_on = false;
    :: (state == s_h_extend) -> 
        hcyl_extend[0] = (((minimum_wp / 32) % 2) == 1);
        hcyl_extend[1] = (((minimum_wp / 16) % 2) == 1);
        hcyl_extend[2] = (((minimum_wp / 8) % 2) == 1);
        hcyl_extend[3] = (((minimum_wp / 4) % 2) == 1);
        hcyl_extend[4] = (((minimum_wp / 2) % 2) == 1);
        hcyl_extend[5] = (((minimum_wp / 1) % 2) == 1);
    :: (state == s_h_retract) -> 
        hcyl_extend[0] = false;
        hcyl_extend[1] = false;
        hcyl_extend[2] = false;
        hcyl_extend[3] = false;
        hcyl_extend[4] = false;
        hcyl_extend[5] = false;
    :: ((state == s_v_extend) || (state == s_v_extend_output)) -> 
        vcyl_extend = true;
    :: ((state == s_v_retract) || (state == s_v_retract_output)) -> 
        vcyl_extend = false;
    :: else -> ;
    fi
}
