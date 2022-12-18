import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Submission{
    public String id;
    Problem problem;
    public boolean[] results;
    public String[] answers;

    Semaphore[] semaphores;
    int num;
    public Submission(String id, char problem, int submissionNumber){
        this.id = id;
        this.problem = new Problem(problem);
        this.num = submissionNumber;
        File[] testcases = new File("testcases/"+ this.problem.problemNumber +"/" + submissionNumber).listFiles();
        answers = new String[Objects.requireNonNull(testcases).length];
        for (int i = 0; i < testcases.length; i++) {
            try {
                answers[i] = Files.readString(testcases[i].toPath());
            } catch (IOException e){throw new RuntimeException(e);}
        }
        results = new boolean[answers.length];
        semaphores = new Semaphore[answers.length];
        for (int i = 0; i < answers.length; i++) {
            semaphores[i] = new Semaphore(0);
        }
    }

    public void getTested(int testNumber) throws InterruptedException {
        System.out.println(this.id + " submitted and is being tested on testcase " + testNumber);
        semaphores[testNumber].acquire();
        System.out.println("Testcase " + testNumber + " of " + this.id + " got tested");
    }

    public void waitInQueue(){
        System.out.println("Submission " + this.id + " is waiting in queue");
    }

    public void leaveQueue(){
        System.out.println("Submission " + this.id + " left the queue");
    }
}
