package com.asura.structure.stack;

import com.asura.structure.list.ListNode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author jamieLu
 * @create 2025-02-05
 */
public class StackCase {
    /**
     * 71. 简化路径
     */
    public String simplifyPath(String path) {
        // 放入栈 然后取出解析
        Stack<String> stack = new Stack<>();
        String[] res = path.split("/", -1);
        for (int i = 0; i < res.length; i++) {
            String cur = res[i];
            if (cur.equals(".") || cur.isEmpty()) {
                continue;
            } else if (cur.equals("..")) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            // 满足条件放入栈
            } else {
                stack.push(cur);
            }
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, "/" + stack.pop());
        }
        return result.isEmpty() ? "/" : result.toString();
    }
    /**
     * 143. 重排链表
     */
    public void reorderList(ListNode head) {
        // 放入栈 然后取出轮流插入
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (!stack.isEmpty()){
            ListNode temp = stack.pop();
            ListNode next = cur.next;
            if (cur == temp || temp.next == next) {
                temp.next = null;
                return;
            }
            cur.next = temp;
            temp.next = next;

            cur = next;
        }
    }
    /**
     * 150. 逆波兰表达式求值
     */
    public int evalRPN(String[] tokens) {
        // 数值入栈 运算符吐出2个数
        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int right = stack.pop();
                int left = stack.pop();
                int sum = switch (token) {
                    case "+" -> left + right;
                    case "-" -> left - right;
                    case "*" -> left * right;
                    case "/" -> left / right;
                    default -> right;
                };
                stack.push(sum);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.peek();
    }
    public int lengthLongestPath(String input) {
        // 栈存储当前路径长度
        // 遇到文件名,计算累计和 + 当前文件名的长度 + 路径分隔符（栈长度）
        Stack<Integer> stack = new Stack<>();
        String[] paths = input.split("\n");
        int maxLen = 0;
        for (String path : paths) {
            int level = path.lastIndexOf("\t") + 1;
            while (level < stack.size()) {
                stack.pop();
            }
            if (path.contains(".")) {
                int sum = stack.stream().mapToInt(Integer::intValue).sum();
                sum = sum + path.length() - level + stack.size();
                maxLen = Math.max(maxLen, sum);
            } else {
                stack.push(path.length() - level);
            }
        }
        return maxLen;
    }
    /**
     * 155. 最小栈
     */
    private class MinStack {
        Stack<Integer> stack;
        Stack<Integer> minStack;
        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            int val = stack.pop();
            if (val == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
    /**
     * 895. 最大频率栈
     */
    private class FreqStack {
        // 记录最大频率
        private int maxFreq;
        // 记录值得频率
        private Map<Integer, Integer> valToFreq;
        // 记录频率的值栈

        private Map<Integer, Stack<Integer>> freqToVals;

        public FreqStack() {
            maxFreq = 0;
            valToFreq = new HashMap<>();
            freqToVals = new HashMap<>();
        }

        public void push(int val) {
            valToFreq.put(val, valToFreq.getOrDefault(val, 0) + 1);
            int freq = valToFreq.get(val);
            if (freq > maxFreq) {
                maxFreq = freq;
            }
            Stack<Integer> stack = freqToVals.get(freq);
            if (stack != null) {
                stack.push(val);
            } else {
                stack = new Stack<>();
                stack.push(val);
                freqToVals.put(freq, stack);
            }
        }

        public int pop() {
            Stack<Integer> stack = freqToVals.get(maxFreq);
            int val = stack.pop();
            if (valToFreq.get(val) == 1) {
                valToFreq.remove(val);
            } else {
                valToFreq.put(val, valToFreq.get(val) - 1);
            }
            if (stack.isEmpty()) {
                freqToVals.remove(maxFreq);
                if (!freqToVals.isEmpty()) {
                    maxFreq = freqToVals.keySet().stream().max(Integer::compareTo).get();
                } else {
                    maxFreq = 0;
                }
            }
            return val;
        }
    }
}
