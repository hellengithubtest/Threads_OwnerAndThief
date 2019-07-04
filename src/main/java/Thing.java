package com.threads.ownerandthief;

public class Thing {
    private double cost;
    private double weight;
    public Thing(double cost, double weight) {
        this.cost = cost;
        this.weight = weight;

    }
    public double getCost(){
        return this.cost;
    }
    public double getWeight(){
        return this.weight;
    }

    public String toString(){
        return "Thing "+"cost is "+cost+" weight is " + weight;
    }
}
