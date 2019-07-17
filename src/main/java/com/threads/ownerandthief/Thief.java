package com.threads.ownerandthief;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class Thief implements Callable <List> {
    private final Home sharedHouse;
    private final Backpack backpack;
    private CountDownLatch latch = null;

    public Thief(Home sharedHouse, CountDownLatch latch) {
        this.sharedHouse = sharedHouse;
        this.backpack = new Backpack();
        this.latch = latch;
    }

    @Override
    public List<Thing> call() {
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Try to stole thing " + Thread.currentThread().getName());
        stole();
        System.out.println("Things is stolen " + Thread.currentThread().getName());
        return backpack.getList();
    }

    private void stole() {
        try {
            synchronized (sharedHouse){
                while(sharedHouse.getCountOfOwnersInHome() > 0 || sharedHouse.getThiefInHome()){
                    sharedHouse.wait();
                }
                sharedHouse.setThiefInHome(true);
                System.out.println("Check count of owners " + sharedHouse.getCountOfOwnersInHome());
            }
            /*
            we get expensive things while there is a place in the backpack and they are at home
             */
            synchronized (sharedHouse) {
/*
                System.out.println("Get number of owners" + sharedHouse.getCountOfOwnersInHome() + sharedHouse.getThiefInHome());

                if(sharedHouse.getThiefInHome() && sharedHouse.getCountOfOwnersInHome() > 0 ){
                    System.out.println("ERROR: thread Thief" + sharedHouse.getThiefInHome() + Thread.currentThread().getName());
                }
*/
                List<Thing> copyOfListThings = new ArrayList<>(sharedHouse.getList());
                Collections.sort(copyOfListThings, Thing.COST_DESC);
                List<Thing> listToDelete = new ArrayList<>();
                while (!copyOfListThings.isEmpty() && backpack.setThing(copyOfListThings.get(0))){
                    listToDelete.add(copyOfListThings.get(0));
                    copyOfListThings.remove(0);
                }
                sharedHouse.removeListOfStolenThings(listToDelete);
                sharedHouse.setThiefInHome(false);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            synchronized (sharedHouse){
                sharedHouse.notifyAll();
            }
        }
    }
}