package com.threads.ownerandthief;

public class Backpack {
    private int currentSize;

    public Backpack() {
        this.currentSize = 15;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setThing(Thing thing) {
        if (currentSize > thing.getWeight()){
            this.currentSize = currentSize-thing.getWeight();
            System.out.println("The thief was pick up thing" + thing.getCost()+ "" + thing.getWeight());
        }else{
            System.out.println("The backpack is so heavy...");
        }
    }
}