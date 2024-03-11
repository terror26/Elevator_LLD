package org.example.service;

import lombok.Data;
import org.example.model.CurrentState;
import org.example.model.Elevator;
import org.example.util.LiftThread;

import java.util.ArrayList;
import java.util.List;

@Data
public class ElevatorController {
    private Elevator elevator;
    List<Integer> destinations = new ArrayList<>();

    public ElevatorController(Elevator elevator) {
        this.elevator = elevator;
    }

    public void addDestination(int floor) {
        destinations.add(floor);
        System.out.println("addDestination -> " + floor);
    }

    public void startLiftThread() {
        LiftThread liftThread = new LiftThread(this);
        Thread thread = new Thread(liftThread);
        thread.start();
        ;
    }

    public void moveLift() {
        if (destinations.size() > 0) {
            setState(elevator.getState());

            int nxtFloor = elevator.getCurrentFloor();
            if (elevator.getState() == CurrentState.UP) {
                nxtFloor++;
            } else {
                nxtFloor--;
            }
            System.out.println("Lift moving from " + elevator.getCurrentFloor() + " to " + nxtFloor);
            elevator.setCurrentFloor(nxtFloor);
            removeDestination(nxtFloor);
        } else {
            elevator.setState(CurrentState.IDLE);
        }
    }

    private void removeDestination(int currentFloor) {
        for (int i = 0; i < destinations.size(); i++) {
            if (destinations.get(i) == currentFloor) {
                destinations.remove(i); // remove the floor if reached
                System.out.println("REACHED: destination floor = " + currentFloor);
            }
        }
    }

    private void setState(CurrentState currentState) {
        if (destinations.size() == 0) return; // safe case

        int destination = destinations.get(0);
        int currentFloor = elevator.getCurrentFloor();
        CurrentState nxtState = elevator.getState();
        if (currentState == CurrentState.IDLE) { // should be changed
            if (currentFloor < destination) {
                nxtState = CurrentState.UP;
            } else {
                nxtState = CurrentState.DOWN;
            }
        } else {
            // check if directionChange is required
            if (CurrentState.UP.equals(currentState) && !containsHigherFloor(destinations, currentFloor)) { // check if directionChange is required
                nxtState = CurrentState.DOWN;
            } else if (CurrentState.DOWN.equals(currentState) && !containsLowerFloor(destinations, currentFloor)) {
                nxtState = CurrentState.UP;
            }
        }
        System.out.println("State set from " + elevator.getState() + " to state " + nxtState);
        elevator.setState(nxtState);
    }

    private boolean containsLowerFloor(List<Integer> destinations, int currentFloor) {
        int n = destinations.size();
        for (int i = 0; i < n; i++) {
            if (destinations.get(i) < currentFloor) {
                return true;
            }
        }
        return false;
    }

    private boolean containsHigherFloor(List<Integer> destinations, int currentFloor) {
        int n = destinations.size();
        for (int i = 0; i < n; i++) {
            if (destinations.get(i) > currentFloor) {
                return true;
            }
        }
        return false;

    }
}
