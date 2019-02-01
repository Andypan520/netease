package test2;

import java.util.concurrent.Semaphore;

/**
 * @author pandechuan 2019/01/28
 */
public class PrintABC {

    public static void main(String[] args) {
        MajusculeABC maj = MajusculeABC.newInstance();
        Thread t_a = new Thread(new Thread_ABC(maj, 'A'));
        Thread t_b = new Thread(new Thread_ABC(maj, 'B'));
        Thread t_c = new Thread(new Thread_ABC(maj, 'C'));
        t_a.start();
        t_b.start();
        t_c.start();
    }
}

class MajusculeABC {
    private static MajusculeABC majusculeABC = null;
    private char character = 'A';

    private MajusculeABC() {
    }

    public static MajusculeABC newInstance() {
        if (majusculeABC == null) {
            majusculeABC = new MajusculeABC();
        }
        return majusculeABC;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character += 1;
        if (this.character == 'D') {
            this.character = 'A';
        }
    }

}

/*class Thread_ABC implements Runnable {
    private MajusculeABC majusculeABC;
    private char character;
    public Thread_ABC(MajusculeABC majusculeABC, char character) {
        this.majusculeABC = majusculeABC;
        this.character = character;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {
            synchronized (majusculeABC) {

                while (this.character != majusculeABC.getCharacter()) {
                    try {
                        majusculeABC.wait();
                    } catch (InterruptedException e) {
                    }
                }
                System.out.print(this.character);
                if (this.character == 'C') {
                    System.out.println();
                }
                i++;
                majusculeABC.setCharacter(this.character);
                majusculeABC.notifyAll();

            }
        }
    }*/

/*class Thread_ABC implements Runnable {
    private static Lock lock = new ReentrantLock();
    private MajusculeABC majusculeABC;
    private char character;

    public Thread_ABC(MajusculeABC majusculeABC, char character) {
        this.majusculeABC = majusculeABC;
        this.character = character;
    }
    @Override
    public void run() {
        int i = 0;
        while (i < 10) {

            try {
                lock.lock();
                while (this.character == majusculeABC.getCharacter()) {
                    System.out.print(this.character);
                    if (this.character == 'C') {
                        System.out.println();
                    }
                    i++;
                    majusculeABC.setCharacter(this.character);
                }
            } finally {
                lock.unlock();
            }
        }
    }*/

class Thread_ABC implements Runnable {
    private static Semaphore semaphore = new Semaphore(1);
    private MajusculeABC majusculeABC;
    private char character;

    public Thread_ABC(MajusculeABC majusculeABC, char character) {
        this.majusculeABC = majusculeABC;
        this.character = character;
    }

    @Override
    public void run() {
        int i = 0;
        while (i < 10) {

            try {
                try {
                    semaphore.acquire();
                    while (this.character == majusculeABC.getCharacter()) {
                        System.out.print(this.character);
                        if (this.character == 'C') {
                            System.out.println();
                        }
                        i++;
                        majusculeABC.setCharacter(this.character);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } finally {
                semaphore.release();
            }
        }
    }
}
