import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args){
        BarberShop shop = new BarberShop(10, 2);
        Scanner input = new Scanner(System.in);
        ExecutorService executor = Executors.newCachedThreadPool();
        while (input.nextInt() > 0){
            executor.execute(shop::addCustomer);
        }
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}