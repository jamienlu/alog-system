import com.asura.structure.hash.CalcWebCount;
import com.asura.structure.hash.DegreeArray;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class Week2Test {
    @Test
    public void testArrayDegree() {
        int[] arr = new int[]{1,3,5,3,9};
        Assert.assertEquals(new DegreeArray().findShortestSubArray(arr), 3);
    }

    @Test
    public void testWebCount() {

        String[] arr = new String[]{"9001 discuss.leetcode.com"};
        List<String> result = new CalcWebCount().subdomainVisits(arr);
        Assert.assertTrue(result.contains("9001 discuss.leetcode.com"));
        Assert.assertTrue(result.contains("9001 leetcode.com"));
        Assert.assertTrue(result.contains("9001 com"));
    }
}
