import com.asura.structure.linklist.ListNode;
import com.asura.structure.recur.MergeKListNode;
import com.asura.structure.recur.Rank;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Week3Test {
    @Test
    public void testMergeKlistNode() {
        int[] arr = new int[]{1,3,5,3,9};
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(2);
        listNode1.next = listNode2;
        ListNode listNode3 = new ListNode(8);
        ListNode listNode4 = new ListNode(9);
        listNode3.next = listNode4;
        ListNode target = new MergeKListNode().mergeKLists(new ListNode[]{listNode1,listNode3});
        Assert.assertEquals(target.val, 1);
    }

    @Test
    public void testRank() {
        List<List<Integer>> target = new Rank().permuteUnique(new int[]{1,3,5,7});
        Assert.assertEquals(target.size(), 24);
    }
}
