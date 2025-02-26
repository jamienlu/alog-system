package com.asura.structure.design;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author jamieLu
 * @create 2025-02-11
 */
public class Calc {
    public int calculate(String s) {
        Map<Integer, Integer> calcIndex = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
           if (s.charAt(i) == '(') {
               stack.push(i);
           } else if (s.charAt(i) == ')') {
               int start = stack.pop();
               calcIndex.put(start, i);
           }
        }
        return calc(s, 0, s.length() - 1, calcIndex);
    }

    private int calc(String s, int start, int end, Map<Integer, Integer> calcIndex) {
        Stack<Integer> calcStack = new Stack<>();
        int result = 0;
        char sign = '+';
        for (int i = start; i <= end; i++) {
            char c = s.charAt(i);
            // 转数值
            if (Character.isDigit(c)) {
                result = result * 10 + (c- '0');
            }
            if (c == '(') {
                // 迭代表达式值
                result = calc(s, i + 1, calcIndex.get(i), calcIndex);
                i = calcIndex.get(i);
            }
            // 字符计算
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == end) {
                switch (sign) {
                    case '+': calcStack.push(result);
                        break;
                    case '-': calcStack.push(-result);
                        break;
                    case '*': calcStack.push(calcStack.pop() * result);
                        break;
                    case '/': calcStack.push(calcStack.pop() / result);
                        break;
                }
                result = 0;
                sign = c;
            }
        }
        // 计算栈
        while (!calcStack.isEmpty()) {
            result += calcStack.pop();
        }
        return result;
    }
}
