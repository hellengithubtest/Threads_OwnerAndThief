import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Thief implements Runnable {
    private final Home sharedHouse;
    private final Bagpack bagpack;
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Thief(Home sharedHouse) {
        this.sharedHouse = sharedHouse;
        this.bagpack = new Bagpack();
    }

    @Override
    public void run() {
        Lock readLock = rwLock.readLock();
        readLock.lock();
        try {
            stole();
            System.out.println("Thief: ");
        } finally {
            readLock.unlock();
        }
    }

    private void stole() {
        while (bagpack.getCurrentSize() != 0) {
            List<Thing> in = sharedHouse.getList();
            int maxInd = getMax(in);
            Thing thing = in.get(maxInd);
            bagpack.setThing(thing);
            in.remove(maxInd);
            sharedHouse.setList(in);
        }
    }

    public int getMax(List<Thing> list) {
        int size = list.size();
        int ind[] = new int[size];
        int max = 0;
        for (int j = 0; j < size; j++)

            for (int i = 0; i < size - 1; i++) {
                if (list.get(i).getCost() < list.get(i + 1).getCost()) {
                    max = i + 1;
                } else {
                    max = i;
                }

            }
        return max;
    }
}
