import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;


public class Game extends JPanel implements ActionListener, MouseListener, KeyListener
{
	private Timer refresh = new Timer(10, this);  // 360 frames per seconds
	public static int WIDTH = 1000, HEIGHT = 750; // size of frame

	ObjectHandler handler;
	/*
	 *  ObjectHandler class is a bridge between our Board and GameObjects.
	 *  It contains LinkedList of Objects
	 *  Two main functionality:
	 *      1. Update() 
	 *      	: position of objects are updated.
	 *      2. renderObject(Graphics g)
	 *      	: this will set color into new position.
	 *      
	 *      hierarchy: Game -> handler -> GameObject -> [Player or Layer] 
	 *     
	 */
	
	public Game()
	{
		// insert JPanel Game screen here!!
		
		handler = new ObjectHandler();			// create handler for objects
				
		handler.addObject(new Player(100, 100, handler, ObjectID.Player));	// one player (blue rectangle)
		
		handler.CreateBottomLayer();			// bottom layer created by handler
		
		refresh.start();
	}

	public void paintComponent(Graphics g)
	{
		// objects left here will be "Redrawn"
		// thus character objects should be called here.
		
		System.out.println("TO SHOW THAT THIS IS BEING CALLED!!!");
		super.paintComponent(g);

		handler.Update();			
		handler.renderObject(g);
	}

	public void actionPerformed(ActionEvent event){

		
		// this calls the paintComponent and refreshes the screen
		repaint();  
	}	
	
	
	// TODO:: MouseListener not working
	public void mouseReleased  (MouseEvent click) {}
	public void mouseClicked   (MouseEvent click) {}
	public void mousePressed   (MouseEvent click) {} 
	public void mouseEntered   (MouseEvent click) {} 
	public void mouseExited    (MouseEvent click) {}
	
	
	
	// TODO:: KeyListener not working
	public void keyPressed(KeyEvent type) {
		int key = type.getKeyCode();
		
		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);
			
			if(tempObject.getId() == ObjectID.Player)
			{
				if(key == KeyEvent.VK_D) tempObject.setVelX(5);
				if(key == KeyEvent.VK_A) tempObject.setVelX(-5);
			}
		}	
	}

	public void keyReleased(KeyEvent type) {
		int key = type.getKeyCode();
		
		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);
		
			if(tempObject.getId() == ObjectID.Player)
			{
				if(key == KeyEvent.VK_D) tempObject.setVelX(0);
				if(key == KeyEvent.VK_A) tempObject.setVelX(0);
			}
		}
	}

	public void keyTyped(KeyEvent type) { }
}