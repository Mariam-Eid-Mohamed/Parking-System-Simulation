package com.parking.simulation;

public class Car implements Runnable {
    private final int carId;
    private final int gateId;
    private final int arriveTime;
    private final int parkedTime;
    private int waitingTime;
    private long waitingStartTime;
    private final ParkingLot parkingLot;


    public Car(int carId, int gateId, int arriveTime, int parkedTime, ParkingLot parkingLot) {
        this.carId = carId;
        this.gateId = gateId;
        this.arriveTime = arriveTime;
        this.parkedTime = parkedTime;
        this.parkingLot = parkingLot;
        this.waitingTime = 0;
    }
    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }
    // Getter for carId
    public int getCarId() {
        return this.carId;
    }

    public int getGateId() {
        return this.gateId;
    }
    public int getParkedTime(){
        return this.parkedTime;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(arriveTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Car " + carId + " from Gate " + gateId + " arrived at time " + arriveTime);

        if (parkingLot.getCarsCurrentlyParked() == parkingLot.totalSpots)
        {
            System.out.println("Car " + carId + " from Gate " + gateId + " waiting for a spot.");
            this.waitingStartTime = System.currentTimeMillis();
            parkingLot.waitForSpot();
        }

        parkingLot.tryToParkCar(this);

        if (waitingStartTime > 0)
        {
            long currentTime = System.currentTimeMillis();
           waitingTime = (int) Math.ceil((currentTime - waitingStartTime) / 1000.0);

           // System.out.println("\nWaiting Start Time: " + waitingStartTime + ", Current Time: " + currentTime);
           // System.out.println("\nCalculated Waiting Time: " + waitingTime);
            System.out.println("Car " + carId + " from Gate " + gateId + " parked after waiting for " + waitingTime + " units of time. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
        }

        else
        {
            System.out.println("Car " + carId + " from Gate " + gateId + " parked. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
        }

        try {
            Thread.sleep(parkedTime * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        parkingLot.releaseSpot(this);

    }
}