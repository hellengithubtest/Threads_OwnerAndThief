import java.util.Vector;

public class Thief implements Runnable{
    private final Vector<Thing> sharedHouse;
    private final Vector<Thing> bagpack;
    private final int lockWeight;
    private final int size;
    private final int bagpackSize;
    public Thief (Vector sharedHouse, Vector bagpack, int size, int lockWeight, int bagpackSize){
        this.sharedHouse = sharedHouse;
        this.bagpack = bagpack;
        this.size = size;
        this.lockWeight = lockWeight;
        this.bagpackSize = bagpackSize;
    }
    @Override
    public void run(){
        while(true) {
            try {
                System.out.println("Thief: " + stole());
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    private Thing stole() throws InterruptedException{
        while (sharedHouse.isEmpty()){
            synchronized (sharedHouse){
                System.out.println("House is empty " + Thread.currentThread().getName() + " is waiting, size " + sharedHouse.size());
                sharedHouse.wait();
            }
        }
        synchronized (sharedHouse){
            sharedHouse.notifyAll();
            int max = 0;
            if(sharedHouse.size() == 1){
                return sharedHouse.remove(max);
            }else {
                for (int i = 0; i < sharedHouse.size()-1; i++) {   //get index of max element
                    if (sharedHouse.get(i).getCost() < sharedHouse.get(i + 1).getCost()) {
                        max = i + 1;
                    } else {
                        max = i;
                    }
                }
            }
            return sharedHouse.remove(max);
        }
    }
}
