package test;

import static org.junit.Assert.assertTrue;
import model.Piece;
import model.Playfield;
import model.Tetromino;

import org.junit.Before;
import org.junit.Test;

import data_types.RotateDirection;
import data_types.Vector2D;

/**
 * TestRotateState
 * @author Josh Chia
 *
 * Tests the rotation functionlaity of the Tetromino class, 
 * 
 * Ensures that rotate methods correctly update the relative
 * position of the Minos and updates the rotation state  
 * 
 * Tests:
 *  0 degrees (original position)
 *  90 degrees right
 *  90 degrees left
 *  180 degrees right
 *  180 degrees left
 *  270 degrees right
 *  270 degrees left
 *  360 degrees right
 *  360 degrees left
 *  90 degrees right and left (original position)
 *  90 degrees left and right (original position)
 */
public class TestRotation {
	Tetromino t, i, o, s, z, j, l;
	
	Playfield playfield = new Playfield(new Vector2D(0, 0), 1024, 600, 10, 22, 2);
	
	Tetromino T_ZERO, T_RIGHT, T_TWO, T_LEFT;
	Tetromino I_ZERO, I_RIGHT, I_TWO, I_LEFT;
	Tetromino O_ZERO, O_RIGHT, O_TWO, O_LEFT;
	Tetromino S_ZERO, S_RIGHT, S_TWO, S_LEFT;
	Tetromino Z_ZERO, Z_RIGHT, Z_TWO, Z_LEFT;
	Tetromino J_ZERO, J_RIGHT, J_TWO, J_LEFT;
	Tetromino L_ZERO, L_RIGHT, L_TWO, L_LEFT;
	
	@Before
	public void setUp() {
		t = Piece.T.create(playfield);
		i = Piece.I.create(playfield);
		o = Piece.O.create(playfield);
		s = Piece.S.create(playfield);
		z = Piece.Z.create(playfield);
		j = Piece.J.create(playfield);
		l = Piece.L.create(playfield);
		
		T_ZERO = Piece.T.create(playfield);
		T_RIGHT = Piece.T.create(playfield);
		T_RIGHT.rotate(RotateDirection.RIGHT);
		T_TWO = Piece.T.create(playfield);
		T_TWO.rotate(RotateDirection.RIGHT);
		T_TWO.rotate(RotateDirection.RIGHT);
		T_LEFT = Piece.T.create(playfield);
		T_LEFT.rotate(RotateDirection.LEFT);
		
		I_ZERO = Piece.I.create(playfield);
		I_RIGHT = Piece.I.create(playfield);
		I_RIGHT.rotate(RotateDirection.RIGHT);
		I_TWO = Piece.I.create(playfield);
		I_TWO.rotate(RotateDirection.RIGHT);
		I_TWO.rotate(RotateDirection.RIGHT);
		I_LEFT = Piece.I.create(playfield);
		I_LEFT.rotate(RotateDirection.LEFT);
		
		O_ZERO = Piece.O.create(playfield);
		O_RIGHT = Piece.O.create(playfield);
		O_RIGHT.rotate(RotateDirection.RIGHT);
		O_TWO = Piece.O.create(playfield);
		O_TWO.rotate(RotateDirection.RIGHT);
		O_TWO.rotate(RotateDirection.RIGHT);
		O_LEFT = Piece.O.create(playfield);
		O_LEFT.rotate(RotateDirection.LEFT);
		
		S_ZERO = Piece.S.create(playfield);
		S_RIGHT = Piece.S.create(playfield);
		S_RIGHT.rotate(RotateDirection.RIGHT);
		S_TWO = Piece.S.create(playfield);
		S_TWO.rotate(RotateDirection.RIGHT);
		S_TWO.rotate(RotateDirection.RIGHT);
		S_LEFT = Piece.S.create(playfield);
		S_LEFT.rotate(RotateDirection.LEFT);
		
		Z_ZERO = Piece.Z.create(playfield);
		Z_RIGHT = Piece.Z.create(playfield);
		Z_RIGHT.rotate(RotateDirection.RIGHT);
		Z_TWO = Piece.Z.create(playfield);
		Z_TWO.rotate(RotateDirection.RIGHT);
		Z_TWO.rotate(RotateDirection.RIGHT);
		Z_LEFT = Piece.Z.create(playfield);
		Z_LEFT.rotate(RotateDirection.LEFT);
		
		J_ZERO = Piece.J.create(playfield);
		J_RIGHT = Piece.J.create(playfield);
		J_RIGHT.rotate(RotateDirection.RIGHT);
		J_TWO = Piece.J.create(playfield);
		J_TWO.rotate(RotateDirection.RIGHT);
		J_TWO.rotate(RotateDirection.RIGHT);
		J_LEFT = Piece.J.create(playfield);
		J_LEFT.rotate(RotateDirection.LEFT);
		
		L_ZERO = Piece.L.create(playfield);
		L_RIGHT = Piece.L.create(playfield);
		L_RIGHT.rotate(RotateDirection.RIGHT);
		L_TWO = Piece.L.create(playfield);
		L_TWO.rotate(RotateDirection.RIGHT);
		L_TWO.rotate(RotateDirection.RIGHT);
		L_LEFT = Piece.L.create(playfield);
		L_LEFT.rotate(RotateDirection.LEFT);
	}
	
