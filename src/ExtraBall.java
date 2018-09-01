import java.awt.*;

/**
 * @author kaungkhant
 * Plus extra ball powerUp
 */
public class ExtraBall extends PowerUp {
    public static final Color COLOR = Color.BLACK ; 
	
	public ExtraBall(int courtWidth, int courtHeight, int px, int py) {
		super(courtWidth, courtHeight, px, py);
	}

	@Override
	public String activate() {
		return "Extra Ball"; 
	}	

	@Override
	public void draw(Graphics g) {
		 g.setColor(COLOR);
		 g.drawOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
	     g.fillOval(this.getPx() + 5, this.getPy() + 5, 10, 10); 
	} 
}

