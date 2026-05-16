package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class SpritePanel extends JPanel {

    private String characterName;
    private BufferedImage image;
    // Animation timer, imageCache, HP/Mana bars, state strings — not yet added

    public SpritePanel(String characterName) {
        this.characterName = characterName;
        setOpaque(false);
        loadImage(characterName + "_idle.png");
    }

    private void loadImage(String filename) {
        try {
            image = ImageIO.read(new File("Images/" + filename));
        } catch (Exception e) {
            image = null; // silent fallback; multi-path search not yet implemented
        }
    }

    public void setSprite(String name, String state) {
        characterName = name;
        loadImage(name + "_" + state + ".png");
        repaint();
        // no auto-return-to-idle timer yet
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            // Fixed-size draw, not scaled to panel; no aspect ratio logic yet
            g.drawImage(image, 10, 10, 120, 120, null);
        }
    }
    // No HP/Mana bars, no name tag, no isHero flag, no symbol mode yet
}
