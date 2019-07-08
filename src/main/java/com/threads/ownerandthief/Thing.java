package com.threads.ownerandthief;
import java.util.*;

public class Thing {
    private int cost;
    private int weight;
    public Thing(int cost, int weight) {
        this.cost = cost;
        this.weight = weight;

    }
    public int getCost(){
        return this.cost;
    }
    public int getWeight(){
        return this.weight;
    }

    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(o == null || getClass() != o.getClass()) return false;
        Thing aThing = (Thing) o;
        if(cost != aThing.cost)
            return false;
        /*if(!weight.equals(aThing.weight))
            return false;*/
        return true;
    }

    public String toString(){
        return "Thing "+"cost is "+cost+" weight is " + weight;
    }
}
