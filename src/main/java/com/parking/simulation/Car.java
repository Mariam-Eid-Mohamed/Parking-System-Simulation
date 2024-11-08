package com.parking.simulation;

public class Car implements Runnable
{
    private final int carId;
    private final int gateId;
    private final int arriveTime;
    private final int parkedTime;
    private final ParkingLot parkingLot;

    public Car(int carId, int gateId, int arriveTime, int parkedTime,ParkingLot parkingLot)
    {
        this.carId = carId;
        this.gateId = gateId;
        this.arriveTime = arriveTime;
        this.parkedTime = parkedTime;
        this.parkingLot = parkingLot;
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
        if (parkingLot.getCarsCurrentlyParked() == parkingLot.totalSpots) {
            System.out.println("Car " + carId + " from Gate " + gateId + " waiting for a spot.");

            // Wait for a spot if the parking lot is full
            parkingLot.waitForSpot();
        }

        // parking a car
        parkingLot.tryToParkCar();

        System.out.println("Car " + carId + " from Gate " + gateId + " parked. " +
                "(Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");

        // simulate the time of parking
        try {
            Thread.sleep((long) parkedTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Releasing a car after parking duration
        parkingLot.releaseSpot();
        System.out.println("Car " + carId + " from Gate " + gateId + " left after " + parkedTime + " units of time. " +
                "(Parking Status: " + parkingLot.getCarsCurrentlyParked() + " spots occupied)");
       // System.out.println("Car " + carId + " is leaving.");
    }

}