package com.parking.simulation;
import java.util.ArrayList;
import java.util.List;

class Gate {
    private final int gateId;
    private int counter = 0; // counting cars that pass across each gate 
    private final List<Car> carQueue;

    public Gate(int gateId) {
        this.gateId = gateId;
        this.carQueue = new ArrayList<>();
    }

    public void addCar(Car car) {
        carQueue.add(car);
        counter++;
    }

    public List<Car> getCarQueue() {
        return carQueue;
    }
    public void displayDetails() {
        System.out.println("Gate " +  gateId + " served " + counter + " cars.");
    }
}
