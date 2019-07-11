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


    //private final Thing thing = new Thing(Math.random()*1, Math.random()private ReadWriteLock rwLock = new ReentrantReadWriteLock();*2);

    public Owner(Home sharedHouse, CyclicBarrier barrier1, Semaphore semOw, Semaphore semTh) {

        this.sharedHouse = sharedHouse;
        this.barrier1 = barrier1;
        this.semOw = semOw;
        this.semTh = semTh;
    }

    @Override
    public void run() {
        System.out.println("||O||Try to add thing" + Thread.currentThread().getName());
        addThing();
        System.out.println("||O||Owner was added the thing" + Thread.currentThread().getName());
    }
    public void addThing(){
        //List<Thing> list = sharedHouse.getList();
        try{
            this.barrier1.await();
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("||O||Owner thread check permits " + semOw.availablePermits() + " " + Thread.currentThread().getName());
            while (semTh.availablePermits()==0){
                try {
                    synchronized (sharedHouse) {
                        sharedHouse.wait();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            semOw.acquire();
            System.out.println("||O||Get available permits " + semOw.availablePermits());
            System.out.println("||O||Owner thread get acqure " + Thread.currentThread().getName() + "available permits" + semOw.availablePermits());
/*          sem.availablePermits();*/

            synchronized (sharedHouse) {
                System.out.println("||O||Owner sync " + sharedHouse.getPermits()+"Thread is "+Thread.currentThread().getName());
                if(sharedHouse.getPermits()==0){
                    sharedHouse.setPermits(5);
                }
                Random random = new Random();
                sharedHouse.getList().add(new Thing(random.nextInt(10) + 1, random.nextInt(15)));
                System.out.println("||O||List of things updated " + sharedHouse.size() + "Current thread" + Thread.currentThread().getName());
                sharedHouse.setPermits(sharedHouse.getPermits() - 1);
                sharedHouse.notifyAll();
            }

        } catch (InterruptedException e){
            e.printStackTrace();
            sharedHouse.notifyAll();
        }
        semOw.release();
    }
}
