package com.threads.ownerandthief;

import java.util.*;

public class Home {
    private List<Thing> thingsList = new ArrayList<Thing>();

    public int size() {
        return thingsList.size();
    }

    public List<Thing> getList() {
        return thingsList;
    }

    public void setList(List<Thing> list) {
        this.thingsList = list;
    }

    public String printList() {
        String out = "";
        for (int i = 0; i < thingsList.size(); i++)
            out = out + thingsList.get(i).toString();
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