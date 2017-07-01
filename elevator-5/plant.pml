// Plant execution
d_step {
    elevator_pos = elevator_pos + up - down;
    elevator_pos = (elevator_pos > 2 * FLOORS -> 2 * FLOORS : elevator_pos);
    elevator_pos = (elevator_pos < 0 -> 0 : elevator_pos);
    
    int floor;
    for (floor : 0..(FLOORS - 1)) {
        on_floor[floor] = elevator_pos == 2 * floor;
        door_state[floor] = (open[floor] -> (door_state[floor] == d_closed || door_state[floor] == d_closing -> d_opening : d_open) : (door_state[floor] == d_open || door_state[floor] == d_opening -> d_closing : d_closed));
        door_closed[floor] = door_state[floor] == d_closed;
        door_open[floor] = door_state[floor] == d_open;
        button[floor] = (on_floor[floor] && door_open[floor] -> false : (user_floor_button[floor] -> true : button[floor]));
        call[floor] = (on_floor[floor] && door_open[floor] -> false : (user_cabin_button[floor] -> true : call[floor]));
    }
}