import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Monster extends GameObject{

    private float width = 32, height = 48;	

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
    private boolean playerLeftDetected = false;
    private boolean playerRightDetected = false;
    private boolean collideWithPlayer = false;

	private ObjectHandler handler;

    // --------- Motion BufferedImage ------------- //
    ImageLoader imageLoading = new ImageLoader();	


    // -------------------------------------------- //


    public Monster(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;

        setHealthPoint(50);
	    setDamagePower(1);

        loadMotionImage();
    }

    private void loadMotionImage(){
        //imageLoading.LoadImage("/res/.............");
    }

    public void Update(LinkedList<GameObject> ObjectList) {
        x += velX;
        y += velY;

        if( velX < 0 ) 		direction = -1;		// facing Left
		else if( velX > 0)	direction = 1;		// facing Right

        if(falling){
            velY += gravity;

            if( velY > MAX_SPEED )
				velY = MAX_SPEED;
        }

        CollisionDetection(ObjectList);
        MonsterAI();
    }

    public void CollisionDetection(LinkedList<GameObject> ObjectList){
        for(int i = 0; i < handler.ObjectList.size(); i++ ){
            GameObject tempObject = handler.ObjectList.get(i);

            if(tempObject.getId() == ObjectID.BottomLayer || tempObject.getId() == ObjectID.SpecialLayer){
				if( getBoundsBottom().intersects(tempObject.getBounds()) ){
					y = tempObject.getY() - height;
					velY = 0;

					falling = false;
					jumping = false;
			
				} else {
					falling = true;
				}

                if( getBoundsTop().intersects(tempObject.getBounds()) ){			
					y = tempObject.getY() + 40;
					velY = 0;
				} 
				
				if(getBoundsRight().intersects(tempObject.getBounds())){	   	

					x = tempObject.getX() - width;		
                    velX *= -1;		
				}
				
				else if(getBoundsLeft().intersects(tempObject.getBounds())){	

					x = tempObject.getX() + 35;
                    velX *= -1;		      
				}
			}

            if(tempObject.getId() == ObjectID.Player )
            {
                if( getVisionLeft().intersects(tempObject.getBounds() )){
                    //System.out.println("Player detected Left!!!");
                    playerLeftDetected = true;
                }
                else if( getVisionRight().intersects(tempObject.getBounds() )){
                    //System.out.println("Player detected Right!!!");    
                    playerRightDetected = true;                
                }
                else
                {
                    playerLeftDetected = false;
                    playerRightDetected = false;
                }
              
                if( getBounds().intersects(tempObject.getBounds())){
                    collideWithPlayer = true;
                }
                else
                    collideWithPlayer = false;

                if( getAttackBoundsLeft().intersects(tempObject.getBounds()))
                {
                    //System.out.println("Monster attack left!!");

                    tempObject.attacked( DamagePower );
                    if( tempObject.isDead() ){
                        //System.out.println("Game Over!!");
                        //System.exit(0);
                    }
                }
                else if(getAttackBoundsRight().intersects(tempObject.getBounds()))
                {
                    //System.out.println("Monster attack Right!!");     

                    tempObject.attacked( DamagePower );        
                    if( tempObject.isDead() ){
                        //System.out.println("Game Over!!");
                        //System.exit(0);
                    }            
                }
            }

        }
    }

    private void MonsterAI(){
        if( collideWithPlayer )
        {
            velX = 0;
            return;
        }

        int move = (int)(Math.random() * 1000);

        if( playerLeftDetected )
        {
            velX = (float)(-2);
        }
        else if( playerRightDetected )
        {
            velX = (float)(2);
        }
        else if( move < 10)
        {
            velX = (float)1;
        }
        else if( move > 990)
        {
            velX = (float)(-1);
        }
        else if( move == 10 && falling)
        {
            falling = false;
            velX = (float)2;
            velY = (float)(-10);
        }
        else if( move == 20 && falling)
        {
            falling = false;                        
            velX = (float)(-2);
            velY = (float)(-10);
        }
    }

    public void renderObject(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, (int)width, (int)height);

        Graphics2D gg = (Graphics2D)g;
		gg.setColor(Color.BLUE);
        gg.draw(getBounds());	
        gg.draw(getVisionLeft());
        gg.draw(getVisionRight());
        // gg.draw(getBoundsLeft());
		// gg.draw(getBoundsRight());
        // gg.draw(getBoundsTop());
        // gg.draw(getBoundsBottom());

        // gg.setColor(Color.RED);
        // gg.draw(getAttackBoundsLeft());
        // gg.draw(getAttackBoundsRight());
    }

    public Rectangle getBoundsBottom() {
		return new Rectangle ((int)x, (int)(y+height*0.75), (int)width, (int)height/4);
	}

    public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int)x+(width/2)-((width/2)/2)),(int)y, (int)width/2, (int)height/2); 
	}

    public Rectangle getBoundsRight() {		
		return new Rectangle((int) ((int)x+width-5),(int)y+5, (int)5, (int)height-10); 
	}
	
    public Rectangle getBoundsLeft() {		
		return new Rectangle((int)x,(int)y+5, (int)5, (int)height-10); 
	}
    
    public Rectangle getBounds(){
        return new Rectangle((int)x-7, (int)y, (int)width+14, (int)height);
    }

    public Rectangle getVisionLeft(){
        return new Rectangle((int)x-150,(int)y+20, (int)150, (int)height-30); 
    }

    public Rectangle getVisionRight(){
        return new Rectangle((int) ((int)x + width),(int)y+20, (int)150, (int)height-30);        
    }

    public Rectangle getAttackBoundsRight(){
		return new Rectangle((int) ((int)x + width-10),(int)y+5, (int)40, (int)height-10); 
	}

	public Rectangle getAttackBoundsLeft(){
		return new Rectangle((int)x-30,(int)y+5, (int)40, (int)height-10); 
	}


}