package com.asura.template.doubleref;

import com.asura.structure.list.ListNode;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 双指针
 * 链表分解
 * 翻转回文
 *
 * @author jamieLu
 * @create 2025-02-01
 */
public class DoubleRefCase {

    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        // 快慢指针初始化指向 head
        ListNode slow = head, fast = head;
        // 快指针走到末尾时停止
        while (fast != null && fast.next != null) {
            // 慢指针走一步，快指针走两步
            slow = slow.next;
            fast = fast.next.next;
            // 快慢指针相遇，说明含有环
            if (slow == fast) {
                return true;
            }
        }
        // 不包含环
        return false;
    }

    /**
     * 142. 环形链表 II
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        // 双指针记录成环的步数k
        // 环长度为m k = m*n
        // 假设环离起点为k-x 那么慢指针还走了x步到相遇点，走到环起点还要K-X步
        // 相遇后从相遇点和起点出发同时走K-X步就是环起点
        ListNode slow = head,fast =head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        fast = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return fast;
    }

    /**
     * 160. 相交链表
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // hashset 2个一起遍历 有重复结束
        // 链表逻辑相连 相交后归于一条线，相当于都走过了后面的路和交点前得路
        ListNode p1 = headA,p2 = headB;
        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        // p1 == p2 = null 无交点
        return p1;
    }
    /**
     * 19. 删除链表的倒数第 N 个结点
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 倒数N 就是 size - k
        // 走完K后再走 size-k就到达终点，从头走的刚好到倒数N的位置
        // 创建一个虚拟节点链接head 查询倒数N+1个直接指向下下个节点
        ListNode dumy = new ListNode(-1);
        dumy.next = head;

        ListNode p = dumy;
        for (int i = 0; i < n+1; i++) {
            p = p.next;
        }
        ListNode temp = dumy;
        while (p != null) {
            p = p.next;
            temp = temp.next;
        }
        temp.next = temp.next.next;
        return dumy.next;
    }
    /**
     * 21. 合并两个有序链表
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // 1.往优先级队列塞然后全部排出
        // 2. 创建新列表 2个指针同时遍历，谁小放入同时指针后移
        ListNode prtect = new ListNode(0);
        ListNode head = prtect;
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                head.next = p1;
                p1 = p1.next;
            } else {
                head.next = p2;
                p2 = p2.next;
            }
            head = head.next;
        }
        if (p1 != null) {
            head.next = p1;
        }
        if (p2 != null) {
            head.next = p2;
        }
        return prtect.next;

    }
    /**
     * 23. 合并K个升序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 创建1个优先级队列，把每个链表的头结点放入
        // 循环队列，每次取出最小的，放入结果链表，后移后放入队列
        // 队列排空完成
        ListNode protect = new ListNode(-1);
        Queue<ListNode> queue = new PriorityQueue<>(Comparator.comparingInt(v -> v.val));
        for (ListNode list : lists) {
            if (list != null) {
                queue.add(list);
            }
        }
        ListNode cur = protect;
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            cur.next = node;
            cur = cur.next;
            if (node.next != null) {
                queue.add(node.next);
            }
        }
        return protect.next;
    }
    /**
     * 86. 分隔链表
     */
    public ListNode partition(ListNode head, int x) {
        // 创建2个链表一个存小于1个存大于
        // 遍历存链表
        // 合并链表
        ListNode dumy1 = new ListNode(-1);
        ListNode dumy2 = new ListNode(-1);
        ListNode p1 = dumy1;
        ListNode p2 = dumy2;
        ListNode p = head;
        while (p != null) {
            if (p.val < x) {
                p1.next = p;
                p1 = p1.next;
            } else {
                p2.next = p;
                p2 = p2.next;
            }
            ListNode next = p.next;
            p.next = null;
            p = next;
        }
        p1.next = dumy2.next;
        return dumy1.next;
    }
    /**
     * 206. 反转链表
     */
    public ListNode reverseList(ListNode head) {
        // 递归到尾了返回自己
        if (head == null || head.next == null) {
            return head;
        }
        // 返回链表尾
        ListNode lastNode = reverseList(head.next);
        // head -> last -> null 修改为last -> head
        head.next.next = head;
        head.next = null;
        return lastNode;
    }

    /**
     * 92. 反转链表 II
     */
    private ListNode nextNode;
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // 从头开始
        if (left == 1) {
            return reverseList(head, right);
        }
        head.next = reverseBetween(head.next, left - 1, right - 1);
        return head;
    }
    // 翻转链表前n个
    public ListNode reverseList(ListNode head, int step) {
        // 反转个数为1记录后继节点
        if (step == 1) {
            nextNode = head.next;
            return head;
        }
        ListNode last = reverseList(head.next, step - 1);
        // head -> last -> nextNode 修改为last -> head -> nextNode
        head.next.next = head;
        head.next = nextNode;
        return last;
    }
    /**
     * 25. K 个一组翻转链表
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 记录翻转的区间
        ListNode left = head;
        ListNode right = head;
        // 区间不足返回头节点
        for (int i = 0; i < k; i++) {
            if (right == null) {
                return head;
            }
            right = right.next;
        }
        // 翻转前半部分
        ListNode newHead = reverseList(left, k);
        // 翻转完毕 链接后续节点
        left.next = reverseKGroup(right, k);
        return newHead;
    }
}
