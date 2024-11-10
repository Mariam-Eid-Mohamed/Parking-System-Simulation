package com.parking.simulation;

public class Semaphore {
    private  int permits;

    public Semaphore(int initialPermits) {
        this.permits = initialPermits;
    }
    public synchronized void acquire() throws InterruptedException {
        // Wait if no permits are available
        while (permits <= 0) {
            wait();
        }
        // A permit is taken, reduce the count
        permits--;
    }
    public synchronized boolean tryAcquire() {
        if (permits > 0) {
            permits--;
            return true;
        }
        return false;
    }
    // Release a permit
    public synchronized void release() {
        permits++;
        // Notify one waiting thread
        notify();
    }
}