	/* Test starting orientation for all states */
	@Test
	public void testPieceT() {
		assertTrue(t.equals(T_ZERO));
	}
	
	@Test
	public void testPieceI() {
		assertTrue(i.equals(I_ZERO));
	}
	
	@Test
	public void testPieceO() {
		assertTrue(o.equals(O_ZERO));
	}
	
	@Test
	public void testPieceS() {
		assertTrue(s.equals(S_ZERO));
	}
	
	@Test
	public void testPieceZ() {
		assertTrue(z.equals(Z_ZERO));
	}
	
	@Test
	public void testPieceJ() {
		assertTrue(j.equals(J_ZERO));
	}
	
	@Test
	public void testPieceL() {
		assertTrue(l.equals(L_ZERO));
	}
	
	/* Test 90 degree single rotation right for all states */
	@Test
	public void testRightPieceT() {
		t.rotate(RotateDirection.RIGHT);
		assertTrue(t.equals(T_RIGHT));
	}
	
	@Test
	public void testRightPieceI() {
		i.rotate(RotateDirection.RIGHT);
		assertTrue(i.equals(I_RIGHT));
	}
	
	@Test
	public void testRightPieceO() {
		o.rotate(RotateDirection.RIGHT);
		assertTrue(o.equals(O_RIGHT));
	}
	
	@Test
	public void testRightPieceS() {
		s.rotate(RotateDirection.RIGHT);
		assertTrue(s.equals(S_RIGHT));
	}
	
	@Test
	public void testRightPieceZ() {
		z.rotate(RotateDirection.RIGHT);
		assertTrue(z.equals(Z_RIGHT));
	}
	
	@Test
	public void testRightPieceJ() {
		j.rotate(RotateDirection.RIGHT);
		assertTrue(j.equals(J_RIGHT));
	}
	
	@Test
	public void testRightPieceL() {
		l.rotate(RotateDirection.RIGHT);
		assertTrue(l.equals(L_RIGHT));
	}
	
	/* Test 90 degree single rotation left for all states */
	@Test
	public void testLeftPieceT() {
		t.rotate(RotateDirection.LEFT);
		assertTrue(t.equals(T_LEFT));
	}
	
	@Test
	public void testLeftPieceI() {
		i.rotate(RotateDirection.LEFT);
		assertTrue(i.equals(I_LEFT));
	}
	
	@Test
	public void testLeftPieceO() {
		o.rotate(RotateDirection.LEFT);
		assertTrue(o.equals(O_LEFT));
	}
	
	@Test
	public void testLeftPieceS() {
		s.rotate(RotateDirection.LEFT);
		assertTrue(s.equals(S_LEFT));
	}
	
