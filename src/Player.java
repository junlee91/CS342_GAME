import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;


public class Player extends GameObject{

	private float width = 48, height = 69;
	private int stance = 1; 	

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
	private final float BOOST_MAX = width;
	
	private boolean dead = false;
	private boolean deathImageSelected = false;

	private ObjectHandler handler;
	private int healthBar;
	private int boostBar;

	Color lightBlue = new Color(0,0,182,155);

	//------------ Motion arrayList  --------------//
	ImageLoader imageLoading = new ImageLoader();	
	//private BufferedImage[] characterStanding = new BufferedImage[2];
	private BufferedImage[] characterRight = new BufferedImage[4];
	private BufferedImage[] characterLeft = new BufferedImage[4];
	private BufferedImage[] characterAttackRight = new BufferedImage[4];
	private BufferedImage[] characterAttackLeft = new BufferedImage[4];	
	private BufferedImage characterJumpingRight = null;
	private BufferedImage characterJumpingLeft = null;
	private BufferedImage characterCrouchRight = null;
	private BufferedImage characterCrouchLeft = null;

	private BufferedImage[] characterGNDShootingLeft = new BufferedImage[1];
	private BufferedImage[] characterGNDShootingRight = new BufferedImage[1];	
	private BufferedImage[] characterSKYShootingLeft = new BufferedImage[1];
	private BufferedImage[] characterSKYShootingRight = new BufferedImage[1];

	private BufferedImage[] characterDamagedRight = new BufferedImage[4];
	private BufferedImage[] characterDamagedLeft  = new BufferedImage[4];

	private BufferedImage[] characterDeathLeft  = new BufferedImage[2];
	private BufferedImage[] characterDeathRight = new BufferedImage[2];

	private ObjectMotion playerWalkRight;
	private ObjectMotion playerWalkLeft;
	//private ObjectMotion playerStand;
	private ObjectMotion playerAttackRight;
	private ObjectMotion playerAttackLeft;

	private ObjectMotion playerGNDShootRight;
	private ObjectMotion playerGNDShootLeft;

	private ObjectMotion playerSKYShootRight;
	private ObjectMotion playerSKYShootLeft;
	//--------------------------------------------//

	public Player(float x, float y, ObjectHandler handler, ObjectID id) {
		super(x, y, id);
		this.handler = handler;

		healthBar = (int)width;
		boostBar = (int)width;
		
		setHealthPoint((int)width);
	    setDamagePower(15);

		loadMotionImage();

		playerWalkRight = new ObjectMotion(5, characterRight[0], characterRight[1],
				characterRight[2], characterRight[3]);
		playerWalkLeft = new ObjectMotion(5, characterLeft[0], characterLeft[1], 
				characterLeft[2], characterLeft[3]);
		//playerStand = new ObjectMotion(10, characterStanding[0], characterStanding[1]);
		
		playerAttackRight = new ObjectMotion(4, characterAttackRight[0],characterAttackRight[1],
				characterAttackRight[2], characterAttackRight[3]);
		playerAttackLeft = new ObjectMotion(4, characterAttackLeft[0],characterAttackLeft[1],
				characterAttackLeft[2], characterAttackLeft[3]);

		playerGNDShootLeft = new ObjectMotion(7, characterGNDShootingLeft[0]);
		playerGNDShootRight = new ObjectMotion(7, characterGNDShootingRight[0]);
		playerSKYShootLeft = new ObjectMotion(7, characterSKYShootingLeft[0]);
		playerSKYShootRight = new ObjectMotion(7, characterSKYShootingRight[0]);
	}

