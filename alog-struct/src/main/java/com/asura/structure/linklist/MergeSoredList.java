package com.asura.structure.linklist;

/**
 * 合并有序链表
 */
public class MergeSoredList {
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
