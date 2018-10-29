import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by pandechuan on 2018/10/15.
 */
public class BloomFilterTest {

    @Test
    public void test1() {
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                500,
                0.01);

        filter.put(1);
        filter.put(2);
        filter.put(3);

        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(103));
        Assert.assertTrue(filter.mightContain(103));

    }


    @Test
    public void test2() {

        /**
         * Because the expected number of elements is so small, the filter will occupy very little memory.

         However, as we add more items than expected, the filter becomes over-saturated and has
         a much higher probability of returning false positive results than the desired one percent:
         */
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(),
                5,
                0.01);

        IntStream.range(0, 100_00).forEach(filter::put);
        Assert.assertTrue(filter.mightContain(1));
        Assert.assertTrue(filter.mightContain(2));
        Assert.assertTrue(filter.mightContain(3));
        Assert.assertFalse(filter.mightContain(103));
        Assert.assertTrue(filter.mightContain(103));
        System.out.println();

    }
}
