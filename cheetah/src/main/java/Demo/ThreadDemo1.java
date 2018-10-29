package Demo;

/**
 * Created by pandechuan on 2018/10/15.
 */
class ThreadTest implements Runnable {
    private int tickets = 20;

    @Override
    public void run() {
        while (true) {
            if (tickets > 0) {
                System.out.println(Thread.currentThread().getName()
                        + " is saling ticket " + tickets--);
            } else {
                break;
            }
        }
    }
}

public class ThreadDemo1 {

    public static void main(String[] args) {
        ThreadTest t = new ThreadTest();
        new Thread(t).start();
        new Thread(t).start();
        new Thread(t).start();
    }
}