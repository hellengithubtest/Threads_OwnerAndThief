package com.threads.ownerandthief;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.*;

public class Thief implements Runnable {
    private final Home sharedHouse;
    private final Backpack backpack;
    private CyclicBarrier barrier1 = null;
    private Semaphore semOw = null;
    private Semaphore semTh = null;

    public Thief(Home sharedHouse, CyclicBarrier barrier1, Semaphore semOw, Semaphore semTh) {
        this.sharedHouse = sharedHouse;
        this.backpack = new Backpack();
        this.barrier1 = barrier1;
        this.semOw = semOw;
        this.semTh = semTh;
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
        System.out.println("Things is stolen " + Thread.currentThread().getName());
    }

    private void stole() {

            try {
                /*
                 if Owners return all permits and home have a thing
                 */
                while(semOw.availablePermits() < 5 || sharedHouse.size() == 0){
                    try {
                        synchronized (sharedHouse) {
                            sharedHouse.wait();
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                /*
                    only one thief can enter to home
                 */
                semTh.acquire(1);
                int maxInd = 0;
                /*
                we get expensive things while there is a place in the backpack and they are at home
                 */
                while (backpack.getCurrentSize() != 0 && maxInd == 0 && sharedHouse.size() != 0) {
                    List<Thing> in = sharedHouse.getList(); //get current list of things
                    System.out.println("Thief while House size " + sharedHouse.size() + " list of things: " + sharedHouse.printList() + Thread.currentThread().getName());

                    /*
                    get index of expensive thing
                     */
                    maxInd = getMax(in);
                    Thing thing = in.get(maxInd);
                    System.out.println("Max ind is " + maxInd);
                    /*
                    add to backpack the thing with max cost
                     */
                    backpack.setThing(thing);
                    in.remove(maxInd);// remove the thing from list
                    sharedHouse.setList(in);// update list of things in home
                    /*
                    if the next expensive thing does not fit, then we leave
                     */
                    if (backpack.getCurrentSize() != 0 && !in.isEmpty()) {
                        maxInd = getMax(in); //next max ind
                        int dif = backpack.getCurrentSize() - in.get(maxInd).getWeight();
                        if (dif < 0) {
                            break;
                        }
                    }
                }
                System.out.println("Thief is stole some things" + Thread.currentThread().getName());

            }catch(InterruptedException e){
                e.printStackTrace();
                sharedHouse.notifyAll();
            }
        semTh.release(1);
    }

    /*
    go through until we find an expensive item
     */
    public int getMax(List<Thing> list) {
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
