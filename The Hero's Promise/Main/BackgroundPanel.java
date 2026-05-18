package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BackgroundPanel extends JPanel {

    private BufferedImage image;
    private final GameGUI gui;

    public BackgroundPanel(GameGUI gui) {
        this.gui = gui;
        setOpaque(true);
        setBackground(new Color(10, 8, 5));
        image = gui.loadBg("background_terminal");
    }

    public void setImage(BufferedImage img) {
        this.image = img;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            // Gradient fade at bottom added; values slightly off from final
            GradientPaint fade = new GradientPaint(
                0, getHeight() - 60, new Color(0, 0, 0, 0),
                0, getHeight(),      new Color(0, 0, 0, 180));
            g2.setPaint(fade);
            g2.fillRect(0, getHeight() - 60, getWidth(), 60);
        } else {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(20, 10, 0));
            g2.fillRect(0, 0, getWidth(), getHeight());
            // Fallback gradient not yet refined to two-stop GradientPaint
        }
    }
}
