package com.threads.ownerandthief;

public class Bagpack {
    private int currentSize;

    public Bagpack() {
        this.currentSize = 10;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setThing(Thing thing) {
        if (currentSize < thing.getWeight()){
            this.currentSize = currentSize-thing.getWeight();
            System.out.println("The thief was pick up thing" + thing.getCost()+thing.getWeight());
        }else{
            System.out.println("The bagpack is so heavy...");
        }
    }
}