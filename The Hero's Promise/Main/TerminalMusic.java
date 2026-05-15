package Main;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class TerminalMusic {
    private static Clip musicClip;

    public static void playMusic() {
        try {
            String[] paths = {"Main/Terminalmusic.wav", "The Hero's Promise/Main/Terminalmusic.wav", "Terminalmusic.wav"};
            for (String path : paths) {
                File f = new File(path);
                if (!f.exists()) continue;
                musicClip = AudioSystem.getClip();
                musicClip.open(AudioSystem.getAudioInputStream(f));
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                musicClip.start();
                return;
            }
        } catch (Exception e) { System.out.println("Error playing music: " + e.getMessage()); }
    }

    public static void stopMusic() { if (musicClip != null && musicClip.isRunning()) { musicClip.stop(); } }
    public static Clip getMusicClip() { return musicClip; }
}