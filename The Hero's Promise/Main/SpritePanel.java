package Main;
import javax.swing.*;
import java.awt.*;

public class SpritePanel extends JPanel {
    private String characterName;
    private boolean isHero;
    private int currentHp = 100;
    private int maxHp = 100;
    private int currentMana = 100;
    private int maxMana = 100;
    private boolean showBars = false;
    private boolean useSymbol = true;
    
    public SpritePanel(String characterName, boolean isHero) {
        this.characterName = characterName;
        this.isHero = isHero;
        setOpaque(false);
        setPreferredSize(new Dimension(300, 350));
    }
    
    public void setCharacterName(String name) {
        this.characterName = name;
        repaint();
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
        repaint();
    }
    
    public void hideBars() {
        this.showBars = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(255, 140, 0));
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        int w = getWidth();
        
        if (useSymbol && !showBars) {
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            String symbol = isHero ? "⭐ HERO" : "👾 ENEMY";
            FontMetrics fm = g.getFontMetrics();
            int x = (w - fm.stringWidth(symbol)) / 2;
            g.drawString(symbol, x, 150);
        }
        
        if (showBars) {
            g.setFont(new Font("SansSerif", Font.BOLD, 18));
            FontMetrics fm = g.getFontMetrics();
            int nameX = (w - fm.stringWidth(characterName)) / 2;
            g.drawString(characterName, nameX, 50);
            
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            g.setColor(Color.RED);
            g.drawString("HP: " + currentHp + "/" + maxHp, 50, 100);
            g.setColor(new Color(50, 100, 220));
            g.drawString("MP: " + currentMana + "/" + maxMana, 50, 130);
        }
    }
}
