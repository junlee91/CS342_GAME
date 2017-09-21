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
    private GameObject player = null;
    private ObjectHandler handler;
	private int healthBar;

   // --------- Motion BufferedImage ------------- //
    ImageLoader imageLoading = new ImageLoader();
    private BufferedImage MonsterRight = null;
    private BufferedImage MonsterLeft = null;
    private BufferedImage[] MonsterAttackRight = new BufferedImage[4];
    private BufferedImage[] MonsterAttackLeft = new BufferedImage[4];
    
    private ObjectMotion objectAttackLeft;
    private ObjectMotion objectAttackRight;
    private ObjectMotion objectLookLeft;
    private ObjectMotion objectLookRight;
    
    // -------------------------------------------- //

    public MonsterArcher(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;

        setHealthPoint((int)width);
	    setDamagePower(1);

        healthBar = (int)width;


        loadMotionImage();

        objectAttackRight = new ObjectMotion(10, MonsterAttackRight[0],MonsterAttackRight[1], 
        		MonsterAttackRight[2], MonsterAttackRight[3]);
        objectAttackLeft  = new ObjectMotion(10, MonsterAttackLeft[0],MonsterAttackLeft[1],
        		MonsterAttackLeft[2],MonsterAttackLeft[3]);
        objectLookLeft  = new  ObjectMotion(20,MonsterAttackLeft[0]);
        objectLookRight = new  ObjectMotion(20,MonsterAttackRight[0]);


    }

    private void loadMotionImage(){

    	MonsterAttackRight[0] = imageLoading.LoadImage("/res/EnemyWizard/Attack/RightAttack/R_Attack_1.png");
		MonsterAttackRight[1] = imageLoading.LoadImage("/res/EnemyWizard/Attack/RightAttack/R_Attack_2.png");
		MonsterAttackRight[2] = imageLoading.LoadImage("/res/EnemyWizard/Attack/RightAttack/R_Attack_3.png");
		MonsterAttackRight[3] = imageLoading.LoadImage("/res/EnemyWizard/Attack/RightAttack/R_Attack_4.png");

		MonsterAttackLeft[0] = imageLoading.LoadImage("/res/EnemyWizard/Attack/LeftAttack/L_Attack_1.png");
		MonsterAttackLeft[1] = imageLoading.LoadImage("/res/EnemyWizard/Attack/LeftAttack/L_Attack_2.png");
		MonsterAttackLeft[2] = imageLoading.LoadImage("/res/EnemyWizard/Attack/LeftAttack/L_Attack_3.png");
		MonsterAttackLeft[3] = imageLoading.LoadImage("/res/EnemyWizard/Attack/LeftAttack/L_Attack_4.png");
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

        objectAttackRight.runMotion();
        objectAttackLeft.runMotion();
        objectLookLeft.runMotion();
        objectLookRight.runMotion();

        CollisionDetection(ObjectList);
        MonsterAI();
    }

    private void releventHealth(){

		if( !isAttacked ) return;

		damaged += damagedPoint;
		
		setObjectAttacked();
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
                player = tempObject;
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
                    
                    // set look direction
                    if(tempObject.getX() - x > 0){
                    	direction = 1;
                    }
                    else{
                    	direction = -1;
                    }  
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
            handler.addObject(new FireBall(getX()-30, getY(), handler, ObjectID.FireBall, direction*8));    
            handler.addObject(new FireBall(getX()-30, getY()+10, handler, ObjectID.FireBall, direction*10));    
        }
        else{
            handler.addObject(new FireBall(getX()+width, getY(), handler, ObjectID.FireBall, direction*8));
            handler.addObject(new FireBall(getX()+width, getY()+10, handler, ObjectID.FireBall, direction*10));            
        }

        time = 0;
    }


    public void renderObject(Graphics g){

    	if(playerLeftDetected)
    	{
    		objectAttackLeft.drawMotion(g, (int)x-55, (int)y+10);
    	}
    	else if(playerRightDetected)
    	{
    		objectAttackRight.drawMotion(g, (int)x-15, (int)y+10);
    	}
    	else{
    		if(direction == -1){
	    		objectLookLeft.drawMotion(g, (int)x-55, (int)y+10);
    		} 
    		else{
    			objectLookRight.drawMotion(g, (int)x-15, (int)y+10);
    		}
    	}

        if( isAttacked )
		{
			// System.out.println("Attacked!!");
			g.setColor(Color.RED);
			g.fillRect((int)(getX()), (int)(getY()), (int)width, (int)height+20);
		}

        releventHealth();

        g.setColor(Color.gray);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)healthBar, 10);

        g.setColor(Color.green);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)(healthBar-damaged), 10);

        //g.setColor(Color.MAGENTA);
        //g.fillRect((int)x, (int)y, (int)width, (int)height);

        //Graphics2D gg = (Graphics2D)g;
		//gg.setColor(Color.MAGENTA);
        //gg.draw(getBounds());	
        //gg.draw(getVisionLeft());
        //gg.draw(getVisionRight());
    }

    public Rectangle getBoundsBottom() {
		return new Rectangle ((int)x, (int)(y+height*0.75), (int)width, (int)height/4);
	}

    public Rectangle getBounds(){
        return new Rectangle((int)x-7, (int)y, (int)width+14, (int)height);
    }

    public Rectangle getVisionLeft(){
        return new Rectangle((int)x-550,(int)y+200, (int)300, (int)height+300); 
    }

    public Rectangle getVisionRight(){
        return new Rectangle((int) ((int)x + 350),(int)y+200, (int)300, (int)height+300);        
    }
}