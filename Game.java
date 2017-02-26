import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;
import java.awt.Color;
import java.util.Scanner;


public class Game extends JPanel implements ActionListener, MouseListener
{
	private Timer refresh = new Timer(60, this);  // 60 frames per sec


	public Game()
	{
		// insert JPanel Game screen here!!
		



		refresh.start();
	}


	public void paintComponent(Graphics g)
	{
		// objects left here will be "Redrawn"
		// thus character objects should be called here.


		System.out.println("TO SHOW THAT THIS IS BEING CALLED!!!");
		super.paintComponent(g);
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