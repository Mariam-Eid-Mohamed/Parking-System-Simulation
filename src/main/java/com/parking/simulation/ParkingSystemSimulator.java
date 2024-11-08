package com.parking.simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParkingSystemSimulator {

    public static void main(String[] args) {

        ParkingLot parkingLot = new ParkingLot();
        List<Thread> carThreads = new ArrayList<>();


        // Reading the input file from resources
        try (InputStream inputStream = ParkingSystemSimulator.class.getResourceAsStream("/input.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                System.err.println("Could not find input.txt file in resources.");
                return;
            }

            String line;
            while ((line = reader.readLine()) != null) {
               // System.out.println(line); // Print each line to verify it’s being read correctly
                String[] details = line.split(", ");
                int gate = Integer.parseInt(details[0].split(" ")[1]);
                int carId = Integer.parseInt(details[1].split(" ")[1]);
                int arrivalTime = Integer.parseInt(details[2].split(" ")[1]);
                int parkingDuration = Integer.parseInt(details[3].split(" ")[1]);

                Car car = new Car(carId, gate, arrivalTime, parkingDuration, parkingLot);
                Thread carThread = new Thread(car);
                carThreads.add(carThread);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        // Start all car threads
        for (Thread carThread : carThreads) {
            carThread.start();
        }

        // Wait for all car threads to finish
        for (Thread carThread : carThreads) {
            try {
                carThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Print final report
        parkingLot.printReport();
    }
}