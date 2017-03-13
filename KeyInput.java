import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyInput implements KeyListener{
	
	ObjectHandler handler;
	
	public KeyInput(ObjectHandler handler){
		this.handler = handler;
	}

	public void keyPressed(KeyEvent type) {
		
		int key = type.getKeyCode();
		
		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);
			
			if(tempObject.getId() == ObjectID.Player)
			{
				if(key == KeyEvent.VK_D && tempObject.isGoingRight()) { 
					tempObject.setVelX(5);  
					
				}
				
				if(key == KeyEvent.VK_A && tempObject.isGoingLeft()) { 
					tempObject.setVelX(-5); 
					
				}
				
				//TODO: S and W commands
				//TODO: Need to fix gravitational motion.
				if(key == KeyEvent.VK_S )
				{
					tempObject.setPosition(5);
				}
				if(key == KeyEvent.VK_W)
				{
					tempObject.setPosition(-5);
				}
				
				
				if(key == KeyEvent.VK_SPACE && !tempObject.isJumping())
				{
					tempObject.setJumping(true);
					tempObject.setVelY(-15);
				}
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