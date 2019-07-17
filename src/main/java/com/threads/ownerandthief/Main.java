package com.threads.ownerandthief;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    static int numberOfOwners = 20;
    static int numberOfThieves = 20;
    static int totalNumberOfThingsForEachOwner = 5;
    private static final CountDownLatch latch = new CountDownLatch(numberOfOwners + numberOfThieves);

    public static void main(String[] args) {

        List<Future<List>> listOfThreads = new ArrayList<Future<List>>();
        List<Thing> originListOfThings = new ArrayList<>();
        List<Thing> returnedListOfThings = new ArrayList<>();
        Home home = new Home();

        initializeOwnersAndThieves(home, originListOfThings,listOfThreads, latch);

        for (Future<List> fut : listOfThreads) {
            try {
                returnedListOfThings.addAll(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
/*
        System.out.println("STOLEN THINGS " + returnedListOfThings);
        System.out.println("IN HOME " + home.getList());
*/
        returnedListOfThings.addAll(home.getList());
        printOutput(originListOfThings, returnedListOfThings);
    }
    public static void initializeOwnersAndThieves(Home home, List<Thing> originListOfThings, List <Future<List>> listOfThreads, CountDownLatch latch){
        ExecutorService executor = Executors.newFixedThreadPool(numberOfOwners + numberOfThieves);
        Random random = new Random();

        for (int i = 0; i < numberOfOwners; i++){
            Owner owner = new Owner(home, latch);
            owner.pullBackpackList(random.nextInt(totalNumberOfThingsForEachOwner) + 1);
            originListOfThings.addAll(owner.getBackpackList());
            Callable<List> callableOwners = owner;
            Future<List> future = executor.submit(callableOwners);
            listOfThreads.add(future);
            latch.countDown();
        }
        for (int i = 0; i < numberOfThieves; i++){
            Callable<List> callableThieves = new Thief(home, latch);
            Future<List> future = executor.submit(callableThieves);
            listOfThreads.add(future);
            latch.countDown();
        }

        executor.shutdown();
    }
    public static void printOutput(List<Thing> originListOfThings, List<Thing> returnedListOfThings){
        System.out.println("ORIGIN " + "Size: " + originListOfThings.size() + " " + originListOfThings);
        System.out.println("OUT    " + "Size: " + returnedListOfThings.size() + " " + returnedListOfThings);
        System.out.println("Lists are compare? " + originListOfThings.containsAll(returnedListOfThings));
    }
}