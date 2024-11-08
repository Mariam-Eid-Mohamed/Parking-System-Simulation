package com.parking.simulation;
import java.util.concurrent.Semaphore;
import java.util.concurrent.LinkedBlockingQueue;

    public class ParkingLot {
        final int totalSpots = 4; // Total parking spots
        private int carsCurrentlyParked = 0;
        private int totalCarsServed = 0;
        private final Semaphore parkingSpots; // Semaphore to manage access
        private final LinkedBlockingQueue<Car> waitingQueue; // Queue for waiting cars

        public ParkingLot() {
            parkingSpots = new Semaphore(totalSpots);
            waitingQueue = new LinkedBlockingQueue<>();
        }


    public boolean tryToParkCar()
    {
        try {
            // Attempt to acquire a spot
            parkingSpots.acquire();
            synchronized (this) {
                carsCurrentlyParked++;
            }
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted status
            return false;
        }
    }

    public void releaseSpot() {
        synchronized (this) {
            carsCurrentlyParked--;
            totalCarsServed++;
        }
        parkingSpots.release();
        notifyWaitingCars();
    }
//
//    public synchronized void releaseSpot() {
//        carsCurrentlyParked--;
//        totalCarsServed++;
//        parkingSpots.release();
//        notifyWaitingCars();
//    }

    public synchronized void waitForSpot() {
        while (carsCurrentlyParked == totalSpots) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized int getCarsCurrentlyParked() {
        return carsCurrentlyParked;
    }

    public synchronized int getTotalCarsServed() {
        return totalCarsServed;
    }

    public synchronized int getWaitingQueueSize() {
        return waitingQueue.size();
    }

    // Notify all waiting cars that a spot is now available
    public synchronized void notifyWaitingCars() {
        notifyAll();
    }

    public void printReport() {
        System.out.println("Total cars served: " + getTotalCarsServed());
        System.out.println("Cars currently parked: " + getCarsCurrentlyParked());
    }
}