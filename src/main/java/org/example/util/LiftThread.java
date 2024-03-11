package org.example.util;

import org.example.service.ElevatorController;

import java.util.List;

public class LiftThread implements Runnable{
    private ElevatorController elevatorController;

    public LiftThread(ElevatorController elevatorController) {
        this.elevatorController = elevatorController;
    }

    public void run() {
        try {
            while(true) {
                elevatorController.moveLift();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
