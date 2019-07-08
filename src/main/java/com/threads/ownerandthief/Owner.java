package com.threads.ownerandthief;
import java.sql.Time;
import java.util.ArrayList;
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

        try {
        while (sharedHouse.isThief() || sharedHouse.getNumberOwner() > 10) {
            wait();
        }
        l.lock();
        Random random = new Random();
        System.out.println("House size " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());
        sharedHouse.add(new Thing(random.nextInt(10), random.nextInt(15)));
        } catch (InterruptedException e){
            l.unlock();
            notifyAll();
        }
    }
}
