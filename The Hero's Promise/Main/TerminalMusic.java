package Main;

import java.io.File;
import javax.sound.sampled.*;

public class TerminalMusic {
    private static Clip musicClip;

    public static void playMusic() {
        try {
            // Only one path checked; no fallback array yet
            File f = new File("Main/Terminalmusic.wav");
            musicClip = AudioSystem.getClip();
            musicClip.open(AudioSystem.getAudioInputStream(f));
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            musicClip.start();
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (musicClip != null) musicClip.stop();
        // no isRunning() guard yet
    }
    // getMusicClip() not yet added; SoundManager fade methods don't exist yet
}
