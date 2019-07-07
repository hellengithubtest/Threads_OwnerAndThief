import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Owner implements Runnable {
    private final Home sharedHouse;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();
    //private final Thing thing = new Thing(Math.random()*1, Math.random()*2);

    public Owner(Home sharedHouse) {
        this.sharedHouse = sharedHouse;
    }

    @Override
    public void run() {
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            addSomeThing();
        } finally{
            writeLock.unlock();
        }
    }
    private void addSomeThing(){
        Random random = new Random();
        System.out.println("House size " + sharedHouse.size());
        sharedHouse.add(new Thing(random.nextInt(10), random.nextInt(15)));
        sharedHouse.notifyAll();
        System.out.println("Add thing " + sharedHouse);
    }
}
