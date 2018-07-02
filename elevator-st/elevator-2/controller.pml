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
            :: (on_floor[0] && (button[1] || call[1])) -> 
                up = true;
                down = false;
            :: else -> ;
            fi
        fi
    fi
    open[0] = (open[0] || (door_open[0] && (door_timer > 0)));
    open[1] = (open[1] || (door_open[1] && (door_timer > 0)));
}
