import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class ObjectHandler 
{
	public LinkedList<GameObject> ObjectList = new LinkedList<GameObject>();
	
	private GameObject tempObject;
	
	public void Update(){
		for(int i = 0; i < ObjectList.size(); i++){
			
			tempObject = ObjectList.get(i);			
			tempObject.Update(ObjectList);
		}
	}
	
	public void renderObject(Graphics g){
		for(int i = 0; i < ObjectList.size(); i++){
			
			tempObject = ObjectList.get(i);			
			tempObject.renderObject(g);
		}
	}	
	
	public void addObject(GameObject object){
		this.ObjectList.add(object);
	}
	
	public void removeObject(GameObject object){
		this.ObjectList.remove(object);
	}
	
	// TODO:: Adding Ground Image
	public void CreateBottomLayer(){
		for(int i = 0; i < Game.WIDTH+32; i += 32)
			addObject(new Layer(i, Game.HEIGHT-64, ObjectID.BottomLayer));
		
	}
}
