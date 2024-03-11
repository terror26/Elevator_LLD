package org.example.model;

import lombok.Data;

@Data
public class Elevator {
    private String id;
    private int currentFloor;
    private CurrentState state;
    public Elevator(String id, int currentFloor, CurrentState idle) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.state = idle;
    }
}
