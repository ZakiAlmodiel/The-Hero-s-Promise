package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SpritePanel extends JPanel {
    private boolean isHero;
    private String characterName;
    private String currentState = "idle";
    private boolean spriteVisible = true;
    private boolean useSymbol = true;
    private int currentHp = 100, maxHp = 100;
    private int currentMana = 100, maxMana = 100;
    private boolean showBars = false;
    private boolean infinityMana = false;
    private final Map<String, BufferedImage> imageCache = new HashMap<>();
    private Timer animationTimer;
    private int animFrame = 0;

    public SpritePanel(String characterName, boolean isHero) {
        this.characterName = characterName;
        this.isHero = isHero;
        setOpaque(false);
        setBorder(null);
        animationTimer = new Timer(600, e -> { animFrame = (animFrame + 1) % 2; repaint(); });
        animationTimer.start();
        preload("hero_symbol", "idle");
        preload("enemy_symbol", "idle");
    }

    public void showSymbol() { useSymbol = true; spriteVisible = true; currentState = "idle"; showBars = false; repaint(); }
    public void setSprite(String name, String state) { characterName = name; currentState = state; spriteVisible = true; useSymbol = false; preload(name, state); repaint(); if (!state.equals("idle")) { Timer t = new Timer(600, e -> { currentState = "idle"; repaint(); }); t.setRepeats(false); t.start(); } }
    public void setCharacterName(String name) { characterName = name; repaint(); }
    public void hideSprite() { spriteVisible = false; useSymbol = false; repaint(); }
    public void showSprite() { spriteVisible = true; repaint(); }
    public void updateBars(int hp, int maxHp, int mana, int maxMana) { this.currentHp = hp; this.maxHp = maxHp; this.currentMana = mana; this.maxMana = maxMana; this.infinityMana = !isHero; this.showBars = true; repaint(); }
    public void hideBars() { showBars = false; repaint(); }

    private void preload(String name, String state) {
        String key = name + "_" + state;
        if (imageCache.containsKey(key)) return;
        boolean isSymbol = name.endsWith("_symbol");
        String filename = isSymbol ? name + ".png" : name + "_" + state + ".png";
        String[] paths = {"Images/" + filename, "The Hero's Promise/Images/" + filename, filename};
        for (String p : paths) { try { BufferedImage img = ImageIO.read(new File(p)); if (img != null) { imageCache.put(key, img); return; } } catch (Exception ignored) {} }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        int w = getWidth(), h = getHeight();
        int barZone = showBars ? 52 : 0;
        if (showBars) drawBars(g2, w);
        if (!spriteVisible) { g2.dispose(); return; }
        String key = useSymbol ? (isHero ? "hero_symbol" : "enemy_symbol") + "_idle" : characterName + "_" + currentState;
        if (!imageCache.containsKey(key)) preload(characterName, currentState);
        BufferedImage img = imageCache.get(key);
        if (img == null) { g2.dispose(); return; }
        int availH = h - barZone, availW = w;
        double scaleFit = Math.min((double) availW / img.getWidth(), (double) availH / img.getHeight());
        double scale = Math.min(scaleFit * 2.0, Math.min((double)(availW - 4) / img.getWidth(), (double)(availH - 4) / img.getHeight()));
        int dw = (int)(img.getWidth() * scale), dh = (int)(img.getHeight() * scale);
        int dx = (w - dw) / 2;
        int gapPx = 38, dy = h - dh - gapPx;
        if (dy < barZone) dy = barZone;
        g2.drawImage(img, dx, dy, dw, dh, null);
        g2.dispose();
    }

    private void drawBars(Graphics2D g2, int w) {
        int bw = w - 20, bh = 14, x = 10, y1 = 6, y2 = y1 + bh + 8;
        drawBar(g2, x, y1, bw, bh, (double) currentHp / maxHp, new Color(200, 50, 50), new Color(60, 10, 10), "HP " + currentHp + "/" + maxHp);
        if (infinityMana) { drawBar(g2, x, y2, bw, bh, 1.0, new Color(50, 100, 220), new Color(10, 20, 60), "MP \u221E"); }
        else { drawBar(g2, x, y2, bw, bh, (double) currentMana / maxMana, new Color(50, 100, 220), new Color(10, 20, 60), "MP " + currentMana + "/" + maxMana); }
        String name = useSymbol ? (isHero ? "HERO" : "ENEMY") : characterName;
        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.setColor(GameGUI.ORANGE_GLOW);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(name, (w - fm.stringWidth(name)) / 2, y2 + bh + 14);
    }

    private void drawBar(Graphics2D g2, int x, int y, int w, int h, double ratio, Color fill, Color bg, String label) {
        g2.setColor(bg);
        g2.fillRoundRect(x, y, w, h, 6, 6);
        int fw = (int)(w * Math.max(0, Math.min(1, ratio)));
        if (fw > 0) { g2.setColor(fill); g2.fillRoundRect(x, y, fw, h, 6, 6); }
        g2.setColor(new Color(200, 200, 200, 60));
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(x, y, w, h, 6, 6);
        g2.setFont(new Font("SansSerif", Font.BOLD, 10));
        g2.setColor(Color.WHITE);
        FontMetrics fm = g2.getFontMetrics();
        int lx = x + (w - fm.stringWidth(label)) / 2;
        int ly = y + (h + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(label, lx, ly);
    }
}
