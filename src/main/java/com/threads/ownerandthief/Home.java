package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Home {
    private List<Thing> thingsList = new ArrayList<>();
    private AtomicBoolean isThief = new AtomicBoolean(false);
    private AtomicInteger numberOwner = new AtomicInteger(0);

    public AtomicBoolean isThief(){
        return this.isThief;
    }
    public void setThief(AtomicBoolean thief){
        this.isThief = thief;
    }
    public AtomicInteger getNumberOwner(){
        return this.numberOwner;
    }
    public int size(){
        return thingsList.size();
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