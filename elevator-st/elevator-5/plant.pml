#define d_closed 0
#define d_opening 1
#define d_open 2
#define d_closing 3

d_step {
    if
    :: up -> 
        elevator_pos = (elevator_pos + 1);
    :: else -> ;
    fi
    if
    :: down -> 
        elevator_pos = (elevator_pos - 1);
    :: else -> ;
    fi
    if
    :: (elevator_pos > 12) -> 
        elevator_pos = 12;
    :: else -> ;
    fi
    if
    :: (elevator_pos < 0) -> 
        elevator_pos = 0;
    :: else -> ;
    fi
    on_floor[0] = (elevator_pos == 0);
    if
    :: open[0] -> 
        if
        :: ((door_state[0] == d_closed) || (door_state[0] == d_closing)) -> 
            door_state[0] = d_opening;
        :: else -> 
            door_state[0] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[0] == d_open) || (door_state[0] == d_opening)) -> 
            door_state[0] = d_closing;
        :: else -> 
            door_state[0] = d_closed;
        fi
    fi
    door_closed[0] = (door_state[0] == d_closed);
    door_open[0] = (door_state[0] == d_open);
    if
    :: (on_floor[0] && door_open[0]) -> 
        button[0] = false;
        call[0] = false;
    :: else -> 
        button[0] = (button[0] || user_floor_button[0]);
        call[0] = (call[0] || user_cabin_button[0]);
    fi
    on_floor[1] = (elevator_pos == 3);
    if
    :: open[1] -> 
        if
        :: ((door_state[1] == d_closed) || (door_state[1] == d_closing)) -> 
            door_state[1] = d_opening;
        :: else -> 
            door_state[1] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[1] == d_open) || (door_state[1] == d_opening)) -> 
            door_state[1] = d_closing;
        :: else -> 
            door_state[1] = d_closed;
        fi
    fi
    door_closed[1] = (door_state[1] == d_closed);
    door_open[1] = (door_state[1] == d_open);
    if
    :: (on_floor[1] && door_open[1]) -> 
        button[1] = false;
        call[1] = false;
    :: else -> 
        button[1] = (button[1] || user_floor_button[1]);
        call[1] = (call[1] || user_cabin_button[1]);
    fi
    on_floor[2] = (elevator_pos == 6);
    if
    :: open[2] -> 
        if
        :: ((door_state[2] == d_closed) || (door_state[2] == d_closing)) -> 
            door_state[2] = d_opening;
        :: else -> 
            door_state[2] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[2] == d_open) || (door_state[2] == d_opening)) -> 
            door_state[2] = d_closing;
        :: else -> 
            door_state[2] = d_closed;
        fi
    fi
    door_closed[2] = (door_state[2] == d_closed);
    door_open[2] = (door_state[2] == d_open);
    if
    :: (on_floor[2] && door_open[2]) -> 
        button[2] = false;
        call[2] = false;
    :: else -> 
        button[2] = (button[2] || user_floor_button[2]);
        call[2] = (call[2] || user_cabin_button[2]);
    fi
    on_floor[3] = (elevator_pos == 9);
    if
    :: open[3] -> 
        if
        :: ((door_state[3] == d_closed) || (door_state[3] == d_closing)) -> 
            door_state[3] = d_opening;
        :: else -> 
            door_state[3] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[3] == d_open) || (door_state[3] == d_opening)) -> 
            door_state[3] = d_closing;
        :: else -> 
            door_state[3] = d_closed;
        fi
    fi
    door_closed[3] = (door_state[3] == d_closed);
    door_open[3] = (door_state[3] == d_open);
    if
    :: (on_floor[3] && door_open[3]) -> 
        button[3] = false;
        call[3] = false;
    :: else -> 
        button[3] = (button[3] || user_floor_button[3]);
        call[3] = (call[3] || user_cabin_button[3]);
    fi
    on_floor[4] = (elevator_pos == 12);
    if
    :: open[4] -> 
        if
        :: ((door_state[4] == d_closed) || (door_state[4] == d_closing)) -> 
            door_state[4] = d_opening;
        :: else -> 
            door_state[4] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[4] == d_open) || (door_state[4] == d_opening)) -> 
            door_state[4] = d_closing;
        :: else -> 
            door_state[4] = d_closed;
        fi
    fi
    door_closed[4] = (door_state[4] == d_closed);
    door_open[4] = (door_state[4] == d_open);
    if
    :: (on_floor[4] && door_open[4]) -> 
        button[4] = false;
        call[4] = false;
    :: else -> 
        button[4] = (button[4] || user_floor_button[4]);
        call[4] = (call[4] || user_cabin_button[4]);
    fi
}
