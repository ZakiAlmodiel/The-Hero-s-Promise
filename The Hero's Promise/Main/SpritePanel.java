package Main;
import javax.swing.*;
import java.awt.*;

public class SpritePanel extends JPanel {
    public SpritePanel() {
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("SPRITE", 50, 50);
    }
}