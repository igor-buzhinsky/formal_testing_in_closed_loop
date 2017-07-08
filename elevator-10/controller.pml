// Controller execution
d_step {
    int floor;
    bool on_some_floor = false;
    bool is_requested = false;
    
    for (floor : 0..(FLOORS - 1)) {
        on_some_floor = on_some_floor || on_floor[floor];
        is_requested = is_requested || button[floor] || call[floor];
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
            need_stop = need_stop || !door_closed[floor] || on_floor[floor] && (button[floor] || call[floor]);
            open[floor] = open[floor] || on_floor[floor] && (button[floor] || call[floor]);
        }
        
        if
        :: need_stop -> up = false; down = false;
        :: else ->
            int floor_cur;
            for (floor : 0..(FLOORS - 1)) {
                for (floor_cur : 0..(floor - 1)) {
                    if
                    :: on_floor[floor_cur] && (button[floor] || call[floor]) -> up = true; down = false;
                    :: else -> ;
                    fi
                }
                for (floor_cur : (floor + 1)..(FLOORS - 1)) {
                    if
                    :: on_floor[floor_cur] && (button[floor] || call[floor]) -> up = false; down = true;
                    :: else -> ;
                    fi
                }
            }
        fi
    fi
}