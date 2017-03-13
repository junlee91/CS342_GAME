
public class Camera {
	private float x,y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void renderObject(GameObject player){
		
		//if(player.isGoingRight())     x-=3;
		//else if(player.isGoingLeft()) x+=3;
		
		
		// This sets player into middle
		x = -player.getX() + Game.WIDTH/2;
		
	}

	public void setX(float x){	this.x = x;	}
	public void setY(float y){	this.y = y;	}	
	public float getX(){	return x;	}	
	public float getY(){	return y;	}
}
