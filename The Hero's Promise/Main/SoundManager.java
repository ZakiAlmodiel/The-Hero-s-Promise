package Main;
import java.io.File;
import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
    private static Map<String, Clip> soundCache = new HashMap<>();
    private static Clip currentBGM;
    private static float currentVolume = 0.7f;
    private static boolean muted = false;
    
    public static void playSound(String filename) {
        if (muted) return;
        
        try {
            if (soundCache.containsKey(filename)) {
                Clip clip = soundCache.get(filename);
                clip.setFramePosition(0);
                clip.start();
                return;
            }
            
            String[] paths = {"Main/" + filename, "The Hero's Promise/Main/" + filename, filename};
            for (String path : paths) {
                File soundFile = new File(path);
                if (soundFile.exists()) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    soundCache.put(filename, clip);
                    clip.start();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Sound error: " + e.getMessage());
        }
    }
    
    public static void playLooping(String filename) {
        if (muted) return;
        
        try {
            if (currentBGM != null && currentBGM.isRunning()) {
                currentBGM.stop();
            }
            
            String[] paths = {"Main/" + filename, "The Hero's Promise/Main/" + filename, filename};
            for (String path : paths) {
                File soundFile = new File(path);
                if (soundFile.exists()) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
                    currentBGM = AudioSystem.getClip();
                    currentBGM.open(ais);
                    currentBGM.loop(Clip.LOOP_CONTINUOUSLY);
                    setVolume(currentVolume);
                    currentBGM.start();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Looping sound error: " + e.getMessage());
        }
    }
    
    public static void stopLooping() {
        if (currentBGM != null && currentBGM.isRunning()) {
            currentBGM.stop();
        }
    }
    
    public static void setVolume(float volume) {
        currentVolume = Math.max(0f, Math.min(1f, volume));
        if (currentBGM != null && currentBGM.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) currentBGM.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(currentVolume) / Math.log(10.0) * 20.0);
            gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB)));
        }
    }
    
    public static void toggleMute() {
        muted = !muted;
        if (muted) {
            if (currentBGM != null) currentBGM.stop();
        } else {
            if (currentBGM != null) currentBGM.start();
        }
    }
    
    public static boolean isMuted() {
        return muted;
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
    
    public static void playLevelUp() {
        playSound("levelup_sound.wav");
    }
}
