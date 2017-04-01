import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.awt.image.BufferedImage;

public class FireBall extends GameObject{
    private float gravity = 0.1f;
    private int degree = 30;            // degree adjustable
    private ObjectHandler handler;
    
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage fire_R = null;
    private BufferedImage fire_L = null;

    public FireBall(float x, float y, ObjectHandler handler, ObjectID id, int speed){
        super(x, y, id);
        this.velX = speed;
        this.handler = handler;
        loadGraphicImage();
    }

    private void loadGraphicImage(){

    }

    public void Update(LinkedList<GameObject> ObjectList) {
        x += velX;
        y -= gravity*degree;

        degree -= 5;
        CollisionDetection(ObjectList);
    }

    public void CollisionDetection(LinkedList<GameObject> ObjectList){
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
        g.setColor(Color.MAGENTA);
        g.fillRect((int)x, (int)y, (int)8, (int)8);

    }

    public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 8, 8);
	}

}