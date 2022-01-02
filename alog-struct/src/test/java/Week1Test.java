import com.asura.structure.linklist.ListNode;
import com.asura.structure.linklist.MergeSoredList;
import com.asura.structure.stack.PlusOne;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Week1Test {
    @Test
    public void testPlusOne() {
        int[] arr = new int[]{1,3,5,8,9};
        Assert.assertEquals(Arrays.toString(new PlusOne().plusOne(arr)), Arrays.toString(new int[]{1,3,5,9,0}));
    }

    @Test
    public void mergeTwoLists() {
        int[] arr = new int[]{1,3,5,8,9};
        ListNode listNode1 = new ListNode(0);
        ListNode listNode2 = new ListNode(1);
        ListNode node = new MergeSoredList().mergeTwoLists(listNode1, listNode2);
        Assert.assertEquals(node.val,0);
        Assert.assertEquals(node.next.val,1);
    }
}
