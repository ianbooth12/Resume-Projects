import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

// Sounds class that manages all app audio...
public class Sounds
{    
    // Takes a filepath parameter and plays that sound
    public static void playSound(String file)
    {
        try
        {
            // Gets sound file
            File musicPath = new File(SunDevilPizza.resourcesPath + file);
            // If sound file exists
            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    // Plays button click
    public static void playButtonClick()
    {
        try
        {
            // Gets sound file
            File musicPath = new File(SunDevilPizza.resourcesPath + "click.wav");
            // If sound file exists...
            if (musicPath.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}