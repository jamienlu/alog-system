import com.asura.structure.find.FindSolution;
import com.asura.structure.tree.TreeNode;
import org.junit.Assert;
import org.junit.Test;

public class Week4Test {
    @Test
    public void testShipWithinDays() {
        FindSolution solution = new FindSolution();
        TreeNode node = new TreeNode(5);
        node.left = new TreeNode(2);
        node.right = new TreeNode(8);
        TreeNode result = solution.convertBST(node);
        Assert.assertTrue(result.val == 13);
    }

    @Test
    public void testSolve() {
        FindSolution solution = new FindSolution();
        solution.solve(null);
    }


}
