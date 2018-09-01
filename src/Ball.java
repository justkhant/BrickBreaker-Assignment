
import java.awt.*;

public class Ball extends GameObj implements Comparable<Ball> {
    public static final int SIZE = 15;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    private boolean onFire = false;
    
    public Ball(int courtWidth, int courtHeight, int px, int py) {
        super(INIT_VEL_X, INIT_VEL_Y, px, py, SIZE, SIZE, courtWidth, courtHeight);
    }
    
    //hits the paddle (travels different directions based on which paddle it hits) 
    public void hitPaddle(Paddle eLeft, Paddle mLeft, Paddle mRight, Paddle eRight) {
        if (this.intersects(eLeft)) {
            this.setVy(-6);
            this.setVx(-14);
        }
        else if (this.intersects(mLeft)) {
        	this.setVy(-14);
        	this.setVx(-6);
        }
        else if (this.intersects(mRight)) {
        	this.setVy(-14);
        	this.setVx(6);
        }
        else if (this.intersects(eRight)) {
        	this.setVy(-6);
        	this.setVx(14);
        }
    }
    
    //checks the type of ball
    public boolean isOnFire() {
    	return onFire; 
    }
     
    //setters
    public void setFire(boolean b) {
    	onFire = b; 
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        if (onFire) {
        	g.setColor(Color.RED);
        }
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());  
    }

	@Override
	public int compareTo(Ball o) {
		return -1;
	}
}
	

