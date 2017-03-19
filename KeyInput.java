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
				
				
				if(key == KeyEvent.VK_S )
				{
					tempObject.setPickUp(true);
				}
				
				if(key == KeyEvent.VK_W && !tempObject.isJumping() && tempObject.getVelY() <= 5 )
				{
					tempObject.setJumping(true);
					tempObject.setVelY(-15);
				}

				if(key == KeyEvent.VK_K)
				{
					tempObject.setAttacking(true);
				}

				if(key == KeyEvent.VK_L)
				{
					if( tempObject.HasBow() ){
						tempObject.setShooting(true);
						handler.addObject(new Arrow( tempObject.getX(), tempObject.getY()+30, handler, ObjectID.Arrow, tempObject.getDirection()*10));
					}			
				}
			}
		}
		if(key == KeyEvent.VK_ESCAPE){
			System.exit(0);
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
				if(key == KeyEvent.VK_S) tempObject.setPickUp(false);
				
				tempObject.setAttacking(false);
				tempObject.setShooting(false);
			}
		}
	}

	public void keyTyped(KeyEvent type) { }

}
