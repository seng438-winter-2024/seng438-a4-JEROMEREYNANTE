package org.jfree.data;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;

import org.jmock.*;

import org.jfree.data.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataUtilitiesTest extends DataUtilities {
	private Mockery context;
	private Values2D values2DMock;
	private KeyedValues keyedValuesMock;

	/*------------------------- Before stuff -------------------------*/
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Before
	/**
	 * Sets up the test cases by creating a new mockery and creating mocks for Values2D and KeyedValues
	 * @throws Exception
	 */
	public void setUp() throws Exception {
        // Create a new mockery
        context = new Mockery();

        // Create a mock for Values2D
        values2DMock = context.mock(Values2D.class);
        
        // Create a mock for Values2D
        keyedValuesMock = context.mock(KeyedValues.class);
	}
	
	/*------------------------- After stuff -------------------------*/

	@After
	/**
	 * Tears down the test cases by shutting down the mockery
	 * @throws Exception
	 */
	public void tearDown() throws Exception {
        // Shutdown the mockery
        context.assertIsSatisfied();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	/*------------------------- Tests -------------------------*/
	
	/*------------------------- calculateColumnTotal -------------------------*/

//	/**
//	 * Test calculateColumnTotal with null as the data argument
//	 * Should throw an InvalidParameterException since the data argument is invalid
//	 * @throws InvalidParameterException
//	 */
//	@Test(expected=NullPointerException.class)
//	public void testCalculateColumnTotal_UsingNullAsDataArgument() throws NullPointerException {
//		double result = DataUtilities.calculateColumnTotal(null, 0);
//		assertNull("Expected to be null", result);
//	}
	
	/**
	 * Check that the method returns 0 when the data argument has no rows
	 */
	@Test
	public void testCalculateColumnTotal_ZeroRows() {

        // Define expectations for the mock
        context.checking(new Expectations() {{
            oneOf(values2DMock).getRowCount();
            will(returnValue(0));
        }});

        double result = DataUtilities.calculateColumnTotal(values2DMock, 1);

        // Verify the result
        assertEquals(0, result, 0.0001);
    }
	
	/**
	 * Check that the method returns 0 when the data argument has no columns
	 */
	@Test
	public void testCalculateColumnTotal_ZeroColumns() {

		 context.checking(new Expectations() {{
	            allowing(values2DMock).getColumnCount();
	            will(returnValue(0)); 

	            allowing(values2DMock).getRowCount();
	            will(returnValue(0)); 
	        }});
		
        double result = DataUtilities.calculateColumnTotal(values2DMock, 0);

        // Verify the result
        assertEquals(0, result, 0.0001); // With invalid input, a total of zero will be returned.
    }
	
	/**
	 * Check that the method throws exception when the column argument is negative
	 */
	@Test
	public void testCalculateColumnTotal_NegativeColumnArg_BLB() {
		Values2D data = new DefaultKeyedValues2D();
        ((DefaultKeyedValues2D)data).setValue(1.0, "Row1", "Column1");
        ((DefaultKeyedValues2D)data).setValue(2.0, "Row2", "Column1");
        ((DefaultKeyedValues2D)data).setValue(3.0, "Row3", "Column1");

        try {
            double result = DataUtilities.calculateColumnTotal(data, -1);
            fail("Expected to throw Illegal argument exception");
        } catch (Exception e) {
            assertTrue("Unexpected exception message", e instanceof IndexOutOfBoundsException);
        }
    }
	
	/**
	 * Check if the method returns the correct total when the column argument is 0 (LB)
	 */
	@Test
	public void testCalculateColumnTotal_ColumnEqualsZero_LB() {
        // Define expectations for the mock
		context.checking(new Expectations() {{
			allowing(values2DMock).getColumnCount();
            will(returnValue(3)); 

            allowing(values2DMock).getRowCount();
            will(returnValue(3)); 

            // Set up expectations for the getValue() method to return values for the given column
            oneOf(values2DMock).getValue(0, 0); will(returnValue(1.0)); // Assuming the value at (0, 0) is 1.0
            oneOf(values2DMock).getValue(1, 0); will(returnValue(2.0)); // Assuming the value at (1, 0) is 2.0
            oneOf(values2DMock).getValue(2, 0); will(returnValue(3.0)); // Assuming the value at (2, 0) is 3.0
        }});

        // Calculate the column total with column index 0
        double columnTotal = DataUtilities.calculateColumnTotal(values2DMock, 0);

        // Assert that the result is the sum of values in column 0
        assertEquals(6.0, columnTotal, 0.00001);
    }
	
	/**
	 * Check if the method returns the correct total when the column argument is a nominal value
	 */
	@Test
	public void testCalculateColumnTotal_ColumnIsNominal() {
        // Define expectations for the mock
		context.checking(new Expectations() {{
            // Define expectations for the getValue() method based on your data
            allowing(values2DMock).getRowCount();
            will(returnValue(3)); // Example row count
            allowing(values2DMock).getColumnCount();
            will(returnValue(2)); // Example column count
            allowing(values2DMock).getValue(0, 0);
            will(returnValue(5.0)); // Example value at row 0, column 0
            allowing(values2DMock).getValue(1, 0);
            will(returnValue(3.0)); // Example value at row 1, column 0
            allowing(values2DMock).getValue(2, 0);
            will(returnValue(7.0)); // Example value at row 2, column 0
        }});

        // Calculate the column total
        double columnTotal = DataUtilities.calculateColumnTotal(values2DMock, 0);

        // Verify that the result matches the expected value
        assertEquals(15, columnTotal, 0.00001); // Adjust the delta as needed
    }
	
	/**
	 * Check if the method returns 0 when the column argument is out of bounds at the upper bound
	 */
	@Test
	public void testCalculateColumnTotal_ColumnIsAtUB() {
		// Set up expectations for the getRowCount(), getColumnCount(), and getValue() methods
        context.checking(new Expectations() {{
            allowing(values2DMock).getRowCount();
            will(returnValue(3)); // Assuming 3 rows for the example
            
            allowing(values2DMock).getColumnCount();
            will(returnValue(3)); // Assuming 3 columns for the example
            
            allowing(values2DMock).getValue(with(any(int.class)), with(any(int.class)));
            will(returnValue(0)); // Assuming the value returned doesn't matter for this test case
        }});

        // Calculate the column total with an out-of-bounds column index
        double columnTotal = DataUtilities.calculateColumnTotal(values2DMock, 3);

        // Assert that the result is 0
        assertEquals(0.0, columnTotal, 0.001); // Assuming a delta of 0.001 for comparison
    }
	
	/**
	 * Check if the method returns 0 when the column argument is out of bounds above the upper bound
	 */
	@Test
	public void testCalculateColumnTotal_ColumnIsAUB() {

		context.checking(new Expectations() {{
            allowing(values2DMock).getRowCount();
            will(returnValue(3)); // Assuming 3 rows for the example
            
            allowing(values2DMock).getColumnCount();
            will(returnValue(3)); // Assuming 3 columns for the example
            
            allowing(values2DMock).getValue(with(any(int.class)), with(any(int.class)));
            will(returnValue(0)); // Assuming the value returned doesn't matter for this test case
        }});

        // Calculate the column total with an out-of-bounds column index
        double columnTotal = DataUtilities.calculateColumnTotal(values2DMock, 6);

        // Verify the result
        assertEquals(0, columnTotal, 0.0001); // With invalid input, a total of zero will be returned.


    }	

	/**
	 * Method meant to help achieve 100% branch coverage. Passes a Values2D object with its last element as null
	 */
	@Test
	public void testCalculateColumnTotal_NullLastElement() {

		 context.checking(new Expectations() {{
			// Define expectations for the getValue() method based on your data
	            allowing(values2DMock).getRowCount();
	            will(returnValue(3)); // Example row count
	            allowing(values2DMock).getColumnCount();
	            will(returnValue(2)); // Example column count
	            allowing(values2DMock).getValue(0, 0);
	            will(returnValue(5.0)); // Example value at row 0, column 0
	            allowing(values2DMock).getValue(1, 0);
	            will(returnValue(3.0)); // Example value at row 1, column 0
	            allowing(values2DMock).getValue(2, 0);
	            will(returnValue(null)); // Example value at row 2, column 0
	        }});
		
        double result = DataUtilities.calculateColumnTotal(values2DMock, 0);

        // Verify the result
        assertEquals(8.0, result, 0.0001); // With invalid input, a total of zero will be returned.
    }
	
	/*------------------------- getCumulativePercentages -------------------------*/

	/**
	 * Check that the result object is empty when the data argument is empty
	 */
	@Test
	public void testGetCumulativePercentages_EmptyDataArg() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(0));
		}});
		
		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(0, result.getItemCount()); // check that its empty too
	}
	
	/**
	 * Check that the method throws an InvalidParameterException when the data argument is null
	 * @throws InvalidParameterException
	 */
	@Test
	public void testGetCumulativePercentages_NullDataArg() {
		try {
			KeyedValues result = DataUtilities.getCumulativePercentages(null);
			fail("Expected illegal argument exception");
		} catch (Exception e) {
			assertTrue("Exception should be IllegalArgumentException", e instanceof IllegalArgumentException);
		}
	}
	
	/**
	 * Check that the method returns the correct cumulative percentages for a simple example
	 */
	@Test
	public void testGetCumulativePercentages_ExampleFromDocs() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(5));
			
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(9));
			
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(2));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		assertEquals(0.3125, result.getValue(0));
		assertEquals(0.875, result.getValue(1));
		assertEquals(1.0, result.getValue(2));
	}
	
	/**
	 * Check that the method returns the correct cumulative percentages for an example with negative numbers
	 */
	@Test
	public void testGetCumulativePercentages_WithNegativeNumbers() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(-5));
			
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(9));
			
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(2));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		assertEquals(1.0, result.getValue(2));
	}
	
	/**
	 * Check that the method returns the correct cumulative percentages for an example with a zero value
	 */
	@Test
	public void testGetCumulativePercentages_WithAZero() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(5));
			
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(0));
			
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(2));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		assertEquals(5.0/7, result.getValue(0));
		assertEquals(5.0/7, result.getValue(1));
		assertEquals(1.0, result.getValue(2));
	}
	
	/**
	 * Check that the method returns NaN as the percentages for an example with all zeros
	 * This is because Java's floating point division has 0.0 / 0.0 = NaN
	 */
	@Test
	public void testGetCumulativePercentages_WithAllZeros() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(0));
			
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(0));
			
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(0));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		// floating point division of 0.0/0.0 = NaN
		assertEquals(0.0/0.0, result.getValue(2));
	}
	
	/**
	 * Check that the method returns a KeyedValues object with nulls for an example with all nulls
	 */
	@Test
	public void testGetCumulativePercentages_WithAllNulls() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(null));
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(null));
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(null));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		// division by 0
		assertTrue("Should be zero", Double.isNaN((result.getValue(0)).doubleValue()));
		assertTrue("Should be zero", Double.isNaN((result.getValue(1)).doubleValue()));
		assertTrue("Should be zero", Double.isNaN((result.getValue(2)).doubleValue()));
	}

	/**
	 * Check that the method returns the correct cumulative percentages for an example with all negative numbers
	 */
	@Test
	public void testGetCumulativePercentages_WithAllNegativeNumbers() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(-5));
			
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(-9));
			
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(-2));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		// double negative should allow for positive decimal values
		assertEquals(0.3125, result.getValue(0));
		assertEquals(0.875, result.getValue(1));
		assertEquals(1.0, result.getValue(2));
	}
	
	/**
	 * Check that the method returns the correct cumulative percentage for an example with a single value in the data argument
	 */
	@Test
	public void testGetCumulativePercentages_WithSingleValue() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(1));
			
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(2));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		
		assertNotNull(result);
		assertEquals(1, result.getItemCount());
		assertEquals(1.0, result.getValue(0));
	}

	/**
	 * Check that the method returns the correct cumulative percentages for an example with very small values
	 */
	@Test
	public void testGetCumulativePercentages_WithVerySmallValues() {
		context.checking(new Expectations() {{
			atLeast(1).of(keyedValuesMock).getItemCount();
			will(returnValue(3));
			oneOf(keyedValuesMock).getKey(0);
			will(returnValue(0));
			oneOf(keyedValuesMock).getKey(1);
			will(returnValue(1));
			oneOf(keyedValuesMock).getKey(2);
			will(returnValue(2));
			atLeast(1).of(keyedValuesMock).getValue(0);
			will(returnValue(0.0000000000005));
			atLeast(1).of(keyedValuesMock).getValue(1);
			will(returnValue(0.0000000000002));
			atLeast(1).of(keyedValuesMock).getValue(2);
			will(returnValue(0.0000000000003));
		}});

		KeyedValues result = DataUtilities.getCumulativePercentages(keyedValuesMock);
		assertNotNull(result);
		assertEquals(3, result.getItemCount());
		assertEquals("Value should be 0.5", 0.5, (result.getValue(0)).doubleValue(), .000000000000001d);
		assertEquals("Value should be 0.7", 0.7, (result.getValue(1)).doubleValue(), .000000000000001d);
		assertEquals("Value should be 1.0", 1.0, (result.getValue(2)).doubleValue(), .000000000000001d);
	}
	
	/*------------------------- createNumberArray -------------------------*/
	
	@Test
	public void testCreateNumberArray_PositiveValues() {
		double[] values = {1.0, 2.0, 3.0, 4.0};
		Number[] expected = {1.0, 2.0, 3.0, 4.0};
		Number[] result = DataUtilities.createNumberArray(values);
		assertArrayEquals(expected, result);
	}

	@Test
	public void testCreateNumberArray_NegativeValues() {
		double[] values = {-1.0, -2.0, -3.0, -4.0};
		Number[] expected = {-1.0, -2.0, -3.0, -4.0};
		Number[] result = DataUtilities.createNumberArray(values);
		assertArrayEquals(expected, result);
	}

	@Test
	public void testCreateNumberArray_ZeroValues() {
		double[] values = {0.0, 0.0, 0.0, 0.0};
		Number[] expected = {0.0, 0.0, 0.0, 0.0};
		Number[] result = DataUtilities.createNumberArray(values);
		assertArrayEquals(expected, result);
	}

	@Test
	public void testCreateNumberArray_EmptyArray() {
		double[] values = {};
		Number[] expected = {};
		Number[] result = DataUtilities.createNumberArray(values);
		assertArrayEquals(expected, result);
	}
	
	/*------------------------- calculateRowTotal -------------------------*/
	
    @Test
    public void calculateRowTotal_CorrectTotal() {
    	
    	context.checking(new Expectations() {{
    		allowing(values2DMock).getColumnCount();
    		will(returnValue(3));
    		allowing(values2DMock).getRowCount();
    		will(returnValue(1));
    		oneOf(values2DMock).getValue(0, 0);
    		will(returnValue(1.0));
    		oneOf(values2DMock).getValue(0, 1);
    		will(returnValue(2.0));
    		oneOf(values2DMock).getValue(0, 2);
    		will(returnValue(3.0));
    	}});
    
        double expectedTotal = 6.0; // Expected row total
        assertEquals("The calculated row total should be correct",
                expectedTotal, DataUtilities.calculateRowTotal(values2DMock, 0), .000000001d);
    }
    
    @Test
    public void calculateRowTotalWithNegativeValues_ReturnCorrectTotal() {
    	
    	context.checking(new Expectations() {{
    		allowing(values2DMock).getColumnCount();
    		will(returnValue(3));
    		allowing(values2DMock).getRowCount();
    		will(returnValue(1));
    		oneOf(values2DMock).getValue(0, 0);
    		will(returnValue(-1.0));
    		oneOf(values2DMock).getValue(0, 1);
    		will(returnValue(-2.0));
    		oneOf(values2DMock).getValue(0, 2);
    		will(returnValue(-3.0));
    	}});
    	
        double expectedTotal = -6.0; // Expected row total
        double actualTotal = DataUtilities.calculateRowTotal(values2DMock, 0);
        assertEquals("The calculated row total should be correct", expectedTotal, actualTotal, .000000001d);
    }

    @Test
    public void calculateRowTotalWithZeroValues_ReturnCorrectTotal() {

    	context.checking(new Expectations() {{
    		allowing(values2DMock).getColumnCount();
    		will(returnValue(3));
    		allowing(values2DMock).getRowCount();
    		will(returnValue(1));
    		oneOf(values2DMock).getValue(0, 0);
    		will(returnValue(0));
    		oneOf(values2DMock).getValue(0, 1);
    		will(returnValue(0));
    		oneOf(values2DMock).getValue(0, 2);
    		will(returnValue(0));
    	}});

        double expectedTotal = 0.0; // Expected row total
        double calculatedTotal = DataUtilities.calculateRowTotal(values2DMock, 0);
        assertEquals("The calculated row total should be correct", expectedTotal, calculatedTotal, .000000001d);
        
    }

    @Test
    public void calculateRowTotalWithEmptyDataSet_ReturnZeroTotal() {
    	
    	context.checking(new Expectations() {{
    		oneOf(values2DMock).getColumnCount();
    		will(returnValue(0));
    	}});
    	
        // Values2D values = new CreateEmptyValues2D(); //calls a constructor for creating a sample values2D without dataset for testing purposes
        int row = 0; // assign row to 0

        double expectedTotal = 0.0; // Expected row total

        assertEquals("The calculated row total should be zero for an empty dataset",
                expectedTotal, DataUtilities.calculateRowTotal(values2DMock, row), .000000001d);
    }

	/**
	 * Method meant to help achieve 100% branch coverage. Passes a Values2D object with its last element as null
	 */
	@Test
	public void testCalculateRowTotal_NullLastElement() {


		 context.checking(new Expectations() {{
				// Define expectations for the getValue() method based on your data
	            allowing(values2DMock).getRowCount();
	            will(returnValue(3)); // Example row count
	            allowing(values2DMock).getColumnCount();
	            will(returnValue(2)); // Example column count
	            allowing(values2DMock).getValue(0, 0);
	            will(returnValue(5.0)); // Example value at row 0, column 0
	            allowing(values2DMock).getValue(1, 0);
	            will(returnValue(3.0)); // Example value at row 1, column 0
	            allowing(values2DMock).getValue(2, 0);
	            will(returnValue(null)); // Example value at row 2, column 0
	        }});

		
        double result = DataUtilities.calculateColumnTotal(values2DMock, 0);

        // Verify the result
        assertEquals(8.0, result, 0.0001); // With invalid input, a total of zero will be returned.
        
    }

	@Test
	 public void testCreateNumberArray2D_ValidArray() {
		 double[][] exampleArray1 = {{0, 1, 5}, {-2, 3, -9}};
		 Number[][] newNumberArray1 = DataUtilities.createNumberArray2D(exampleArray1);
		 
		 assertNotNull("newNumberArray1 should not be null", newNumberArray1);
		 
		 for( int i = 0; i <newNumberArray1.length; i++ ) {
			 for( int j = 0;  j < newNumberArray1[i].length; j++ ) {
				 assertTrue("Row: " + i + " Column: " + j + " should be a Number object", newNumberArray1[i][j] instanceof Number);
				 assertEquals("Row: " + i + " Column: " + j + " should be: " + exampleArray1[i][j] + " not: " +  newNumberArray1[i][j], exampleArray1[i][j], newNumberArray1[i][j]);
			 }
		 }
	 }
	
	 @Test
	 public void testCreateNumberArray2D_EmptyArray() {
		 double[][] emptyArray = {};
		 Number[][] newNumberArray = DataUtilities.createNumberArray2D(emptyArray);
		 
		 assertEquals("Length should be zero", 0, newNumberArray.length);
	 }
	 
	 @Test
	 public void testCreateNumberArray2D_NullArray() {
		 double[][] nullArray = null;
		 try {
		 	Number[][] newNumberArray = DataUtilities.createNumberArray2D(nullArray);
		 	fail("Should not have created an array will null input, length of new array: " + newNumberArray.length);
		 } catch (Exception e) {
			 assertTrue("Null data argument", e instanceof IllegalArgumentException);
		 }
	 }
}
