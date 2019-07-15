package com.threads.ownerandthief;

import java.util.*;

public class Home {
    private List<Thing> stolenThings = new ArrayList<Thing>();
    private List<Thing> thingsList = Collections.synchronizedList(new ArrayList<Thing>());

    public int size() {
        return this.thingsList.size();
    }

    public List<Thing> getList() {
        return this.thingsList;
    }

    public void setList(List<Thing> list) {
        this.thingsList = list;
    }

    public String printList() {
        String out = "";
        for (int i = 0; i < this.thingsList.size(); i++)
            out = out + this.thingsList.get(i).toString();
        return out;
    }

    public void joinLists(List<Thing> a) {
        if ((this.thingsList == null) || this.thingsList.isEmpty()) {
            this.thingsList = a;
        } else {
            int thisSize = thingsList.size();
            int aSize = a.size();
            List<Thing> res = new ArrayList<Thing>(aSize + thisSize);
            res.addAll(a);
            res.addAll(this.thingsList);
            this.thingsList = res;
        }

    }
}