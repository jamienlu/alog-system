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
		 * 3.每改变一次指向，节点前进一次
		 */
		ListNode protect = new ListNode();
		while (head != null) {
			// 记录下次遍历的节点
			ListNode tempHead = head.next;
			// 后继节点指向前一个节点
			head.next = protect;
			protect = head;
			// 准备遍历下一个节点
			head = tempHead;
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
		/**
		 * 思路
		 * 1.遍历链表获取达到K个长度或链表尾巴的子链表
		 * 2.翻转子链表
		 * 3.更新子链表和链表的边界
		 * 4.循环 直到1为null
		 */
		// 新增保护节点
		ListNode protect = new ListNode(0,head);
		// 分组每一组的保护节点要变化
		ListNode groupProtect = protect;
		while (head != null) {
			// 获取组尾节点
			ListNode tail = getTail(head, k-1);
			if (tail == null) {
				break;
			} else {
				// 记录下一组的头
				ListNode nextHead = tail.next;
				// 翻转
				reverse(head, nextHead);
				// 更新这次翻转的头和尾
				groupProtect.next = tail;
				head.next = nextHead;
				// 更新下一组翻转的头
				groupProtect = head;
				head = nextHead;
			}
		}
		return protect.next;
	}


	private static ListNode getTail(ListNode head, int k) {
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
		/**
		 * 1.创建合并链表头节点
		 * 2.指针指向2链表最小值，链表指针后移
		 * 3.其中一个链表指向空时，结束遍历，指向剩余链表
		 */
		// 保护节点记录合并链表头节点
		ListNode prtect = new ListNode(0);
		// 移动头节点
		ListNode head = prtect;

		while (list1 != null && list2 != null) {
			if (list1.val < list2.val) {
				head.next = list1;
				list1 = list1.next;
				head = head.next;
			} else {
				head.next = list2;
				list2 = list2.next;
				head = head.next;
			}
		}
		// 追加剩余节点
		head.next = list1 == null ? list2 : list1;
		return  prtect.next;
	}

	/**
	 * 删除链表倒数第n个节点
	 *
	 * @param head
	 * @param n
	 * @return
	 */
	public static ListNode removeNthFromEnd(ListNode head, int n) {
		/**
		 * 1.创建保护节点用于遍历
		 * 2.寻找链表长度
		 * 3.遍历链表到删除节点前
		 * 4.设置该节点的next指针指向其后节点的后节点
		 */
		ListNode protect = new ListNode(0,head);
		int len = getLength(head);
		ListNode cur = protect;
		// 遍历到删除节点前
		for (int i = 0; i < len - n; i++) {
			cur = cur.next;
		}
		// 指向后后节点
		cur.next = cur.next.next;
		return protect.next;
	}

	private static int getLength(ListNode head) {
		int length = 0;
		while (head != null) {
			head = head.next;
			length++;
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
		/**
		 * 分治法 2各链表合并
		 */
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
	 * 返回链表到环的节点
	 *
	 * @param head
	 * @return
	 */
	public ListNode detectCycle(ListNode head) {
		/**
		 * 1.hash存节点 遍历重复点为环节点
		 */
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
		/**
		 * 思路：
		 * 1.添加保护头节点
		 * 2.添加有序节点链表，遍历待插节点链表，
		 * 3.待插节点进入有序节点链表，重有序节点的尾节点的后节点重新遍历
		 * 4.遍历结束 返回头节点的后节点
		 *
		 */
		// 保护节点
		ListNode protect = new ListNode(0, head);
		// 有序节点尾巴
		ListNode sorted = head;
		// 待插节点
		ListNode curIn = head.next;
		while (curIn != null) {
			// 待插节点比有序节点大，有序节点增长直到比它小才有插入的位置
			if (sorted.val <= curIn.val) {
				sorted = sorted.next;
			} else {
				ListNode pre = protect;
				// 从头节点开始遍历，直到节点比待插节点大
				while (pre.next.val <= curIn.val) {
					pre = pre.next;
				}
				// 把待插节点放在位置节点的前面，有序节点重新回到节点尾巴
				sorted.next = curIn.next;
				curIn.next = pre.next;
				pre.next = curIn;
			}
			// 待插节点到有序节点的后节点
			curIn = sorted.next;
		}
		return protect.next;
	}


}
