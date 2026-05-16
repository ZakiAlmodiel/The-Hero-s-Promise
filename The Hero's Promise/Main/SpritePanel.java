package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpritePanel extends JPanel {
    private String characterName;
    private boolean isHero;
    private int currentHp = 100;
    private int maxHp = 100;
    private int currentMana = 100;
    private int maxMana = 100;
    private boolean showBars = false;
    private boolean useSymbol = true;
    private String currentState = "idle";
    private Map<String, BufferedImage> spriteCache;
    
    public SpritePanel(String characterName, boolean isHero) {
        this.characterName = characterName;
        this.isHero = isHero;
        this.spriteCache = new HashMap<>();
        setOpaque(false);
        setPreferredSize(new Dimension(350, 400));
        setMinimumSize(new Dimension(250, 300));
    }
    
    public void setCharacterName(String name) {
        this.characterName = name;
        repaint();
    }
    
    public void setSprite(String name, String state) {
        this.characterName = name;
        this.currentState = state;
        this.useSymbol = false;
        loadSprite(name, state);
        repaint();
        
        if (!state.equals("idle")) {
            Timer timer = new Timer(500, e -> {
                currentState = "idle";
                repaint();
            });
            timer.setRepeats(false);
            timer.start();
        }
    }
    
    private void loadSprite(String name, String state) {
        String key = name + "_" + state;
        if (spriteCache.containsKey(key)) return;
        
        String filename = name + "_" + state + ".png";
        String[] paths = {"Images/" + filename, "The Hero's Promise/Images/" + filename, filename};
        
        for (String path : paths) {
            try {
                File f = new File(path);
                if (f.exists()) {
                    BufferedImage img = ImageIO.read(f);
                    if (img != null) {
                        spriteCache.put(key, img);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to load sprite: " + path);
            }
        }
    }
    
    public void updateBars(int hp, int maxHp, int mana, int maxMana) {
        this.currentHp = hp;
        this.maxHp = maxHp;
        this.currentMana = mana;
        this.maxMana = maxMana;
        this.showBars = true;
        this.useSymbol = false;
        repaint();
    }
    
    public void showSymbol() {
        this.showBars = false;
        this.useSymbol = true;
        this.currentState = "idle";
        repaint();
    }
    
    public void hideBars() {
        this.showBars = false;
        repaint();
    }
    
    private void drawHealthBar(Graphics2D g2, int x, int y, int width, int height, int current, int max, Color color) {
        g2.setColor(new Color(40, 40, 40));
        g2.fillRect(x, y, width, height);
        
        int fillWidth = (int)((double)current / max * width);
        g2.setColor(color);
        g2.fillRect(x, y, fillWidth, height);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRect(x, y, width, height);
        
        String text = current + "/" + max;
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        FontMetrics fm = g2.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(text, textX, textY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int w = getWidth();
        int h = getHeight();
        
        if (useSymbol) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 28));
            g2.setColor(new Color(255, 200, 100));
            String symbol = isHero ? "⚔️ HERO" : "👾 ENEMY";
            FontMetrics fm = g2.getFontMetrics();
            int x = (w - fm.stringWidth(symbol)) / 2;
            g2.drawString(symbol, x, h / 2);
        }
        
        if (showBars) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 16));
            g2.setColor(new Color(255, 200, 100));
            FontMetrics fm = g2.getFontMetrics();
            int nameX = (w - fm.stringWidth(characterName)) / 2;
            g2.drawString(characterName, nameX, 35);
            
            drawHealthBar(g2, 40, 60, w - 80, 18, currentHp, maxHp, new Color(200, 50, 50));
            drawHealthBar(g2, 40, 88, w - 80, 18, currentMana, maxMana, new Color(50, 100, 220));
        }
        
        String key = characterName + "_" + currentState;
        if (spriteCache.containsKey(key)) {
            BufferedImage sprite = spriteCache.get(key);
            int spriteW = Math.min(sprite.getWidth(), w - 40);
            int spriteH = (int)((double)spriteW / sprite.getWidth() * sprite.getHeight());
            int x = (w - spriteW) / 2;
            int y = showBars ? 120 : 80;
            g2.drawImage(sprite, x, y, spriteW, spriteH, null);
        }
    }
}
