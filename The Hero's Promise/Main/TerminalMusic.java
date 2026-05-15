package Main;
import java.io.File;
import javax.sound.sampled.*;

public class TerminalMusic {
    private static Clip musicClip;
    private static boolean isPlaying = false;
    private static FloatControl volumeControl;
    private static String currentTrack = "";
    
    public static void playMusic() {
        playMusic("Terminalmusic.wav");
    }
    
    public static void playMusic(String filename) {
        if (isPlaying && currentTrack.equals(filename)) return;
        
        stopMusic();
        currentTrack = filename;
        
        try {
            String[] paths = {"Main/" + filename, "The Hero's Promise/Main/" + filename, filename};
            for (String path : paths) {
                File f = new File(path);
                if (f.exists()) {
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    musicClip = AudioSystem.getClip();
                    musicClip.open(ais);
                    
                    if (musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        volumeControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                        setVolume(0.7f);
                    }
                    
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicClip.start();
                    isPlaying = true;
                    System.out.println("Music playing: " + filename);
                    return;
                }
            }
            System.out.println("Music file not found: " + filename);
        } catch (Exception e) {
            System.out.println("Music error: " + e.getMessage());
        }
    }
    
    public static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
            isPlaying = false;
            System.out.println("Music stopped");
        }
    }
    
    public static void pauseMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }
    
    public static void resumeMusic() {
        if (musicClip != null && !musicClip.isRunning() && isPlaying) {
            musicClip.start();
        }
    }
    
    public static void setVolume(float volume) {
        if (volumeControl != null) {
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float dB = Math.max(min, Math.min(max, (float)(Math.log(volume) / Math.log(10.0) * 20.0)));
            volumeControl.setValue(dB);
        }
    }
    
    public static Clip getMusicClip() {
        return musicClip;
    }
    
    public static boolean isPlaying() {
        return isPlaying;
    }
}