	@Test
	public void testLeftPieceZ() {
		z.rotate(RotateDirection.LEFT);
		assertTrue(z.equals(Z_LEFT));
	}
	
	@Test
	public void testLeftPieceJ() {
		j.rotate(RotateDirection.LEFT);
		assertTrue(j.equals(J_LEFT));
	}
	
	@Test
	public void testLeftPieceL() {
		l.rotate(RotateDirection.LEFT);
		assertTrue(l.equals(L_LEFT));
	}
	
	/* Test 180 degree rotation right for all states */
	@Test
	public void testDoubleRightPieceT() {
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		assertTrue(t.equals(T_TWO));
	}
	
	@Test
	public void testDoubleRightPieceI() {
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		assertTrue(i.equals(I_TWO));
	}
	
	@Test
	public void testDoubleRightPieceO() {
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		assertTrue(o.equals(O_TWO));
	}
	
	@Test
	public void testDoubleRightPieceS() {
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		assertTrue(s.equals(S_TWO));
	}
	
	@Test
	public void testDoubleRightPieceZ() {
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		assertTrue(z.equals(Z_TWO));
	}
	
	@Test
	public void testDoubleRightPieceJ() {
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		assertTrue(j.equals(J_TWO));
	}
	
	@Test
	public void testDoubleRightPieceL() {
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		assertTrue(l.equals(L_TWO));
	}
	
