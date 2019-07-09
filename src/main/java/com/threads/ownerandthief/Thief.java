package com.threads.ownerandthief;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;

public class Thief implements Runnable {
    private final Home sharedHouse;
    private final Bagpack bagpack;
    CyclicBarrier barrier1 = null;

    public Thief(Home sharedHouse, CyclicBarrier barrier1) {
        this.sharedHouse = sharedHouse;
        this.bagpack = new Bagpack();
        this.barrier1 = barrier1;
    }

    @Override
    public void run() {
        try {
            this.barrier1.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch (BrokenBarrierException e){
            e.printStackTrace();
        }
        System.out.println("Try to stole thing " + Thread.currentThread().getName());
        stole();
        System.out.println("Thief is stolen " + Thread.currentThread().getName());
    }

    private synchronized void stole() {

            while(sharedHouse.isThief().get() || sharedHouse.getNumberOwner().get() > 0 || sharedHouse.size() == 0) {
                System.out.println("Thief is wait " + Thread.currentThread().getName() + " isThief " + sharedHouse.isThief() + " Number owner " + sharedHouse.getNumberOwner() + " Size " + sharedHouse.size());
                try {
                    synchronized (sharedHouse) {
                        sharedHouse.wait();
                    }
                } catch (InterruptedException e) {

                }
            }
            synchronized (sharedHouse) {
                sharedHouse.setThief(new AtomicBoolean(true));
                System.out.println("Thief is set true " + Thread.currentThread().getName() + " isThief " + sharedHouse.isThief() + " Number owner " + sharedHouse.getNumberOwner() + " Size " + sharedHouse.size());
            }
            synchronized (sharedHouse){
                int maxInd = 0;
            while (bagpack.getCurrentSize() != 0 && maxInd == 0 && sharedHouse.size() != 0) {
                List<Thing> in = sharedHouse.getList(); //get current list of things
                System.out.println("Thief while House size " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());
                maxInd = getMax(in);//get index of thing with max cost
                Thing thing = in.get(maxInd);
                System.out.println("Max ind is "+ maxInd);
                bagpack.setThing(thing); //add to bagpack the thing with max cost
                in.remove(maxInd);// remove the thing from list
                sharedHouse.setList(in);// update list of things in home
                if(bagpack.getCurrentSize() != 0 && !in.isEmpty()) {
                    maxInd = getMax(in); //next max ind
                    int dif = bagpack.getCurrentSize() - in.get(maxInd).getWeight();
                    if(dif < 0){
                        break;
                    }
                }
            }
            }
            System.out.println("Thief is stole some things" + Thread.currentThread().getName());
            sharedHouse.setThief(new AtomicBoolean(false));
            synchronized (sharedHouse) {
                sharedHouse.notifyAll();
            }
    }

    public int getMax(List<Thing> list) { //get the thing with max cost
        int size = list.size();
        int ind[] = new int[size];
        int max = 0;
        for (int j = 0; j < size; j++)

            for (int i = 0; i < size - 1; i++) {
                if (list.get(i).getCost() < list.get(i + 1).getCost()) {
                    max = i + 1;
                } else {
                    max = i;
                }

            }
        return max;
    }
}
