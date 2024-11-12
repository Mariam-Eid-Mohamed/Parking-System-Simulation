package com.parking.simulation;

import java.util.Queue;
import java.util.LinkedList;

public class ParkingLot {
    final int totalSpots = 4;
    private int carsCurrentlyParked = 0;
    private int totalServedCars = 0;
    private final Semaphore parkingSpots;
    private final Queue<Car> waitingQueue;

    public ParkingLot()
    {
        parkingSpots = new Semaphore(totalSpots);
        waitingQueue = new LinkedList<>();

    }

    public synchronized boolean tryToParkCar(Car car)
    {
        long waitingStartTime = System.currentTimeMillis();
        boolean parked = false;

        try {

            if (!parkingSpots.tryAcquire())
            {
                System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateId() + " waiting for a spot.");
                waitingQueue.add(car);
            }
            else
            {
                int waitingTime = (int) ((System.currentTimeMillis() - waitingStartTime) / 1000);
                car.setWaitingTime(waitingTime);
                carsCurrentlyParked++;
                parked = true;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return parked;
    }

    public synchronized void releaseSpot(Car car)
    {
        carsCurrentlyParked--;
        totalServedCars++;
        parkingSpots.release();

        System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateId()
                                  + " has left after parking for " + car.getParkedTime()
                                  + " units of time. (Parking Status: " + carsCurrentlyParked  + " spots occupied)");
        if (!waitingQueue.isEmpty())
        {
            Car nextCar = waitingQueue.poll();
            tryToParkCar(nextCar);
        }
        notifyAll();
    }

    public synchronized void waitForSpot() {
        while (carsCurrentlyParked == totalSpots) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public synchronized void notifyWaitingCars() {
        notifyAll();
    }

    public synchronized int getCarsCurrentlyParked() {
        return carsCurrentlyParked;
    }

    public void printReport() {
        System.out.println("Total cars served: " + totalServedCars);
        System.out.println("Current Cars in Parking: " + carsCurrentlyParked);
    }
}
