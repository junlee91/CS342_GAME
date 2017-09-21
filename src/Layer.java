import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;


public class Layer extends GameObject{

	public Layer(float x, float y, ObjectID id) {
		super(x, y, id);
	}


	public void Update(LinkedList<GameObject> ObjectList) {
		// Layer is fixed graphic. No implementation needed
	}

	public void renderObject(Graphics g) {
		g.setColor(Color.WHITE);				// layer default: white rectangle.  Subject to change graphics
		
		if(id == ObjectID.SpecialLayer )
		{
			g.setColor(Color.BLACK);
		}

		 g.drawRect((int)x, (int)y, 32, 32);

	}

	public Rectangle getBounds() {
		return new Rectangle ((int)x, (int)y, 32, 32);
	}
}
