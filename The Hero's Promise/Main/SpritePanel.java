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
    
    public SpritePanel(String characterName, boolean isHero) {
        this.characterName = characterName;
        this.isHero = isHero;
        setOpaque(false);
        setPreferredSize(new Dimension(300, 300));
    }
    
    public void updateBars(int hp, int maxHp, int mana, int maxMana) {
        this.currentHp = hp;
        this.maxHp = maxHp;
        this.currentMana = mana;
        this.maxMana = maxMana;
        this.showBars = true;
        repaint();
    }
    
    public void showSymbol() {
        showBars = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 20));
        
        if (showBars) {
            g.drawString(characterName, 50, 50);
            g.drawString("HP: " + currentHp + "/" + maxHp, 50, 100);
            g.drawString("MP: " + currentMana + "/" + maxMana, 50, 130);
        } else {
            g.drawString(isHero ? "HERO" : "ENEMY", 120, 150);
        }
    }
}
