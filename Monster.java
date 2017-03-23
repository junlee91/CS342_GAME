import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Monster extends GameObject{

    private float width = 32, height = 48;	

	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;

	private ObjectHandler handler;


    public Monster(float x, float y, ObjectHandler handler, ObjectID id){
        super(x, y, id);
		this.handler = handler;
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
    }

    public void CollisionDetection(LinkedList<GameObject> ObjectList){
        for(int i = 0; i < handler.ObjectList.size(); i++ ){
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
			}

        }
    }

    private void MonsterAI(){
        int move = (int)(Math.random() * 1000);

        if( move == 0)
        {
            velX = (float)1;
        }
        else if( move == 1)
        {
            velX = (float)1 * -1;
        }
    }

    public void renderObject(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, (int)width, (int)height);
    }

    public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, (int)width, (int)height);
	}

}