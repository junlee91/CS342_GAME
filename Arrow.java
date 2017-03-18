import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Arrow extends GameObject{
    
    private float gravity = 0.1f;
    private int degree = 30;            // degree adjustable
    private ObjectHandler handler;

    public Arrow(float x, float y, ObjectHandler handler, ObjectID id, int speed){
        super(x, y, id);
        this.velX = speed;
        this.handler = handler;
    }

    public void Update(LinkedList<GameObject> ObjectList) {
		x += velX;
        y -= gravity*degree;

        degree -= 1;
        CollisionDetection(ObjectList);
	}

    public void CollisionDetection(LinkedList<GameObject> ObjectList)
    {
        for(int i = 0; i < handler.ObjectList.size(); i++){
            GameObject tempObject = handler.ObjectList.get(i);

            if(tempObject.getId() == ObjectID.BottomLayer){
                if( getBounds().intersects(tempObject.getBounds()) ){

                    handler.removeObject( this );
                }
            }
        
        }
    }

	public void renderObject(Graphics g) {
		g.setColor(Color.RED);				
		g.fillRect((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 16, 16);
	}
}