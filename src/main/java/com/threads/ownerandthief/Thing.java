package com.threads.ownerandthief;

import java.util.*;

public class Thing {

    private int cost;
    private int weight;

    public static class Builder {
        private Thing newThing;

        public Builder() {
            newThing = new Thing();
        }

        public Builder withRandomCost( int maxCost) {
            Random random = new Random();
            newThing.cost = random.nextInt(maxCost) + 1;
            return this;
        }

        public Builder withRandomWeight( int maxWeight) {
            Random random = new Random();
            newThing.weight = random.nextInt(maxWeight) + 1;
            return this;
        }

        public Thing build() {
            return newThing;
        }
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
