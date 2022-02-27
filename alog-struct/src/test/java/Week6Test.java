import com.asura.structure.autoplan.AntoSolution;
import org.junit.Assert;
import org.junit.Test;

public class Week6Test {
    @Test
    public void testClimbStairs() {
        AntoSolution solution = new AntoSolution();
        int result = solution.climbStairs(2);
        Assert.assertEquals(result,2);
    }

    @Test
    public void testFindNumberOfLIS() {
        AntoSolution solution = new AntoSolution();
        int result = solution.findNumberOfLIS(new int[]{1,3,5,4,7});
        Assert.assertEquals(result,2);
    }
}
