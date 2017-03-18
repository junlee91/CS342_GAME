import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Arrow extends GameObject{
    
    private float gravity = 0.1f;
    private float count = 0.3f;
    private ObjectHandler handler;

    public Arrow(float x, float y, ObjectHandler handler, ObjectID id, int speed){
        super(x, y, id);
        this.velX = speed;
        this.handler = handler;
    }

    public void Update(LinkedList<GameObject> ObjectList) {
		x += velX;
        y += gravity*count;

        count += 0.2;
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
		g.setColor(Color.RED);				// layer default: white rectangle.  Subject to change graphics
		g.fillRect((int)x, (int)y, 16, 16);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 16, 16);
	}
}