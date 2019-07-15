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
    public Backpack(int capacity) {

        this.currentSize = capacity;
    }
    public Backpack() {

        this.currentSize = 0;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setThing(Thing thing) {
        List <Thing> list = this.backpackList;
        list.add(thing);
        this.backpackList = list;
        int size = this.currentSize;
        this.currentSize = size - thing.getWeight();
    }

    public void pullThing(List <Thing> list){
        this.backpackList = list;
    }
    public List<Thing> getList(){
        return this.backpackList;
    }
}