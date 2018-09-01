import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 */

/**
 * @author kaungkhant
 * FireBall powerUp: the ball burns through all 
 */
public class FireBall extends PowerUp {
	public static final Color COLOR = Color.RED; 
	
	public FireBall(int courtWidth, int courtHeight, int px, int py) {
		super(courtWidth, courtHeight, px, py);
	}

	@Override
	public String activate() {
		return "Fire Ball"; 
	}	

	@Override
	public void draw(Graphics g) {
		 g.setColor(COLOR);
		 g.drawOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
	     g.fillOval(this.getPx() + 5, this.getPy() + 5, 10, 10); 
	} 
}

