package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class BackgroundPanel extends JPanel {
    private BufferedImage image;
    
    public BackgroundPanel() {
        setOpaque(true);
        setBackground(new Color(10, 8, 5));
    }
    
    public void setImage(BufferedImage img) {
        this.image = img;
        repaint();
    }
    
    public BufferedImage loadBackground(String name) {
        try {
            String path = "Images/" + name + ".png";
            File f = new File(path);
            if (f.exists()) {
                return ImageIO.read(f);
            }
        } catch (Exception e) {
            System.out.println("Failed to load background: " + name);
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(10, 8, 5));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}