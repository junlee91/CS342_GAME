import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Player extends GameObject{
	private String name;
	private int damage;
	private int maxHealth;
	private int curHealth;
	private boolean dead;
	private boolean airborn;

	// TODO:: to be changed to Polygon
	private float width = 48, height = 96;

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;

	private ObjectHandler handler;

	public Player(float x, float y, ObjectHandler handler, ObjectID id) {
		super(x, y, id);
		this.handler = handler;

		name = "Bob";
		damage = 10;
		maxHealth = 100;
		curHealth = 100;
		dead = false;
		airborn = false; //starts grounded //based on y coordinate and ground
	}

	public void Update(LinkedList<GameObject> ObjectList) {
		x += velX;
		y += velY;

		if( falling ){
			velY += gravity;

			if( velY > MAX_SPEED )
				velY = MAX_SPEED;
		}

		CollisionDetection(ObjectList);
	}

	public void CollisionDetection(LinkedList<GameObject> ObjectList){
		for(int i = 0; i < handler.ObjectList.size(); i++){
			GameObject tempObject = handler.ObjectList.get(i);

			if(tempObject.getId() == ObjectID.BottomLayer)
			{
				if(getBounds().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY = 0;

					falling = false;
					jumping = false;
					//handler.removeObject(tempObject);
				} else {
					falling = true;
				}
				
				if(getBoundsRight().intersects(tempObject.getBounds()) )
				{
					x = tempObject.getX() - width;
				}
				
				if(getBoundsLeft().intersects(tempObject.getBounds()))
				{
					x = tempObject.getX() + 35;
				}
			}
		}
	}


	public void renderObject(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x, (int)y, (int)width, (int)height);	
		
		Graphics2D gg = (Graphics2D)g;
		gg.setColor(Color.RED);
		gg.draw(getBoundsLeft());
		gg.draw(getBoundsRight());
		gg.draw(getBounds());
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, (int)width, (int)height);
	}
	
	public Rectangle getBoundsRight(){
		return new Rectangle((int) ((int)x+width-5),(int)y+5, (int)5, (int)height-10); 
	}
	public Rectangle getBoundsLeft(){
		return new Rectangle((int)x,(int)y+5, (int)5, (int)height-10); 
	}
	
	
	
	//-------------------------------------------------------------------//
	//  Following functions are not in abstract class yet.               //
	//  We will choose later which parts we need.                        //
	//  Too many abstract item considered not good. In my opinion.       //
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
