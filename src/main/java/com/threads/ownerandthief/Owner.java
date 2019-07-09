package com.threads.ownerandthief;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;

public class Owner implements Runnable {
    private final Home sharedHouse;
    CyclicBarrier barrier1 = null;


    //private final Thing thing = new Thing(Math.random()*1, Math.random()private ReadWriteLock rwLock = new ReentrantReadWriteLock();*2);

    public Owner(Home sharedHouse, CyclicBarrier barrier1) {

        this.sharedHouse = sharedHouse;
        this.barrier1 = barrier1;
    }

    @Override
    public void run() {
        System.out.println("Try to add thing" + Thread.currentThread().getName());
        addThing();
        System.out.println("Owner was added the thing" + Thread.currentThread().getName());
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
            System.out.println("Check number of owners " + sharedHouse.getNumberOwner().intValue() + " " + Thread.currentThread().getName());
            while (sharedHouse.isThief().get() || sharedHouse.getNumberOwner().get() > 5) { //lock home for thief and lock for more then 5 owners
                synchronized (sharedHouse){
                    sharedHouse.wait();
                }
            }
            System.out.println("Count of owners: " + sharedHouse.getNumberOwner().get()+" "+Thread.currentThread().getName());
            sharedHouse.getNumberOwner().incrementAndGet();
            Random random = new Random();
            System.out.println("House size B " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());
            synchronized (sharedHouse) {
                sharedHouse.getList().add(new Thing(random.nextInt(10) + 1, random.nextInt(15)));
                sharedHouse.getNumberOwner().decrementAndGet();
            }
//*                sharedHouse.notifyAll();
//*            }
        } catch (InterruptedException e){
            e.printStackTrace();
            sharedHouse.notifyAll();
        }
    }
}
