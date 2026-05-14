package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class BackgroundPanel extends JPanel {
    private BufferedImage currentImage;
    private Map<String, BufferedImage> imageCache;
    private String currentBgName;
    private Timer fadeTimer;
    private float currentAlpha = 1.0f;
    private BufferedImage nextImage;
    
    public BackgroundPanel() {
        setOpaque(true);
        setBackground(new Color(10, 8, 5));
        imageCache = new HashMap<>();
        currentBgName = "default";
        loadBackground("chooseherobackground");
    }
    
    public void loadBackground(String name) {
        if (imageCache.containsKey(name)) {
            currentImage = imageCache.get(name);
            currentBgName = name;
            repaint();
            return;
        }
        
        String[] paths = {
            "Images/" + name + ".png",
            "The Hero's Promise/Images/" + name + ".png",
            name + ".png"
        };
        
        for (String path : paths) {
            try {
                File f = new File(path);
                if (f.exists()) {
                    BufferedImage img = ImageIO.read(f);
                    if (img != null) {
                        imageCache.put(name, img);
                        currentImage = img;
                        currentBgName = name;
                        repaint();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to load: " + path);
            }
        }
        
        System.out.println("Background not found: " + name);
        currentImage = null;
        repaint();
    }
    
    public void fadeToBackground(String name, int durationMs) {
        BufferedImage newImg = imageCache.get(name);
        if (newImg == null) {
            loadBackground(name);
            newImg = imageCache.get(name);
        }
        
        if (newImg == null || newImg == currentImage) return;
        
        nextImage = newImg;
        currentAlpha = 1.0f;
        
        if (fadeTimer != null && fadeTimer.isRunning()) {
            fadeTimer.stop();
        }
        
        long startTime = System.currentTimeMillis();
        fadeTimer = new Timer(16, e -> {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= durationMs) {
                currentImage = nextImage;
                currentBgName = name;
                currentAlpha = 1.0f;
                nextImage = null;
                fadeTimer.stop();
                repaint();
            } else {
                currentAlpha = 1.0f - ((float) elapsed / durationMs);
                repaint();
            }
        });
        fadeTimer.start();
    }
    
    public void setImage(BufferedImage img) {
        this.currentImage = img;
        repaint();
    }
    
    public String getCurrentBgName() {
        return currentBgName;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        if (currentImage != null) {
            g2.drawImage(currentImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            GradientPaint gradient = new GradientPaint(0, 0, new Color(20, 10, 0), 0, getHeight(), new Color(5, 3, 0));
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        
        if (nextImage != null && currentAlpha < 1.0f) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f - currentAlpha));
            g2.drawImage(nextImage, 0, 0, getWidth(), getHeight(), null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        
        g2.setColor(new Color(0, 0, 0, 60));
        g2.fillRect(0, getHeight() - 50, getWidth(), 50);
        
        GradientPaint vignette = new GradientPaint(0, 0, new Color(0, 0, 0, 0), getWidth() / 2, getHeight() / 2, new Color(0, 0, 0, 80));
        g2.setPaint(vignette);
        g2.fillRect(0, 0, getWidth(), getHeight());
    }
}