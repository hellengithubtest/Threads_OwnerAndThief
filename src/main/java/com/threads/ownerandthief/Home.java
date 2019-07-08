package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;


public class Home {
    private List<Thing> thingsList = new ArrayList<>();
    private volatile boolean isThief;
    private volatile int numberOwner;

    public boolean isThief(){
        return this.isThief;
    }
    public void setThief(boolean thief){
        this.isThief = thief;
    }
    public int getNumberOwner(){
        return this.numberOwner;
    }

    public void setUpNumber() {
        this.numberOwner = numberOwner + 1;
    }

    public void setDownNumber() {
        this.numberOwner = numberOwner - 1;
    }
    public int size(){
        return thingsList.size();
    }
    public void add(Thing thing){
        thingsList.add(thing);
    }

    public List<Thing> getList(){
        return thingsList;
    }
    public void setList(List<Thing> list){
        this.thingsList = list;
    }
    public String printList(){
        String out = "";
        for (int i = 0; i < thingsList.size(); i++)
            out = out + thingsList.get(i).toString();
        return out;
    }
}