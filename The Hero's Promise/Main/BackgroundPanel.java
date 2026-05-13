package Main;
import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {
    public BackgroundPanel() {
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}