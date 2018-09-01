import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author kaungkhant
 * J-unit tests for BoardLayout
 */
public class BoardLayoutTest {
	// Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 700;
    public static final int GRID_X_SCALAR = 50;
    public static final int GRID_Y_SCALAR = 20; 
    
    
	/**
	 * Test method for {@link BoardLayout#BoardLayout()}.
	 */
	@Test
	public void testBoardLayout() {
		BoardLayout test = new BoardLayout(); 
		assertEquals(test.getWidth(), 10);
		assertEquals(test.getHeight(), 32);
		assertTrue(test.allEmpty());
	}

	/**
	 * Test method for {@link BoardLayout#lower()}.
	 */
	@Test
	public void testLowerNoObjsInLayout() {
		BoardLayout test = new BoardLayout(); 
		assertEquals(test.getWidth(), 10);
		assertEquals(test.getHeight(), 32);
		assertTrue(test.allEmpty());
		test.lower(); 
		//should have no effect
		assertEquals(test.getWidth(), 10);
		assertEquals(test.getHeight(), 32);
		assertTrue(test.allEmpty()); 
	}
    
	@Test 
	public void testLowerChangesPyOfObjs() {
		BoardLayout test = new BoardLayout(); 
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 0, 0 , 1); 
		assertEquals(b.getPy(), 0);
		test.addObjs(b, 0, 0); 
		test.lower(); 
		assertEquals(b.getPy(), 1 * GRID_Y_SCALAR); 
		test.lower(); 
		assertEquals(b.getPy(), 2 * GRID_Y_SCALAR); 
		test.lower();
		test.lower();
		assertEquals(b.getPy(), 4 * GRID_Y_SCALAR); 
	}
	
	@Test
	public void testLowerOneObjInLayout() {
		BoardLayout test = new BoardLayout(); 
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 0, 0, 1); 
		//Brick b should be in index (0,0) with pY = 0
		assertEquals(b.getPy(), 0);
		test.addObjs(b, 0, 0); 
		assertFalse(test.isEmpty(0, 0)); 
		assertEquals(test.getObj(0, 0), b); 
		//lower once
		test.lower(); 
		//Brick b should now be in index (0, 1) with pY = 1 * GRID_Y_SCALAR
		assertTrue(test.isEmpty(0, 0));
		assertFalse(test.isEmpty(0, 1));
		assertEquals(b, test.getObj(0, 1)); 
		assertEquals(1 * GRID_Y_SCALAR, b.getPy()); 
		//now lower twice
		test.lower(); 
		test.lower(); 
		//Brick b should now be in index (0, 3) with pY = 3 * GRID_Y_SCALAR
		assertTrue(test.isEmpty(0, 1));
		assertTrue(test.isEmpty(0, 2));
		assertFalse(test.isEmpty(0, 3));
		assertEquals(b, test.getObj(0, 3)); 
		assertEquals(3 * GRID_Y_SCALAR, b.getPy()); 
	}
	
	@Test 
	public void testLowerMultipleObjsInLayout() {
		BoardLayout test = new BoardLayout(); 
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 5 * GRID_X_SCALAR, 8 * GRID_Y_SCALAR, 3); 
		ExtraBall p = new ExtraBall(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 24 * GRID_Y_SCALAR); 
		
		//Brick b should be in index (5, 8) with pY = 8 * GRID_Y_SCALAR
		//ExtraBall p should be in index (7, 24) with pY = 24 * GRID_Y_SCALAR
		assertEquals(b.getPy(), 8 * GRID_Y_SCALAR);
		assertEquals(p.getPy(), 24 * GRID_Y_SCALAR); 
		test.addObjs(b, 5, 8); 
		test.addObjs(p, 7, 24);
		assertFalse(test.isEmpty(5, 8)); 
		assertFalse(test.isEmpty(7, 24));
		assertEquals(test.getObj(5, 8), b);
		assertEquals(test.getObj(7, 24), p); 
		
		//lower
		test.lower(); 
		
		//Brick b should be in index (5, 9) with pY = 9 * GRID_Y_SCALAR
		//ExtraBall p should be in index (7, 25) with pY = 25 * GRID_Y_SCALAR
		assertEquals(b.getPy(), 9 * GRID_Y_SCALAR);
		assertEquals(p.getPy(), 25 * GRID_Y_SCALAR); 
		assertTrue(test.isEmpty(5, 8)); 
		assertFalse(test.isEmpty(5, 9));
		assertTrue(test.isEmpty(7, 24));
		assertFalse(test.isEmpty(7, 25));
		assertEquals(test.getObj(5, 9), b);
		assertEquals(test.getObj(7, 25), p);		
	}
	
	@Test
	public void testLowerObjsInLastRowShouldBeRemoved() {
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 5 * GRID_X_SCALAR, 32 * GRID_Y_SCALAR, 3); 
		//add Brick to last row
		test.addObjs(b, 5, 31);
		assertEquals(32 * GRID_Y_SCALAR, b.getPy());
		assertFalse(test.isEmpty(5, 31));
		assertEquals(b, test.getObj(5, 31)); 
		//lower the Objs
		test.lower();
		//the Obj should be removed from board (ie. replaced with null since there were no objs above it). 
		//in the game you would lose if the bottom row is ever filled with a Brick but if a PowerUp was ever in
		//the last row it should just disappear
		assertEquals(32 * GRID_Y_SCALAR, b.getPy());
		assertTrue(test.isEmpty(5, 31)); 
	}
	
	
	/**
	 * Test method for {@link BoardLayout#addBricks()}.
	 */
	@Test
	public void testAddBricksToEmptyBoard() {
		BoardLayout test = new BoardLayout(); 
		assertTrue(test.allEmpty()); 
		
		test.addBricks();
		assertFalse(test.allEmpty()); 
		
		//check for number of bricks in the first row
		int numBricks = 0; 
		for (int i = 0; i < 10; i++) {
			if (GameCourt.isBrick(test.getObj(i, 0))) {
				numBricks++; 
			}
		}
		//number of bricks should be more than 0 and less than 5 
		assertTrue(numBricks > 0 && numBricks <= 5); 
	}
    
	/**
	 * Test method for {@link BoardLayout#addPowerUps()}.
	 */
	@Test
	public void testAddPowerUpsToEmptyBoard() {
		BoardLayout test = new BoardLayout(); 
		assertTrue(test.allEmpty()); 
		
		test.addPowerUps();
		assertFalse(test.allEmpty()); 
		
		//check for number of bricks in the first row
		int numPowerUps = 0; 
		for (int i = 0; i < 10; i++) {
			if (GameCourt.isPowerUp(test.getObj(i, 0))) {
				numPowerUps++; 
			}
		}
		//number of PowerUps should be 1; 
		assertTrue(numPowerUps == 1 ); 
	}
	
    @Test
    public void testAddPowerUpsToRowFilledWithBricksExceptForOne() { 
    	BoardLayout test = new BoardLayout(); 
    	//fill out all of the first row except one index
    	for (int i = 0; i < 10; i++) {
    		//leave index 7 empty
			if (i != 7) {
				test.addObjs(new Brick(COURT_WIDTH, COURT_HEIGHT, i, 0 , 3), i, 0);
			}
		}
    	assertTrue(test.isEmpty(7, 0)); 
    	test.addPowerUps(); 
    	
    	//PowerUp should spawn in remaining space
    	assertFalse(test.isEmpty(7, 0));
    	assertTrue(GameCourt.isPowerUp(test.getObj(7,0)));
    }
    
    
	/**
	 * Test method for {@link BoardLayout#isAbovePaddle()}.
	 */
	@Test
	public void testIsAbovePaddleEmptyBoard() {
		BoardLayout test = new BoardLayout(); 
		assertTrue(test.isAbovePaddle()); 
	}
	
	@Test
	public void testIsAbovePaddleObjsNotInLastRow() { 
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 23 * GRID_Y_SCALAR, 1);
		FireBall f = new FireBall(COURT_WIDTH, COURT_HEIGHT, 4 * GRID_X_SCALAR, 14 * GRID_Y_SCALAR); 
		test.addObjs(b, 7, 23);
		test.addObjs(f, 4, 14); 
		assertTrue(test.isAbovePaddle()); 	
	}
	
	@Test 
	public void testIsAbovePaddleBrickInLastRow() { 
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 31 * GRID_Y_SCALAR, 1);
		test.addObjs(b, 7, 31);
		assertFalse(test.isAbovePaddle());
	}
	
	@Test 
	public void testIsAbovePaddlePowerUpInLastRow() {
		BoardLayout test = new BoardLayout(); 
		GunPaddle g = new GunPaddle(COURT_WIDTH, COURT_HEIGHT, 4 * GRID_X_SCALAR, 31 * GRID_Y_SCALAR); 
		test.addObjs(g, 4, 31);
		//shouldn't matter if a Power-Up is in the last row
		assertTrue(test.isAbovePaddle()); 
	}
	
	@Test
	public void testIsAbovePaddleBrickisLoweredIntoLastRow() {
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 30 * GRID_Y_SCALAR, 1);
		test.addObjs(b, 7, 30);
		assertTrue(test.isAbovePaddle());
		test.lower(); 
		assertFalse(test.isAbovePaddle());
	}

	/**
	 * Test method for {@link BoardLayout#getObj(int, int)}.
	 */
	@Test
	public void testGetFromEmptyIndex() {
		BoardLayout test = new BoardLayout();
		assertEquals(null, test.getObj(7, 30));
	}
	
	@Test
	public void testGetObjBrick() {
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 30 * GRID_Y_SCALAR, 1);
		test.addObjs(b, 7, 30);
		assertEquals(b, test.getObj(7, 30));
	}
	
	@Test
	public void testGetObjPowerUp() { 
		BoardLayout test = new BoardLayout();
		PowerUp p = new FireBall(COURT_WIDTH, COURT_HEIGHT, 7 * GRID_X_SCALAR, 30 * GRID_Y_SCALAR);
		test.addObjs(p, 7, 30);
		assertEquals(p, test.getObj(7, 30));
	}
    
	/**
	 * Test method for {@link BoardLayout#remove(int, int)}.
	 */
	@Test
	public void testRemoveFromEmptyIndex() {
		BoardLayout test = new BoardLayout();
		assertEquals(test.getObj(0, 0), null);
		test.remove(0, 0);
		assertEquals(test.getObj(0, 0), null);
	}
	
	@Test
	public void testRemoveFromNonEmptyIndex() {
		BoardLayout test = new BoardLayout();
		Brick b = new Brick(COURT_WIDTH, COURT_HEIGHT, 0, 0, 1);
		test.addObjs(b, 0, 0);
		assertEquals(test.getObj(0, 0), b);
		test.remove(0, 0);
		assertEquals(test.getObj(0, 0), null);
	}

	/**
	 * Test method for {@link BoardLayout#getWidth()}.
	 */
	@Test
	public void testGetWidth() {
		BoardLayout test = new BoardLayout(); 
		assertEquals(10, test.getWidth());
	}

	/**
	 * Test method for {@link BoardLayout#getLength()}.
	 */
	@Test
	public void testGetHeight() {
		BoardLayout test = new BoardLayout(); 
		assertEquals(32, test.getHeight()); 
	}
}
