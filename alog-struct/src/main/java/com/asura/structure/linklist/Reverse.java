package com.asura.structure.linklist;

/**
 * 翻转链表
 */
public class Reverse {
    public ListNode reverseList(ListNode head) {
        ListNode protect = null;
        while (head != null) {
            ListNode nextHead = head.next;
            head.next = protect;
            protect = head;
            head = nextHead;
        }
        return protect;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode protext = new ListNode(0,head);
        ListNode last = protext;
        // 找一组 一组head - end
        while (head != null) {
            ListNode end = getGroupNode(head, k-1);
            if (end == null) {
                break;
            }
            ListNode nextGroupHead = end.next;
            //  组内部 翻转
            reverse(head, nextGroupHead);
            // 更新边
            last.next = end;
            head.next = nextGroupHead;

            last = head;
            head = nextGroupHead;
        }
        return protext.next;
    }

    public void reverse(ListNode head, ListNode stop) {
        ListNode last = head;
        head = head.next;
        while (head != stop) {
            ListNode nextHead = head.next;
            head.next = last;
            last = head;
            head = nextHead;
        }
    }

    public ListNode getGroupNode(ListNode head, int k) {
        while (head != null) {
            if (k == 0) {
                return head;
            }
            head = head.next;
            k--;
        }
        return null;
    }
}
