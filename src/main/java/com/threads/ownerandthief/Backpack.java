package com.threads.ownerandthief;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Backpack {
    private List<Thing> backpackList = new ArrayList<Thing>();
    private int currentSize;

    /*
    Constructor for Thief
     */
    public Backpack() {
        this.currentSize = 15;
    }
    /*
    Constructor for Owner
     */
    public Backpack(int countThings){
        Random random = new Random();
        for(int i = 0; i < countThings; i++){
            backpackList.add(new Thing(random.nextInt(10) + 1, random.nextInt(15)+1));
        }

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
    public List<Thing> getList(){
        return this.backpackList;
    }
}