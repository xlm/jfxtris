package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import data_types.RotateDirection;
import data_types.RotateState;

/**
 * TestRotateState
 * @author Josh Chia
 *
 * Unit test for RotateState class
 * 
 * Ensures that the left and right class methods used to update 
 * the enumeration behavior correctly.
 * 
 * Tests:
 *  90 degrees right
 *  90 degrees left
 *  180 degrees right
 *  180 degrees left
 *  360 degrees right
 *  360 degrees left
 *  90 degrees right and left (original position)
 *  90 degrees left and right (original position)
 */
public class TestRotateState {
	RotateState rs_zero, rs_right, rs_two, rs_left;
	
	@Before
	public void setUp() {
		rs_zero = RotateState.ZERO;
		rs_right = RotateState.RIGHT;
		rs_two = RotateState.TWO;
		rs_left = RotateState.LEFT;
	}
	
	/* Test 90 degree single rotation right for all states */
	@Test
	public void testRightOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.RIGHT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testRightOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.RIGHT) == RotateState.TWO); 
	}
	
	@Test
	public void testRightOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.RIGHT) == RotateState.ZERO); 
	}
	
	@Test
	public void testRightOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.RIGHT) == RotateState.LEFT);
	}
	
	/* Test 90 degree single rotation left for all states */
	@Test
	public void testLeftOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.LEFT) == RotateState.LEFT); 
	}
	
	@Test
	public void testLeftOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.LEFT) == RotateState.ZERO); 
	}
	
	@Test
	public void testLeftOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.LEFT) == RotateState.TWO); 
	}
	
	@Test
	public void testLeftOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.LEFT) == RotateState.RIGHT);
	}
	
	/* Test 180 degree (double) rotation right for all states */
	@Test
	public void testDoubleRightOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.TWO); 
	}
	
	@Test
	public void testDoubleRightOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.LEFT); 
	}
	
	@Test
	public void testDoubleRightOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testDoubleRightOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.ZERO);
	}
	
	/* Test 180 degree (double) rotation left for all states */
	@Test
	public void testDoubleLeftOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.TWO); 
	}
	
	@Test
	public void testDoubleLeftOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.LEFT); 
	}
	
	@Test
	public void testDoubleLeftOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testDoubleLeftOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.ZERO);
	}
	
	// Test 360 degree revolution right for all states
	@Test
	public void testRevolutionRightOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.ZERO); 
	}
	
	@Test
	public void testRevolutionRightOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testRevolutionRightOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.LEFT); 
	}
	
	@Test
	public void testRevolutionRightOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT).rotate(RotateDirection.RIGHT) == RotateState.TWO);
	}
	
	/* Test 360 degree revolution left for all states */
	@Test
	public void testRevolutionLeftOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.ZERO); 
	}
	
	@Test
	public void testRevolutionLeftOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testRevolutionLeftOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.LEFT); 
	}
	
	@Test
	public void testRevolutionLeftOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT).rotate(RotateDirection.LEFT) == RotateState.TWO);
	}
	
	/* Test 90 degree right then left for all states 
	 * Should be in original position 
	 */
	@Test
	public void testRightLeftOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.RIGHT).rotate(RotateDirection.LEFT) == RotateState.ZERO); 
	}
	
	@Test
	public void testRightLeftOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.RIGHT).rotate(RotateDirection.LEFT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testRightLeftOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.RIGHT).rotate(RotateDirection.LEFT) == RotateState.LEFT); 
	}
	
	@Test
	public void testRightLeftOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.RIGHT).rotate(RotateDirection.LEFT) == RotateState.TWO);
	}
	
	/* Test 90 degree left then right for all states 
	 * Should be in original position 
	 */
	@Test
	public void testLeftRightOfZero() {
		assertTrue(rs_zero.rotate(RotateDirection.LEFT).rotate(RotateDirection.RIGHT) == RotateState.ZERO); 
	}
	
	@Test
	public void testLeftRightOfRight() {
		assertTrue(rs_right.rotate(RotateDirection.LEFT).rotate(RotateDirection.RIGHT) == RotateState.RIGHT); 
	}
	
	@Test
	public void testLeftRightOfLeft() {
		assertTrue(rs_left.rotate(RotateDirection.LEFT).rotate(RotateDirection.RIGHT) == RotateState.LEFT); 
	}
	
	@Test
	public void testLeftRightOfTwo() {
		assertTrue(rs_two.rotate(RotateDirection.LEFT).rotate(RotateDirection.RIGHT) == RotateState.TWO);
	}
}