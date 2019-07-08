package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.*;


public class Home {
    private List<Thing> thingsList = new ArrayList<>();

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
}