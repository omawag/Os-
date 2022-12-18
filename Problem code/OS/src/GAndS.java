import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GAndS implements Runnable{
    AtomicBoolean lock = new AtomicBoolean(false);
    static int val = 0;
    AtomicInteger cnt = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i <  1_000000; i++) {
//            while (lock.compareAndExchange(false, true)){
            synchronized (this) {
                val++;
            }
//                lock.set(false);
//            }
        }
    }
}
