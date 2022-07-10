/******************************************************************************
 *  Copyright 2002-2020, Robert Sedgewick and Kevin Wayne.
 *
 *  This file is part of algs4.jar, which accompanies the textbook
 *
 *      Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne,
 *      Addison-Wesley Professional, 2011, ISBN 0-321-57351-X.
 *      http://algs4.cs.princeton.edu
 *
 *
 *  algs4.jar is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  algs4.jar is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with algs4.jar.  If not, see http://www.gnu.org/licenses.
 ******************************************************************************/

package com.asura.alog.structure.queue;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedQueue<T> implements Iterable<T> {
	/**
	 * cao
	 */
    private int n;
	/**
	 * top
	 */
    private Node first;
	/**
	 * tail
	 */
    private Node last;

    private class Node {
        private T item;
        private Node next;
    }

    public LinkedQueue() {
        first = null;
        last  = null;
        n = 0;
        assert check();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;     
    }

    /**
     * see top
	 *
     * @return the item least recently added to this queue
     * @throws NoSuchElementException if this queue is empty
     */
    public T peek() {
        if (isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
        return first.item;
    }

    /**
     * Adds last node.
	 *
     * @param item the item to add
     */
    public void enqueue(T item) {
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
			first = last;
		}
        else {
			oldlast.next = last;
		}
        n++;
        assert check();
    }

    /**
     * remove fisrt node
	 *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
        T item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
			// to avoid loitering
			last = null;
		}
        assert check();
        return item;
    }

    /**
     * Returns a string representation of this queue.
     * @return the sequence of items in FIFO order, separated by spaces
     */
	@Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {
			s.append(item + " ");
		}
        return s.toString();
    } 

    private boolean check() {
		// 负容量
        if (n < 0) {
            return false;
        }
		// 空容量
        else if (n == 0) {
            if (first != null) {
				return false;
			}
            if (last  != null) {
				return false;
			}
        }
        else if (n == 1) {
            if (first == null || last == null) {
				return false;
			}
            if (first != last) {
				return false;
			}
            if (first.next != null) {
				return false;
			}
        }
		// 容量  > 1
        else {
            if (first == null || last == null) {
				return false;
			}
            if (first == last) {
				return false;
			}
            if (first.next == null) {
				return false;
			}
            if (last.next  != null) {
				return false;
			}

            // 链表中间不允许null
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != n) {
				return false;
			}
            // 头节点的尾节点和尾节点一致
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) {
				return false;
			}
        }
        return true;
    } 
 

    /**
     * Returns an iterator that iterates over the items in this queue in FIFO order.
     * @return an iterator that iterates over the items in this queue in FIFO order
     */
	@Override
    public Iterator<T> iterator()  {
        return new LinkedIterator();  
    }

    private class LinkedIterator implements Iterator<T> {
        private Node current = first;
		@Override
        public boolean hasNext()  { return current != null;                     }
		@Override
        public void remove()      { throw new UnsupportedOperationException();  }
		@Override
        public T next() {
            if (!hasNext()) {
				throw new NoSuchElementException();
			}
            T item = current.item;
            current = current.next; 
            return item;
        }
    }



}

