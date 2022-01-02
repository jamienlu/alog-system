package com.asura.structure.stack;

import java.util.Stack;

public class PlusOne {
    public int[] plusOne(int[] digits) {
        // 记录是否需要+1
        Stack<Integer> stack = new Stack();
        stack.push(1);
        for (int i = digits.length - 1; i >= 0; i--) {
            int value = digits[i];
            if (!stack.isEmpty()) {
                value += stack.peek();
                if (value < 10) {
                    // 不进位后续不需要在+1弹出
                    stack.pop();
                } else {
                    value -= 10;
                }
            }
            digits[i] = value;
        }
        // 到首位需要+1
        if (!stack.isEmpty()) {
            int[] target = new int[digits.length + 1];
            target[0] = 1;
            for (int i = 0; i < digits.length ; i++) {
                target[i+1] = digits[i];
            }
            return target;
        } else {
            return digits;
        }
    }
}
