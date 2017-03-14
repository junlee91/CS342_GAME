import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class Game_Container{

	public static void main(String[] args) {
		JFrame game_window = new JFrame(); 
		Game manager = new Game();
		manager.setBackground(Color.BLACK);
		
		game_window.add(manager);
		game_window.setVisible(true);
		game_window.setResizable(false);
		//game_window.setLocationRelativeTo(null);	// SET by JUN: This does not fit into my screen.
		game_window.setTitle("D.E.X.-001");
		game_window.setSize(1800, 750);
		game_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public boolean isGameManagerCreated()
	{
		if(!manager) return false;
		return true;
	}
}
