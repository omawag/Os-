public class PrintThread implements Runnable{
    String text;
    public PrintThread(String text){this.text = text;}
    @Override
    public void run() {
        try {
            for (int i = 0; i < 50; i++) {
                System.out.println(text);
                Thread.sleep(100);
            }
        } catch (InterruptedException exception){
            System.out.println("Interrupted");
        }
    }
}
