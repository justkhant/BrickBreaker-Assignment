import java.awt.*;
import java.util.*; 

/**
 * @author kaungkhant
 * Represents the brick/powerup layout with a 2D GameObj array. Each array corresponds to a space on the board (50 x 20). 
 * The board space will be the space above the paddle except the last row (500 x 660) 
 * Each index will be filled with a GameObj or null (brick or powerup) 
 * If the last row is ever filled with a Brick, we lose because this means that the bricks went past the paddle. 
 */
public class BoardLayout {
	// Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 700;
    public static final int GRID_X_SCALAR = 50;
    public static final int GRID_Y_SCALAR = 20; 
    
	private GameObj[][] layout; 
	
	public BoardLayout() {
		layout = new GameObj[10][32]; 
	}
	
	//lowers all the GameObj's in the layout down one index. If an object is in the last row, it should disappear 
	public void lower() {
		for (int i = 0; i < layout.length; i++) {
			for (int j = layout[i].length - 2; j >= 0; j--) {
				layout[i][j + 1] = layout[i][j];
				layout[i][j] = null; 
				if (layout[i][j + 1] != null) {
				//have to change the y positions of the object
					layout[i][j + 1].setPy((j + 1) * GRID_Y_SCALAR);
					 
				}
			}
		}
	}
	
	//add new bricks to the first row randomly 
	public void addBricks() {
		//determine how many new items should be generated (1 - 5)
		int num = (int) Math.round((Math.random() * 5) + 1);
		
		//determine which indexes should be filled (fill 'num' indexes) 
		Set<Integer> indexesToFill = new TreeSet<Integer>(); 
		while (indexesToFill.size() < num) {
			 indexesToFill.add((int) Math.round(Math.random() * 9));
		}
		
		//fill the indexes with a brick with random strength
		for (int i = 0; i < layout.length; i++) {
			if (indexesToFill.contains(i)) {
				int value = (int) Math.round(Math.random() * 3 + 1);  
					layout[i][0] = new Brick(COURT_WIDTH, COURT_HEIGHT, i * GRID_X_SCALAR, 0, value);
			}
			else {
					layout[i][0] = null;
			}
		}
	}
	
	//adds a PowerUp to the first row of the board at a random available space
	public void addPowerUps() {
		//determine which powerUp to add
		double powerUp = (int) Math.round(Math.random() * 3 + 1); 
		
		//if the first row is every completely filled, there will be an infinite loop. 
		//However, the nature of the game ensures that there will always be one empty index in the first row
		while (true) {
			//generate random position in the first row
			int i = (int) Math.round(Math.random() * 9); 
			// add PowerUp to the first empty position
			if (layout[i][0] == null)  {
				//if powerUp is 1 add the extra ball PowerUp
				if (powerUp == 1) {
					layout[i][0] = new ExtraBall(COURT_WIDTH, COURT_HEIGHT, i * GRID_X_SCALAR + 15, 0);
				}
				else if (powerUp == 2) {
					layout[i][0] = new FireBall(COURT_WIDTH, COURT_HEIGHT, i * GRID_X_SCALAR + 15, 0); 
				}
				
				else {
					layout[i][0] = new GunPaddle(COURT_WIDTH, COURT_HEIGHT, i * GRID_X_SCALAR + 15, 0);
				}
				break;
			}
		}	
	}
	
	//checks if the bottom row is filled with a brick. If it isn't return false
	public boolean isAbovePaddle() {
		for (int i = 0; i < layout.length; i++) {
			if (GameCourt.isBrick(layout[i][31])) {
				return false; 
			}
			else {
				layout[i][31] = null; 
			}
		}
		return true; 
	}
	
	//returns the value at a specific index. Assumes that the given i, j will always be in bounds
	public GameObj getObj(int i, int j) {		
		return layout[i][j]; 
	}
    
	//remove the value at a specific index. Assumes that the given i, j will always be in bounds
	public void remove(int i, int j) {
			layout[i][j] = null; 
		}
	
	//getters
	public int getWidth() {
		return layout.length;
	}
     
	public int getHeight() {
		return layout[0].length; 
	}
	
	public void drawObjects(Graphics g) {
	    for (int i = 0; i < layout.length; i++) {
	    	for (int j = 0; j < layout[i].length; j++) {
	        		if (layout[i][j] != null) {
	        			layout[i][j].draw(g);		
	        		}
	        	}
	        }
	}
	
	//methods to use in J-unit tests
	public boolean allEmpty() { 
		for (GameObj[] column : layout) {
			for (GameObj o : column) {
				if (o != null) {
					return false;
				}
			}
		}
		return true;	
	}
	
	public boolean isEmpty(int i, int j) {
		return layout[i][j] == null;
	}
	
	public void addObjs(GameObj o, int i, int j) {
		layout[i][j] = o; 
	}
}
