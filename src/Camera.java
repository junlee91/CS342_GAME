
public class Camera {
	private float x,y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void renderObject(GameObject player){
		
		// This sets player into middle
		x = -player.getX() + Game.WIDTH/2;
		y = -player.getY() + Game.HEIGHT/2 + 50;   // subject to change

	}

	public void setX(float x){	this.x = x;	}
	public void setY(float y){	this.y = y;	}	
	public float getX(){	return x;	}	
	public float getY(){	return y;	}
}
