import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final int owner_size = 10;
    static final int thief_size = 2;

    public static void main(String[] args) {
        Home home = new Home();

        Owner owner = new Owner(home);
        Thief thief = new Thief(home);
        ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        service.execute(owner);
        service.execute(thief);
        service.shutdown();
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