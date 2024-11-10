package com.parking.simulation;
import java.util.ArrayList;
import java.util.List;
//Represents each gate as a separate thread to handle car arrivals.
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
        for (Car car : carQueue) {
            new Thread(car).start();
        }
    }

    public List<Car> getCarQueue() {
        return carQueue;
    }
}
