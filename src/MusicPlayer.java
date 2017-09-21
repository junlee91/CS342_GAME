
import java.io.BufferedInputStream; 
import java.io.InputStream; 

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import java.util.ArrayList;

public class MusicPlayer implements Runnable, LineListener{

    private ArrayList<String> musicList;
    private int index = 0;
    private Clip clip;

    public MusicPlayer(String... files){
        musicList = new ArrayList<>();      // ArrayList of music files

        for(String music : files)
            musicList.add(music);
	}
	
    /*
        Reference - http://www.programcreek.com/java-api-examples/javax.sound.sampled.AudioInputStream  Example4
    */

	private void playSound(String fileName){
        try{
            InputStream in = getClass().getResourceAsStream(fileName);
            InputStream buf = new BufferedInputStream(in);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buf);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);
            clip.addLineListener(this);

            clip.start();               
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	public void run() {
		playSound(musicList.get(index));
    }

    public void update(LineEvent event){
        if(event.getType() == LineEvent.Type.START){
            System.out.println("Music START");
        }
        else if(event.getType() == LineEvent.Type.STOP){
            System.out.println("Music STOP");

            clip.close();
            clip.flush();
            clip.setFramePosition(0);
            

            /*
                Note:: 3/22 - Jun
                We can repeat list of bgms 
                or just repeat one bgm 
            */    
            //index++;    // play next song
            if(index >= musicList.size() )  // index bound check
                index = 0;                  // return to first bgm

            run();      // play clip again  
        }
    }

}