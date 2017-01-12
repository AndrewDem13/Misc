import org.junit.Test;

import static org.junit.Assert.*;

public class TrappingWaterTest {
    @Test
    public void basic() {
        assertEquals(6, TrappingWater.compute(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        assertEquals(15, TrappingWater.compute(new int[]{3,1,4,0,2,0,3,2,4}));
    }

    @Test
    public void shortArrays() {
        assertEquals(0, TrappingWater.compute(new int[]{}));
        assertEquals(0, TrappingWater.compute(new int[]{0}));
        assertEquals(0, TrappingWater.compute(new int[]{0,1}));
        assertEquals(0, TrappingWater.compute(new int[]{0,1,4}));
        assertEquals(1, TrappingWater.compute(new int[]{2,1,4}));
        assertEquals(59, TrappingWater.compute(new int[]{60,1,89}));
    }

}