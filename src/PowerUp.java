/*
 * @author kaungkhant
 * Represents a PowerUp in the game 
 */
public abstract class PowerUp extends GameObj{	
	 public static final int SIZE = 20;
	 public static final int INIT_VEL_X = 0;
	 public static final int INIT_VEL_Y = 0;
	 
	public PowerUp(int courtWidth, int courtHeight, int px, int py) {
		super(INIT_VEL_X, INIT_VEL_Y, px, py, SIZE, SIZE, courtWidth, courtHeight);
	}
	
	//ability of powerUp activated by Ball
	//returns the name of the powerup that was activated ("Extra Ball", "Fire Ball", "Bomb Ball") 
	public abstract String activate();
}
