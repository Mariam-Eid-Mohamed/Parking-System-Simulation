package com.parking.simulation;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Logger;

public class Car implements Runnable{
    private int carId;
    private int gateId;
    private int arriveTime;
    private int parkedTime;
    private static ParkingLot parkingLot = new ParkingLot();
    public Car(int carId, int gateId, int arriveTime, int parkedTime) {
        this.carId = carId;
        this.gateId = gateId;
        this.arriveTime = arriveTime;
        this.parkedTime = parkedTime;
    }

    @Override
    public void run() {
        // simulate the arrival time before parking
        try {
            Thread.sleep((long) arriveTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // display car ID & gateID & arrival time of a car
        System.out.println("Car " + carId + " from Gate " + gateId + " arrived at time " +  arriveTime);
        
        // send waiting message if the Park Lot is full
        if (parkingLot.getCarsCurrentlyParked() == 4)
            System.out.println("Car " + carId + " from Gate " + gateId + " waiting for a spot.");
        
        // parking a car
        parkingLot.tryToParkCar();

        // simulate the time of parking
        try {
            Thread.sleep((long) parkedTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // releasing a car after the duration of parking
        System.out.println("Car " + carId + " from Gate " + gateId + " parked. " +
                "(parking status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");

        parkingLot.releaseSpot();
        System.out.println("Car " + carId + " is leaving.");
    }
}
