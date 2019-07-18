package com.threads.ownerandthief;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class Main {
    static final int NUMBER_OF_OWNERS = 10;
    static final int NUMBER_OF_THIEVES = 10;

    static final int TOTAL_NUMBER_OF_THINGS_FOR_EACH_OWNER = 5;
    private static final CountDownLatch latch =
            new CountDownLatch(NUMBER_OF_OWNERS + NUMBER_OF_THIEVES);

    public static void main(String[] args) {

        List<Future<List>> listOfThreads = new ArrayList<Future<List>>();
        List<Thing> originListOfThings = new ArrayList<>();
        List<Thing> returnedListOfThings = new ArrayList<>();
        Home home = new Home();

        ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_OWNERS + NUMBER_OF_THIEVES);

        List<Callable> callableListOfObjects = getListOfCreatedObjects(home, originListOfThings, latch);

        startAndWaitThreads(executor, callableListOfObjects, listOfThreads);

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
    public static void startAndWaitThreads(ExecutorService executor, List<Callable> callableListOfObjects, List<Future<List>> listOfThreads){
        for (int i = 0; i < callableListOfObjects.size(); i++) {
            Future<List> future = executor.submit(callableListOfObjects.get(i));
            listOfThreads.add(future);
            latch.countDown();
        }
        executor.shutdown();
    }

    public static List<Callable> getListOfCreatedObjects(Home home, List<Thing> originListOfThings, CountDownLatch latch) {
        Random random = new Random();
        List<Callable> callableListOfObjects = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_OWNERS; i++) {
            Backpack backpack = new Backpack.Builder().withListOfThings(random.nextInt(TOTAL_NUMBER_OF_THINGS_FOR_EACH_OWNER) + 1).build();
            originListOfThings.addAll(backpack.getList());
            Callable<List> owner = new Owner.Builder().withHome(home).withBackpack(backpack).withLatch(latch).build();
            callableListOfObjects.add(owner);
        }

        for (int i = 0; i < NUMBER_OF_THIEVES; i++) {
            Backpack backpack = new Backpack.Builder().withRandomWeight().build();
            Callable<List> thief = new Thief.Builder().withHome(home).withBackpack(backpack).withLatch(latch).build();
            callableListOfObjects.add(thief);
        }
        return callableListOfObjects;
    }

    public static void printOutput(List<Thing> originListOfThings, List<Thing> returnedListOfThings){
        boolean compareLists = originListOfThings.containsAll(returnedListOfThings) && returnedListOfThings.containsAll(originListOfThings);
        System.out.println("ORIGIN " + "Size: " + originListOfThings.size() + " " + originListOfThings);
        System.out.println("OUT    " + "Size: " + returnedListOfThings.size() + " " + returnedListOfThings);
        System.out.println("Lists are compare? " + compareLists);
    }
}
