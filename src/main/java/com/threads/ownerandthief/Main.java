package com.threads.ownerandthief;

import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static final CyclicBarrier barrier = new CyclicBarrier(20);
    private static final Semaphore semOw = new Semaphore(5); //count of Owners in home at the same time
    private static final Semaphore semTh = new Semaphore(1); //count of Thiefs in home at the same time
    static int owner_size = 10;
    static int thief_size = 10;


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<List>> list = new ArrayList<Future<List>>();

        Random random = new Random();
        Home home = new Home();
        /*
        Initial list of things
         */
        List<Thing> origin = new ArrayList<>();
        List<Thing> out = new ArrayList<>();

        for (int i = 0; i < owner_size * 3; i++) {
            Thing thing = new Thing(random.nextInt(10) + 1, random.nextInt(15) + 1);
            origin.add(thing);
        }

        int counter = 0;
        for (; ; ) {
            if (random.nextInt(100) % 2 == 0 && owner_size != 0) {
                //System.out.println("Owner");
                Owner owner = new Owner(home, barrier, semOw, semTh);
                List<Thing> subList = origin.subList(counter, counter + 3);
                owner.pullBackpack(subList);
                new Thread(owner).start();
                counter = counter + 3;
                owner_size--;

            } else if (thief_size != 0) {
                //System.out.println("Thief");
                Callable<List> callable = new Thief(home, barrier, semOw, semTh);
                //new Thread(new Thief(home,barrier, semOw, semTh)).start();
                Future<List> future = executor.submit(callable);
                list.add(future);
                thief_size--;
            } if (owner_size == 0 && thief_size == 0) {
                break;
            }

        }
        for (Future<List> fut : list) {
            try {
                out.addAll(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        out.addAll(home.getList());
        System.out.println("*** origin" + origin.size() + origin);
        System.out.println("****** out" + out.size() + out);
        int count = 0;
        for (int i = 0 ; i < origin.size(); i++){
            for (int j = 0; j < origin.size(); j++){
                if(origin.get(i).equals(out.get(j))){
                    count++;
                System.out.println(origin.get(i) + " is equal " + out.get(j) + " Count of things: " +count);
                }
            }
        }
        executor.shutdown();


    }
}