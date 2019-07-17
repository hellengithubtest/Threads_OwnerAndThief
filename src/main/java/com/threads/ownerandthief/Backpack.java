package com.threads.ownerandthief;
import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private List<Thing> backpackList = new ArrayList<Thing>();
    private int totalWeight = 15;
    private int remainingWeight;

    public Backpack() {
        this.remainingWeight = totalWeight;
    }

    public boolean tryToAddThing(Thing thing) {
        if (remainingWeight >= thing.getWeight()) {
            this.backpackList.add(thing);
            int size = this.remainingWeight;
            this.remainingWeight = size - thing.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public void pullBackpack(int size) {
        for (int i = 0; i < size; i++) {
            this.backpackList.add(new Thing());
        }
    }

    public List<Thing> getList() {
        return this.backpackList;
    }
}