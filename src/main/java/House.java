package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class House {

    public static void main(String[] args) {
        Vector sharedHouse = new Vector();
        int size = 5;
        Vector bagpack = new Vector();
        int lockWeight = 2;
        int bagpackSize = 50;
        Thread ownerThread = new Thread(new Owner(sharedHouse, size), "Owner");
        Thread thiefThread = new Thread(new Thief(sharedHouse, bagpack, size, lockWeight, bagpackSize), "Thief");
        ownerThread.start();
        thiefThread.start();

    }
}