package com.parking.simulation;
import java.util.concurrent.Semaphore;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;
public class ParkingLot {
    private final int totalSpots = 4; // Total parking spots
    private int carsCurrentlyParked = 0;
    private int totalCarsServed = 0;
    private final Semaphore parkingSpots; // Semaphore to manage access
    private final LinkedBlockingQueue<Car> waitingQueue; // Queue for waiting cars
    private static final Logger logger = Logger.getLogger(ParkingLot.class.getName());

    public ParkingLot() {
        parkingSpots = new Semaphore(totalSpots);
        waitingQueue = new LinkedBlockingQueue<>();
    }

    public boolean tryToParkCar() {
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
    }
    // Method to park the waiting cars once a spot is free
//    public void parkWaitingCar() throws InterruptedException {
//        Car car = waitingQueue.take();  // Blocks if the queue is empty
//        tryToParkCar(car);
//    }

    public synchronized int getCarsCurrentlyParked() {
        return carsCurrentlyParked;
    }
    public synchronized int getTotalCarsServed() {
        return totalCarsServed;
    }
    public synchronized int getWaitingQueueSize() {
        return waitingQueue.size();
    }
}
