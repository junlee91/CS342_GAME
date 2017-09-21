import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.awt.image.BufferedImage;


public class Potion extends GameObject{
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage Potion = null;

    public Potion(float x, float y, ObjectID id){
        super(x, y, id);

        loadGraphicImage();
    }

    private void loadGraphicImage(){
        Potion = imageLoading.LoadImage("res/Collectables/Heal_potion.png");
    }

    public void Update(LinkedList<GameObject> ObjectList) {
		// Potion is fixed graphic. No implementation needed
	}

	public void renderObject(Graphics g) {
      
        g.drawImage(Potion, (int)x, (int)y-15, null);
 
	}

	public Rectangle getBounds() {
        
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
}