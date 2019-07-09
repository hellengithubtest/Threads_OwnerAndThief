package com.threads.ownerandthief;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final CyclicBarrier barrier = new CyclicBarrier(50);
        static final int owner_size = 25;
        static final int thief_size = 25;


    public static void main(String[] args) {
        Home home = new Home();

        for (int i = 0; i < owner_size; i++) {
            new Thread(new Owner(home,barrier)).start();
        }
        for (int i = 0; i < thief_size; i++) {
            new Thread(new Thief(home,barrier)).start();
        }

//*        try{
//*            barrier.await();
//*        }catch (Exception e){
//*            e.printStackTrace();
//*        }

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