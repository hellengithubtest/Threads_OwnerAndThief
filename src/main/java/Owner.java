package com.threads.ownerandthief;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class Owner implements Runnable {
    private final Vector<Thing> sharedHouse;
    private final int size;

    public Owner(Vector sharedHouse, int size) {
        this.sharedHouse = sharedHouse;
        this.size = size;
    }

    @Override
    public void run() {
        for (double i = 0; i < 10; i++) {
            try {
                addSomeStaff(i);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }

    }
    private void addSomeStaff(double i) throws InterruptedException{
        System.out.println("House size " + sharedHouse.size());
        while (sharedHouse.size() == size){
            synchronized (sharedHouse){
                System.out.println("Queue is full " + Thread.currentThread().getName() + "is waiting, size: " +sharedHouse.size());
                sharedHouse.wait();
            }
        }
        synchronized (sharedHouse){
            double min = 1;
            double max = 100;
            sharedHouse.add(new Thing(Math.random() * max-min + i*2, Math.random() * max-min + i));
            sharedHouse.notifyAll();
            System.out.println("Add thing " + sharedHouse);
        }
    }
}