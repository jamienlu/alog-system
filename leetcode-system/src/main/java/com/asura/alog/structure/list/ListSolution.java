package com.asura.alog.structure.list;

import com.asura.alog.structure.tree.TreeNode;

import java.util.HashSet;
import java.util.Set;

/**
 * solution about listnode
 *
 */
public class ListSolution {
	/**
	 * 翻转链表
	 *
	 * @param head 链表头节点
	 * @return
	 */
	public static ListNode reverseListNode(ListNode head) {
		/**
		 * 思路
		 * 1.新增1个空节点
		 * 2.让头节点指向空节点，节点指向顺序反向
		 * 3.没改变一次指向，节点前进一次
		 */
		ListNode protect = null;
		while (head != null) {
			ListNode nextHead = head.next;
			head.next = protect;
			protect = head;
			head = nextHead;
		}
		return protect;
	}

	/**
	 * 每K个元素内部翻转一次链表
	 *
	 * @param head
	 * @param k
	 * @return
	 */
	public static ListNode reverseKGroup(ListNode head, int k) {
		ListNode protect = new ListNode(0,head);
		ListNode last = protect;

		while (head != null) {
			// 获取待翻转链表
			ListNode end = getGroupNode(head, k-1);
			if (end == null) {
				break;
			}
			ListNode nextGroupHead = end.next;
			//  组内翻转
			reverse(head,nextGroupHead);
			// 更新边
			last.next = end;
			head.next = nextGroupHead;

			last = head;
			head = nextGroupHead;
		}
		return protect.next;
	}

	private static ListNode getGroupNode(ListNode head, int k) {
		while (head != null) {
			if (k == 0) {
				return head;
			}
			head = head.next;
			k--;
		}
		return null;
	}

	private static void reverse(ListNode head, ListNode stop) {
		ListNode last = head;
		head = head.next;
		while (head != stop) {
			ListNode nextHead = head.next;
			head.next = last;
			last = head;
			head = nextHead;
		}
	}

	/**
	 * 合并链表 从小到大
	 *
	 * @param list1
	 * @param list2
	 * @return
	 */
	public static ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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

	/**
	 * 删除链表倒数第n个节点
	 *
	 * @param head
	 * @param n
	 * @return
	 */
	public static ListNode removeNthFromEnd(ListNode head, int n) {
		ListNode protect = new ListNode(0, head);
		int length = getLength(head);
		ListNode cur = protect;
		for (int i = 0; i < length - n; ++i) {
			cur = cur.next;
		}
		cur.next = cur.next.next;
		return protect.next;
	}

	private static int getLength(ListNode head) {
		int length = 0;
		while (head != null) {
			++length;
			head = head.next;
		}
		return length;
	}

	/**
	 * 利用二分合并K个链表转为logK次合并2个链表
	 *
	 * @param lists
	 * @return
	 */
	public static ListNode mergeKLists(ListNode[] lists) {
		return mergeListNodeRange(lists, 0, lists.length - 1);
	}

	private static ListNode mergeListNodeRange(ListNode[] lists, int left, int right) {
		if (left == right) {
			return lists[left];
		}
		if (left > right) {
			return null;
		}
		int mid = left + ((right-left) >> 1);
		return mergeTwoLists(mergeListNodeRange(lists, left, mid), mergeListNodeRange(lists, mid + 1, right));
	}

	/**
	 * 展开为右树链表 - 从小到大
	 *
	 * @param root
	 */
	public void flatten(TreeNode root) {
		TreeNode curr = root;
		while (curr != null) {
			if (curr.left != null) {
				TreeNode next = curr.left;
				TreeNode predecessor = next;
				while (predecessor.right != null) {
					predecessor = predecessor.right;
				}
				// 右子树放在最左右子数
				predecessor.right = curr.right;
				curr.left = null;
				// 向右伸展，右节点接左节点
				curr.right = next;
			}
			// 当前节点跳到之前的左节点重复操作
			curr = curr.right;
		}
	}

	/**
	 * 返回链表到环的节点
	 *
	 * @param head
	 * @return
	 */
	public ListNode detectCycle(ListNode head) {
		Set<ListNode> set = new HashSet<>();
		while (set.add(head)) {
			if (head == null) {
				return null;
			}
			head = head.next;
		}
		return head;
	}

	/**
	 * 链表插入排序
	 *
	 * @param head
	 * @return
	 */
	public ListNode insertionSortList(ListNode head) {
		ListNode protect = new ListNode(0);
		protect.next = head;
		ListNode sorted = head;
		ListNode cur = head.next;
		while (cur != null) {
			if (sorted.val <= cur.val) {
				sorted = sorted.next;
			} else {
				ListNode pre = protect;
				while (pre.next.val <= cur.val) {
					pre = pre.next;
				}
				sorted.next = cur.next;
				cur.next = pre.next;
				pre.next = cur;
			}
			cur = sorted.next;
		}
		return protect.next;
	}


}
