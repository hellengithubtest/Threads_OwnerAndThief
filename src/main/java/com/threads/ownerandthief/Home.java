package com.threads.ownerandthief;

import java.util.*;

public class Home {
    private List<Thing> thingsList = new ArrayList<>();

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