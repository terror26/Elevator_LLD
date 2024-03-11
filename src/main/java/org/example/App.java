package org.example;

import org.example.model.CurrentState;
import org.example.model.Elevator;
import org.example.service.ElevatorController;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        new App().run();

    }

    private void run() throws InterruptedException {
        Elevator elevator = new Elevator("id1", 0, CurrentState.IDLE); // id and currentfloor and state
        ElevatorController elevatorController = new ElevatorController(elevator);
        elevatorController.startLiftThread();

        elevatorController.addDestination(10); // move up
        Thread.sleep(1000);
        elevatorController.addDestination(1); // if not reached then go come back from 10
        Thread.sleep(1000);
        elevatorController.addDestination(2); // if not reached then complete on way back


    }
}
