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

package com.asura.alog.structure.tree;

import com.asura.alog.structure.queue.LinkedQueue;

import java.util.NoSuchElementException;

/**
 * 二叉树
 *
 * @param <U>
 * @param <T>
 */
public class BinarySearchTree<U extends Comparable<U>, T> {
    private Node root;

    private class Node {
		/**
		 * node add sort rule
		 */
        private U key;
		/**
		 * node data
		 */
        private T val;
        private Node left;
		private Node right;
        private int size;

        public Node(U key, T val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }


    public boolean isEmpty() {
        return size() == 0;
    }


    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
			return 0;
		}
        else {
			return x.size;
		}
    }


    public boolean contains(U key) {
        if (key == null) {
			throw new IllegalArgumentException("argument to contains() is null");
		}
        return get(key) != null;
    }

    /**
     * find by key
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public T get(U key) {
        return get(root, key);
    }

    private T get(Node x, U key) {
        if (key == null) {
			throw new IllegalArgumentException("calls get() with a null key");
		}
        if (x == null) {
			return null;
		}
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
			return get(x.left, key);
		}
        else if (cmp > 0) {
			return get(x.right, key);
		}
        else  {
			return x.val;
		}
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(U key, T val) {
        if (key == null) {
			throw new IllegalArgumentException("calls put() with a null key");
		}
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }

    private Node put(Node x, U key, T val) {
        if (x == null) {
			return new Node(key, val, 1);
		}
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
			x.left = put(x.left,  key, val);
		}
        else if (cmp > 0) {
			x.right = put(x.right, key, val);
		}
        else  {
			x.val = val;
		}
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }


    /**
     * Removes the smallest key and associated value
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMin() {
        if (isEmpty()) {
			throw new NoSuchElementException("Symbol table underflow");
		}
        root = deleteMin(root);
        assert check();
    }

    private Node deleteMin(Node x) {
        if (x.left == null) {
			return x.right;
		}
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the largest key and associated value
     *
     * @throws NoSuchElementException if the symbol table is empty
     */
    public void deleteMax() {
        if (isEmpty()) {
			throw new NoSuchElementException("Symbol table underflow");
		}
        root = deleteMax(root);
        assert check();
    }

    private Node deleteMax(Node x) {
        if (x.right == null) {
			return x.left;
		}
        x.right = deleteMax(x.right);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    /**
     * Removes the specified key and its associated value
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(U key) {
        if (key == null) {
			throw new IllegalArgumentException("calls delete() with a null key");
		}
        root = delete(root, key);
        assert check();
    }

    private Node delete(Node x, U key) {
        if (x == null) {
			return null;
		}
        int cmp = key.compareTo(x.key);
        if  (cmp < 0) {
			x.left  = delete(x.left,  key);
		} else if (cmp > 0) {
			x.right = delete(x.right, key);
		}
        else { 
            if (x.right == null) {
				return x.left;
			}
            if (x.left == null) {
				return x.right;
			}
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        } 
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    } 


    /**
     * Returns the smallest key in the symbol table.
     *
     * @return the smallest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public U min() {
        if (isEmpty()) {
			throw new NoSuchElementException("calls min() with empty symbol table");
		}
        return min(root).key;
    } 

    private Node min(Node x) { 
        if (x.left == null) {
			return x;
		}
        else  {
			return min(x.left);
		}
    } 

    /**
     * Returns the largest key in the symbol table.
     *
     * @return the largest key in the symbol table
     * @throws NoSuchElementException if the symbol table is empty
     */
    public U max() {
        if (isEmpty()) {
			throw new NoSuchElementException("calls max() with empty symbol table");
		}
        return max(root).key;
    } 

    private Node max(Node x) {
        if (x.right == null) {
			return x;
		}
        else  {
			return max(x.right);
		}
    } 

    /**
     * Returns the largest key in the symbol table less than or equal to {@code key}.
     *
     * @param  key the key
     * @return the largest key in the symbol table less than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public U floor(U key) {
        if (key == null) {
			throw new IllegalArgumentException("argument to floor() is null");
		}
        if (isEmpty()) {
			throw new NoSuchElementException("calls floor() with empty symbol table");
		}
        Node x = floor(root, key);
        if (x == null) {
			throw new NoSuchElementException("argument to floor() is too small");
		}
        else {
			return x.key;
		}
    } 

    private Node floor(Node x, U key) {
        if (x == null) {
			return null;
		}
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
			return x;
		}
        if (cmp <  0) {
			return floor(x.left, key);
		}
        Node t = floor(x.right, key); 
        if (t != null) {
			return t;
		}
        else {
			return x;
		}
    } 

    public U floor2(U key) {
        U x = floor2(root, key, null);
        if (x == null){
			throw new NoSuchElementException("argument to floor() is too small");
		}
        else {
			return x;
		}

    }

    private U floor2(Node x, U key, U best) {
        if (x == null) {
			return best;
		}
        int cmp = key.compareTo(x.key);
        if  (cmp  < 0) {
			return floor2(x.left, key, best);
		}
        else if (cmp  > 0) {
			return floor2(x.right, key, x.key);
		}
        else  {
			return x.key;
		}
    } 

    /**
     * Returns the smallest key in the symbol table greater than or equal to {@code key}.
     *
     * @param  key the key
     * @return the smallest key in the symbol table greater than or equal to {@code key}
     * @throws NoSuchElementException if there is no such key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public U ceiling(U key) {
        if (key == null) {
			throw new IllegalArgumentException("argument to ceiling() is null");
		}
        if (isEmpty()) {
			throw new NoSuchElementException("calls ceiling() with empty symbol table");
		}
        Node x = ceiling(root, key);
        if (x == null) {
			throw new NoSuchElementException("argument to floor() is too large");
		} else {
			return x.key;
		}
    }

    private Node ceiling(Node x, U key) {
        if (x == null) {
			return null;
		}
        int cmp = key.compareTo(x.key);
        if (cmp == 0) {
			return x;
		}
        if (cmp < 0) { 
            Node t = ceiling(x.left, key); 
            if (t != null) {
				return t;
			} else {
				return x;
			}
        } 
        return ceiling(x.right, key); 
    } 

    /**
     * Return the key in the symbol table of a given {@code rank}.
     * This key has the property that there are {@code rank} keys in
     * the symbol table that are smaller. In other words, this key is the
     * ({@code rank}+1)st smallest key in the symbol table.
     *
     * @param  rank the order statistic
     * @return the key in the symbol table of given {@code rank}
     * @throws IllegalArgumentException unless {@code rank} is between 0 and
     *        <em>n</em>–1
     */
    public U select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + rank);
        }
        return select(root, rank);
    }

    // Return key in BST rooted at x of given rank.
    // Precondition: rank is in legal range.
    private U select(Node x, int rank) {
        if (x == null) {
			return null;
		}
        int leftSize = size(x.left);
        if (leftSize > rank) {
			return select(x.left,  rank);
		} else if (leftSize < rank) {
			return select(x.right, rank - leftSize - 1);
		}
        else {
			return x.key;
		}
    }

    /**
     * Return the number of keys in the symbol table strictly less than {@code key}.
     *
     * @param  key the key
     * @return the number of keys in the symbol table strictly less than {@code key}
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int rank(U key) {
        if (key == null) {
			throw new IllegalArgumentException("argument to rank() is null");
		}
        return rank(key, root);
    } 

    // Number of keys in the subtree less than key.
    private int rank(U key, Node x) {
        if (x == null) {
			return 0;
		}
        int cmp = key.compareTo(x.key); 
        if (cmp < 0) {
			return rank(key, x.left);
		}
        else if (cmp > 0) {
			return 1 + size(x.left) + rank(key, x.right);
		}
        else {
			return size(x.left);
		}
    } 

    /**
     * Returns all keys in the symbol table in ascending order,
     * as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table in ascending order
     */
    public Iterable<U> keys() {
        if (isEmpty()) {
			return new LinkedQueue<U>();
		}
        return keys(min(), max());
    }

    /**
     * Returns all keys in the symbol table in the given range
     * in ascending order, as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo} 
     *         (inclusive) and {@code hi} (inclusive) in ascending order
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public Iterable<U> keys(U lo, U hi) {
        if (lo == null) {
			throw new IllegalArgumentException("first argument to keys() is null");
		}
        if (hi == null) {
			throw new IllegalArgumentException("second argument to keys() is null");
		}
		LinkedQueue<U> queue = new LinkedQueue<U>();
        keys(root, queue, lo, hi);
        return queue;
    } 

    private void keys(Node x, LinkedQueue<U> queue, U lo, U hi) {
        if (x == null) {
			return;
		}
        int cmplo = lo.compareTo(x.key); 
        int cmphi = hi.compareTo(x.key); 
        if (cmplo < 0) {
			keys(x.left, queue, lo, hi);
		}
        if (cmplo <= 0 && cmphi >= 0) {
			queue.enqueue(x.key);
		}
        if (cmphi > 0) {
			keys(x.right, queue, lo, hi);
		}
    } 

    /**
     * Returns the number of keys in the symbol table in the given range.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return the number of keys in the symbol table between {@code lo} 
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public int size(U lo, U hi) {
        if (lo == null) {
			throw new IllegalArgumentException("first argument to size() is null");
		}
        if (hi == null) {
			throw new IllegalArgumentException("second argument to size() is null");
		}
        if (lo.compareTo(hi) > 0) {
			return 0;
		}
        if (contains(hi)) {
			return rank(hi) - rank(lo) + 1;
		} else  {
			return rank(hi) - rank(lo);
		}
    }

    /**
     * Returns the height of the BST (for debugging).
     *
     * @return the height of the BST (a 1-node tree has height 0)
     */
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) {
			return -1;
		}
        return 1 + Math.max(height(x.left), height(x.right));
    }

    /**
     * Returns the keys in the BST in level order (for debugging).
     *
     * @return the keys in the BST in level order traversal
     */
    public Iterable<U> levelOrder() {
		LinkedQueue<U> keys = new LinkedQueue<U>();
		LinkedQueue<Node> queue = new LinkedQueue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) {
				continue;
			}
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }

  /*************************************************************************
    *  Check integrity of BST data structure.
    ***************************************************************************/
    private boolean check() {
        if (!isBST()) {
			System.out.println("Not in symmetric order");
		}
        if (!isSizeConsistent()) {
			System.out.println("Subtree counts not consistent");
		}
        if (!isRankConsistent()) {
			System.out.println("Ranks not consistent");
		}
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    // does this binary tree satisfy symmetric order?
    // Note: this test also ensures that data structure is a binary tree since order is strict
    private boolean isBST() {
        return isBST(root, null, null);
    }

    // is the tree rooted at x a BST with all keys strictly between min and max
    // (if min or max is null, treat as empty constraint)
    // Credit: Bob Dondero's elegant solution
    private boolean isBST(Node x, U min, U max) {
        if (x == null) {
			return true;
		}
        if (min != null && x.key.compareTo(min) <= 0) {
			return false;
		}
        if (max != null && x.key.compareTo(max) >= 0) {
			return false;
		}
        return isBST(x.left, min, x.key) && isBST(x.right, x.key, max);
    } 

    // are the size fields correct?
    private boolean isSizeConsistent() { return isSizeConsistent(root); }
    private boolean isSizeConsistent(Node x) {
        if (x == null) {
			return true;
		}
        if (x.size != size(x.left) + size(x.right) + 1) {
			return false;
		}
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    } 

    // check that ranks are consistent
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
			if (i != rank(select(i))) {
				return false;
			}
		}
        for (U key : keys()) {
			if (key.compareTo(select(rank(key))) != 0) {
				return false;
			}
		}
        return true;
    }


}

