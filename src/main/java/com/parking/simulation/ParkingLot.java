package com.parking.simulation;

import java.util.concurrent.Semaphore;

public class ParkingLot {
    final int totalSpots = 4; // Total parking spots
    private int carsCurrentlyParked = 0;
    private final Semaphore parkingSpots; // Semaphore to manage access to parking spots

    public ParkingLot() {
        parkingSpots = new Semaphore(totalSpots);
    }

    // Try to park a car
    public boolean tryToParkCar() {
        try {
            parkingSpots.acquire(); // Try to acquire a spot
            synchronized (this) {
                carsCurrentlyParked++; // Increment the count of cars currently parked
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // Release a parking spot after a car leaves
    public void releaseSpot() {
        synchronized (this) {
            carsCurrentlyParked--;
        }
        parkingSpots.release(); // Release the parking spot
        notifyWaitingCars();
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
