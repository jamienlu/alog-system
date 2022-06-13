package com.asura.structure.recur;

import com.asura.structure.list.ListNode;

public class MergeKListNode {
    public ListNode mergeKLists(ListNode[] lists) {
        return recurMerge(lists, 0, lists.length - 1);
    }

    /**
     * 分治法
     *
     * @param lists 待合并列表
     * @param start 起点
     * @param end 终点
     * @return
     */
    public ListNode recurMerge(ListNode[] lists, int start, int end) {
        // 起点大于终点无效
        if (start > end) {
            return null;
        }
        // 起点等于终点递归终止
        if (start == end) {
            return lists[start];
        }
        // 分治中点
        int mid = (start + end) >> 1;
        // 合并2个有序链表
        return mergeTwoLists(recurMerge(lists, start, mid), recurMerge(lists, mid + 1, end));
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode prtect = new ListNode(0);
        ListNode head = prtect;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                head.next = list1;
                list1 = list1.next;
                head = head.next;
            } else {
                head.next = list2;
                list2 = list2.next;
                head = head.next;
            }
        }
        head.next = list1 == null ? list2 : list1;
        return prtect.next;
    }

}
