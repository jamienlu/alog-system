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
package com.asura.structure.stack;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class LinkedStack<T> implements Iterable<T> {
	/**
	 * stack size
	 */
    private int n;
	/**
	 * top
	 */
	private Node first;

    private class Node {
        private T item;
        private Node next;
    }


    public LinkedStack() {
        first = null;
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
     * push data
	 *
     * @param item the item to add
     */
    public void push(T item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        n++;
        assert check();
    }

    /**
     * pop top
	 * .
     * @return Item
     * @throws NoSuchElementException if this stack is empty
     */
    public T pop() {
        if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
        T item = first.item;
		// delete first node
        first = first.next;
        n--;
        assert check();
		// delete first node
        return item;
    }


	/**
	 * see top
	 *
	 * @return Item
	 */
	public T peek() {
        if (isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
        return first.item;
    }

    /**
     * Returns a string representation of this stack.
     * @return the sequence of items in the stack in LIFO order, separated by spaces
     */
	@Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (T item : this) {
			s.append(item + " ");
		}
        return s.toString();
    }
       
    /**
     * @return an iterator to this stack that iterates through the items in LIFO order.
     */
	@Override
    public Iterator<T> iterator() {
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

    private boolean check() {
        // check cap and data match
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) {
				return false;
			}
        }
        else if (n == 1) {
            if (first == null) {
				return false;
			}
            if (first.next != null) {
				return false;
			}
        }
        else {
            if (first == null) {
				return false;
			}
            if (first.next == null) {
				return false;
			}
        }
        // mid null error
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) {
			return false;
		}
        return true;
    }

}
