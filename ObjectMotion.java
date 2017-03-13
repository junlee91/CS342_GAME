import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ObjectMotion{
    private int motionSpeed;
    private int numFrames;

    private int index = 0;
    private int count = 0;

    private BufferedImage[] CharacterImages;
    private BufferedImage currentImage;

    public ObjectMotion(int speed, BufferedImage... args){
        this.motionSpeed = speed;
        CharacterImages = new BufferedImage[args.length];

        for(int i = 0; i < args.length; i++){
            CharacterImages[i] = args[i];
        }

        numFrames = args.length;
    }

    public void runMotion(){
        index++;
        if(index > motionSpeed)
        {
            index = 0;
            nextMotion();
        }
    }

    private void nextMotion(){
        currentImage = CharacterImages[count];
        count++;

        if(count >= numFrames)
            count = 0;
        
    }

    public void drawMotion(Graphics g, int x, int y)
    {
        g.drawImage(currentImage, x, y, null);
    }

}
