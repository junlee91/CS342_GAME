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
    

	public GameObject(float x, float y, ObjectID id){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void Update(LinkedList<GameObject> ObjectList);
    public abstract void renderObject(Graphics g);
    public abstract Rectangle getBounds();
    
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

	public boolean isPickUp() { return pickUp; }
	public void setPickUp(boolean pickUp){ 	this.pickUp = pickUp; }

	public void setIsPickedUp(boolean status) { this.isPickedUp = status; }
    public boolean isPickedUp() { return isPickedUp; }

	public ObjectID getId() {	return id;	}

} 
