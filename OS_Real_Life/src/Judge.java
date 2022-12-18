import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Judge {
    private final int queueSize;
    public int noOfSubmissions;
    public int noOfTesters;
    private final Semaphore submissionsInQueue;
    private final Semaphore freeTesters;
    private final ReentrantLock submissionMutex = new ReentrantLock(true);
    Queue<Integer> names = new LinkedList<>();
    private final Semaphore namesSemaphore = new Semaphore(1, true);

    public Judge(int queueSize, int noOfTesters){
        this.queueSize = queueSize;
        this.noOfTesters = noOfTesters;
        for (int i = 0; i < noOfTesters; i++) {
            names.add(i + 1);
        }
        submissionsInQueue = new Semaphore(0, true);
        freeTesters = new Semaphore(noOfTesters, true);
    }

    public void submit(Submission submission){
        submission.waitInQueue();
        submissionMutex.lock();
        if (noOfSubmissions + submission.answers.length > queueSize){
            System.out.println("Queue full");
            submissionMutex.unlock();
            submission.leaveQueue();
        } else {
            noOfSubmissions+=submission.answers.length;
            submissionMutex.unlock();
            ExecutorService executor = Executors.newFixedThreadPool(submission.answers.length);
            for (int i = 0; i < submission.answers.length; i++) {
                int finalI1 = i;
                executor.execute(new Thread(()-> {
                    submissionsInQueue.release();
                    Tester tester;
                    try {
                        freeTesters.acquire();
                        namesSemaphore.acquire();
                        tester = new Tester(submission, finalI1, "" + (names.poll()));
                        namesSemaphore.release();
                        submissionMutex.lock();
                        noOfSubmissions--;
                        submissionMutex.unlock();
                        Thread submissionThread = new Thread(() -> {
                            try {
                                submission.getTested(finalI1);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        Thread testerThread = tester.testFile();
                        testerThread.start();
                        submissionThread.start();
                        testerThread.join();
                        submissionThread.join();

                    } catch (InterruptedException e) {
                        System.out.println("Interrupted");
                        return;
                    }
                    try {
                        namesSemaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    names.add(Integer.parseInt(tester.name));
                    namesSemaphore.release();
                    freeTesters.release();
                }));
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (int i = 0; i < submission.results.length; i++) {
                if (!submission.results[i]){
                    System.out.println("Wrong answer on test " + i + " of submission " + submission.id);
                    return;
                }
            }
            System.out.println(submission.id + " Accepted");
        }
    }
}
