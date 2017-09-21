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

import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;

public class Game extends JPanel implements ActionListener, MouseListener
{
	private Timer refresh = new Timer(10, this);  // 360 frames per seconds
	public static int WIDTH = 1800, HEIGHT = 750; // size of frame

	BufferedImage showControls;

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
	 *      hierarchy: Game -> handler -> GameObject 	 *     
	 */
	
	public static BufferedImage City = null;
	static int controlXPos;
	private JLabel dispControlBtn;
	private Thread thread;
	private MusicPlayer bgm;
	public ImageIcon image;
	private Boolean controls = false;

	//public Image contIcon;
	public Game()
	{
		// insert JPanel Game screen here!!
		bgm = new MusicPlayer("/res/audio/MapleStory_BGM_Dragon_Dream.wav", 
							  "/res/audio/Talesweaver Title.wav");	// add multiple music args
		thread = new Thread(bgm);				// thread for MusicPlayer
		camera = new Camera(0,0);				
		handler = new ObjectHandler();			// create handler for objects
		image = new ImageIcon("res/Icon/ControllerIcon.png");
		
		JLabel nameLabel = new JLabel();
		nameLabel.setIcon(image);
		nameLabel.setOpaque(false);

		nameLabel.setHorizontalAlignment(JLabel.RIGHT);
		nameLabel.addMouseListener(new MouseAdapter() 
		{
			public void mouseEntered(MouseEvent arg0) {
				controls = true;
			}
			public void mouseExited(MouseEvent arg0) {
				controls = false;
			}
		});

		System.out.println("control set off\n");
		this.add(nameLabel);
		
		try
		{
			showControls = ImageIO.read(new File("res/Icon/DisplayController.png"));
		}
		catch(IOException imageError){}
	
		handler.SetGameLayer();
		
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addKeyListener(new KeyInput(handler));
		
		thread.start();	// music thread
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
		
		graphicSetting.translate(camera.getX(), camera.getY());			//------------- start	


		for(int x = 0; x < City.getWidth() * 3; x += City.getWidth())
		{
			g.drawImage(City, x, -50, this);
		}

		
		handler.renderObject(g);	

		graphicSetting.translate(-camera.getX(), -camera.getY());		//--------------- end

		if(controls) g.drawImage(showControls, 400,50, this);

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
