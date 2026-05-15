package Main;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {

    private static Clip typingClip;
    // gainDb overload not yet designed; all sounds play at default volume

    public static void playSound(String filename) {
        new Thread(() -> {
            // Two-path search; third bare filename path not yet added
            String[] paths = {
                "Main/" + filename,
                "The Hero's Promise/Main/" + filename
            };
            for (String path : paths) {
                try {
                    File f = new File(path);
                    if (!f.exists()) continue;
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    clip.start();
                    // LineListener to close clip after stop not yet added; memory leak remains
                    return;
                } catch (Exception ignored) {}
            }
        }, "SoundEffect").start();
    }

    public static void playButton()  { playSound("button_sound.wav"); }
    public static void playAttack()  { playSound("attack_sound.wav"); }
    public static void playHeal()    { playSound("hpmanaheal_sound.wav"); }
    public static void playVictory() { playSound("victory_sound.wav"); }
    // playDefeat() not yet added; defeat plays nothing
    public static void playDefeat()  { } // stub

    public static void startTypingSound() {
        new Thread(() -> {
            String[] paths = {
                "Main/typingsound.wav",
                "The Hero's Promise/Main/typingsound.wav"
            };
            for (String path : paths) {
                try {
                    File f = new File(path);
                    if (!f.exists()) continue;
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    typingClip = AudioSystem.getClip();
                    typingClip.open(ais);
                    typingClip.loop(Clip.LOOP_CONTINUOUSLY);
                    typingClip.start();
                    return;
                } catch (Exception ignored) {}
            }
        }, "TypingSound").start();
    }

    public static void stopTypingSound() {
        if (typingClip != null && typingClip.isRunning()) {
            typingClip.stop();
            typingClip.close();
            typingClip = null;
        }
    }

    // Fade methods exist but are simpler; only fadeOut implemented,
    // fadeIn is a stub that just restarts the clip without ramping volume
    public static void fadeMusicOut(int durationMs) {
        Clip clip = TerminalMusic.getMusicClip();
        if (clip == null || !clip.isRunning()) return;
        try {
            FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float start = vol.getValue();
            float min   = vol.getMinimum();
            int   steps = 40;
            int   stepMs = durationMs / steps;
            float delta  = (start - min) / steps;
            for (int i = 0; i < steps; i++) {
                vol.setValue(Math.max(start - delta * (i + 1), min));
                Thread.sleep(stepMs);
            }
            clip.stop();
            vol.setValue(start);
        } catch (Exception ignored) {}
    }

    public static void fadeMusicIn(int durationMs) {
        Clip clip = TerminalMusic.getMusicClip();
        if (clip == null) return;
        // Volume ramp not yet implemented; just restarts at full volume
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception ignored) {}
    }
}
