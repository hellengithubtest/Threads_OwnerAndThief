package com.threads.ownerandthief;
import java.util.*;

public class Thing {
    private int maxCost = 10;
    private int maxWeight = 15;
    private int cost;
    private int weight;

    public Thing() {
        Random random = new Random();
        this.cost = random.nextInt( maxCost ) + 1;    //TODO add init thing
        this.weight = random.nextInt( maxWeight ) + 1;
    }

    public int getCost(){
        return this.cost;
    }
    public int getWeight() {
        return this.weight;
    }

    public static Comparator<Thing> COST_DESC = new Comparator<Thing>() {
        @Override
        public int compare(Thing thing, Thing t1) {
            return Integer.compare(t1.cost, thing.cost);
        }
    };

    @Override
    public String toString() {
        return " Thing: cost-" + cost + " weight-" + weight;
    }

}
