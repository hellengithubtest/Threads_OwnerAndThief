package com.threads.ownerandthief;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class Owner implements Callable {

    private Home sharedHouse;
    private Backpack ownerBackpack;
    private CountDownLatch latch;


    public static class Builder {

        private Owner newOwner;
        
        public Builder() {
            newOwner = new Owner();
        }

        public Builder withHome(Home sharedHouse) {
            newOwner.sharedHouse = sharedHouse;
            return this;
        }

        public Builder withBackpack(Backpack newBackpack) {
            newOwner.ownerBackpack = newBackpack;
            return this;
        }

        public Builder withLatch(CountDownLatch latch) {
            newOwner.latch = latch;
            return this;
        }
        public Owner build() {
            return newOwner;
        }
    }

    @Override
    public List<Thing> call() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Owner try to add thing " + Thread.currentThread().getName());
        addThings();
        System.out.println("Owner have added the thing" + Thread.currentThread().getName());
        return ownerBackpack.getList();
    }

    public void addThings() {
        try {
            synchronized (sharedHouse) {
                while (sharedHouse.getThiefInHome()) {
                    sharedHouse.wait();
                }
                sharedHouse.setCountOfOwnersInHome(sharedHouse.getCountOfOwnersInHome() + 1);
                System.out.println("Owner current count " + sharedHouse.getCountOfOwnersInHome());
            }

/*            if(sharedHouse.getThiefInHome() && sharedHouse.getCountOfOwnersInHome() > 0){
                System.out.println("ERROR: thread Owner " + sharedHouse.getThiefInHome() + Thread.currentThread().getName());
            }*/

            sharedHouse.addThings(ownerBackpack.getList());
            ownerBackpack.getList().clear(); //TODO one by one

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (sharedHouse) {
                sharedHouse.setCountOfOwnersInHome(sharedHouse.getCountOfOwnersInHome() - 1);
                sharedHouse.notifyAll();
            }
        }
    }
}
