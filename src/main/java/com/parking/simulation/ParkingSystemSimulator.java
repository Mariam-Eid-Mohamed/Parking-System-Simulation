package com.parking.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ParkingSystemSimulator {

    public static void main(String[] args) {

        ParkingLot parkingLot = new ParkingLot();
        List<Gate> gates = new ArrayList<>();
        List<Thread> carThreads = new ArrayList<>();
        
        // Create gates
        for (int i = 1; i <= 3; i++) {
            gates.add(new Gate(i));
        }

        // Reading input file from resources
        try (InputStream inputStream = ParkingSystemSimulator.class.getResourceAsStream("/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                System.err.println("Could not find input.txt file in resources.");
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(", ");
                int gateId = Integer.parseInt(details[0].split(" ")[1]);
                int carId = Integer.parseInt(details[1].split(" ")[1]);
                int arriveTime = Integer.parseInt(details[2].split(" ")[1]);
                int parkedTime = Integer.parseInt(details[3].split(" ")[1]);

                Car car = new Car(carId, gateId, arriveTime, parkedTime, parkingLot);
                gates.get(gateId - 1).addCar(car); // Assign the car to the appropriate gate
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Start each car in its own thread and add to carThreads list
        for (Gate gate : gates) {
            for (Car car : gate.getCarQueue) {
                Thread carThread = new Thread(car);
                carThreads.add(carThread);
                carThread.start();
            }
        }

        // Wait for all car threads to finish
        for (Thread carThread : carThreads) {
            carThread.join();
        }

        // Print the final parking report after all threads are completed
        parkingLot.printReport();
    }
}