	private void loadMotionImage(){
		characterRight[0] = imageLoading.LoadImage("/res/Hero/Walk Right/_R1.png");
		characterRight[1] = imageLoading.LoadImage("/res/Hero/Walk Right/_R2.png");
		characterRight[2] = imageLoading.LoadImage("/res/Hero/Walk Right/_R3.png");
		characterRight[3] = imageLoading.LoadImage("/res/Hero/Walk Right/_R4.png");

		characterLeft[0] = imageLoading.LoadImage("/res/Hero/Walk Left/_L1.png");
		characterLeft[1] = imageLoading.LoadImage("/res/Hero/Walk Left/_L2.png");
		characterLeft[2] = imageLoading.LoadImage("/res/Hero/Walk Left/_L3.png");
		characterLeft[3] = imageLoading.LoadImage("/res/Hero/Walk Left/_L4.png");

		// characterStanding[0] = imageLoading.LoadImage("/res/Hero/Stand/stand_2.png");
		// characterStanding[1] = imageLoading.LoadImage("/res/Hero/Stand/stand_3.png");

		characterJumpingRight = imageLoading.LoadImage("/res/Hero/Jump/_J2.png");
		characterJumpingLeft  = imageLoading.LoadImage("/res/Hero/Jump/JL2.png");

		characterCrouchRight = imageLoading.LoadImage("/res/Hero/Pick Up/Crouch_R.png");
		characterCrouchLeft  = imageLoading.LoadImage("/res/Hero/Pick Up/Crouch_L.png");

		characterAttackRight[0] = imageLoading.LoadImage("/res/Hero/Attack/Sword/R_Attack1.png");
		characterAttackRight[1] = imageLoading.LoadImage("/res/Hero/Attack/Sword/R_Attack2.png");
		characterAttackRight[2] = imageLoading.LoadImage("/res/Hero/Attack/Sword/R_Attack3.png");
		characterAttackRight[3] = imageLoading.LoadImage("/res/Hero/Attack/Sword/R_Attack4.png");
		
		characterAttackLeft[0] = imageLoading.LoadImage("/res/Hero/Attack/Sword/L_Attack1.png");
		characterAttackLeft[1] = imageLoading.LoadImage("/res/Hero/Attack/Sword/L_Attack2.png");
		characterAttackLeft[2] = imageLoading.LoadImage("/res/Hero/Attack/Sword/L_Attack3.png");
		characterAttackLeft[3] = imageLoading.LoadImage("/res/Hero/Attack/Sword/L_Attack4.png");

		characterGNDShootingLeft[0] = imageLoading.LoadImage("/res/Hero/Attack/CrossBow/L_Gnd_shot1.png");
		characterGNDShootingRight[0] = imageLoading.LoadImage("/res/Hero/Attack/CrossBow/R_Gnd_shot1.png");

		characterSKYShootingLeft[0] = imageLoading.LoadImage("/res/Hero/Attack/CrossBow/L_Jmp_shot1.png");
		characterSKYShootingRight[0] = imageLoading.LoadImage("/res/Hero/Attack/CrossBow/R_Jmp_shot1.png");

		characterDamagedRight[0] = imageLoading.LoadImage("/res/Hero/Damage/R_damageSit.png");
		characterDamagedRight[1] = imageLoading.LoadImage("/res/Hero/Damage/R_damageStand.png");
		characterDamagedRight[2] = imageLoading.LoadImage("/res/Hero/Damage/R_damageAttack.png");
		characterDamagedRight[3] = imageLoading.LoadImage("/res/Hero/Damage/R_damageJump.png");
		
		characterDamagedLeft[0] = imageLoading.LoadImage("/res/Hero/Damage/L_damageSit.png");
		characterDamagedLeft[1] = imageLoading.LoadImage("/res/Hero/Damage/L_damageStand.png");
		characterDamagedLeft[2] = imageLoading.LoadImage("/res/Hero/Damage/L_damageAttack.png");
		characterDamagedLeft[3] = imageLoading.LoadImage("/res/Hero/Damage/L_damageJump.png");

		characterDeathLeft[0] = imageLoading.LoadImage("/res/Hero/Death/L_deathDown.png");
		characterDeathLeft[1] = imageLoading.LoadImage("/res/Hero/Death/L_deathUp.png");
		
		characterDeathRight[0] = imageLoading.LoadImage("/res/Hero/Death/R_deathDown.png");
		characterDeathRight[1] = imageLoading.LoadImage("/res/Hero/Death/R_deathUp.png");
	}
	

	public void Update(LinkedList<GameObject> ObjectList) {

		x += velX;
		y += velY;

		if( velX < 0 ) 		direction = -1;		// facing Left
		else if( velX > 0)	direction = 1;		// facing Right

		if( falling ){
			velY += gravity;

			if( velY > MAX_SPEED )
				velY = MAX_SPEED;
		}
		
		if(!jumping)
		{
			boost += 0.2;

			if(boost > BOOST_MAX)
			{
				boost = BOOST_MAX;
			}
		}

		CollisionDetection(ObjectList);

		playerWalkRight.runMotion();
		playerWalkLeft.runMotion();
		// playerStand.runMotion();
		
		playerAttackRight.runMotion();
		playerAttackLeft.runMotion();

		playerGNDShootLeft.runMotion();
		playerGNDShootRight.runMotion();
		playerSKYShootLeft.runMotion();
		playerSKYShootRight.runMotion();
	}

	private void releventHealth(){

		if( !isAttacked ) return;

		damaged += damagedPoint;
		
		setObjectAttacked();
	}

	// REMINDER!!: CollisionDetection is subject to change (different character)
	public void CollisionDetection(LinkedList<GameObject> ObjectList){

		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);

