package com.parking.simulation;
//Represents each gate as a separate thread to handle car arrivals.
class Gate implements Runnable {
    private int gateId;
    private List<Car> carQueue;

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
}
