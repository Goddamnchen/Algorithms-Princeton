import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestPercolation {
    @Test
    public void test1(){
        double mean = 0.59688;
        double stddev = 0.02214159220253046;
        int T = 20;
        double low = mean - 1.96 * stddev / Math.sqrt(T);
        System.out.println(low);

    }
}
