package com.parking.simulation;
import java.util.ArrayList;
import java.util.List;
//Represents each gate as a separate thread to handle car arrivals.
class Gate implements Runnable {
    private final int gateId;
    private int totalServedCars;
    private final List<Car> carQueue;

    public Gate(int gateId) {
        this.gateId = gateId;
        this.carQueue = new ArrayList<>();
        this.totalServedCars = 0;
    }

    public void addCar(Car car) {
        carQueue.add(car);
        totalServedCars++;
    }

    @Override
    public void run() {
        for (Car car : carQueue) {
            new Thread(car).start();
        }
    }

    public void displayTotalServedCars() {
        System.out.println("Gate " + gateId + " served " + totalServedCars + " cars.");
    }
}
