package com.threads.ownerandthief;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    static int numberOfOwners = 20;
    static int numberOfThieves = 20;
    static int totalCountOfOwnersInHome = 5;
    static int totalCountOfThievesInHome = 1;
    static int totalNumberOfThingsForEachOwner = 3;
    private static final Semaphore semaphoreForOwners = new Semaphore(totalCountOfOwnersInHome); //count of Owners in home at the same time
    private static final Semaphore semaphoreForThieves = new Semaphore(totalCountOfThievesInHome); //count of Thieves in home at the same time
    private static final CyclicBarrier barrier = new CyclicBarrier(numberOfOwners + numberOfThieves );

    public static void main(String[] args) {

        List<Future<List>> list = new ArrayList<Future<List>>();
        List<Thing> origin = new ArrayList<>();
        List<Thing> out = new ArrayList<>();
        Home home = new Home();

        initializeOwnersAndThieves(home, origin,list);

        for (Future<List> fut : list) {
            try {
                out.addAll(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("List of stolen things " + out);
        System.out.println("List of things stay on home " + home.getList());
        out.addAll(home.getList());
        printOutput(origin, out);
    }
    public static void initializeOwnersAndThieves(Home home, List<Thing> origin, List <Future<List>> list){
        ExecutorService executor = Executors.newFixedThreadPool(numberOfOwners + numberOfThieves);
        Random random = new Random();
        for (int i = 0; i < numberOfOwners; i++){
            Owner owner = new Owner(home, barrier, semaphoreForOwners, semaphoreForThieves);
            owner.pullBackpackList(random.nextInt(totalNumberOfThingsForEachOwner + 1));
            origin.addAll(owner.getBackpackList());
            Callable<List> callableOwners = owner;
            Future<List> future = executor.submit(callableOwners);
            list.add(future);
        }
        for (int i = 0; i < numberOfThieves; i++){
            Callable<List> callableThieves = new Thief(home, barrier, semaphoreForOwners, semaphoreForThieves);
            Future<List> future = executor.submit(callableThieves);
            list.add(future);
        }

        executor.shutdown();
    }
    public static void printOutput(List<Thing> origin, List<Thing> out){
        System.out.println("ORIGIN " + "Size: " + origin.size() + " " + origin);
        System.out.println("OUT    " + "Size: " + out.size() + " " + out);
        System.out.println("Lists is compare? " + origin.containsAll(out));
    }
}