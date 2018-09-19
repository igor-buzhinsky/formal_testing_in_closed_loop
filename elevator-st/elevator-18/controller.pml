#define d_closed 0
#define d_opening 1
#define d_open 2
#define d_closing 3

d_step {
    on_some_floor = false;
    is_requested = false;
    timer_set = false;
    if
    :: ((!(open[0]) && on_floor[0]) && (button[0] || call[0])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[1]) && on_floor[1]) && (button[1] || call[1])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[2]) && on_floor[2]) && (button[2] || call[2])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[3]) && on_floor[3]) && (button[3] || call[3])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[4]) && on_floor[4]) && (button[4] || call[4])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[5]) && on_floor[5]) && (button[5] || call[5])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[6]) && on_floor[6]) && (button[6] || call[6])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[7]) && on_floor[7]) && (button[7] || call[7])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[8]) && on_floor[8]) && (button[8] || call[8])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[9]) && on_floor[9]) && (button[9] || call[9])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[10]) && on_floor[10]) && (button[10] || call[10])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[11]) && on_floor[11]) && (button[11] || call[11])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[12]) && on_floor[12]) && (button[12] || call[12])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[13]) && on_floor[13]) && (button[13] || call[13])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[14]) && on_floor[14]) && (button[14] || call[14])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[15]) && on_floor[15]) && (button[15] || call[15])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[16]) && on_floor[16]) && (button[16] || call[16])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((!(open[17]) && on_floor[17]) && (button[17] || call[17])) -> 
        door_timer = 4;
        timer_set = true;
    :: else -> ;
    fi
    if
    :: ((door_timer > 0) && !(timer_set)) -> 
        door_timer = (door_timer - 1);
    :: else -> ;
    fi
    on_some_floor = (on_some_floor || on_floor[0]);
    is_requested = ((is_requested || button[0]) || call[0]);
    open[0] = (open[0] && !(door_open[0]));
    on_some_floor = (on_some_floor || on_floor[1]);
    is_requested = ((is_requested || button[1]) || call[1]);
    open[1] = (open[1] && !(door_open[1]));
    on_some_floor = (on_some_floor || on_floor[2]);
    is_requested = ((is_requested || button[2]) || call[2]);
    open[2] = (open[2] && !(door_open[2]));
    on_some_floor = (on_some_floor || on_floor[3]);
    is_requested = ((is_requested || button[3]) || call[3]);
    open[3] = (open[3] && !(door_open[3]));
    on_some_floor = (on_some_floor || on_floor[4]);
    is_requested = ((is_requested || button[4]) || call[4]);
    open[4] = (open[4] && !(door_open[4]));
    on_some_floor = (on_some_floor || on_floor[5]);
    is_requested = ((is_requested || button[5]) || call[5]);
    open[5] = (open[5] && !(door_open[5]));
    on_some_floor = (on_some_floor || on_floor[6]);
    is_requested = ((is_requested || button[6]) || call[6]);
    open[6] = (open[6] && !(door_open[6]));
    on_some_floor = (on_some_floor || on_floor[7]);
    is_requested = ((is_requested || button[7]) || call[7]);
    open[7] = (open[7] && !(door_open[7]));
    on_some_floor = (on_some_floor || on_floor[8]);
    is_requested = ((is_requested || button[8]) || call[8]);
    open[8] = (open[8] && !(door_open[8]));
    on_some_floor = (on_some_floor || on_floor[9]);
    is_requested = ((is_requested || button[9]) || call[9]);
    open[9] = (open[9] && !(door_open[9]));
    on_some_floor = (on_some_floor || on_floor[10]);
    is_requested = ((is_requested || button[10]) || call[10]);
    open[10] = (open[10] && !(door_open[10]));
    on_some_floor = (on_some_floor || on_floor[11]);
    is_requested = ((is_requested || button[11]) || call[11]);
    open[11] = (open[11] && !(door_open[11]));
    on_some_floor = (on_some_floor || on_floor[12]);
    is_requested = ((is_requested || button[12]) || call[12]);
    open[12] = (open[12] && !(door_open[12]));
    on_some_floor = (on_some_floor || on_floor[13]);
    is_requested = ((is_requested || button[13]) || call[13]);
    open[13] = (open[13] && !(door_open[13]));
    on_some_floor = (on_some_floor || on_floor[14]);
    is_requested = ((is_requested || button[14]) || call[14]);
    open[14] = (open[14] && !(door_open[14]));
    on_some_floor = (on_some_floor || on_floor[15]);
    is_requested = ((is_requested || button[15]) || call[15]);
    open[15] = (open[15] && !(door_open[15]));
    on_some_floor = (on_some_floor || on_floor[16]);
    is_requested = ((is_requested || button[16]) || call[16]);
    open[16] = (open[16] && !(door_open[16]));
    on_some_floor = (on_some_floor || on_floor[17]);
    is_requested = ((is_requested || button[17]) || call[17]);
    open[17] = (open[17] && !(door_open[17]));
    need_stop = false;
    if
    :: !(on_some_floor) -> ;
    :: !(is_requested) -> 
        up = false;
        down = false;
    :: else -> 
        need_stop = ((need_stop || !(door_closed[0])) || (on_floor[0] && (button[0] || call[0])));
        open[0] = (open[0] || (on_floor[0] && (button[0] || call[0])));
        need_stop = ((need_stop || !(door_closed[1])) || (on_floor[1] && (button[1] || call[1])));
        open[1] = (open[1] || (on_floor[1] && (button[1] || call[1])));
        need_stop = ((need_stop || !(door_closed[2])) || (on_floor[2] && (button[2] || call[2])));
        open[2] = (open[2] || (on_floor[2] && (button[2] || call[2])));
        need_stop = ((need_stop || !(door_closed[3])) || (on_floor[3] && (button[3] || call[3])));
        open[3] = (open[3] || (on_floor[3] && (button[3] || call[3])));
        need_stop = ((need_stop || !(door_closed[4])) || (on_floor[4] && (button[4] || call[4])));
        open[4] = (open[4] || (on_floor[4] && (button[4] || call[4])));
        need_stop = ((need_stop || !(door_closed[5])) || (on_floor[5] && (button[5] || call[5])));
        open[5] = (open[5] || (on_floor[5] && (button[5] || call[5])));
        need_stop = ((need_stop || !(door_closed[6])) || (on_floor[6] && (button[6] || call[6])));
        open[6] = (open[6] || (on_floor[6] && (button[6] || call[6])));
        need_stop = ((need_stop || !(door_closed[7])) || (on_floor[7] && (button[7] || call[7])));
        open[7] = (open[7] || (on_floor[7] && (button[7] || call[7])));
        need_stop = ((need_stop || !(door_closed[8])) || (on_floor[8] && (button[8] || call[8])));
        open[8] = (open[8] || (on_floor[8] && (button[8] || call[8])));
        need_stop = ((need_stop || !(door_closed[9])) || (on_floor[9] && (button[9] || call[9])));
        open[9] = (open[9] || (on_floor[9] && (button[9] || call[9])));
        need_stop = ((need_stop || !(door_closed[10])) || (on_floor[10] && (button[10] || call[10])));
        open[10] = (open[10] || (on_floor[10] && (button[10] || call[10])));
        need_stop = ((need_stop || !(door_closed[11])) || (on_floor[11] && (button[11] || call[11])));
        open[11] = (open[11] || (on_floor[11] && (button[11] || call[11])));
        need_stop = ((need_stop || !(door_closed[12])) || (on_floor[12] && (button[12] || call[12])));
        open[12] = (open[12] || (on_floor[12] && (button[12] || call[12])));
        need_stop = ((need_stop || !(door_closed[13])) || (on_floor[13] && (button[13] || call[13])));
        open[13] = (open[13] || (on_floor[13] && (button[13] || call[13])));
        need_stop = ((need_stop || !(door_closed[14])) || (on_floor[14] && (button[14] || call[14])));
        open[14] = (open[14] || (on_floor[14] && (button[14] || call[14])));
        need_stop = ((need_stop || !(door_closed[15])) || (on_floor[15] && (button[15] || call[15])));
        open[15] = (open[15] || (on_floor[15] && (button[15] || call[15])));
        need_stop = ((need_stop || !(door_closed[16])) || (on_floor[16] && (button[16] || call[16])));
        open[16] = (open[16] || (on_floor[16] && (button[16] || call[16])));
        need_stop = ((need_stop || !(door_closed[17])) || (on_floor[17] && (button[17] || call[17])));
        open[17] = (open[17] || (on_floor[17] && (button[17] || call[17])));
        if
        :: need_stop -> 
            up = false;
            down = false;
        :: else -> 
            if
            :: (on_floor[1] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[0] || call[0])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[1] || call[1])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[1] || call[1])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[2] || call[2])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[2] || call[2])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[2] || call[2])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[3] || call[3])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[3] || call[3])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[3] || call[3])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[3] || call[3])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[4] || call[4])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[4] || call[4])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[4] || call[4])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[4] || call[4])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[4] || call[4])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[5] || call[5])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[5] || call[5])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[5] || call[5])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[5] || call[5])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[5] || call[5])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[5] || call[5])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[6] || call[6])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[6] || call[6])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[7] || call[7])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[7] || call[7])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[8] || call[8])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[8] || call[8])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[9] || call[9])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[9] || call[9])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[10] || call[10])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[10] || call[10])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[11] || call[11])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[11] || call[11])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[12] || call[12])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[12] || call[12])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[12] || call[12])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[12] || call[12])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[12] || call[12])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[12] || call[12])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[13] || call[13])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[13] || call[13])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[13] || call[13])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[13] || call[13])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[13] || call[13])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[14] || call[14])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[14] || call[14])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[14] || call[14])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[14] || call[14])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[15] || call[15])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[15] || call[15])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[15] || call[15])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[16] || call[16])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[17] && (button[16] || call[16])) -> 
                up = false;
                down = true;
            :: else -> ;
            fi
            if
            :: (on_floor[0] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[1] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[2] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[3] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[4] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[5] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[6] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[7] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[8] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[9] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[10] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[11] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[12] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[13] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[14] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[15] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
            if
            :: (on_floor[16] && (button[17] || call[17])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
        fi
    fi
    open[0] = (open[0] || (door_open[0] && (door_timer > 0)));
    open[1] = (open[1] || (door_open[1] && (door_timer > 0)));
    open[2] = (open[2] || (door_open[2] && (door_timer > 0)));
    open[3] = (open[3] || (door_open[3] && (door_timer > 0)));
    open[4] = (open[4] || (door_open[4] && (door_timer > 0)));
    open[5] = (open[5] || (door_open[5] && (door_timer > 0)));
    open[6] = (open[6] || (door_open[6] && (door_timer > 0)));
    open[7] = (open[7] || (door_open[7] && (door_timer > 0)));
    open[8] = (open[8] || (door_open[8] && (door_timer > 0)));
    open[9] = (open[9] || (door_open[9] && (door_timer > 0)));
    open[10] = (open[10] || (door_open[10] && (door_timer > 0)));
    open[11] = (open[11] || (door_open[11] && (door_timer > 0)));
    open[12] = (open[12] || (door_open[12] && (door_timer > 0)));
    open[13] = (open[13] || (door_open[13] && (door_timer > 0)));
    open[14] = (open[14] || (door_open[14] && (door_timer > 0)));
    open[15] = (open[15] || (door_open[15] && (door_timer > 0)));
    open[16] = (open[16] || (door_open[16] && (door_timer > 0)));
    open[17] = (open[17] || (door_open[17] && (door_timer > 0)));
}
