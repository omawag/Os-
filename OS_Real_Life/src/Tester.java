public class Tester implements Runnable{
    public final String name;
    Submission submission;
    int number;
    public Tester(Submission submission, int number){
        this(submission,number ,"");
    }
    public Tester(Submission submission,int number, String name){
        this.submission = submission;
        this.name = name;
        this.number = number;
    }


    public Thread testFile() throws InterruptedException {
        return new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("Tester " + this.name + " is testing testcase " + this.number + " of submission " + submission.id);
        submission.results[number] = submission.answers[number].compareTo(submission.problem.answers[number]) == 0;
        submission.semaphores[number].release();
        System.out.println("Tester " + this.name + " finished testing test case " + this.number + " of submission " + submission.id);
    }
}
