package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundPanel extends JPanel {

    private BufferedImage image;

    public BackgroundPanel() {
        // gui reference not yet introduced; image loaded externally and set
        setOpaque(true);
        setBackground(Color.BLACK);
    }

    public void setImage(BufferedImage img) {
        this.image = img;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Simple stretch draw; no bilinear hint, no gradient fade overlay yet
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        }
        // No fallback gradient yet; blank black if null
    }
}
