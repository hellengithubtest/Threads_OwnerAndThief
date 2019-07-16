package com.threads.ownerandthief;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Thief implements Callable <List> {
    private final Home sharedHouse;
    private final Backpack backpack;
    private CyclicBarrier barrier = null;
    private Semaphore semaphoreForOwners = null;
    private Semaphore semaphoreForThieves = null;

    public Thief(Home sharedHouse, CyclicBarrier barrier, Semaphore semaphoreForOwners, Semaphore semaphoreForThieves) {
        int totalWeight = 15;
        this.sharedHouse = sharedHouse;
        this.backpack = new Backpack(totalWeight);
        this.barrier = barrier;
        this.semaphoreForOwners = semaphoreForOwners;
        this.semaphoreForThieves = semaphoreForThieves;
    }

    @Override
    public List<Thing> call() {
        try {
            this.barrier.await();
        } catch (InterruptedException | BrokenBarrierException  e) {
            e.printStackTrace();
        }
        System.out.println("Try to stole thing " + Thread.currentThread().getName());
        stole();
        System.out.println("Things is stolen " + Thread.currentThread().getName());
        return backpack.getList();
    }

    private void stole() {
        try {
            /*
             if Owners return all permits and home have a thing
             */
            while (semaphoreForOwners.availablePermits() < 5 ) {
                try {
                    synchronized (sharedHouse) {
                        sharedHouse.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            /*
            only one thief can enter to home
             */
            synchronized (sharedHouse) {
                semaphoreForThieves.acquire();
            }
            /*
            we get expensive things while there is a place in the backpack and they are at home
             */
            System.out.println("Thief after sync " + Thread.currentThread().getName() + " Permits owner" + semaphoreForOwners.availablePermits() + " Permits thief " + semaphoreForThieves.availablePermits());
            List<Thing> copyOfListThings = new ArrayList<>(sharedHouse.getList());
            Collections.sort(copyOfListThings, Thing.COST_DESC);
            List<Thing> listToDelete = new ArrayList<>();

            while (!copyOfListThings.isEmpty() && backpack.setThing(copyOfListThings.get(0))){
                listToDelete.add(copyOfListThings.get(0));
                copyOfListThings.remove(0);
            }
            sharedHouse.removeListOfThings(listToDelete);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semaphoreForThieves.release();
            synchronized (sharedHouse){
                sharedHouse.notifyAll();
            }
        }
    }
}