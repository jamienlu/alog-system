package com.asura.alog.structure.queue;

/**
 * 循环双端队列
 */
public class MyCircularDeque {

    public MyCircularDeque(int k) {

    }

    public boolean insertFront(int value) {
        return true;
    }

    public boolean insertLast(int value) {
        return true;
    }

    public boolean deleteFront() {
        return true;
    }

    public boolean deleteLast() {
        return true;
    }

    public int getFront() {
        return -1;
    }

    public int getRear() {
        return -1;
    }

    public boolean isEmpty() {
        return true;
    }

    public boolean isFull() {
        return true;
    }

    public static void main(String[] args) {
         MyCircularDeque obj = new MyCircularDeque(1);
         boolean param_1 = obj.insertFront(1);
         boolean param_2 = obj.insertLast(1);
         boolean param_3 = obj.deleteFront();
         boolean param_4 = obj.deleteLast();
         int param_5 = obj.getFront();
         int param_6 = obj.getRear();
         boolean param_7 = obj.isEmpty();
         boolean param_8 = obj.isFull();
    }
}
