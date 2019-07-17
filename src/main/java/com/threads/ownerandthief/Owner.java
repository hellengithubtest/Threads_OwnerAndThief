package com.threads.ownerandthief;
import java.util.List;
import java.util.concurrent.*;

public class Owner implements Callable {
    private final Home sharedHouse;
    private Backpack ownerBackpack;
    private CountDownLatch latch = null;

    public Owner(Home sharedHouse, CountDownLatch barrier) {
        int totalWeight = 10;
        this.sharedHouse = sharedHouse;
        this.ownerBackpack = new Backpack(totalWeight);
        this.latch = barrier;
    }

    @Override
    public List<Thing> call() {
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("Owner try to add thing " + Thread.currentThread().getName());
        addThing();
        System.out.println("Owner have added the thing" + Thread.currentThread().getName());
        return ownerBackpack.getList();
    }

    public void addThing() {
        try {
            synchronized (sharedHouse){
                while (sharedHouse.getThiefInHome()){
                    sharedHouse.wait();
                }
                sharedHouse.setCountOfOwnersInHome(sharedHouse.getCountOfOwnersInHome() + 1);
                System.out.println("Owner current count " + sharedHouse.getCountOfOwnersInHome());
            }

/*            if(sharedHouse.getThiefInHome() && sharedHouse.getCountOfOwnersInHome() > 0){
                System.out.println("ERROR: thread Owner " + sharedHouse.getThiefInHome() + Thread.currentThread().getName());
            }*/

            sharedHouse.addThings(this.ownerBackpack.getList());
            ownerBackpack.getList().clear();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            synchronized (sharedHouse) {
                sharedHouse.setCountOfOwnersInHome(sharedHouse.getCountOfOwnersInHome() - 1);
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
