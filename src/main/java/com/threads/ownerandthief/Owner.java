package com.threads.ownerandthief;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

public class Owner implements Runnable {
    private final Home sharedHouse;
    private CyclicBarrier barrier1 = null;
    private Semaphore semOw = null;
    private Semaphore semTh = null;

    public Owner(Home sharedHouse, CyclicBarrier barrier1, Semaphore semOw, Semaphore semTh) {

        this.sharedHouse = sharedHouse;
        this.barrier1 = barrier1;
        this.semOw = semOw;
        this.semTh = semTh;
    }

    @Override
    public void run() {
        try{
            this.barrier1.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println("Owner try to add thing " + Thread.currentThread().getName());
        addThing();
        System.out.println("Owner have added the thing" + Thread.currentThread().getName());
    }
    public void addThing(){
        try {
            /*
            if Thief returned permit for home we can go through
             */
            while (semTh.availablePermits()==0){
                try {
                    synchronized (sharedHouse) {
                        sharedHouse.wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            /*
            5 owners can enter to home, semaphore have 5 permits
            */
            semOw.acquire();
            /*
            but only one owner can access to write list
             */
          synchronized (sharedHouse) {
                System.out.println("Owner sync " + semOw.availablePermits()+"Thread is "+Thread.currentThread().getName());
                Random random = new Random();
                sharedHouse.getList().add(new Thing(random.nextInt(10) + 1, random.nextInt(15)));
                System.out.println("List of things was updated " + sharedHouse.size() + "Current thread" + Thread.currentThread().getName());
                sharedHouse.notifyAll();
            }

        } catch (InterruptedException e){
            e.printStackTrace();
            sharedHouse.notifyAll();
        }
        semOw.release();
    }
}
