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

	private ObjectHandler handler;


    public Monster(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;
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

        }
    }

    private void MonsterAI(){
        int move = (int)(Math.random() * 1000);

        if( move < 10)
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

        // Graphics2D gg = (Graphics2D)g;
		// gg.setColor(Color.BLUE);
        // gg.draw(getBounds());	
        // gg.draw(getBoundsLeft());
		// gg.draw(getBoundsRight());
        // gg.draw(getBoundsTop());
        // gg.draw(getBoundsBottom());
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
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
    }

}