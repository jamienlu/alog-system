package com.asura.structure.work;

import com.asura.structure.tree.MinArrayHeap;
import com.asura.structure.tree.TreeNode;

import java.util.Arrays;

import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
	private static final Test a = new Test();
    public static String serialize(TreeNode root) {
        if (root == null) {
            return "NULL";
        }
        String result = String.valueOf(root.val);
        String left = serialize(root.left);
        String right = serialize(root.right);
        return result + "," + left + "," + right;
    }


}
