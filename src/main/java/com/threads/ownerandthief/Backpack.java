package com.threads.ownerandthief;
import java.util.ArrayList;
import java.util.List;

public class Backpack {

    private List<Thing> backpackList = new ArrayList<Thing>();
    private int totalWeight;
    private int remainingWeight = 0;

    /*
    Constructor for Thief
     */

    public Backpack(int totalWeight) {

        this.totalWeight = totalWeight;
        this.remainingWeight = totalWeight;
    }

    public boolean setThing(Thing thing) {
        if(remainingWeight >= thing.getWeight()) {
            this.backpackList.add(thing);
            int size = this.remainingWeight;
            this.remainingWeight = size - thing.getWeight();
            return true;
        }else{
            return false;
        }
    }

    public void pullBackpack(int size){
        for(int i = 0; i < size; i++){
            this.backpackList.add(new Thing());
        }
    }

    public List<Thing> getList(){

        return this.backpackList;
    }
}