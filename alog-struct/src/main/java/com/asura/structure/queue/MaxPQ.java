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

package com.asura.structure.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 最大优先队列 堆实现
 *
 * @param <T>
 */
public class MaxPQ<T> implements Iterable<T> {
	/**
	 * data
	 */
    private T[] pq;
	/**
	 * cap
	 */
    private int n;
	/**
	 * sort
	 */
	private Comparator<T> comparator;

    public MaxPQ(int initCapacity) {
        pq = (T[]) new Object[initCapacity + 1];
        n = 0;
    }

    /**
     * Initializes an empty priority queue.
     */
    public MaxPQ() {
        this(1);
    }


    public MaxPQ(int initCapacity, Comparator<T> comparator) {
        this.comparator = comparator;
        pq = (T[]) new Object[initCapacity + 1];
        n = 0;
    }

    public MaxPQ(Comparator<T> comparator) {
        this(1, comparator);
    }

	/**
	 * add datas
	 *
	 * @param keys data
	 */
	public MaxPQ(T[] keys) {
        n = keys.length;
        pq = (T[]) new Object[keys.length + 1];
        for (int i = 0; i < n; i++) {
			pq[i+1] = keys[i];
		}
		// 二分法存储最大堆
        for (int k = n/2; k >= 1; k--) {
			sink(k);
		}
        assert isMaxHeap();
    }
      

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }


    public T max() {
        if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
        return pq[1];
    }

    private void resize(int capacity) {
        assert capacity > n;
        T[] temp = (T[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }


    /**
     * Adds a new key to this priority queue.
     *
     * @param  x the new key to add to this priority queue
     */
    public void insert(T x) {
		// cap 2
        if (n == pq.length - 1) {
			resize(2 * pq.length);
		}
        // maintain heap invariant
        pq[++n] = x;
        swim(n);
        assert isMaxHeap();
    }

    /**
     * Removes and returns a largest key on this priority queue.
     *
     * @return a largest key on this priority queue
     * @throws NoSuchElementException if this priority queue is empty
     */
    public T delMax() {
        if (isEmpty()) {
			throw new NoSuchElementException("Priority queue underflow");
		}
        T max = pq[1];
        exch(1, n--);
        sink(1);
        pq[n+1] = null;
		// maintain heap cap
        if ((n > 0) && (n == (pq.length - 1) / 4)) {
			resize(pq.length / 2);
		}
        assert isMaxHeap();
        return max;
    }

	/**
	 * 由下至上 维护堆秩序
	 *
	 * @param k
	 */
    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k/2, k);
            k = k/2;
        }
    }
	/**
	 * 由上至下 维护堆秩序
	 *
	 * @param k
	 */
    private void sink(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && less(j, j+1)) {
				j++;
			}
            if (!less(k, j)) {
				break;
			}
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<T>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        T swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..n] a max heap?
    private boolean isMaxHeap() {
        for (int i = 1; i <= n; i++) {
            if (pq[i] == null) {
				return false;
			}
        }
        for (int i = n+1; i < pq.length; i++) {
            if (pq[i] != null) {
				return false;
			}
        }
        if (pq[0] != null) {
			return false;
		}
        return isMaxHeapOrdered(1);
    }

    // is subtree of pq[1..n] rooted at k a max heap?
    private boolean isMaxHeapOrdered(int k) {
        if (k > n) {
			return true;
		}
        int left = 2*k;
        int right = 2*k + 1;
        if (left  <= n && less(k, left))  {
			return false;
		}
        if (right <= n && less(k, right)) {
			return false;
		}
        return isMaxHeapOrdered(left) && isMaxHeapOrdered(right);
    }


   /***************************************************************************
    * Iterator.
    ***************************************************************************/

    /**
     * Returns an iterator that iterates over the keys on this priority queue
     * in descending order.
     * The iterator doesn't implement {@code remove()} since it's optional.
     *
     * @return an iterator that iterates over the keys in descending order
     */
	@Override
    public Iterator<T> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<T> {

        // create a new pq
        private MaxPQ<T> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) {
				copy = new MaxPQ<T>(size());
			}
            else {
				copy = new MaxPQ<T>(size(), comparator);
			}
            for (int i = 1; i <= n; i++) {
				copy.insert(pq[i]);
			}
        }
		@Override
        public boolean hasNext()  { return !copy.isEmpty();                     }
		@Override
        public void remove()      { throw new UnsupportedOperationException();  }
		@Override
        public T next() {
            if (!hasNext()) {
				throw new NoSuchElementException();
			}
            return copy.delMax();
        }
    }
}

