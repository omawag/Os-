public class Barber implements Runnable{
    public final String name;
    int delay;
    public Barber(int delay){
        this(delay, "");
    }
    public Barber(int delay, String name){
        this.delay = delay;
        this.name = name;
    }

    public Thread cutHair() throws InterruptedException {
        return new Thread(this);
    }

    @Override
    public void run() {
        System.out.println("Barber " + this.name + " is cutting hair");
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Barber " + this.name + " finished cutting hair");
    }
}
