import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class NextLevel extends GameObject{

	private ObjectHandler handler;

	ImageLoader imageLoading = new ImageLoader();
	private BufferedImage[] Gate = new BufferedImage[4];
	private ObjectMotion gateOpen;

	public NextLevel(float x, float y, ObjectHandler handler, ObjectID id) {
		super(x, y, id);

		this.handler = handler;

		Gate[0] = imageLoading.LoadImage("");
		Gate[1] = imageLoading.LoadImage("");
		Gate[2] = imageLoading.LoadImage("");
		Gate[3] = imageLoading.LoadImage("");
		

		gateOpen = new ObjectMotion(15, Gate[1], Gate[2], Gate[3]);
	}


	public void Update(LinkedList<GameObject> ObjectList) {
		gateOpen.runMotion();
	}

	public void renderObject(Graphics g) {
		
		if( handler.isLevelCleared() )
		{
			gateOpen.drawMotion(g, (int)x, (int)y);
		}
		else
			g.drawImage(Gate[0], (int)x, (int)y, null);

	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 32, 64);
	}
}
