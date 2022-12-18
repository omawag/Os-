public class Customer implements Runnable{
    private final String name;
    int delay;
    public Customer(int delay){
        this(delay, "");
    }
    public Customer(int delay, String name){
        this.delay = delay;
        this.name = name;
    }

    public Thread getHairCut() throws InterruptedException {
        return new Thread(this);
    }

    @Override
    public void run(){
        System.out.println("Customer " + this.name + " is getting their hair cut");
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Customer " + this.name + " got their hair cut");
    }

    public void enterShop(){
        System.out.println("Customer " + this.name + " entered the shop");
    }

    public void leaveShop(){
        System.out.println("Customer " + this.name + " left the shop");
    }
}
