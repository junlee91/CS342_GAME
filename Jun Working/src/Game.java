import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;


public class Game extends JPanel implements ActionListener, MouseListener
{
	private Timer refresh = new Timer(10, this);  // 360 frames per seconds
	public static int WIDTH = 1000, HEIGHT = 750; // size of frame

	Camera camera;
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
		camera = new Camera(0,0);
				
		handler.addObject(new Player(100, 100, handler, ObjectID.Player));	// one player (blue rectangle)
		
		handler.CreateBottomLayer();			// bottom layer created by handler
		
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(new KeyInput(handler));
		
		refresh.start();
	}

	public void paintComponent(Graphics g)
	{
		// objects left here will be "Redrawn"
		// thus character objects should be called here.
		
		//System.out.println("TO SHOW THAT THIS IS BEING CALLED!!!");
		super.paintComponent(g);
		
		handler.Update();	
		
		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);
			if(tempObject.getId() == ObjectID.Player)
			{				
				camera.renderObject(tempObject);
			}
		}
		
		
		Graphics2D graphicSetting = (Graphics2D) g;
		
		graphicSetting.translate(camera.getX(), camera.getY());		
		handler.renderObject(g);		
		graphicSetting.translate(-camera.getX(), -camera.getY());		
	}

	public void actionPerformed(ActionEvent event){

		
		// this calls the paintComponent and refreshes the screen
		repaint();  
	}	
	
	
	public void mouseReleased  (MouseEvent click) {}
	public void mouseClicked   (MouseEvent click) {}
	public void mousePressed   (MouseEvent click) {} 
	public void mouseEntered   (MouseEvent click) {} 
	public void mouseExited    (MouseEvent click) {}
	
}