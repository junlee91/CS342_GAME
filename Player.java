import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;


public class Player extends GameObject{
	private String name;
	private int damage;
	private int maxHealth;
	private int curHealth;
	private boolean dead;
	private boolean airborn;

	//private int facing = 1;
	private boolean hasSword = false;
	private boolean hasBow = false;
	private float width = 48, height = 77;	

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;

	private ObjectHandler handler;

	//------------ Motion arrayList  --------------//
	ImageLoader imageLoading = new ImageLoader();	
	private BufferedImage[] characterStanding = new BufferedImage[2];
	private BufferedImage[] characterRight = new BufferedImage[4];
	private BufferedImage[] characterLeft = new BufferedImage[4];
	private BufferedImage[] characterAttackRight = new BufferedImage[4];
	private BufferedImage[] characterAttackLeft = new BufferedImage[4];	
	private BufferedImage characterJumpingRight = null;
	private BufferedImage characterJumpingLeft = null;
	private BufferedImage characterCrouchRight = null;
	private BufferedImage characterCrouchLeft = null;

	private ObjectMotion playerWalkRight;
	private ObjectMotion playerWalkLeft;
	private ObjectMotion playerStand;
	private ObjectMotion playerAttackRight;
	private ObjectMotion playerAttackLeft;
	//--------------------------------------------//

	public Player(float x, float y, ObjectHandler handler, ObjectID id) {
		super(x, y, id);
		this.handler = handler;

		loadMotionImage();

		playerWalkRight = new ObjectMotion(5, characterRight[0], characterRight[1],
				characterRight[2], characterRight[3]);
		playerWalkLeft = new ObjectMotion(5, characterLeft[0], characterLeft[1], 
				characterLeft[2], characterLeft[3]);
		playerStand = new ObjectMotion(10, characterStanding[0], characterStanding[1]);
		
		playerAttackRight = new ObjectMotion(5, characterAttackRight[0],characterAttackRight[1],
				characterAttackRight[2], characterAttackRight[3]);
		playerAttackLeft = new ObjectMotion(5, characterAttackLeft[0],characterAttackLeft[1],
				characterAttackLeft[2], characterAttackLeft[3]);
	}

	private void loadMotionImage(){
		characterRight[0] = imageLoading.LoadImage("/res/Motion/Walk Right/_R1.png");
		characterRight[1] = imageLoading.LoadImage("/res/Motion/Walk Right/_R2.png");
		characterRight[2] = imageLoading.LoadImage("/res/Motion/Walk Right/_R3.png");
		characterRight[3] = imageLoading.LoadImage("/res/Motion/Walk Right/_R4.png");

		characterLeft[0] = imageLoading.LoadImage("/res/Motion/Walk Left/_L1.png");
		characterLeft[1] = imageLoading.LoadImage("/res/Motion/Walk Left/_L2.png");
		characterLeft[2] = imageLoading.LoadImage("/res/Motion/Walk Left/_L3.png");
		characterLeft[3] = imageLoading.LoadImage("/res/Motion/Walk Left/_L4.png");

		characterStanding[0] = imageLoading.LoadImage("/res/Motion/Stand/stand_2.png");
		characterStanding[1] = imageLoading.LoadImage("/res/Motion/Stand/stand_3.png");

		characterJumpingRight = imageLoading.LoadImage("/res/Motion/Jump/_J2.png");
		characterJumpingLeft = imageLoading.LoadImage("/res/Motion/Jump/JL2.png");

		characterCrouchRight = imageLoading.LoadImage("/res/Motion/Pick Up/Crouch_R.png");
		characterCrouchLeft = imageLoading.LoadImage("/res/Motion/Pick Up/Crouch_L.png");

		characterAttackRight[0] = imageLoading.LoadImage("/res/Motion/Attack/R_Attack1.png");
		characterAttackRight[1] = imageLoading.LoadImage("/res/Motion/Attack/R_Attack2.png");
		characterAttackRight[2] = imageLoading.LoadImage("/res/Motion/Attack/R_Attack3.png");
		characterAttackRight[3] = imageLoading.LoadImage("/res/Motion/Attack/R_Attack4.png");
		
		characterAttackLeft[0] = imageLoading.LoadImage("/res/Motion/Attack/L_Attack1.png");
		characterAttackLeft[1] = imageLoading.LoadImage("/res/Motion/Attack/L_Attack2.png");
		characterAttackLeft[2] = imageLoading.LoadImage("/res/Motion/Attack/L_Attack3.png");
		characterAttackLeft[3] = imageLoading.LoadImage("/res/Motion/Attack/L_Attack4.png");
	}

