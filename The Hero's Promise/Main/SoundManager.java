package Main;
import java.io.File;
import javax.sound.sampled.*;

public class SoundManager {
    
    public static void playSound(String filename) {
        try {
            File soundFile = new File("Main/" + filename);
            if (soundFile.exists()) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(ais);
                clip.start();
            } else {
                System.out.println("Sound not found: " + filename);
            }
        } catch (Exception e) {
            System.out.println("Error playing sound: " + e.getMessage());
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
}
