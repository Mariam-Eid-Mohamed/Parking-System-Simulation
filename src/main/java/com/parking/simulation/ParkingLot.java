package com.parking.simulation;

import java.util.concurrent.Semaphore;
import java.util.Queue;
import java.util.LinkedList;

public class ParkingLot {
    final int totalSpots = 4; // Total parking spots
    private int carsCurrentlyParked = 0;
    private final Semaphore parkingSpots; // Semaphore to manage access to parking spots
    private final Queue<Car> waitingQueue;  // Queue to manage waiting cars

    public ParkingLot() {
        parkingSpots = new Semaphore(totalSpots);
        waitingQueue = new LinkedList<>();

    }

    // Synchronized method to manage parking, waiting, and logging accurately
    public synchronized boolean tryToParkCar(Car car) {
        long waitingStartTime = System.currentTimeMillis();
        boolean parked = false;

        try {
            // If parking spots are not available, the car joins the waiting queue
            if (!parkingSpots.tryAcquire()) {  // Check if the car needs to wait
                System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateId() + " waiting for a spot.");
                waitingQueue.add(car);  // Add car to the queue to wait for a spot
            } else {
                // If there is a spot, park the car immediately
                int waitingTime = (int) ((System.currentTimeMillis() - waitingStartTime) / 1000);
                car.setWaitingTime(waitingTime);  // Store waiting time in the car object
                carsCurrentlyParked++;



                parked = true;
            }
        }
        catch (Exception e) {
            // Handle other exceptions (though unlikely in this context)
            e.printStackTrace();
        }

        return parked;
    }

    // Synchronized method to release a parking spot and log the status
    public synchronized void releaseSpot(Car car) {
        carsCurrentlyParked--;
        parkingSpots.release();

        System.out.println("Car " + car.getCarId() + " from Gate " + car.getGateId() + " has left after parking for " + car.getParkedTime() + " units of time. (Parking Status: " + carsCurrentlyParked  + " spots occupied)");
        // After releasing a spot, check if there are any waiting cars
        if (!waitingQueue.isEmpty()) {
            // Give the next car in the queue a chance to park
            Car nextCar = waitingQueue.poll();  // Remove the first car from the queue
            tryToParkCar(nextCar);  // Try to park this car
        }
        notifyAll();
    }

    // Wait for a parking spot to become available
    public synchronized void waitForSpot() {
        while (carsCurrentlyParked == totalSpots) {
            try {
                wait(); // Wait until a parking spot becomes available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Notify waiting cars that a spot is available
    public synchronized void notifyWaitingCars() {
        notifyAll(); // Notify all waiting cars
    }

    // Get the number of cars currently parked
    public synchronized int getCarsCurrentlyParked() {
        return carsCurrentlyParked;
    }

    // Print the current parking status
    public void printReport() {
        System.out.println("Total cars served: " + carsCurrentlyParked);
    }
}