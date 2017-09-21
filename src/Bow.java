import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Bow extends GameObject{
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage CrossBow = null;

    public Bow(float x, float y, ObjectID id){
        super(x, y, id);

        loadGraphicImage();
    }

    private void loadGraphicImage(){
        CrossBow = imageLoading.LoadImage("res/Collectables/CrossBow.png");
    }

    public void Update(LinkedList<GameObject> ObjectList) {
		// Bow is fixed graphic. No implementation needed
	}

	public void renderObject(Graphics g) {
      
        g.drawImage(CrossBow, (int)x, (int)y-70, null);
 
	}

	public Rectangle getBounds() {
        
		return new Rectangle ((int)x, (int)y, 32, 32);
	}

}