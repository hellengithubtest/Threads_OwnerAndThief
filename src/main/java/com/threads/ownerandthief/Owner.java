package com.threads.ownerandthief;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Callable;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Owner implements Callable {
    private final Home sharedHouse;
    private Backpack ownerBackpack;
    private CyclicBarrier barrier = null;
    private Semaphore semaphoreForOwners = null;
    private Semaphore semaphoreForThieves = null;

    public Owner(Home sharedHouse, CyclicBarrier barrier, Semaphore semaphoreForOwners, Semaphore semaphoreForThieves) {
        int totalWeight = 10;
        this.sharedHouse = sharedHouse;
        this.ownerBackpack = new Backpack(totalWeight);
        this.barrier = barrier;
        this.semaphoreForOwners = semaphoreForOwners;
        this.semaphoreForThieves = semaphoreForThieves;
    }

    @Override
    public List<Thing> call() {
        try {
            this.barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        System.out.println("Owner try to add thing " + Thread.currentThread().getName());
        addThing();
        System.out.println("Owner have added the thing" + Thread.currentThread().getName());
        return ownerBackpack.getList();
    }

    public void addThing() {
        try {
            /*
            if Thief returned permit for home we can go through
             */
            while (semaphoreForThieves.availablePermits() == 0) {
                try {
                    synchronized (sharedHouse) {
                        sharedHouse.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
            5 owners can enter to home, semaphore have 5 permits
            */
            System.out.println("Owner before sync " + Thread.currentThread().getName());
            synchronized (sharedHouse) {
                semaphoreForOwners.acquire();
            }
            /*
            but only one owner can access to write list
             */
            System.out.println("Owner after sync " + Thread.currentThread().getName() + " Permits owner" + semaphoreForOwners.availablePermits() + " Permits thief " + semaphoreForThieves.availablePermits());
            List<Thing> before = sharedHouse.getList();
            System.out.println("Owner LIST " + before + Thread.currentThread().getName());
            sharedHouse.addThings(this.ownerBackpack.getList());
            ownerBackpack.getList().clear();
            System.out.println("List of things was updated " + sharedHouse.size() + "Current thread" + Thread.currentThread().getName());
            List<Thing> after = sharedHouse.getList();
            System.out.println("List before and after: " + before.size() + "/n" + after.size());

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphoreForOwners.release();
            synchronized (sharedHouse) {
                sharedHouse.notifyAll();
            }
        }
    }
    public List<Thing> getBackpackList(){
        return ownerBackpack.getList();
    }

    public void pullBackpackList(int size){
        this.ownerBackpack.pullBackpack(size);
    }
}
