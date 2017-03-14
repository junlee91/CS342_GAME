import java.lang.String;

public class Test
{
	String pass = "passed! \n";
	String fail = "failed! \n";

	// JAE's Test Cases:
	public Test(int id)
	{
		System.out.println("ID:" + id +" Tester successfully created!\n");
	}

	public void isGameCreated(Game game)
	{
		String id = "isGameCreated: ";
		
		if(game != null) System.out.println(id + pass);
		else System.out.println(id + fail);
	}

	public void isHandlerCreated(ObjectHandler handler)
	{
		String id = "isHandlerCreated: ";
		
		if(handler != null)
		{
			if(handler.ObjectList.size() == 0)
			{
				System.out.println(id + pass);
			}
			else
			{
				System.out.println(id + fail);
				System.out.println("Inside: " + handler.ObjectList.size() +"\n");
			} 
		} 
		else System.out.println(id + fail);
	}

	public void isCameraCreated(Camera camera)
	{
		String id = "isCameraCreated: ";

		if(camera != null)
		{
			 if(camera.getX() == 0f && camera.getY() == 0f)
			 	System.out.println(id + pass);
			 else
			 {
			 	System.out.println(id + fail);	
			 	System.out.println("Camera location X: "+ camera.getX()
			 								  + "  Y: " + camera.getY());
			 } 
			 
		}
		else System.out.println(id + fail);
	}

	public void isImgLoaderCreated(ImageLoader imgLoader)
	{
		String id = "isImgLoaderCreated: ";
		
		if(imgLoader != null) System.out.println(id + pass);
		else System.out.println(id + fail);
	}
}