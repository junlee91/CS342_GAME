
import java.io.BufferedInputStream; 
import java.io.InputStream; 

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import java.util.ArrayList;

public class MusicPlayer implements Runnable{

    private ArrayList<String> musicList;
    private int index = 0;

    public MusicPlayer(String... files){
        musicList = new ArrayList<>();      // ArrayList of music files

        for(String music : files)
            musicList.add(music);
	}
	
	private void playSound(String fileName){
        try{
            InputStream in = getClass().getResourceAsStream(fileName);
            InputStream buf = new BufferedInputStream(in);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buf);
            AudioFormat format = ais.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip clip = (Clip)AudioSystem.getLine(info);
            clip.open(ais);

            clip.start();               // TODO:: when music ends play next song (index++)
        }
        catch(Exception e){
            e.printStackTrace();
        }
	}
	
	@Override
	public void run() {
		playSound(musicList.get(index));
    }

}
