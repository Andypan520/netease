package test2;

import java.util.LinkedList;

/**
 * @author pandechuan 2019/01/28
 */
public class StackList<E> {
    private LinkedList<E> ll = new LinkedList<>();

    //入栈
    public void push(E e) {
        ll.addFirst(e);
    }

    //查看栈顶元素但不移除
    public E peek() {
        return ll.getFirst();
    }

    //出栈
    public E pop() {
        return ll.removeFirst();
    }

    //判空
    public boolean empty() {
        return ll.isEmpty();
    }

    //打印栈元素
    public String mytoString() {
        return ll.toString();
    }
}