package Main;
import java.io.File;
import javax.sound.sampled.*;

public class TerminalMusic {
    private static Clip musicClip;
    private static boolean isPlaying = false;
    
    public static void playMusic() {
        if (isPlaying) return;
        
        try {
            String[] paths = {"Main/Terminalmusic.wav", "Terminalmusic.wav"};
            for (String path : paths) {
                File f = new File(path);
                if (f.exists()) {
                    musicClip = AudioSystem.getClip();
                    musicClip.open(AudioSystem.getAudioInputStream(f));
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicClip.start();
                    isPlaying = true;
                    System.out.println("Music started");
                    return;
                }
            }
            System.out.println("Music file not found");
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
    
    public static Clip getMusicClip() {
        return musicClip;
    }
    
    public static void setVolume(float volume) {
        if (musicClip != null && musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), dB)));
        }
    }
}