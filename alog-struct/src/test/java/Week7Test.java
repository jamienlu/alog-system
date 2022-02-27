import com.asura.structure.autoplan.AntoSolution;
import org.junit.Assert;
import org.junit.Test;

public class Week7Test {
    @Test
    public void testNumSquares() {
        AntoSolution solution = new AntoSolution();
        int result = solution.numSquares(12);
        Assert.assertEquals(result,3);
    }

    @Test
    public void testLongestPalindromeSubseq() {
        AntoSolution solution = new AntoSolution();
        int result = solution.longestPalindromeSubseq("bbbab");
        Assert.assertEquals(result,4);
    }
}
