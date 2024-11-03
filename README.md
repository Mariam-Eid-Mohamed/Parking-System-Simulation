Parking System Simulation
Introduction
This project simulates a parking system using semaphores and threads in Java. The parking lot has a limited number of parking spots and three gates through which cars can enter. Each car arrives at a specific time, stays in the parking for a predetermined duration, and exits. The main challenge is to effectively manage parking spots among cars arriving at different gates using thread synchronization mechanisms.

Objectives
Thread Synchronization: Utilize threading and semaphores to manage access to the parking spots.
Concurrency Management: Ensure that the system handles concurrent arrivals and departures without errors.
Simulation Realism: Cars should arrive at specific times, and the simulation should reflect this timing accurately.
Status Reporting: Implement a feature to report the number of cars currently parked and the total number of cars served over time.
System Specifications
Parking Spots: 4 spots available in total.
Gates: 3 gates (Gate 1, Gate 2, Gate 3).
Car Arrival: Each gate will receive cars at different times, specified as per the arrival schedule.
Tasks
Setup Parking Lot: Create a parking lot with 4 parking spots.
Implement Gates: Simulate car arrivals at three different gates using separate threads.
Car Threads: Each car is represented by a thread that attempts to enter the parking lot.
Semaphores: Use semaphores to manage parking spot availability.
Logging and Reporting: Log the activity of each car and report the number of cars currently in the parking and the total served once the simulation ends.
Implementation Details
Thread Function: Each car’s thread will attempt to acquire a parking spot, stay for its duration, and then release the spot.
Arrival Times: Use sleep() to simulate arrival times.
Duration in Parking: Use sleep() to simulate the duration for which each car stays in the parking lot.
Concurrency Control: Use a semaphore to manage the parking spots and ensure there are no race conditions.
Input: Arrival times and durations are read and parsed from a text file.
Usage
To run the simulation:

Clone the repository.
Ensure you have Java installed.
Compile the Java files.
Run the main class.
