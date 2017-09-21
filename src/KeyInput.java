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
				// check if the player is alive over here 
				if(!tempObject.isDead()){

					if(key == KeyEvent.VK_D && tempObject.isGoingRight()) { 
						tempObject.setVelX(5);  
						tempObject.setAttacking(false);
					}
					
					if(key == KeyEvent.VK_A && tempObject.isGoingLeft()) { 
						tempObject.setVelX(-5); 
						tempObject.setAttacking(false);			
					}
					
					
					if(key == KeyEvent.VK_S )
					{
						tempObject.setPickUp(true);
						// temporary here used for charging	
					}

					

					if((key == KeyEvent.VK_W || key == KeyEvent.VK_SPACE ) && !tempObject.isJumping() && tempObject.getVelY() <= 5 )
					{
						tempObject.setJumping(true);
						tempObject.setVelY(-12);
					}

					if( key == KeyEvent.VK_SPACE || key == KeyEvent.VK_W )
					{
						if(tempObject.isJumping()){
							if(tempObject.isBoostAvailable()){
								tempObject.setVelY(-12);
								tempObject.boost();
							}
						}

					}

					if(key == KeyEvent.VK_K)
					{
						tempObject.setAttacking(true);
					}

					if(key == KeyEvent.VK_L)
					{
						if( tempObject.HasBow() && tempObject.getVelX() == 0 && tempObject.isArrowAvailable() ){
							tempObject.setShooting(true);
							tempObject.MinusArrow();
							System.out.println("Shoot!");

							if( tempObject.getDirection() > 0)
								handler.addObject(new Arrow( tempObject.getX()+40, tempObject.getY()+50, handler, ObjectID.Arrow, tempObject.getDirection()*10));
							else
								handler.addObject(new Arrow( tempObject.getX()-10, tempObject.getY()+50, handler, ObjectID.Arrow, tempObject.getDirection()*10));							
						}			
					}
				}
			}
		}

		if( key == KeyEvent.VK_R )
		{
			handler.reset();
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
				if(key == KeyEvent.VK_D) { tempObject.setVelX(0); }
				if(key == KeyEvent.VK_A) { tempObject.setVelX(0); }
				if(key == KeyEvent.VK_S) tempObject.setPickUp(false);
				
				//tempObject.setAttacking(false);
				tempObject.setShooting(false);
			}
		}
	}

	public void keyTyped(KeyEvent type) { }

}
