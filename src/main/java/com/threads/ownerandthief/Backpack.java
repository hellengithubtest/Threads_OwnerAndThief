package com.threads.ownerandthief;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Backpack, the class with many parameters
 */
public class Backpack {

    private List<Thing> backpackList = new ArrayList<Thing>();
    private final int THING_COST = 10;
    private final int THING_WEIGHT = 15;
    private final int TOTAL_WEIGHT = 15;

    private int remainingWeight;

    public static class Builder {
        private Backpack newBackpack;
        public Builder() {
            newBackpack = new Backpack();
        }

        public Builder withListOfThings(int countThings) {
            List<Thing> list = new ArrayList<>();
            for (int i = 0; i < countThings; i++) {
                list.add(new Thing.Builder().withRandomCost(newBackpack.THING_COST).withRandomWeight(newBackpack.THING_WEIGHT).build());
            }
            newBackpack.backpackList = list;
            return this;
        }

        public Builder withRandomWeight() {
            Random random = new Random();
            newBackpack.remainingWeight = random.nextInt(newBackpack.TOTAL_WEIGHT);
            return this;
        }

        public Backpack build() {
            return newBackpack;
        }
    }

    public boolean tryToAddThing(Thing thing) {
        if (remainingWeight >= thing.getWeight()) {
            backpackList.add(thing);
            this.remainingWeight = remainingWeight - thing.getWeight();
            return true;
        } else {
            return false;
        }
    }

    public List<Thing> getList() {
        return this.backpackList;
    }
}