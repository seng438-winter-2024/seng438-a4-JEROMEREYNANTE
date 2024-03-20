package org.jfree.data;

import static org.junit.Assert.*; 
import org.jfree.data.Range; 
import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    private Range anotherExampleRange;
    private Range nullRange;
    
    /*------------------------- Before stuff ------------------------*/
    
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }
   
    @Before
    public void setUp() throws Exception { 
    	exampleRange = new Range(-3, 5);  	
    	anotherExampleRange = new Range(-14, 11);
    	nullRange = new Range(Double.NaN, Double.NaN);
    }
    
    /*------------------------- After stuff -------------------------*/
    
    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    /*--------------------------- Tests -----------------------------*/
    
    /*------------------------- getUpperBound -------------------------*/

    /**
     * check if the upper bound is returned correctly when it is equal to the lower bound
     */
    @Test
    public void testUpperBound_ReturnsWhenEqualLowerBound() {
    	exampleRange = new Range(1.0, 1.0);
    	assertEquals(1.0, exampleRange.getUpperBound(), .000000001d);
    }
    
    /**
     * check if the upper bound is returned correctly when it is greater than the lower bound
     */
    @Test
    public void testUpperBound_ReturnsWhenGreaterThanLowerBound() {
    	exampleRange = new Range(1.0, 5.0);
    	assertEquals(5.0, exampleRange.getUpperBound(), .000000001d);
    }
    
    /**
     * check if an exception is thrown when the upper bound is less than the lower bound
     * @throws IllegalArgumentException
     */
    @Test
    public void testUpperBound_FailsWhenLessThanLowerBound() {
    	try {
    		exampleRange = new Range(5.0, 1.0);
    		fail("Range object should not have been created");
    	} catch (Exception e) {
    		assertTrue("Exception should be IllegalArgumentException", e instanceof IllegalArgumentException);
    	}
    }
    
    /**
     * check if the upper bound is returned correctly when it is very close to the lower bound using decimal places
     */
    @Test
    public void testUpperBound_ReturnsWhenGreaterThanLowerBound_BVT_ALB() {
    	exampleRange = new Range(1.0, 1.0001);
    	assertEquals(1.0001, exampleRange.getUpperBound(), .000000001d);
    }
    
    /**
     * check if an exception is thrown when the upper bound is less than the lower bound using decimal places
     * @throws IllegalArgumentException
     */
    @Test
    public void testUpperBound_FailsWhenLessThanLowerBound_BVT_BLB() {
    	try {
    		exampleRange = new Range(5.0, 4.9999);
    		fail("Range object should not have been created");
    	} catch (Exception e) {
    		assertTrue("Exception should be IllegalArgumentException", e instanceof IllegalArgumentException);
    	}
    }
    
    /**
     * check if the upper bound is returned correctly when it is negative
     */
    @Test
    public void testUpperBound_ReturnsEvenWhenNegative() {
    	exampleRange = new Range(-10, -9);
    	assertEquals(-9, exampleRange.getUpperBound(), .000000001d);
    }
    
    /**
     * check if the upper bound is returned correctly using more extreme decimal places
     */
    @Test
    public void testUpperBound_ExtremeDecimalPlaces() {
    	exampleRange = new Range(1.0, 1.000000001);
    	assertEquals(1.000000001, exampleRange.getUpperBound(), .000000001d);
    }

    /*------------------------- getCentralValue -------------------------*/
    
    // Test case 1: Central value of a range from -1 to 1 should be 0
    @Test
    public void centralValueShouldBeZero() {
        exampleRange = new Range(-1, 1);
        assertEquals("The central value of -1 and 1 should be 0",
                0, exampleRange.getCentralValue(), .000000001d);
    }

    // Test case 2: Central value of a range from 0 to 1 should be 0.5
    @Test
    public void centralValueShouldBeZeroPointFive() {
        exampleRange = new Range(0, 1);
        assertEquals("The central value of 0 and 1 should be 0.5",
                0.5, exampleRange.getCentralValue(), .000000001d);
    }

    // Test case 3: Central value of a range from -2 to 2 should be 0
    @Test
    public void centralValueShouldBeZeroForWideRange() {
        exampleRange = new Range(-2, 2);
        assertEquals("The central value of -2 and 2 should be 0",
                0, exampleRange.getCentralValue(), .000000001d);
    }

    // Test case 4: Central value of a range from -55 to 100 should be 22.5
    @Test
    public void centralValueShouldBeTwentyTwoPointFive() {
        exampleRange = new Range(-55, 100);
        assertEquals("The central value of -55 and 100 should be 22.5",
                22.5, exampleRange.getCentralValue(), .000000001d);
    }

    // Test case 5: Test for range with negative upper and lower bounds
    @Test
    public void negativeBounds() {
        exampleRange = new Range(-10, -5);
        assertEquals("The central value of -55 and 100 should be -7.5",
                -7.5, exampleRange.getCentralValue(), .000000001d);
    }

    // Test case 6: Test for range with zero bounds
    @Test
    public void zeroBounds() {
        exampleRange = new Range(0, 0);
        assertEquals("The central value of 0 and 0 should be 0",
                0, exampleRange.getCentralValue(), .000000001d);
    }
    
    /*------------------------- getLowerBound -------------------------*/
    @Test
    public void testPositiveLowerBound() {
    	Range positiveLowerBound = new Range(5, 10);
    	Range barelyPositiveLowerBound = new Range(0.1, 3);
    	
    	assertEquals("The lower bound value should be 5", 
    			5, positiveLowerBound.getLowerBound(), .000000001d);
    	
    	assertEquals("The lower bound value should be 0.1", 
    			0.1, barelyPositiveLowerBound.getLowerBound(), .000000001d);
    }
    
    @Test
    public void testNegativeLowerBound() {
    	Range negativeLowerBound = new Range(-3, 6);
    	Range barelyNegativeLowerBound = new Range(-0.1, 2);
    	
    	assertEquals("The lower bound value should be -3", 
    			-3, negativeLowerBound.getLowerBound(), .000000001d);
    	
    	assertEquals("The lower bound value should be -0.1", 
    			-0.1, barelyNegativeLowerBound.getLowerBound(), .000000001d);
    }
    
    @Test
    public void testZeroLowerBound() {
    	Range zeroLowerBound = new Range(0, 10);
    	Range barelyZeroLowerBoundPostive = new Range(0.0000001, 5);
    	Range barelyZeroLowerBoundNegative = new Range(-0.0000001, 3);
    	
    	assertEquals("The lower bound value should be 0", 
    			0, zeroLowerBound.getLowerBound(), .000000001d);
    	
    	assertEquals("The lower bound value should be 0.0000001", 
    			0.0000001, barelyZeroLowerBoundPostive.getLowerBound(), .000000001d);
    	
    	assertEquals("The lower bound value should be -0.0000001", 
    			-0.0000001, barelyZeroLowerBoundNegative.getLowerBound(), .000000001d);
    }
    
    @Test
    public void testNullLowerBound() {
    	assertTrue("The lower bound should be null", Double.isNaN(nullRange.getLowerBound()));
    }
    

    /*------------------------- contains -------------------------*/
    
    @Test
    public void testContains_ValueInRange() {
    	assertTrue("Range should contain 2", exampleRange.contains(2));
    	assertTrue("Range should contain -1", exampleRange.contains(-1));
    	assertTrue("Range should contain 2", exampleRange.contains(0));
    }
    
    @Test
    public void testContains_ValueNotInRange() {
    	assertFalse("Range should not contain -5", exampleRange.contains(-5));
    	assertFalse("Range should not contain 8", exampleRange.contains(8));
    }
    
    @Test
    public void testContains_LowerBound() {
    	assertTrue("Range should contain -3", exampleRange.contains(-3));
    	assertTrue("Range should contain -2.9999", exampleRange.contains(-2.9999));
    	assertFalse("Range should not contain -3.0001", exampleRange.contains(-3.0001));
    	
    	assertTrue("Range should contain -14", anotherExampleRange.contains(-14));
    	assertTrue("Range should contain -13.9999", anotherExampleRange.contains(-13.9999));
    	assertFalse("Range should not contain -14.0001", anotherExampleRange.contains(-14.0001));
    }
    
    @Test
    public void testContains_UpperBound() {
    	assertTrue("Range should contain 5", exampleRange.contains(5));
    	assertTrue("Range should contain 4.9999", exampleRange.contains(4.9999));
    	assertFalse("Range should not contain 5.0001", exampleRange.contains(5.0001));
    	
    	assertTrue("Range should contain 11", anotherExampleRange.contains(11));
    	assertTrue("Range should contain 10.9999", anotherExampleRange.contains(10.9999));
    	assertFalse("Range should not contain 11.0001", anotherExampleRange.contains(11.0001));
    }
    
    @Test
    public void testContains_NullRange() {
    	assertFalse("Range should not contain anything", nullRange.contains(0));
    	assertFalse("Range should not contain anything", nullRange.contains(12));
    	assertFalse("Range should not contain anything", nullRange.contains(-5));
    }   

    /*------------------------- getLength -------------------------*/
    
	@Test
	public void testGetLength_ZeroLength() {
		Range zeroRange = new Range(1, 1);
		assertEquals("The length of 1 to 1 should be 0", 0, zeroRange.getLength(), .000000001d);
	}

	@Test
	public void testGetLength_PositiveLength() {
		Range fourRange = new Range(1, 5);
		assertEquals("The length of 1 to 5 should be 4", 4, fourRange.getLength(), .000000001d);
	}
	
	@Test
	public void testGetLength_InfiniteLength() {
		Range infRange = new Range(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		assertEquals("The length of -inf to +inf should be +inf", Double.POSITIVE_INFINITY, infRange.getLength(), .000000001d);
	}
	
	@Test
	public void testGetLength_FractionLength() {
		Range fracRange = new Range(0.99, 2);
		assertEquals("The length of 0.99 to 2 should be 1.01", 1.01, fracRange.getLength(), .000000001d);
	}
    
}
