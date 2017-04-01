import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class MonsterArcher extends GameObject{
    private float width = 32, height = 96;	

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
    private boolean playerLeftDetected = false;
    private boolean playerRightDetected = false;
    private boolean collideWithPlayer = false;

    private int time = 0;

    private ObjectHandler handler;

   // --------- Motion BufferedImage ------------- //
    ImageLoader imageLoading = new ImageLoader();
    private BufferedImage MonsterRight = null;
    private BufferedImage MonsterLeft = null;
    private BufferedImage[] MonsterAttackRight = new BufferedImage[4];
    private BufferedImage[] MonsterAttackLeft = new BufferedImage[4];
    
    private ObjectMotion objectAttackRight;
    private ObjectMotion objectAttackLeft;
    
    // -------------------------------------------- //

    public MonsterArcher(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;

        setHealthPoint(50);
	    setDamagePower(1);

        loadMotionImage();
    }

    private void loadMotionImage(){
        //imageLoading.LoadImage("/res/.............");
    }

    public void Update(LinkedList<GameObject> ObjectList){
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
            }

        }
    }

    private void MonsterAI(){

        if( playerLeftDetected )
        {
            direction = -1;
            MonsterShoot();
        }
        else if( playerRightDetected )
        {
            direction = 1;
            MonsterShoot();
        }
    
    }

    private void MonsterShoot()
    {
        time++;

        if( time%100 != 0 ) return;

        if( direction == -1){
            handler.addObject(new Arrow(getX()-30, getY(), handler, ObjectID.FireBall, direction*10));     // temporay arrow, needs to be changed

        }
        else{ 
            handler.addObject(new Arrow(getX()+width, getY(), handler, ObjectID.FireBall, direction*10));
            
        }

        time = 0;
    }


    public void renderObject(Graphics g){
        g.setColor(Color.MAGENTA);
        g.fillRect((int)x, (int)y, (int)width, (int)height);

        // Graphics2D gg = (Graphics2D)g;
		// gg.setColor(Color.MAGENTA);
        // gg.draw(getBounds());	
        // gg.draw(getVisionLeft());
        // gg.draw(getVisionRight());
    }

    public Rectangle getBoundsBottom() {
		return new Rectangle ((int)x, (int)(y+height*0.75), (int)width, (int)height/4);
	}

    public Rectangle getBounds(){
        return new Rectangle((int)x-7, (int)y, (int)width+14, (int)height);
    }

    public Rectangle getVisionLeft(){
        return new Rectangle((int)x-500,(int)y+100, (int)150, (int)height+500); 
    }

    public Rectangle getVisionRight(){
        return new Rectangle((int) ((int)x + 500),(int)y+100, (int)150, (int)height+500);        
    }
}