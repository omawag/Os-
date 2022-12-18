import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args){
        Judge judge = new Judge(12, 5);
        Scanner input = new Scanner(System.in);
        ExecutorService executor = Executors.newCachedThreadPool();
        int i = 0;
        while (input.nextInt() > 0){
            int finalI = ++i;
            System.out.println("Enter problem and submission number");
            char p = input.next().charAt(0);
            int num = input.nextInt();
            executor.execute(() -> judge.submit(new Submission("" + finalI, p, num)));
        }
        executor.shutdown();
    }
}