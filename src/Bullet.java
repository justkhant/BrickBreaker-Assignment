import java.awt.*;
/**
 * 
 */

/**
 * @author kaungkhant
 * Represents a bullet
 */
public class Bullet extends GameObj implements Comparable<Bullet>{	
	public static final int WIDTH = 5; 
	public static final int HEIGHT = 10; 
	public static final int INIT_PY = 650;
	public static final int VELOCITY_X = 0; 
	public static final int VELOCITY_Y = -15; 
	public static final Color COLOR = Color.BLUE; 
	
	
	
	public Bullet(int courtWidth, int courtHeight, int px) {
		super(VELOCITY_X, VELOCITY_Y, px, INIT_PY, WIDTH, HEIGHT, courtWidth, courtHeight);
	}


	@Override
	public void draw(Graphics g) {
		g.setColor(COLOR);
		g.fillRect(this.getPx(), this.getPy(), WIDTH, HEIGHT);
	}


	@Override
	public int compareTo(Bullet o) {
		return 1;
	}
	
}
