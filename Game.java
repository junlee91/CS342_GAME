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
import java.awt.image.BufferedImage;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Color;

public class Game extends JPanel implements ActionListener, MouseListener
{
	private Timer refresh = new Timer(10, this);  // 360 frames per seconds
	public static int WIDTH = 1800, HEIGHT = 750; // size of frame
	
	// This is for TDD unit testing <Note this needs to change -J>
	public Test test;

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
	
	public static BufferedImage City = null;
	private JButton dispControlBtn;
	private Thread thread;
	private MusicPlayer bgm;
	
	public Game()
	{
		// insert JPanel Game screen here!!
		bgm = new MusicPlayer("/res/audio/MapleStory_BGM_Dragon_Dream.wav", 
							  "/res/audio/Talesweaver Title.wav");	// add multiple music args
		thread = new Thread(bgm);				// thread for MusicPlayer

		camera = new Camera(0,0);				
		handler = new ObjectHandler();			// create handler for objects
		//unitTest();

		dispControlBtn = buttonCreation("/res/Icon/ControllerIcon.png");
		// dispControlBtn.addMouseListener(new MouseListener()
		// {
		// 	public void mouseEntered(MouseEvent e)
		// 	{

		// 	}
		// });

		
		this.add(dispControlBtn);

		handler.SetGameLayer();
		
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(new KeyInput(handler));
		
		thread.start();
		refresh.start();
	}


	JButton buttonCreation(String image)
	{
		  JButton temp = new JButton(new ImageIcon(image));
	      temp.setContentAreaFilled(false);
	      temp.setBorder(null);
	      temp.setFocusPainted(false);
	      temp.setBorderPainted(false);
	      temp.setBackground(Color.black);
	      return temp;
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
		
		graphicSetting.translate(camera.getX(), camera.getY());			//------------- start	


		for(int x = 0; x < City.getWidth() * 3; x += City.getWidth())
		{
			g.drawImage(City, x, -50, this);
		}

		
		handler.renderObject(g);	

		graphicSetting.translate(-camera.getX(), -camera.getY());		//--------------- end
	}

	public void actionPerformed(ActionEvent event){

		// this calls the paintComponent and refreshes the screen
		repaint();  
	}	

	
	public void mouseReleased  (MouseEvent click) {}
	public void mouseClicked   (MouseEvent click) {}
	public void mousePressed   (MouseEvent click) {} 
	
	
	public void mouseEntered   (MouseEvent click) {

	} 
	
	public void mouseExited    (MouseEvent click) {

	}


	public void unitTest()
	{
		Test test = new Test(1);
		
		if(test != null)
		{
			test.isHandlerCreated(handler);
			test.isCameraCreated(camera);
			//test.isImgLoaderCreated(imageLoading);
		}
		else System.out.println("unit tester not found\n");
	}
}
