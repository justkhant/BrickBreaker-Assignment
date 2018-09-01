
import java.awt.*;

/**
 * @author kaungkhant
 * Models a brick in the game
 */

public class Brick extends GameObj{	
	public static final int WIDTH = 50; 
	public static final int HEIGHT = 20; 
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
	//the strength of the brick (how many times it can be hit) 
	private int strength; 
	private Color color; 
	
	public Brick (int courtWidth, int courtHeight, int pX, int pY, int strength) {
	  super(INIT_VEL_X, INIT_VEL_Y, pX, pY, WIDTH, HEIGHT, courtWidth, courtHeight);  
	  this.strength = strength;
	}
	
	//getters and setters
	public int getStrength() {
		return strength;
	}
		
	public void setStrength(int strength) {
		this.strength = strength;
	}
	

	@Override
	public void draw(Graphics g) {		
		 //set color based on strength
		  if (strength == 1) {
			  color = Color.LIGHT_GRAY;
		  }
		  else if (strength == 2) {
			  color = Color.GRAY; 
		  }
		  else {
			  color = Color.DARK_GRAY; 
		  }
		  
		  g.setColor(this.color);
		  g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
		  g.setColor(Color.WHITE); 
		  g.drawRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
	}
	
	@Override
	//Equals method
	public boolean equals(Object o) {	
	        if (this == o) {
	            return true; 
	        }
	        
	        //checks if object is null
	        if (o == null) {
	            return false; 
	        }  
	        
	        //checks if classes are the same
	        if (getClass() != o.getClass()) {
	            return false;
	        }  
	        
	        //checks for structural equality
	        Brick other = (Brick) o;   
	        if (strength != other.getStrength()) {
	            return false; 
	        }
	      
	        return true;
	}
}
	
	
	


