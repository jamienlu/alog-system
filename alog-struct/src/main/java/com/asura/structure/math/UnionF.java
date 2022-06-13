package com.asura.structure.math;

public class UnionF {
	/**
	 * 分量索引
	 */
	private int[] parent;
	/**
	 * 分量信息
	 */
	private byte[] rank;
	/**
	 * cap
	 */
	private int count;

	public UnionF(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}
		count = n;
		parent = new int[n];
		rank = new byte[n];
		for (int i = 0; i < n; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
	}

	/**
	 * Returns the canonical element of the set containing element {@code p}.
	 *
	 * @param  p 分量索引
	 * @return the canonical element of the set containing {@code p}
	 * @throws IllegalArgumentException unless {@code 0 <= p < n}
	 */
	public int find(int p) {
		validate(p);
		while (p != parent[p]) {
			parent[p] = parent[parent[p]];
			p = parent[p];
		}
		return p;
	}

	/**
	 * Returns the number of sets.
	 *
	 * @return the number of sets (between {@code 1} and {@code n})
	 */
	public int count() {
		return count;
	}

	/**
	 * Returns true if the two elements are in the same set.
	 *
	 * @param  p one element
	 * @param  q the other element
	 * @return {@code true} if {@code p} and {@code q} are in the same set;
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException unless
	 *         both {@code 0 <= p < n} and {@code 0 <= q < n}
	 * @deprecated Replace with two calls to {@link #find(int)}.
	 */
	@Deprecated
	public boolean connected(int p, int q) {
		return find(p) == find(q);
	}

	/**
	 * Merges the set containing element {@code p} with the
	 * the set containing element {@code q}.
	 *
	 * @param  p one element
	 * @param  q the other element
	 * @throws IllegalArgumentException unless
	 *         both {@code 0 <= p < n} and {@code 0 <= q < n}
	 */
	public void union(int p, int q) {
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ) {
			return;
		}
		// make root of smaller rank point to root of larger rank
		if (rank[rootP] < rank[rootQ]) {
			parent[rootP] = rootQ;
		}
		else if (rank[rootP] > rank[rootQ]) {
			parent[rootQ] = rootP;
		}
		else {
			parent[rootQ] = rootP;
			rank[rootP]++;
		}
		count--;
	}

	private void validate(int p) {
		int n = parent.length;
		if (p < 0 || p >= n) {
			throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
		}
	}
}
