import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Bow extends GameObject{
    ImageLoader imageLoading = new ImageLoader();	
    private BufferedImage BowPick = null;
    private BufferedImage BowRemoved = null;

    public Bow(float x, float y, ObjectID id){
        super(x, y, id);

        loadGraphicImage();
    }

    private void loadGraphicImage(){

    }

    public void Update(LinkedList<GameObject> ObjectList) {
		// Bow is fixed graphic. No implementation needed
	}

	public void renderObject(Graphics g) {
        // Graphics2D gg = (Graphics2D)g;
        // gg.setColor(Color.RED);
        // gg.draw(getBounds());
        
        // if( !isPickedUp )
        //     g.drawImage(, (int)x, (int)y-40, null);
        // else
        //     g.drawImage(, (int)x, (int)y-40, null);
	}

	public Rectangle getBounds() {
        
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
}