			if(tempObject.getId() == ObjectID.BottomLayer){
				
				if( ( getBoundsBottom().intersects(tempObject.getBounds() ) )){
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
					//System.out.println("Collide Right side!");
					x = tempObject.getX() - width;					
					this.setGoingRight(false);
				}
				
				else if(getBoundsLeft().intersects(tempObject.getBounds())){	
					//System.out.println("Collide Left side!");
					x = tempObject.getX() + 35;
					this.setGoingLeft(false);
				}
				else
				{
					this.setGoingLeft(true);
					this.setGoingRight(true);
				}
			}

			if(tempObject.getId() == ObjectID.Sword){
				if( getBounds().intersects(tempObject.getBounds()))
				{
					if( pickUp && !hasSword ){
						System.out.println("Picked up SWORD!!!!!!!!");
						
						tempObject.setIsPickedUp( true );
						hasSword = true;
					}
				}
			}
			if(tempObject.getId() == ObjectID.Bow){
				if( getBounds().intersects(tempObject.getBounds()))
				{
					if( pickUp && !hasBow ){
						System.out.println("Picked up BOW!!!!!!");
						handler.removeObject( tempObject );
						setArrow(10);							// set 10 arrows
						hasBow = true;
					}
				}
			}

			if(tempObject.getId() == ObjectID.Potion ){
				if( getBounds().intersects(tempObject.getBounds()))
				{
					if( pickUp ){
						System.out.println("Potion !!!!");
						handler.removeObject( tempObject );
						
						damaged -= 10;

						if(healthBar - damaged >= (int)width)
						{
							damaged = 0;
							healthBar = (int)width;
						}
						else
						{
							setHeal(10);
						}

					}
				}
			}

			if(tempObject.getId() == ObjectID.Monster){
				
				if( isAttacking && hasSword )
				{
					if( direction == 1)
					{
						if( getAttackBoundsRight().intersects(tempObject.getBounds()))
						{
							if( !playerAttackRight.isMotionPerformed() ){
								System.out.println("Attack Right!");
								tempObject.attacked( DamagePower );
								playerAttackRight.setMotionPerformed(true);
							}

							if( tempObject.isDead() ){
								handler.removeObject( tempObject );
								handler.KillMonster();
							}
							
						}
					}
					else if( direction == -1)
					{
						if( getAttackBoundsLeft().intersects(tempObject.getBounds()))
						{
							if( !playerAttackLeft.isMotionPerformed() ){
								System.out.println("Attack Left!");							
								tempObject.attacked( DamagePower );
								playerAttackLeft.setMotionPerformed(true);
							}
							
							if( tempObject.isDead() ){							
								handler.removeObject( tempObject );
								handler.KillMonster();								
							}
						}
					}
				}
			}

			if(tempObject.getId() == ObjectID.MonsterArcher ){
				
				if( isAttacking && hasSword )
				{
					if(direction == 1)
					{
						if( getAttackBoundsRight().intersects(tempObject.getBounds()))
						{
							if( !playerAttackRight.isMotionPerformed() ){
								System.out.println("Attack Right!");
								tempObject.attacked(DamagePower);
								playerAttackRight.setMotionPerformed(true);
							}

							if( tempObject.isDead() ){
								handler.removeObject( tempObject );
								handler.KillMonster();
							}
						}
					}
					else if(direction == -1)
					{
						if( getAttackBoundsLeft().intersects(tempObject.getBounds()))
						{
							if( getAttackBoundsLeft().intersects(tempObject.getBounds()))
							{
								if( !playerAttackLeft.isMotionPerformed() ){
									System.out.println("Attack Left!");							
									tempObject.attacked(DamagePower);
									playerAttackLeft.setMotionPerformed(true);
								}
								
								if( tempObject.isDead() ){							
									handler.removeObject( tempObject );
									handler.KillMonster();								
								}
							}
						}						
					}
				}
			}

