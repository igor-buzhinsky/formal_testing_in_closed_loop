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
    :: (elevator_pos > 39) -> 
        elevator_pos = 39;
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
    on_floor[5] = (elevator_pos == 15);
    if
    :: open[5] -> 
        if
        :: ((door_state[5] == d_closed) || (door_state[5] == d_closing)) -> 
            door_state[5] = d_opening;
        :: else -> 
            door_state[5] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[5] == d_open) || (door_state[5] == d_opening)) -> 
            door_state[5] = d_closing;
        :: else -> 
            door_state[5] = d_closed;
        fi
    fi
    door_closed[5] = (door_state[5] == d_closed);
    door_open[5] = (door_state[5] == d_open);
    if
    :: (on_floor[5] && door_open[5]) -> 
        button[5] = false;
        call[5] = false;
    :: else -> 
        button[5] = (button[5] || user_floor_button[5]);
        call[5] = (call[5] || user_cabin_button[5]);
    fi
    on_floor[6] = (elevator_pos == 18);
    if
    :: open[6] -> 
        if
        :: ((door_state[6] == d_closed) || (door_state[6] == d_closing)) -> 
            door_state[6] = d_opening;
        :: else -> 
            door_state[6] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[6] == d_open) || (door_state[6] == d_opening)) -> 
            door_state[6] = d_closing;
        :: else -> 
            door_state[6] = d_closed;
        fi
    fi
    door_closed[6] = (door_state[6] == d_closed);
    door_open[6] = (door_state[6] == d_open);
    if
    :: (on_floor[6] && door_open[6]) -> 
        button[6] = false;
        call[6] = false;
    :: else -> 
        button[6] = (button[6] || user_floor_button[6]);
        call[6] = (call[6] || user_cabin_button[6]);
    fi
    on_floor[7] = (elevator_pos == 21);
    if
    :: open[7] -> 
        if
        :: ((door_state[7] == d_closed) || (door_state[7] == d_closing)) -> 
            door_state[7] = d_opening;
        :: else -> 
            door_state[7] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[7] == d_open) || (door_state[7] == d_opening)) -> 
            door_state[7] = d_closing;
        :: else -> 
            door_state[7] = d_closed;
        fi
    fi
    door_closed[7] = (door_state[7] == d_closed);
    door_open[7] = (door_state[7] == d_open);
    if
    :: (on_floor[7] && door_open[7]) -> 
        button[7] = false;
        call[7] = false;
    :: else -> 
        button[7] = (button[7] || user_floor_button[7]);
        call[7] = (call[7] || user_cabin_button[7]);
    fi
    on_floor[8] = (elevator_pos == 24);
    if
    :: open[8] -> 
        if
        :: ((door_state[8] == d_closed) || (door_state[8] == d_closing)) -> 
            door_state[8] = d_opening;
        :: else -> 
            door_state[8] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[8] == d_open) || (door_state[8] == d_opening)) -> 
            door_state[8] = d_closing;
        :: else -> 
            door_state[8] = d_closed;
        fi
    fi
    door_closed[8] = (door_state[8] == d_closed);
    door_open[8] = (door_state[8] == d_open);
    if
    :: (on_floor[8] && door_open[8]) -> 
        button[8] = false;
        call[8] = false;
    :: else -> 
        button[8] = (button[8] || user_floor_button[8]);
        call[8] = (call[8] || user_cabin_button[8]);
    fi
    on_floor[9] = (elevator_pos == 27);
    if
    :: open[9] -> 
        if
        :: ((door_state[9] == d_closed) || (door_state[9] == d_closing)) -> 
            door_state[9] = d_opening;
        :: else -> 
            door_state[9] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[9] == d_open) || (door_state[9] == d_opening)) -> 
            door_state[9] = d_closing;
        :: else -> 
            door_state[9] = d_closed;
        fi
    fi
    door_closed[9] = (door_state[9] == d_closed);
    door_open[9] = (door_state[9] == d_open);
    if
    :: (on_floor[9] && door_open[9]) -> 
        button[9] = false;
        call[9] = false;
    :: else -> 
        button[9] = (button[9] || user_floor_button[9]);
        call[9] = (call[9] || user_cabin_button[9]);
    fi
    on_floor[10] = (elevator_pos == 30);
    if
    :: open[10] -> 
        if
        :: ((door_state[10] == d_closed) || (door_state[10] == d_closing)) -> 
            door_state[10] = d_opening;
        :: else -> 
            door_state[10] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[10] == d_open) || (door_state[10] == d_opening)) -> 
            door_state[10] = d_closing;
        :: else -> 
            door_state[10] = d_closed;
        fi
    fi
    door_closed[10] = (door_state[10] == d_closed);
    door_open[10] = (door_state[10] == d_open);
    if
    :: (on_floor[10] && door_open[10]) -> 
        button[10] = false;
        call[10] = false;
    :: else -> 
        button[10] = (button[10] || user_floor_button[10]);
        call[10] = (call[10] || user_cabin_button[10]);
    fi
    on_floor[11] = (elevator_pos == 33);
    if
    :: open[11] -> 
        if
        :: ((door_state[11] == d_closed) || (door_state[11] == d_closing)) -> 
            door_state[11] = d_opening;
        :: else -> 
            door_state[11] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[11] == d_open) || (door_state[11] == d_opening)) -> 
            door_state[11] = d_closing;
        :: else -> 
            door_state[11] = d_closed;
        fi
    fi
    door_closed[11] = (door_state[11] == d_closed);
    door_open[11] = (door_state[11] == d_open);
    if
    :: (on_floor[11] && door_open[11]) -> 
        button[11] = false;
        call[11] = false;
    :: else -> 
        button[11] = (button[11] || user_floor_button[11]);
        call[11] = (call[11] || user_cabin_button[11]);
    fi
    on_floor[12] = (elevator_pos == 36);
    if
    :: open[12] -> 
        if
        :: ((door_state[12] == d_closed) || (door_state[12] == d_closing)) -> 
            door_state[12] = d_opening;
        :: else -> 
            door_state[12] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[12] == d_open) || (door_state[12] == d_opening)) -> 
            door_state[12] = d_closing;
        :: else -> 
            door_state[12] = d_closed;
        fi
    fi
    door_closed[12] = (door_state[12] == d_closed);
    door_open[12] = (door_state[12] == d_open);
    if
    :: (on_floor[12] && door_open[12]) -> 
        button[12] = false;
        call[12] = false;
    :: else -> 
        button[12] = (button[12] || user_floor_button[12]);
        call[12] = (call[12] || user_cabin_button[12]);
    fi
    on_floor[13] = (elevator_pos == 39);
    if
    :: open[13] -> 
        if
        :: ((door_state[13] == d_closed) || (door_state[13] == d_closing)) -> 
            door_state[13] = d_opening;
        :: else -> 
            door_state[13] = d_open;
        fi
    :: else -> 
        if
        :: ((door_state[13] == d_open) || (door_state[13] == d_opening)) -> 
            door_state[13] = d_closing;
        :: else -> 
            door_state[13] = d_closed;
        fi
    fi
    door_closed[13] = (door_state[13] == d_closed);
    door_open[13] = (door_state[13] == d_open);
    if
    :: (on_floor[13] && door_open[13]) -> 
        button[13] = false;
        call[13] = false;
    :: else -> 
        button[13] = (button[13] || user_floor_button[13]);
        call[13] = (call[13] || user_cabin_button[13]);
    fi
}
