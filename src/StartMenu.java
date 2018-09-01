import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

/**
 * 
 */

/**
 * @author kaungkhant
 *
 */
public class StartMenu extends JPanel {
	// Game constants
    public static final int MENU_WIDTH = 500;
    public static final int MENU_HEIGHT = 300;
    
	public StartMenu(JButton start, JButton instructions, JLabel title) {
		this.add(title); 
		this.add(start);
		this.add(instructions); 	
	}
	  
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MENU_WIDTH, MENU_HEIGHT);
    }
}
