package Main;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private static Clip typingClip;

    private static void playSoundWithGain(String filename, float gainDb) {
        new Thread(() -> {
            String[] paths = {"Main/" + filename, "The Hero's Promise/Main/" + filename, filename};
            for (String path : paths) {
                try {
                    File f = new File(path);
                    if (!f.exists()) continue;
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    Clip clip = AudioSystem.getClip();
                    clip.open(ais);
                    if (gainDb != 0f && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                        FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        float target = Math.min(vol.getMaximum(), Math.max(vol.getMinimum(), vol.getValue() + gainDb));
                        vol.setValue(target);
                    }
                    clip.start();
                    clip.addLineListener(event -> { if (event.getType() == LineEvent.Type.STOP) clip.close(); });
                    return;
                } catch (Exception ignored) {}
            }
        }, "SoundEffect").start();
    }

    public static void playSound(String filename) { playSoundWithGain(filename, 0f); }
    public static void playButton() { playSound("button_sound.wav"); }
    public static void playAttack() { playSound("attack_sound.wav"); }
    public static void playHeal() { playSound("hpmanaheal_sound.wav"); }
    public static void playVictory() { playSoundWithGain("victory_sound.wav", 6f); }
    public static void playDefeat() { playSound("defeat_sound.wav"); }

    public static void startTypingSound() {
        new Thread(() -> {
            String[] paths = {"Main/typingsound.wav", "The Hero's Promise/Main/typingsound.wav", "typingsound.wav"};
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

    public static void stopTypingSound() { if (typingClip != null && typingClip.isRunning()) { typingClip.stop(); typingClip.close(); typingClip = null; } }

    public static void fadeMusicOut(int durationMs) {
        Clip clip = TerminalMusic.getMusicClip();
        if (clip == null || !clip.isRunning()) return;
        try {
            FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float start = vol.getValue(), min = vol.getMinimum();
            int steps = 40, stepMs = durationMs / steps;
            float delta = (start - min) / steps;
            for (int i = 0; i < steps; i++) { vol.setValue(Math.max(start - delta * (i + 1), min)); Thread.sleep(stepMs); }
            clip.stop();
            vol.setValue(start);
        } catch (Exception ignored) {}
    }

    public static void fadeMusicIn(int durationMs) {
        Clip clip = TerminalMusic.getMusicClip();
        if (clip == null) return;
        try {
            FloatControl vol = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float target = vol.getValue(), min = vol.getMinimum();
            vol.setValue(min);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            int steps = 60, stepMs = durationMs / steps;
            float delta = (target - min) / steps;
            for (int i = 0; i < steps; i++) { vol.setValue(Math.min(min + delta * (i + 1), target)); Thread.sleep(stepMs); }
            vol.setValue(target);
        } catch (Exception ignored) {}
    }
}
