package com.threads.ownerandthief;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;

public class Owner implements Runnable {
    private final Home sharedHouse;
    private Lock l = new ReentrantLock();


    //private final Thing thing = new Thing(Math.random()*1, Math.random()private ReadWriteLock rwLock = new ReentrantReadWriteLock();*2);

    public Owner(Home sharedHouse) {
        this.sharedHouse = sharedHouse;
    }

    @Override
    public void run() {
        System.out.println("Try to add thing" + Thread.currentThread().getName());
        addThing();
        System.out.println("Owner was added the thing" + Thread.currentThread().getName());
    }
    public void addThing(){
        List<Thing> list = sharedHouse.getList();
        try {
            while (sharedHouse.isThief()) { //lock home for thief
                sharedHouse.wait();
            }
            sharedHouse.setUpNumber();
            Random random = new Random();
            System.out.println("House size " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());
            synchronized (sharedHouse) {
                sharedHouse.getList().add(new Thing(random.nextInt(10) + 1, random.nextInt(15)));
            }
            sharedHouse.setDownNumber();
            System.out.println("House size " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());
        } catch (InterruptedException e){
            notifyAll();
        }
    }
}
