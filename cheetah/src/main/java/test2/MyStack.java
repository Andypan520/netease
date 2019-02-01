package test2;

import java.util.LinkedList;

/**
 * @author pandechuan 2019/01/26
 * <p>
 * 模拟 队列 FIFO
 */
public class MyStack {

    private LinkedList linkedList;

    public MyStack() {
        this.linkedList = new LinkedList();
    }

    public static void main(String[] args) {
        MyStack myQuene = new MyStack();
        myQuene.add("java01");
        myQuene.add("java02");
        myQuene.add("java03");
        myQuene.add("java04");
        while (myQuene.mySize() > 0) {
            System.out.println(myQuene.remove());
        }
    }

    public void add(Object object) {
        linkedList.addFirst(object);
    }

    public Object remove() {
        return linkedList.removeFirst();
    }

    public boolean isNull() {
        return linkedList.isEmpty();
    }

    public int mySize() {
        return linkedList.size();
    }

}

