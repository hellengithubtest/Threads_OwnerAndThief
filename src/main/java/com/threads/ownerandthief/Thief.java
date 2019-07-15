package com.threads.ownerandthief;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;

public class Thief implements Callable <List> {
    private final Home sharedHouse;
    private final Backpack backpack;
    private CyclicBarrier barrier1 = null;
    private Semaphore semOw = null;
    private Semaphore semTh = null;

    public Thief(Home sharedHouse, CyclicBarrier barrier1, Semaphore semOw, Semaphore semTh) {
        this.sharedHouse = sharedHouse;
        this.backpack = new Backpack(15);
        this.barrier1 = barrier1;
        this.semOw = semOw;
        this.semTh = semTh;
    }

    @Override
    public List<Thing> call() {
        try {
            this.barrier1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
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
            while (semOw.availablePermits() < 5 || sharedHouse.size() == 0) {
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
            semTh.acquire(1);
                /*
                we get expensive things while there is a place in the backpack and they are at home
                 */
            Collections.sort(sharedHouse.getList(), Thing.COST_DESC);
            /*
            if the next expensive thing does not fit, then we leave
             */
            while (sharedHouse.size() != 0 && (backpack.getCurrentSize() - sharedHouse.getList().get(0).getWeight()) >= 0) {
                    /*
                    add to backpack the thing with max cost
                     */
                backpack.setThing(sharedHouse.getList().remove(0));
                }
        } catch (InterruptedException e) {
            e.printStackTrace();
            sharedHouse.notifyAll();
        }
        /*
        Pick up all things
         */
        semTh.release(1);
        synchronized (sharedHouse){
            sharedHouse.notifyAll();
        }
    }
}