	public void Update(LinkedList<GameObject> ObjectList) {
		x += velX;
		y += velY;

		if( velX < 0 ) 		direction = -1;	// facing Left
		else if( velX > 0)	direction = 1;		// facing Right

		if( falling ){
			velY += gravity;

			if( velY > MAX_SPEED )
				velY = MAX_SPEED;
		}

		CollisionDetection(ObjectList);

		playerWalkRight.runMotion();
		playerWalkLeft.runMotion();
		playerStand.runMotion();
		playerAttackRight.runMotion();
		playerAttackLeft.runMotion();
	}

	// REMINDER!!: CollisionDetection is subject to change (different character)
	public void CollisionDetection(LinkedList<GameObject> ObjectList){

		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);

			if(tempObject.getId() == ObjectID.BottomLayer){
				if( getBounds().intersects(tempObject.getBounds()) ){
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
					System.out.println("Collide Right side!");
					x = tempObject.getX() - width;					
					this.setGoingRight(false);
				}
				else if(getBoundsLeft().intersects(tempObject.getBounds())){	
					System.out.println("Collide Left side!");
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

						tempObject.setIsPickedUp( true );
						hasBow = true;
					}
				}
			}
		}
	}


	public void renderObject(Graphics g) {

		if(jumping || (velY > 5))		// jumping motion
		{
			if( velX > 0)
				g.drawImage(characterJumpingRight, (int)x, (int)y, null);
			else if( velX < 0)
				g.drawImage(characterJumpingLeft, (int)x, (int)y, null);
			else{
				if(direction == 1)
					g.drawImage(characterJumpingRight, (int)x, (int)y, null);
				else if(direction == -1)
					g.drawImage(characterJumpingLeft, (int)x, (int)y, null);
			}
		}
		else							// moving motion
		{
			if( velX != 0){
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
					System.out.println("Attack!");
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
					System.out.println("Shoot!");
					if( direction == 1)
					{

					}
					else if( direction == -1)
					{

					}
				}
				else
					playerStand.drawMotion(g, (int)x, (int)y);	
			}	
		}
	
		Graphics2D gg = (Graphics2D)g;
		// gg.setColor(Color.RED);
		// gg.draw(getBoundsLeft());
		// gg.draw(getBoundsRight());
		// gg.draw(getBoundsTop());		
		// gg.draw(getBounds());	

		gg.setColor(Color.BLUE);
		gg.draw(getAttackBoundsRight());
		gg.draw(getAttackBoundsLeft());
	}

	//---------------------- collision bounds subject to change ------------------------------------------//
	public Rectangle getBounds() {
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

	
	//-------------------------------------------------------------------//
	//  Following functions are not in abstract class yet.               //
	//  We will choose later which parts we need.                        //
	//  Too many abstract item considered not good. In my opinion. 	     //
	//                                                   2/27/17 Jun.    //
	//-------------------------------------------------------------------//

	public void playerInfo(){
		System.out.println("Player name: " + name);
		System.out.println(name + "'s health: " + curHealth);
		System.out.println(name + "'s damage: " + damage);
	
	}

	//TODO: Implement attack for graphics and if collision, then outcome
	public void attack(){

	}

	public void passiveHeal(){
		if(curHealth < maxHealth){
			curHealth = curHealth +1;
		}
	}

	public void heal(int healHP){
		if(curHealth + healHP >= maxHealth){
			curHealth = maxHealth;
		}
		else{
			curHealth = curHealth + healHP;
		}
	}

	public void changeMaxHealth(int newMaxHealth){
		maxHealth = newMaxHealth;
	}

	public void takeDamage(int damageTaken){
		if(curHealth - damageTaken < 0){
			dead = true;
		}
		else{
			curHealth = curHealth - damageTaken;
		}
	}

	public boolean isDead(){
		if(curHealth == 0 || curHealth < 0){
			dead = true;
		}
		return dead;
	}

	public String getName(){
		return name;
	}

	public void setName(String newName){
		name = newName;
	}

	public int getCurrentHealth(){
		return curHealth;
	}

	public int getMaximumHealth(){
		return maxHealth;
	}

	public int getDamage(){
		return damage;
	}

	public void setDamage(int newDamage){
		damage = newDamage;
	}
}
