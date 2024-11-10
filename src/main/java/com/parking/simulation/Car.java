package com.parking.simulation;

public class Car implements Runnable {
    private final int carId;
    private final int gateId;
    private final int arriveTime;
    private final int parkedTime;
    private int waitingTime;
    private long waitingStartTime; // New field to capture the start of waiting time
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
        // Simulate the arrival time before parking
        try {
            Thread.sleep(arriveTime * 1000); // Sleep until the car arrives
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Display car arrival details
        System.out.println("Car " + carId + " from Gate " + gateId + " arrived at time " + arriveTime);

        // Send waiting message if the parking lot is full
        if (parkingLot.getCarsCurrentlyParked() == parkingLot.totalSpots) {
            System.out.println("Car " + carId + " from Gate " + gateId + " waiting for a spot.");
            this.waitingStartTime = System.currentTimeMillis(); // Record start time of waiting
            parkingLot.waitForSpot(); // Wait until a spot becomes available
        }

        // Park the car
        parkingLot.tryToParkCar(this);
        // Calculate waiting time if the car had to wait
        if (waitingStartTime > 0) {
            long currentTime = System.currentTimeMillis();
            waitingTime = (int) ((currentTime - waitingStartTime) / 1000); // Convert to seconds
            System.out.println("Car " + carId + " from Gate " + gateId + " parked after waiting for " + waitingTime + " units of time. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
        } else {
            System.out.println("Car " + carId + " from Gate " + gateId + " parked. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
        }

        // Simulate parking duration
        try {
            Thread.sleep(parkedTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Release the spot after parking duration
        parkingLot.releaseSpot(this);




    }
}