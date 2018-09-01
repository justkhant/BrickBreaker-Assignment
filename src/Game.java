/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("BRICK BREAKER");
        frame.setLocation(500, 300);

        final JButton start = new JButton("Start"); 
       
        final JButton instructions = new JButton("Instructions"); 
        final JLabel title = new JLabel("BRICK BREAKER", SwingConstants.CENTER); 
        title.setPreferredSize(new Dimension(450, 200));
        title.setFont(new Font("Sans Serif", Font.BOLD, 50));
        
        final JPanel startMenu = new StartMenu(start, instructions, title); 
        frame.add(startMenu, BorderLayout.CENTER); 
        
        ActionListener playGame = new ActionListener() {
        	public void actionPerformed(ActionEvent e) { 
        		frame.remove(startMenu); 
        		frame.setLocation(500, 10);
        		frame.setSize(new Dimension(500, 800));
        		
        		// Status panel
                final JPanel status_panel = new JPanel();
                frame.add(status_panel, BorderLayout.SOUTH);
                final JLabel status = new JLabel("Running...");
                status_panel.add(status);
                
                //score label
                final JLabel score = new JLabel("High Score: 0     Score: 0"); 
                     
                // Main playing area
                final GameCourt court = new GameCourt(status, score);
                frame.add(court, BorderLayout.CENTER);
                
                // Score & Reset button
                final JPanel control_panel = new JPanel();
                frame.add(control_panel, BorderLayout.NORTH);
                
                control_panel.add(score);
                
                // Note here that when we add an action listener to the reset button, we define it as an
                // anonymous inner class that is an instance of ActionListener with its actionPerformed()
                // method overridden. When the button is pressed, actionPerformed() will be called.
                final JButton reset = new JButton("Reset");
                reset.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        court.reset();
                    }
                });
                control_panel.add(reset);
                
                // Start game
                court.reset();
        	}
        };
        
        start.addActionListener(playGame);
        instructions.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JOptionPane.showMessageDialog(null, 
        				""
        				+ "INSTRUCTIONS \n" 		
        		        + "This is a variation of the classic Brick Breaker game. \n \n" 
        				+ "Move the mouse to move the paddle! Click to start the ball in motion! \n"	
        				+ "Break the bricks before they reach your paddle! \n \n" 
        		        + "Bricks: Black = 3 hits, Dark Gray = 2 hits, Light Gray = 1 hit. \n \n" 
        			    + "Collect Power Ups to get special upgrades! (Lasts for only a few seconds)\n \n" 
        		        + "Black = + 1 ball (adds a ball to the game! This is permanent until you lose the ball.)\n "
        		        + "Red = Fireball (gives the ball the ability to burn through the bricks!) \n"
        		        + "Blue = Guns! (equips your paddle with guns to shoot the bricks! Click to shoot!) \n \n"
        		        + "You will lose if you run out of balls or if the bricks reach your paddle. \n"
        		        + "Good luck!"
        		        
        				);
        	}
        });
        
        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}