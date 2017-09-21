import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.awt.Polygon;

public abstract class GameObject{  // TODO:: this will extend Polygon class later - JUN
    protected float x,y;
    protected float velX = 0, velY = 0;
    protected ObjectID id;
    
    protected boolean falling = true; 
    protected boolean jumping = false;
    protected boolean goingRight = true;
    protected boolean goingLeft = true;
	protected boolean pickUp = false;
    protected boolean isPickedUp = false;
	protected boolean isAttacking = false;
	protected boolean isShooting = false;
	protected boolean hasSword = false;
	protected boolean hasBow = false;
	protected boolean isAttacked = false;
	protected boolean isDead = false;

	protected int direction = 1;    
	protected int ArrowCount;
	protected int HealthPoint;
	protected int DamagePower;
	protected int damagedPoint;
	protected int damaged;

	protected float boost = 30;

	public GameObject(float x, float y, ObjectID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void Update(LinkedList<GameObject> ObjectList);
    public abstract void renderObject(Graphics g);
    public abstract Rectangle getBounds();

	public void setHealthPoint(int point) { this.HealthPoint = point; }
	public void setDamagePower(int power) { this.DamagePower = power; }
	
	public void attacked(int damage) { 
		this.HealthPoint -= damage; 
		isAttacked = true; 
		damagedPoint = damage; 
	}
	public void setObjectAttacked(){ isAttacked = false; damagedPoint = 0; }
    public boolean isDead() { return (this.HealthPoint <= 0); }

	public void boost(){	boost -= 10;	}
	public boolean isBoostAvailable(){ return (boost > 10); }

    public float getX(){ 	return x;	}
	public float getY() { 	return y;	}
	public void setX(float x) {	this.x = x;	}
	public void setY(float y) {	this.y = y;	}
	
	public float getVelX() {	return velX;	}
	public float getVelY() {	return velY;	}
	public void setVelX(float velX) {	this.velX = velX;	}
	public void setVelY(float velY) {	this.velY = velY;	}	
	
	public void setPosition(float pos){		this.y += pos;	}

	public boolean isFalling() {	return falling;	}
	public void setFalling(boolean falling) {	this.falling = falling;	}
	
	public boolean isJumping() {	return jumping; }
	public void setJumping(boolean jumping) {	this.jumping = jumping;	}

	public boolean isGoingRight() {	return goingRight;	}
	public void setGoingRight(boolean goingRight) {	this.goingRight = goingRight;	}

	public boolean isGoingLeft() {	return goingLeft;	}
	public void setGoingLeft(boolean goingLeft) {	this.goingLeft = goingLeft;	}

	public void setPickUp(boolean pickUp){ 	this.pickUp = pickUp; }
	public void setIsPickedUp(boolean status) { this.isPickedUp = status; }

	public void setAttacking(boolean status) { this.isAttacking = status; }
	public void setShooting(boolean status) { this.isShooting = status; }

	public boolean HasBow(){ return hasBow; }

	public int getDirection(){ return direction; }

	public ObjectID getId() {	return id;	}

	public void setArrow(int count) { this.ArrowCount = count; }	 
	public int getArrowCount() { return ArrowCount; }
	public void MinusArrow() { this.ArrowCount--; }

	public boolean isArrowAvailable() { return (ArrowCount > 0); }

	public void setHeal(int heal){ this.HealthPoint += heal; }

} 
