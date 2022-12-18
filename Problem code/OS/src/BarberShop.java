import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class BarberShop {
    private final int shopChairs;
    private final int noOfBarbers;

    public int noOfCustomers;
    private final Semaphore waitingCustomers;
    private final Semaphore barberSemaphore;
    private final ReentrantLock customerMutex = new ReentrantLock(true);
    Queue<Integer> names = new LinkedList<>();
    private final Semaphore namesSemaphore = new Semaphore(1, true);

    public BarberShop(int chairs, int noOfBarbers){
        this.shopChairs = chairs;
        this.noOfBarbers = noOfBarbers;
        waitingCustomers = new Semaphore(0, true);
        barberSemaphore = new Semaphore(noOfBarbers, true);
        for (int i = 0; i < noOfBarbers; i++) {
            names.add(i + 1);
        }
    }

    public void addCustomer(){
        Random random = new Random();
        int delay = (Math.abs(random.nextInt())%10)*1000;
        Customer customer = new Customer(delay);
        customer.enterShop();
        customerMutex.lock();
        if (noOfCustomers == shopChairs){
            System.out.println("Shop full");
            customerMutex.unlock();
            customer.leaveShop();
        } else {
            noOfCustomers++;
            customerMutex.unlock();
            waitingCustomers.release();
            try {
                barberSemaphore.acquire();
                namesSemaphore.acquire();
                Barber barber = new Barber(delay, "" + names.poll());
                namesSemaphore.release();
                customerMutex.lock();
                noOfCustomers--;
                customerMutex.unlock();
                Thread customerThread = customer.getHairCut();
                Thread barberThread = barber.cutHair();
                barberThread.start();
                customerThread.start();
                customerThread.join();
                barberThread.join();
                namesSemaphore.acquire();
                names.add(Integer.parseInt(barber.name));
                namesSemaphore.release();
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
            }
            barberSemaphore.release();
        }
    }
}
