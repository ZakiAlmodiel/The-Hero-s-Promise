package Main;
import java.io.File;
import javax.sound.sampled.*;

public class TerminalMusic {
    private static Clip musicClip;
    private static boolean isPlaying = false;
    private static FloatControl volumeControl;
    private static String currentTrack = "";
    private static float targetVolume = 0.7f;
    private static Timer fadeTimer;
    
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
                        setVolume(0f);
                    }
                    
                    musicClip.loop(Clip.LOOP_CONTINUOUSLY);
                    musicClip.start();
                    isPlaying = true;
                    
                    fadeIn(1000);
                    System.out.println("🎵 Music playing: " + filename);
                    return;
                }
            }
            System.out.println("⚠️ Music file not found: " + filename);
        } catch (Exception e) {
            System.out.println("🎵 Music error: " + e.getMessage());
        }
    }
    
    public static void stopMusic() {
        if (fadeTimer != null) {
            fadeTimer.stop();
        }
        
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
            musicClip.close();
            isPlaying = false;
            System.out.println("🎵 Music stopped");
        }
    }
    
    public static void fadeOut(int durationMs) {
        if (musicClip == null || !musicClip.isRunning()) return;
        
        if (fadeTimer != null) fadeTimer.stop();
        
        long startTime = System.currentTimeMillis();
        float startVolume = getCurrentVolume();
        
        fadeTimer = new Timer(16, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= durationMs) {
                stopMusic();
                fadeTimer.stop();
            } else {
                float volume = startVolume * (1 - (float) elapsed / durationMs);
                setVolume(Math.max(0, volume));
            }
        });
        fadeTimer.start();
    }
    
    public static void fadeIn(int durationMs) {
        if (musicClip == null) return;
        
        if (fadeTimer != null) fadeTimer.stop();
        
        setVolume(0f);
        if (!musicClip.isRunning()) {
            musicClip.start();
        }
        
        long startTime = System.currentTimeMillis();
        
        fadeTimer = new Timer(16, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= durationMs) {
                setVolume(targetVolume);
                fadeTimer.stop();
            } else {
                float volume = targetVolume * ((float) elapsed / durationMs);
                setVolume(volume);
            }
        });
        fadeTimer.start();
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
            float dB;
            
            if (volume <= 0) {
                dB = min;
            } else {
                dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                dB = Math.max(min, Math.min(max, dB));
            }
            
            volumeControl.setValue(dB);
        }
    }
    
    private static float getCurrentVolume() {
        if (volumeControl != null) {
            float dB = volumeControl.getValue();
            return (float) Math.pow(10, dB / 20.0);
        }
        return 0.7f;
    }
    
    public static void setTargetVolume(float volume) {
        targetVolume = Math.max(0f, Math.min(1f, volume));
    }
    
    public static Clip getMusicClip() {
        return musicClip;
    }
    
    public static boolean isPlaying() {
        return isPlaying;
    }
    
    public static String getCurrentTrack() {
        return currentTrack;
    }
}