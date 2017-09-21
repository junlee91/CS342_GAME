import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Monster extends GameObject{

    private float width = 48, height = 85;	
    private int stance = 0;
	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
    private boolean playerLeftDetected = false;
    private boolean playerRightDetected = false;
    private boolean collideWithPlayer = false;
    private boolean attackPlayer = false;
    private int time = 0;

	private ObjectHandler handler;
    private GameObject player = null;
	private int healthBar;

    // --------- Motion BufferedImage ------------- //
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage[] MonsterRight = new BufferedImage[5];
    private BufferedImage[] MonsterLeft = new BufferedImage[5];

    private BufferedImage[] MonsterAttackRight = new BufferedImage[5];
    private BufferedImage[] MonsterAttackLeft = new BufferedImage[5];

	private BufferedImage[] MonsterDamageRight = new BufferedImage[2];
	private BufferedImage[] MonsterDamageLeft  = new BufferedImage[2];
    
    private BufferedImage monsterJumpingRight = null;
    private BufferedImage monsterJumpingLeft = null;

    private ObjectMotion objectWalkRight;
    private ObjectMotion objectWalkLeft;
    private ObjectMotion objectRunRight;
    private ObjectMotion objectRunLeft;    

    private ObjectMotion objectAttackRight;
    private ObjectMotion objectAttackLeft;    

    // -------------------------------------------- //


    public Monster(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;

        setHealthPoint((int)width);
	    setDamagePower(5);

        healthBar = (int)width;

        loadMotionImage();

        objectWalkRight = new ObjectMotion(14, MonsterRight[1],MonsterRight[2],MonsterRight[3]);
        objectWalkLeft = new ObjectMotion(14, MonsterLeft[1], MonsterLeft[2],MonsterLeft[3]);

        objectRunRight = new ObjectMotion(8, MonsterRight[0], 
                                MonsterRight[1],MonsterRight[2],MonsterRight[3], MonsterRight[4]); 

        objectRunLeft = new ObjectMotion(8, MonsterLeft[0], MonsterLeft[1],MonsterLeft[2],
                                MonsterLeft[3], MonsterLeft[4]);

        objectAttackRight = new ObjectMotion(6, MonsterAttackRight[0],MonsterAttackRight[1],MonsterAttackRight[2],MonsterAttackRight[3],MonsterAttackRight[4]);

        objectAttackLeft = new ObjectMotion(6, MonsterAttackLeft[0],MonsterAttackLeft[1],MonsterAttackLeft[2],MonsterAttackLeft[3], MonsterAttackLeft[4]);

    }

    private void loadMotionImage(){
        
        MonsterRight[0] = imageLoading.LoadImage("/res/EnemyHuman/R_Motion/R_walk_1.png");
        MonsterRight[1] = imageLoading.LoadImage("/res/EnemyHuman/R_Motion/R_walk_2.png");
        MonsterRight[2] = imageLoading.LoadImage("/res/EnemyHuman/R_Motion/R_walk_3.png");
        MonsterRight[3] = imageLoading.LoadImage("/res/EnemyHuman/R_Motion/R_walk_4.png");
        MonsterRight[4] = imageLoading.LoadImage("/res/EnemyHuman/R_Motion/R_walk_5.png");

        MonsterLeft[0] = imageLoading.LoadImage("/res/EnemyHuman/L_Motion/L_walk_1.png");
        MonsterLeft[1] = imageLoading.LoadImage("/res/EnemyHuman/L_Motion/L_walk_2.png");
        MonsterLeft[2] = imageLoading.LoadImage("/res/EnemyHuman/L_Motion/L_walk_3.png");
        MonsterLeft[3] = imageLoading.LoadImage("/res/EnemyHuman/L_Motion/L_walk_4.png");
        MonsterLeft[4] = imageLoading.LoadImage("/res/EnemyHuman/L_Motion/L_walk_5.png");
        

        MonsterAttackRight[0] = imageLoading.LoadImage("/res/EnemyHuman/Attack/RightAttack/R1_attack.png");
        MonsterAttackRight[1] = imageLoading.LoadImage("/res/EnemyHuman/Attack/RightAttack/R2_attack.png");
        MonsterAttackRight[2] = imageLoading.LoadImage("/res/EnemyHuman/Attack/RightAttack/R3_attack.png");
        MonsterAttackRight[3] = imageLoading.LoadImage("/res/EnemyHuman/Attack/RightAttack/R4_attack.png");   
        MonsterAttackRight[4] = imageLoading.LoadImage("/res/EnemyHuman/Attack/RightAttack/R5_attack.png");      

        MonsterAttackLeft[0] = imageLoading.LoadImage("/res/EnemyHuman/Attack/LeftAttack/L1_attack.png");
        MonsterAttackLeft[1] = imageLoading.LoadImage("/res/EnemyHuman/Attack/LeftAttack/L2_attack.png");
        MonsterAttackLeft[2] = imageLoading.LoadImage("/res/EnemyHuman/Attack/LeftAttack/L3_attack.png");
        MonsterAttackLeft[3] = imageLoading.LoadImage("/res/EnemyHuman/Attack/LeftAttack/L4_attack.png");
        MonsterAttackLeft[4] = imageLoading.LoadImage("/res/EnemyHuman/Attack/LeftAttack/L5_attack.png");
        
		MonsterDamageLeft[0]  = imageLoading.LoadImage("/res/EnemyHuman/Damaged/L_DamageStand.png");
		MonsterDamageLeft[1]  = imageLoading.LoadImage("/res/EnemyHuman/Damaged/L_DamageAttack.png");
        MonsterDamageRight[0] = imageLoading.LoadImage("/res/EnemyHuman/Damaged/R_DamageStand.png");
        MonsterDamageRight[1] = imageLoading.LoadImage("/res/EnemyHuman/Damaged/R_DamageAttack.png");
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

        objectWalkLeft.runMotion();
        objectWalkRight.runMotion();
        objectRunLeft.runMotion();
        objectRunRight.runMotion();
        objectAttackLeft.runMotion();
        objectAttackRight.runMotion();

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
                    direction = -1;
                }
                else
                    playerLeftDetected = false;
                
                if( getVisionRight().intersects(tempObject.getBounds() )){
                    //System.out.println("Player detected Right!!!");    
                    playerRightDetected = true;       
                    direction = 1;         
                }
                else
                    playerRightDetected = false;
                
              
                if( getBounds().intersects(tempObject.getBounds())){
                    collideWithPlayer = true;
                }
                else
                    collideWithPlayer = false;

                if( getAttackBoundsLeft().intersects(tempObject.getBounds()))
                {
                    attackPlayer = true;
                    player = tempObject;
                }
                else if(getAttackBoundsRight().intersects(tempObject.getBounds()))
                {
                    attackPlayer = true;     
                    player = tempObject;                                         
                }
                else
                {
                    attackPlayer = false;
                    time = 0;
                }
            }

        }
    }

    private void MonsterAI(){

        if( playerLeftDetected )
        {
        	stance = 0;
            velX = (float)(-2); //direction = -1; 
        }
        else if( playerRightDetected )
        {
        	stance = 0;
            velX = (float)(2); //direction = 1;         
        }


        if( attackPlayer )
        {
        	stance = 1;
            time++;
            if( time % 50 == 0){
                if(direction == 1)
                {
                    System.out.println("Monster attack Right!!");   

                }
                else if(direction == -1)
                {             
                    System.out.println("Monster attack left!!");
                }

                player.attacked( DamagePower );
                
                if( player.isDead() )
                {
                    System.out.println("Game Over!!");
                }
            }
        }

        if( collideWithPlayer )
        {
        	stance = 1;
            velX = 0;
            return;
        }

        int move = (int)(Math.random() * 1000);

        if( move < 10)
        {
        	stance = 0;
            velX = (float)1;
        }
        else if( move > 990)
        {
        	stance = 0;
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

        if( attackPlayer )
        {
            if( direction == 1)
            {
                //  attack Right
                objectAttackRight.drawMotion(g, (int)x+3, (int)y);
            }
            else if( direction == -1)
            {
                //  attack Left
                objectAttackLeft.drawMotion(g, (int)x-28, (int)y);
            }
        }
        else if( playerRightDetected )
        {
            objectRunRight.drawMotion(g, (int)x, (int)y+7);
        }
        else if( playerLeftDetected )
        {
            objectRunLeft.drawMotion(g, (int)x, (int)y+7);            
        }
        else
        {
        	stance = 0;
            if( direction == 1 )
            {
                //g.drawImage(MonsterRight[0], (int)x, (int)y, null);
                objectWalkRight.drawMotion(g,(int)x, (int)y+7);
            }
            else if( direction == -1 )
            {
                //g.drawImage(MonsterLeft[0], (int)x, (int)y, null);     
                objectWalkLeft.drawMotion(g,(int)x, (int)y+7);                       
            }
        
        }

        if( isAttacked )
		{
			if(direction == 1) // facing right
			{
				switch(stance)
				{
					case 1: g.drawImage(MonsterDamageRight[stance], (int)x+3, (int)y, null); break;
					default: g.drawImage(MonsterDamageRight[stance], (int)x, (int)y, null); break;
				}
			}
			else  // facing left 
			{ 
				switch(stance)
				{
					case 1: g.drawImage(MonsterDamageLeft[stance], (int)x-28, (int)y, null); break;
					default: g.drawImage(MonsterDamageLeft[stance], (int)x, (int)y, null); break;
				}
			}

			System.out.println("AHhh!!");
			// g.setColor(Color.RED);
			// g.fillRect((int)(getX()), (int)(getY()), (int)width, (int)height);
		}

        releventHealth();

        g.setColor(Color.gray);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)healthBar, 10);

        g.setColor(Color.green);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)(healthBar-damaged), 10);
        
        Graphics2D gg = (Graphics2D)g;
		//gg.setColor(Color.BLUE);
        //gg.draw(getBounds());	
        //gg.draw(getVisionLeft());
        //gg.draw(getVisionRight());
        //gg.draw(getBoundsLeft());
		//gg.draw(getBoundsRight());
        //gg.draw(getBoundsTop());
        //gg.draw(getBoundsBottom());

        gg.setColor(Color.RED);
        //gg.draw(getAttackBoundsLeft());
        //gg.draw(getAttackBoundsRight());
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
		return new Rectangle((int) ((int)x + width),(int)y+5, (int)20, (int)height-10); 
	}

	public Rectangle getAttackBoundsLeft(){
		return new Rectangle((int)x-20,(int)y+5, (int)20, (int)height-10); 
	}


}