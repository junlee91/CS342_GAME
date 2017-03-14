import junit.framework.TestCase;

public class Jae_TestCase extends TestCase
{
	Game_Container game;
	
	public void setup()
	{
		game = new Game_Container();
	}

	public void testIfGameisCreated() throws Exception
	{
		assertNotNull(game);
	}
}