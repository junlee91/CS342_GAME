import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.awt.image.BufferedImage;

public class Arrow extends GameObject{
    
    private float gravity = 0.1f;
    private int degree = 30;            // degree adjustable
    private ObjectHandler handler;
    
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage arrow_R = null;
    private BufferedImage arrow_L = null;
    

    public Arrow(float x, float y, ObjectHandler handler, ObjectID id, int speed){
        super(x, y, id);
        this.velX = speed;
        this.handler = handler;
        loadGraphicImage();

        setDamagePower(7);
    }

    private void loadGraphicImage(){
        arrow_R = imageLoading.LoadImage("res/Hero/Attack/CrossBow/R_arrow.png");
        arrow_L = imageLoading.LoadImage("res/Hero/Attack/CrossBow/L_arrow.png");
    }

    public void Update(LinkedList<GameObject> ObjectList) {
		x += velX;
        y -= gravity*degree;

        degree -= 2;
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

            if(tempObject.getId() == ObjectID.Monster || tempObject.getId() == ObjectID.MonsterArcher ){
                if( getBounds().intersects(tempObject.getBounds())){

                    tempObject.attacked( DamagePower );
                    handler.removeObject(this);
                    
                    if( tempObject.isDead() )
                    {                        
                        handler.removeObject(tempObject);
                        handler.KillMonster();
                    }
                }
            }
        
        }
    }

	public void renderObject(Graphics g) {
		if( velX > 0 )
        {
            g.drawImage(arrow_R, (int)x, (int)y, null);
        }
        else
        {
            g.drawImage(arrow_L, (int)x, (int)y, null);
        }
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 8, 8);
	}
}