package Main;
import java.io.File;
import javax.sound.sampled.*;

public class SoundManager {
    private static Clip currentClip;
    
    public static void playSound(String filename) {
        try {
            String[] paths = {"Main/" + filename, filename};
            for (String path : paths) {
                File soundFile = new File(path);
                if (soundFile.exists()) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.start();
                    currentClip = clip;
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }
    
    public static void playButton() {
        playSound("button_sound.wav");
    }
    
    public static void playAttack() {
        playSound("attack_sound.wav");
    }
    
    public static void playHeal() {
        playSound("hpmanaheal_sound.wav");
    }
    
    public static void playVictory() {
        playSound("victory_sound.wav");
    }
    
    public static void playDefeat() {
        playSound("defeat_sound.wav");
    }
    
    public static void stopSound() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }
}
