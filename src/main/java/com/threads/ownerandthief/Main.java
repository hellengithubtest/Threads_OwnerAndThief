package com.threads.ownerandthief;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Main {
    private static final CyclicBarrier barrier = new CyclicBarrier(50);
    private static final Semaphore semOw = new Semaphore(5); //count of Owners in home at the same time
    private static final Semaphore semTh = new Semaphore(1); //count of Thiefs in home at the same time
        static final int owner_size = 25;
        static final int thief_size = 25;


    public static void main(String[] args) {
        Random random = new Random();
        Home home = new Home();
/*        List<Thing> origin = new ArrayList<>();
        for(int i = 0; i < owner_size*3; i++){
            Thing thing = new Thing(random.nextInt(10) + 1, random.nextInt(15)+1);
            origin.add(thing);
        }*/
        for (int i = 0; i < owner_size; i++) {
            new Thread(new Owner(home,barrier, semOw, semTh)).start();
        }
        for (int i = 0; i < thief_size; i++) {
            new Thread(new Thief(home,barrier, semOw, semTh)).start();
        }
    }
}