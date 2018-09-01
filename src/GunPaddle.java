import java.awt.Color;
import java.awt.Graphics;


/**
 * @author kaungkhant
 * Bomb powerUp: clears all the bricks in a radius of 1
 */
public class GunPaddle extends PowerUp {
	public static final Color COLOR = Color.BLUE; 
	
	public GunPaddle(int courtWidth, int courtHeight, int px, int py) {
		super(courtWidth, courtHeight, px, py);
	}

	@Override
	public String activate() {
		return "Gun Paddle"; 
	}	

	@Override
	public void draw(Graphics g) {
		 g.setColor(COLOR);
		 g.drawOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
	     g.fillOval(this.getPx() + 5, this.getPy() + 5, 10, 10); 
	} 
}
