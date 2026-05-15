package Main;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TerminalMusic {
    private static Clip musicClip;

    public static void playMusic() {
        try {
            // Multi-path fallback now added; third plain path not yet included
            String[] paths = {
                "Main/Terminalmusic.wav",
                "The Hero's Promise/Main/Terminalmusic.wav"
            };
            for (String path : paths) {
                File f = new File(path);
                if (!f.exists()) continue;
                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(f));
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error playing music: " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    // getMusicClip() added so SoundManager can fade volume
    public static Clip getMusicClip() {
        return musicClip;
    }
}
