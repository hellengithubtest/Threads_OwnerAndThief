package com.threads.ownerandthief;

import java.util.*;

public class Home {
    private List<Thing> thingsList = Collections.synchronizedList(new ArrayList<Thing>());
    private  boolean isThiefInHome = false;
    private int countOfOwnersInHome = 0;

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

    public void removeListOfStolenThings(List<Thing> a) {
        for (Thing thing : a){
            thingsList.remove(thing);
        }
    }
    public boolean getThiefInHome(){
        return this.isThiefInHome;
    }

    public void setThiefInHome(boolean status){
        this.isThiefInHome = status;
    }
    public int getCountOfOwnersInHome(){
        return this.countOfOwnersInHome;
    }

    public void setCountOfOwnersInHome(int countOfOwnersInHome) {
        this.countOfOwnersInHome = countOfOwnersInHome;
    }
}