			if(tempObject.getId() == ObjectID.Level){
				if( getBounds().intersects(tempObject.getBounds()))
				{
					if(handler.isLevelCleared())
						handler.setNextLevel(this);
					else
						System.out.println("Player has not cleared the level");
				}
			}
		}
	}

	int randomImageIndex = 0; // default
	public void renderObject(Graphics g) {

		if( isDead() || dead )
		{
			//width = height = 0;
			if( !deathImageSelected ){
				randomImageIndex = (int)(Math.random() * 2);
				deathImageSelected = true;
			}

			if(direction == 1)
			{
				g.drawImage(characterDeathRight[randomImageIndex], (int)x-13, (int)y, null);
			}
			else if(direction == -1)
			{
				g.drawImage(characterDeathLeft[randomImageIndex], (int)x-13, (int)y, null);				
			}

			dead = true;

			return;
		}

		if(jumping || (velY > 5))		// jumping motion
		{
			stance = 3;
			if( velX > 0)				// jumping Right
			{					
				g.drawImage(characterJumpingRight, (int)x, (int)y, null);
			}
			else if( velX < 0)			// jumping Left
			{				
				g.drawImage(characterJumpingLeft, (int)x, (int)y, null);
			}
			else
			{
				if( isShooting && hasBow ) 
				{
					//System.out.println("Shoot!");
					if( direction == 1)
					{
						playerSKYShootRight.drawMotion(g, (int)x, (int)y);
					}
					else if( direction == -1)
					{
						playerSKYShootLeft.drawMotion(g, (int)x-20, (int)y);						
					}
				}
				else
				{
					if(direction == 1)			// falling Right
						g.drawImage(characterJumpingRight, (int)x, (int)y, null);
					else if(direction == -1)	// falling Left
						g.drawImage(characterJumpingLeft, (int)x, (int)y, null);
				}
			}
		}
		else								// moving motion
		{
			if( velX != 0){
				stance = 1;
				if( direction == 1 )		// going Right
				{
					playerWalkRight.drawMotion(g, (int)x, (int)y);
				}
				else if( direction == -1 )	// going Left
				{
					playerWalkLeft.drawMotion(g, (int)x, (int)y);
				}
			}	
			else						// standing
			{	
				if( pickUp )
				{
					stance = 0;
					if( direction == 1 )
					{
						g.drawImage(characterCrouchRight, (int)x, (int)y, null);						
					}
					else if( direction == -1 )
					{
						g.drawImage(characterCrouchLeft, (int)x, (int)y, null);
					}
				}
				else if( isAttacking && hasSword )
				{	
					stance = 2;									
					if( direction == 1 )
					{
						playerAttackRight.drawAttackMotion(g, (int)x, (int)y, this);
					}
					else if( direction == -1 )
					{
						playerAttackLeft.drawAttackMotion(g, (int)x-30, (int)y, this);
					}
				}
				else if( isShooting && hasBow ) 
				{
					//System.out.println("Shoot!");
					stance = 0;
					if( direction == 1)
					{
						playerGNDShootRight.drawMotion(g, (int)x, (int)y+10);
					}
					else if( direction == -1)
					{
						playerGNDShootLeft.drawMotion(g, (int)x-20, (int)y+10);						
					}
				}
				else{
					stance = 1;
					if( direction == 1 )		// going Right
					{
						g.drawImage(characterRight[2], (int)x, (int)y, null);
					}
					else if( direction == -1 )	// going Left
					{
						g.drawImage(characterLeft[3], (int)x, (int)y, null);						
					}
				}
			}	
		}

		if( isAttacked )
		{
			if(direction == 1) // facing right
			{
				switch(stance)
				{
					case 2: g.drawImage(characterDamagedRight[stance], (int)x+3, (int)y, null); break; // attack
					default: g.drawImage(characterDamagedRight[stance], (int)x, (int)y, null); break; 
				}
			}
			else  // facing left 
			{ 
				switch(stance)
				{					
					case 2: g.drawImage(characterDamagedLeft[stance], (int)x-25, (int)y, null); break;
					default: g.drawImage(characterDamagedLeft[stance], (int)x, (int)y, null); break; 
				}
			}
		}

		releventHealth();

		g.setColor(Color.gray);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)healthBar, 10);
		g.fillRect( (int)(getX()), (int)(getY()-40), (int)boostBar, 10);

		g.setColor(Color.green);
		g.fillRect( (int)(getX()), (int)(getY()-20), (int)(healthBar-damaged), 10);

		g.setColor(lightBlue);
		g.fillRect( (int)(getX()), (int)(getY()-40), (int)boost, 10);


		// Graphics2D gg = (Graphics2D)g;
		// gg.setColor(Color.RED);
		// gg.draw(getBoundsLeft());
		// gg.draw(getBoundsRight());
		// gg.draw(getBoundsTop());	
		// gg.draw(getBoundsBottom());	
		// gg.draw(getBounds());	

		// gg.setColor(Color.BLUE);
		// gg.draw(getAttackBoundsRight());
		// gg.draw(getAttackBoundsLeft());
	}


	//---------------------- collision bounds subject to change ------------------------------------------//
	public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)width, (int)height);
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

	public Rectangle getAttackBoundsRight(){
		return new Rectangle((int) ((int)x + width-10),(int)y+5, (int)40, (int)height-10); 
	}

	public Rectangle getAttackBoundsLeft(){
		return new Rectangle((int)x-30,(int)y+5, (int)40, (int)height-10); 
	}
	//---------------------------------------------------------------------------------------------------//

}
