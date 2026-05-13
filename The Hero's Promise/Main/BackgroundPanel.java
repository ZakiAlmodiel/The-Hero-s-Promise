package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(10, 8, 5));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}