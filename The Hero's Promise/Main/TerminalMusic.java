package Main;
import java.io.File;
import javax.sound.sampled.*;

public class TerminalMusic {
    private static Clip musicClip;
    
    public static void playMusic() {
        try {
            String[] paths = {"Main/Terminalmusic.wav", "Terminalmusic.wav"};
            for (String path : paths) {
                File f = new File(path);
                if (f.exists()) {
                    musicClip = AudioSystem.getClip();
                    musicClip.open(AudioSystem.getAudioInputStream(f));
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicClip.start();
                    System.out.println("Music playing: " + path);
                    return;
                }
            }
            System.out.println("Music file not found");
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }
    
    public static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
        }
    }
    
    public static Clip getMusicClip() {
        return musicClip;
    }
}