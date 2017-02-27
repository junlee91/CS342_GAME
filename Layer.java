import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;


public class Layer extends GameObject{

	public Layer(float x, float y, ObjectID id) {
		super(x, y, id);
	}


	public void Update(LinkedList<GameObject> ObjectList) {
		// Layer is fixed graphic.
	}

	public void renderObject(Graphics g) {		
		g.setColor(Color.WHITE);
		g.drawRect((int)x, (int)y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
	
}
