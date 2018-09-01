
import java.awt.*;

/**
 * @author kaungkhant
 * Models a paddle in the game
 */
public class Paddle extends GameObj {

	public static final int INIT_HEIGHT = 5;
	public static final int INIT_PY = 650; 
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    private boolean gunModeOn = false;
    private Color color;

	public Paddle(int courtWidth, int courtHeight, int pX, int width, Color color) {
	  super(INIT_VEL_X, INIT_VEL_Y, pX, INIT_PY, width, INIT_HEIGHT, courtWidth, courtHeight);
	  this.color = color;
	}
	
	//changes the paddle into guns.(for ends of the paddle in the game). 
	public void setGunMode(boolean b) {
		gunModeOn = b; 
	}
	
	@Override
	public void draw(Graphics g) {
        g.setColor(this.color);
        int height = this.getHeight();
        if (gunModeOn) {
        	height = 15; 
        }
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), height);
	}
}
