import com.asura.structure.find.FindSolution;
import org.junit.Assert;
import org.junit.Test;

public class Week5Test {
    @Test
    public void testShipWithinDays() {
        FindSolution solution = new FindSolution();
        int result = solution.shipWithinDays(new int[]{1,2,3,4,5,6,7,8,9,10},5);
        Assert.assertEquals(result,15);
    }

    @Test
    public void testMinEatingSpeed() {
        FindSolution solution = new FindSolution();
        int result = solution.minEatingSpeed(new int[]{30,11,23,4,20},5);
        Assert.assertEquals(result,30);
    }
}
