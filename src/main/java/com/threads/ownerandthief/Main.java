package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final CyclicBarrier BARRIER = new CyclicBarrier(20);
    static final int owner_size = 15;
    static final int thief_size = 5;

    public static void main(String[] args) {
        Home home = new Home();

//*        Owner owner = new Owner(home);
//*        Thief thief = new Thief(home);

        for (int i = 0; i < owner_size; i++) {
            new Thread(new Owner(home)).start();
        }
        for (int i = 0; i < thief_size; i++) {
            new Thread(new Thief(home)).start();
        }

        try{
            BARRIER.await();
        }catch (Exception e){

        }

        /*
        for (int i = 0; i < owner_size; i++) {
            new Owner(sharedHouse, size).start();
        }
        for (int i = 0; i < thief_size; i++) {
            new Thief(sharedHouse).start();
        }
        */
    }
}