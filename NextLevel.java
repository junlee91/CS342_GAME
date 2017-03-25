import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;


public class NextLevel extends GameObject{

	public NextLevel(float x, float y, ObjectID id) {
		super(x, y, id);
	}


	public void Update(LinkedList<GameObject> ObjectList) {
		// NextLevel is fixed graphic. No implementation needed
	}

	public void renderObject(Graphics g) {
		g.setColor(Color.YELLOW);				// layer default: white rectangle.  Subject to change graphics
		
		g.fillRect((int)x, (int)y, 32, 64);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 32, 64);
	}
}
