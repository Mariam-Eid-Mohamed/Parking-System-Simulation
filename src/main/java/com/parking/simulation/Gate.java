package com.parking.simulation;

import java.util.ArrayList;
import java.util.List;

class Gate implements Runnable {
    private final int gateId;
    private final List<Car> carQueue;

    public Gate(int gateId) {
        this.gateId = gateId;
        this.carQueue = new ArrayList<>();
    }

    public void addCar(Car car) {
        carQueue.add(car);
    }

    @Override
    public void run() {
        // Start processing cars for this gate
        for (Car car : carQueue) {
            new Thread(car).start(); // Start a thread for each car arriving at the gate
        }
    }
}
