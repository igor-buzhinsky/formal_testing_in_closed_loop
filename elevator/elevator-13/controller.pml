// Controller execution
#define _call(floor) (button[floor] || call[floor])
d_step {
    int floor;
    bool on_some_floor = false;
    bool is_requested = false;
    
    bool timer_set = false;
    for (floor : 0..(FLOORS - 1)) {
        if
        :: !open[floor] && on_floor[floor] && _call(floor) -> door_timer = 4; timer_set = true;
        :: else -> ;
        fi
    }
    door_timer = (door_timer > 0 & !timer_set -> door_timer - 1 : door_timer);
    
    for (floor : 0..(FLOORS - 1)) {
        on_some_floor = on_some_floor || on_floor[floor];
        is_requested = is_requested || _call(floor);
        // Close door if open
        open[floor] = (door_open[floor] -> false : open[floor]);
    }
    
    bool need_stop = false;
    
    if
    // Doesn't change direction while moving
    :: !on_some_floor -> ;
    // Stop if nowhere to go
    :: !is_requested -> up = false; down = false;
    :: else ->
        for (floor : 0..(FLOORS - 1)) {
            bool condition = on_floor[floor] && _call(floor);
            need_stop = need_stop || !door_closed[floor] || condition;
            open[floor] = open[floor] || condition;
        }
        
        if
        :: need_stop -> up = false; down = false;
        :: else ->
            int floor_cur;
            for (floor : 0..(FLOORS - 1)) {
                for (floor_cur : 0..(floor - 1)) {
                    if
                    :: on_floor[floor_cur] && _call(floor) -> up = true; down = false;
                    :: else -> ;
                    fi
                }
                for (floor_cur : (floor + 1)..(FLOORS - 1)) {
                    if
                    :: on_floor[floor_cur] && _call(floor) -> up = false; down = true;
                    :: else -> ;
                    fi
                }
            }
        fi
    fi
    
    for (floor : 0..(FLOORS - 1)) {
        open[floor] = (door_open[floor] && door_timer > 0 -> true : open[floor]);
    }
}