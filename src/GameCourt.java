/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
    
    // the state of the game logic
	
	//Collection of balls in the game 
	private Set<Ball> balls;
	
	//Collection of bullets in the game
	private Set<Bullet> bullets; 
	
	//the paddle is made up of four parts
	private Paddle edgeLeft; 
	private Paddle middleLeft;
	private Paddle middleRight;
	private Paddle edgeRight;
	
	//the bricks
	private BoardLayout boardLayout; 
	
	//GunModeOn
	private boolean gunModeOn = false; 
	
    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    private JLabel score; // text that displays the score 
    
    private ArrayList<Integer> scores = new ArrayList<Integer>(); //Collection of scores
    private int scoreCounter; //score counter
    
    // Game constants
    public static final int COURT_WIDTH = 500;
    public static final int COURT_HEIGHT = 700;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public GameCourt(JLabel status, JLabel score) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!
          
        Timer brickTimer = new Timer(4000, new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		boardLayout.lower(); 
        		boardLayout.addBricks();
        		//15% chance of adding a PowerUp  
        		if (Math.random() < 0.15) {
        			boardLayout.addPowerUps(); 
        		}
        	}
        });
        brickTimer.start();
        
        //moves the paddle (balls moves with the paddle before click)
        addMouseMotionListener(new MouseMotionAdapter() {
        	public void mouseMoved (MouseEvent e) {
        		//shift x-coordinates of mouse
        		int x = 0; 
        		if (e.getX() >= 48) { 
        			x = e.getX() - 48;
        		} 
        		edgeLeft.setPx(x);
        		middleLeft.setPx(x + 15);
        		middleRight.setPx(x + 50);
        		edgeRight.setPx(x + 85);
        		
        	    for (Ball b : balls) {
        			if (b.getVx() == 0) {
        				b.setPx(middleLeft.getPx() + 15);
        			}
        		}
        	}
        });
        
        //mouse click starts the balls in motion and shoots bullets if gunMode is on
        addMouseListener(new MouseAdapter() {
        	public void mouseClicked (MouseEvent e) {    	
        		for (Ball b : balls) { 
        			if (b.getVx() == 0) {
        				b.setVx(-6);
        				b.setVy(-12);
        			}
        		}
        		
        		if (gunModeOn) {
        			bullets.add(new Bullet(COURT_WIDTH, COURT_HEIGHT, edgeLeft.getPx() + 2));
        			bullets.add(new Bullet(COURT_WIDTH, COURT_HEIGHT, edgeRight.getPx() + 2)); 
        		}
        	}
        }); 
        
        this.status = status; 
        this.score = score; 
    }
    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        //make paddles
        edgeLeft = new Paddle(COURT_WIDTH - 85, COURT_HEIGHT, 135, 10, Color.BLACK);
        middleLeft = new Paddle(COURT_WIDTH - 50, COURT_HEIGHT, 150, 30, Color.BLUE); 
        middleRight = new Paddle(COURT_WIDTH - 15, COURT_HEIGHT, 185, 30, Color.BLUE);
        edgeRight = new Paddle(COURT_WIDTH, COURT_HEIGHT, 220, 10, Color.BLACK);
        
        //make Collection of Balls with one ball
        balls = new TreeSet<Ball>();
        balls.add(new Ball(COURT_WIDTH, COURT_HEIGHT, middleLeft.getPx() + 15, middleLeft.getPy() - 16));
        
        //make empty collection of bullets
        bullets = new TreeSet<Bullet>(); 
       
        scores.add(scoreCounter); 
        Collections.sort(scores);
        scoreCounter = 0; 
        saveHighScore(); 
        
        //make brick layout
        boardLayout = new BoardLayout(); 
        
        playing = true;
        gunModeOn = false; 
        
        status.setText("Running...");
        score.setText("HIGHSCORE: " + scores.get(scores.size() - 1) + "     SCORE: " + scoreCounter);
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {	
        	        	
        	//number of balls to add to the game 
        	int ballsToAdd = 0; 
        	
        	//Iterate through the balls set
        	Iterator<Ball> bSet = balls.iterator();  
        	while (bSet.hasNext()) {	
        		Ball b = bSet.next(); 
        		
        		// advance the ball in its current direction.
        		b.move(); 
        		
        		// make the ball bounce off walls...
        		b.bounce(b.hitWall());
        		
        		// ...and the paddle
        		b.hitPaddle(edgeLeft, middleLeft, middleRight, edgeRight);
        		
        		// ...and the bricks, should not bounce for powerUps
        		for (int i = 0; i < boardLayout.getWidth(); i++) {
        			for (int j = 0; j < boardLayout.getHeight(); j++) {
        				GameObj o = boardLayout.getObj(i, j);
        				
        				if (o != null) {			
        					//if the ball hits the object
        					if (b.willIntersect(o)) { 
        						
        						//if the object is a brick
        					    if (isBrick(o)) {
        					    	Brick br = (Brick) o; 
        					    	
        					    	//if ball is a fire ball
        					    	if (b.isOnFire()) {
        					    		boardLayout.remove(i, j);
        					    		scoreCounter++;
        					    	}
        					    		
        					    	//else, it is a normal ball
        					    	else {
        					    	    hitBrick(br, i, j); 
        					    		//ball bounces
        					    		b.bounce(b.hitObj(o));
        					    	}
        					   }
        					   //if the object is a powerUp
        					   else {
        						    PowerUp p = (PowerUp) o; 
        						    if (p.activate().equals("Extra Ball")) {
        						    	ballsToAdd++; 
        						    }
        						    else if (p.activate().equals("Fire Ball")) {
        						    	b.setFire(true); 
        						    	
        						    	//return back to normal after 8 secs.
        						    	java.util.Timer powerUp = new java.util.Timer();
        						        powerUp.schedule(new java.util.TimerTask() {
        						        	public void run() {
        						        		b.setFire(false);
        						        		powerUp.cancel(); 
        						        	}
        						        }, 8000);
        						    }
        						    else if (p.activate().equals("Gun Paddle")) {
        						        edgeRight.setGunMode(true);
        						        edgeLeft.setGunMode(true);
        						        gunModeOn = true; 
        						        
        						        //return back to normal after 8 secs
        						        java.util.Timer powerUp = new java.util.Timer();
        						        powerUp.schedule(new java.util.TimerTask() {
        						        	public void run() {
        						        		edgeRight.setGunMode(false);
                						        edgeLeft.setGunMode(false);
                						        gunModeOn = false; 
        						        		powerUp.cancel(); 
        						        	}
        						        }, 8000);
        						    	
        						    }
        						    boardLayout.remove(i, j);	    
        					   }
        					}
        				}
        			}
        		}
        		
        		//remove ball if goes past paddle
        		if (b.getPy() > 670) {
					bSet.remove();
				}
        		
        		
        	}
        	
        	//iterate through bullets
        	Iterator<Bullet> bulletSet = bullets.iterator();
        	while (bulletSet.hasNext()) {	
        		Bullet b = bulletSet.next(); 
        		
        		//move bullet
        		b.move();
        		
        		//remove bullets if it hits a wall
        		if (b.hitWall() != null) {
        			bulletSet.remove();
        		}
        		
        		else {
        			//check to see if the bullet should be removed
        			boolean willRemove = false; 
        			//if the bullet hits a brick
        			for (int i = 0; i < boardLayout.getWidth(); i++) {
        				for (int j = 0; j < boardLayout.getHeight(); j++) {    				
        					GameObj o = boardLayout.getObj(i, j);
        					if (isBrick(o)) {
        						if (b.willIntersect(o)) {
        							Brick br = (Brick) o;
        							hitBrick(br, i, j);
        							willRemove = true; 
        						}							
        					}
        				}
        			}
        			if (willRemove) {
        				bulletSet.remove();
        			}
        		}
    			
    		}
        	
        	//adds new balls accordingly
        	for (int i = 1; i <= ballsToAdd; i++) {
        		balls.add(new Ball(COURT_WIDTH, COURT_HEIGHT, middleLeft.getPx() + 15, middleLeft.getPy() - 16));
        	}
        	
        	// check for the game end conditions
    		if (balls.isEmpty() || !boardLayout.isAbovePaddle()) {
    			playing = false;
    			status.setText("You lose! Click 'Reset' to try again.");
    			scores.add(scoreCounter); 
    			saveHighScore(); 
    		}
    		
    		//update scores in real time
            Collections.sort(scores);
            int highScore = scores.get(scores.size() - 1);
            if (highScore < scoreCounter) {
            	highScore = scoreCounter; 
            }        	
    		score.setText("HIGHSCORE: " + highScore + "    SCORE: " + scoreCounter);
    		
            // update the display
            repaint();
        }
    }
   
    //HELPER FUNCTIONS 
    //saves the highScore to a file
    public void saveHighScore() {
    	try {
			FileWriter f = new FileWriter("Scores.txt");
			BufferedWriter bw = new BufferedWriter(f); 
			bw.write("1" + scores.get(scores.size() - 1));
			bw.flush();
			bw.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		} 
    }
    
    //checks if a GameObj is a brick
    public static boolean isBrick(GameObj o) {
    	if (o == null) {
    		return false; 
    	}
    	try {
    		Brick br = (Brick) o; 
    		return true;
    	}
        catch (ClassCastException e) {
        	return false; 
        }
    }
    
    //checks if a GameObj is a PowerUp
    public static boolean isPowerUp(GameObj o) {
    	if (o == null) {
    		return false; 
    	}
    	try {
    		PowerUp br = (PowerUp) o; 
    		return true;
    	}
        catch (ClassCastException e) {
        	return false; 
        }
    }
    
    //hits a brick at a specified index in the boardlayout
    public void hitBrick(Brick br, int i, int j) {
		//remove brick if strength is 1, else subtract from strength
		if (br.getStrength() == 1) {
			boardLayout.remove(i, j); 
			scoreCounter++; 
		}
		else if (br.getStrength() == 2) { 
			br.setStrength(1);
		}
		else {
			br.setStrength(2);
		}
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw balls
        for (Ball b : balls) {
        	b.draw(g);
        }
        
        //draw bullets
        for (Bullet b: bullets) {
        	b.draw(g);
        }
        
        //draw paddles
        edgeLeft.draw(g);
        middleLeft.draw(g);
        middleRight.draw(g);
        edgeRight.draw(g);
        
        //draw board
        boardLayout.drawObjects(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
