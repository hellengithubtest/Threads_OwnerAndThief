package com.threads.ownerandthief;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

public class Thief implements Runnable {
    private final Home sharedHouse;
    private final Bagpack bagpack;
    private Lock l = new ReentrantLock();

    public Thief(Home sharedHouse) {
        this.sharedHouse = sharedHouse;
        this.bagpack = new Bagpack();
    }

    @Override
    public void run() {

        if(!sharedHouse.isThief() && sharedHouse.getNumberOwner() == 0) {
            l.lock();
            try {
                sharedHouse.setThief(true);
                stole();
                System.out.println("Thief: " + Thread.currentThread().getName());
            } finally {
                sharedHouse.setThief(false);
                l.unlock();
            }
        }else{
            System.out.println("Home is empty, thief is go out");
        }
    }

    private void stole() {
        while (bagpack.getCurrentSize() != 0) {
            List<Thing> in = sharedHouse.getList();
            int maxInd = getMax(in);
            Thing thing = in.get(maxInd);
            bagpack.setThing(thing);
            in.remove(maxInd);
            sharedHouse.setList(in);
        }
    }

    public int getMax(List<Thing> list) {
        int size = list.size();
        System.out.println("getMax size is " + size);
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
