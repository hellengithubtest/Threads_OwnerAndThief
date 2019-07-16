package com.threads.ownerandthief;

import java.util.*;

public class Home {
    private List<Thing> thingsList = Collections.synchronizedList(new ArrayList<Thing>());

    public int size() {
        return this.thingsList.size();
    }

    public List<Thing> getList() {
        return this.thingsList;
    }

    public String printList() {
        String out = "";
        for (int i = 0; i < this.thingsList.size(); i++)
            out = out + this.thingsList.get(i).toString();
        return out;
    }

    public void addThings(List<Thing> a) {
            for (Thing thing : a){
                thingsList.add(thing);
        }
    }

    public void removeListOfThings(List<Thing> a) {
        for (Thing thing : a){
            thingsList.remove(thing);
        }
    }
}