	/* Test 180 degree rotation left for all states */
	@Test
	public void testDoubleLeftPieceT() {
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		assertTrue(t.equals(T_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceI() {
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		assertTrue(i.equals(I_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceO() {
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		assertTrue(o.equals(O_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceS() {
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		assertTrue(s.equals(S_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceZ() {
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		assertTrue(z.equals(Z_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceJ() {
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		assertTrue(j.equals(J_TWO));
	}
	
	@Test
	public void testDoubleLeftPieceL() {
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		assertTrue(l.equals(L_TWO));
	}
	
	/* Test 270 degree rotation right for all states */
	@Test
	public void testTripleRightPieceT() {
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		assertTrue(t.equals(T_LEFT));
	}
	
	@Test
	public void testTripleRightPieceI() {
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		assertTrue(i.equals(I_LEFT));
	}
	
	@Test
	public void testTripleRightPieceO() {
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		assertTrue(o.equals(O_LEFT));
	}
	
	@Test
	public void testTripleRightPieceS() {
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		assertTrue(s.equals(S_LEFT));
	}
	
	@Test
	public void testTripleRightPieceZ() {
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		assertTrue(z.equals(Z_LEFT));
	}
	
	@Test
	public void testTripleRightPieceJ() {
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		assertTrue(j.equals(J_LEFT));
	}
	
	@Test
	public void testTripleRightPieceL() {
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		assertTrue(l.equals(L_LEFT));
	}
	
	/* Test 270 degree rotation left for all states */
	@Test
	public void testTripleLeftPieceT() {
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		assertTrue(t.equals(T_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceI() {
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		assertTrue(i.equals(I_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceO() {
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		assertTrue(o.equals(O_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceS() {
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		assertTrue(s.equals(S_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceZ() {
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		assertTrue(z.equals(Z_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceJ() {
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		assertTrue(j.equals(J_RIGHT));
	}
	
	@Test
	public void testTripleLeftPieceL() {
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		assertTrue(l.equals(L_RIGHT));
	}
	
	/* Test 360 degree rotation right for all states */
	@Test
	public void testRevolutionRightPieceT() {
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.RIGHT);
		assertTrue(t.equals(T_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceI() {
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.RIGHT);
		assertTrue(i.equals(I_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceO() {
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.RIGHT);
		assertTrue(o.equals(O_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceS() {
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.RIGHT);
		assertTrue(s.equals(S_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceZ() {
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.RIGHT);
		assertTrue(z.equals(Z_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceJ() {
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.RIGHT);
		assertTrue(j.equals(J_ZERO));
	}
	
	@Test
	public void testRevolutionRightPieceL() {
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.RIGHT);
		assertTrue(l.equals(L_ZERO));
	}
	
	/* Test 360 degree revolution left for all states */
	@Test
	public void testRevolutionLeftPieceT() {
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.LEFT);
		assertTrue(t.equals(T_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceI() {
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.LEFT);
		assertTrue(i.equals(I_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceO() {
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.LEFT);
		assertTrue(o.equals(O_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceS() {
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.LEFT);
		assertTrue(s.equals(S_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceZ() {
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.LEFT);
		assertTrue(z.equals(Z_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceJ() {
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.LEFT);
		assertTrue(j.equals(J_ZERO));
	}
	
	@Test
	public void testRevolutionLeftPieceL() {
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.LEFT);
		assertTrue(l.equals(L_ZERO));
	}
	
	/* Test right and left degree rotation for all states */
	@Test
	public void testRightLeftPieceT() {
		t.rotate(RotateDirection.RIGHT);
		t.rotate(RotateDirection.LEFT);
		assertTrue(t.equals(T_ZERO));
	}
	
	@Test
	public void testRightLeftPieceI() {
		i.rotate(RotateDirection.RIGHT);
		i.rotate(RotateDirection.LEFT);
		assertTrue(i.equals(I_ZERO));
	}
	
	@Test
	public void testRightLeftPieceO() {
		o.rotate(RotateDirection.RIGHT);
		o.rotate(RotateDirection.LEFT);
		assertTrue(o.equals(O_ZERO));
	}
	
	@Test
	public void testRightLeftPieceS() {
		s.rotate(RotateDirection.RIGHT);
		s.rotate(RotateDirection.LEFT);
		assertTrue(s.equals(S_ZERO));
	}
	
	@Test
	public void testRightLeftPieceZ() {
		z.rotate(RotateDirection.RIGHT);
		z.rotate(RotateDirection.LEFT);
		assertTrue(z.equals(Z_ZERO));
	}
	
	@Test
	public void testRightLeftPieceJ() {
		j.rotate(RotateDirection.RIGHT);
		j.rotate(RotateDirection.LEFT);
		assertTrue(j.equals(J_ZERO));
	}
	
	@Test
	public void testRightLeftPieceL() {
		l.rotate(RotateDirection.RIGHT);
		l.rotate(RotateDirection.LEFT);
		assertTrue(l.equals(L_ZERO));
	}
	
	/* Test left and right degree rotation for all states */
	@Test
	public void testLeftRightPieceT() {
		t.rotate(RotateDirection.LEFT);
		t.rotate(RotateDirection.RIGHT);
		assertTrue(t.equals(T_ZERO));
	}
	
	@Test
	public void testLeftRightPieceI() {
		i.rotate(RotateDirection.LEFT);
		i.rotate(RotateDirection.RIGHT);
		assertTrue(i.equals(I_ZERO));
	}
	
	@Test
	public void testLeftRightPieceO() {
		o.rotate(RotateDirection.LEFT);
		o.rotate(RotateDirection.RIGHT);
		assertTrue(o.equals(O_ZERO));
	}
	
	@Test
	public void testLeftRightPieceS() {
		s.rotate(RotateDirection.LEFT);
		s.rotate(RotateDirection.RIGHT);
		assertTrue(s.equals(S_ZERO));
	}
	
	@Test
	public void testLeftRightPieceZ() {
		z.rotate(RotateDirection.LEFT);
		z.rotate(RotateDirection.RIGHT);
		assertTrue(z.equals(Z_ZERO));
	}
	
	@Test
	public void testLeftRightPieceJ() {
		j.rotate(RotateDirection.LEFT);
		j.rotate(RotateDirection.RIGHT);
		assertTrue(j.equals(J_ZERO));
	}
	
	@Test
	public void testLeftRightPieceL() {
		l.rotate(RotateDirection.LEFT);
		l.rotate(RotateDirection.RIGHT);
		assertTrue(l.equals(L_ZERO));
	}
}