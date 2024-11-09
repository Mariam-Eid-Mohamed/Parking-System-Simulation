package com.parking.simulation;

public class Car implements Runnable {
    private final int carId;
    private final int gateId;
    private final int arriveTime;
    private final int parkedTime;
    private final ParkingLot parkingLot;

    public Car(int carId, int gateId, int arriveTime, int parkedTime, ParkingLot parkingLot) {
        this.carId = carId;
        this.gateId = gateId;
        this.arriveTime = arriveTime;
        this.parkedTime = parkedTime;
        this.parkingLot = parkingLot;
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
            parkingLot.waitForSpot(); // Wait until a spot becomes available
        }

        // Park the car
        parkingLot.tryToParkCar();
        System.out.println("Car " + carId + " from Gate " + gateId + " parked. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");

        // Simulate parking duration
        try {
            Thread.sleep(parkedTime * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Release the spot after parking duration
        parkingLot.releaseSpot();
        System.out.println("Car " + carId + " from Gate " + gateId + " left after " + parkedTime + " units of time. (Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
    }
}
