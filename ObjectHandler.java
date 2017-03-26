import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.awt.image.BufferedImage;


public class ObjectHandler 
{
	public LinkedList<GameObject> ObjectList = new LinkedList<GameObject>();
	private int LEVEL;
	private GameObject tempObject;

	ImageLoader imageLoading;
	private BufferedImage Layer = null;

	Camera camera;

	public ObjectHandler(Camera cam)
	{
		this.camera = cam;
		imageLoading = new ImageLoader();
		LEVEL = 1;

		Layer = imageLoading.LoadImage("/res/Map/Map_level1.png");
		Game.City = imageLoading.LoadImage("/res/Map/City.png");
	}
	
	public void Update(){
		for(int i = 0; i < ObjectList.size(); i++){
			
			tempObject = ObjectList.get(i);			
			tempObject.Update(ObjectList);

			if(tempObject.getY() > 1800){
				if(tempObject.getId() == ObjectID.Player)
				{
					removeObject( tempObject );
					System.exit(0);				
				}
				removeObject( tempObject );
			}
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


	public void SetGameLayer(){
		int width = Layer.getWidth();
		int height = Layer.getHeight();

		for(int x = 0; x < height; x++){
			for(int y = 0; y < width; y++){
				int pixel = Layer.getRGB(x,y);

				int red		= (pixel >> 16) & 0xff;
				int green	= (pixel >> 8)  & 0xff;
				int blue	= (pixel) & 0xff;

				if(red == 255 && green == 255 && blue == 255)
				{
					addObject(new Layer(x*32, y*32, ObjectID.BottomLayer));
				}

				if(red == 0 && green == 0 && blue == 255)
				{
					addObject(new Player(x*32, y*32, this, ObjectID.Player));
				}

				if(red == 0 && green == 128 && blue == 128)
				{
					addObject(new Sword(x*32, y*32, ObjectID.Sword));
				}

				if(red == 0 && green == 128 && blue == 64)
				{
					addObject(new Bow(x*32, y*32, ObjectID.Bow));
				}

				if(red == 128 && green == 255 && blue == 255)
				{
					// Enemy
					addObject(new Monster(x*32, y*32, this, ObjectID.Monster));
				}


				if(red == 192 && green == 192 && blue == 192)
				{
					// Special Layer for Monster
					addObject(new Layer(x*32, y*32, ObjectID.SpecialLayer));					
				}

				if(red == 255 && green == 255 && blue == 0)
				{
					// Next LEVEL
					addObject(new NextLevel(x*32, y*32, ObjectID.Level));
				}
			}
		}
	}	

	private void LevelClear(){
		ObjectList.clear();
	}

	public void setNextLevel(){
		LevelClear();
		camera.setX(0);

		switch(LEVEL)
		{
			case 1:
				Layer = imageLoading.LoadImage("/res/Map/Map_level2.png");
				Game.City = imageLoading.LoadImage("/res/Map/City2.png");
				SetGameLayer();
				break;
			case 2: break;
			case 3: break;
		}

		LEVEL++;
	}
	
	// TODO:: Adding Ground Image
	public void CreateBottomLayer(){
		for(int i = 0; i < Game.WIDTH*2; i += 32)
			addObject(new Layer(i, Game.HEIGHT-64, ObjectID.BottomLayer));
		
		for(int y = Game.HEIGHT/2; y < Game.HEIGHT+32; y += 32)
			addObject(new Layer(0, y, ObjectID.BottomLayer));
	}
}
