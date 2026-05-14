package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.text.*;

public class GameGUI extends JFrame {
    private JPanel mainPanel;
    private JTextPane terminalPane;
    private JPanel buttonPanel;
    private SpritePanel heroSpritePanel;
    private SpritePanel enemySpritePanel;
    private BackgroundPanel bgPanel;
    private StyledDocument terminalDoc;
    private JScrollPane scrollPane;
    
    public GameGUI() {
        setTitle("The Hero's Promise - v0.4");
        setSize(1200, 850);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 8, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        bgPanel = new BackgroundPanel();
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setPreferredSize(new Dimension(1200, 400));
        
        JPanel spritePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        spritePanel.setOpaque(false);
        spritePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        heroSpritePanel = new SpritePanel("HERO", true);
        enemySpritePanel = new SpritePanel("ENEMY", false);
        spritePanel.add(heroSpritePanel);
        spritePanel.add(enemySpritePanel);
        
        bgPanel.add(spritePanel, BorderLayout.CENTER);
        
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(new Color(0, 0, 0, 200));
        terminalPane.setForeground(new Color(220, 190, 130));
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
        terminalPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        terminalDoc = terminalPane.getStyledDocument();
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 50, 0), 2));
        scrollPane.setPreferredSize(new Dimension(1200, 320));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel glassPanel = new JPanel(new BorderLayout());
        glassPanel.setOpaque(false);
        glassPanel.add(buttonPanel, BorderLayout.CENTER);
        bottomPanel.add(glassPanel, BorderLayout.CENTER);
        
        mainPanel.add(bgPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    public void appendToTerminal(String text) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, new Color(220, 190, 130));
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 13);
        
        try {
            terminalDoc.insertString(terminalDoc.getLength(), text, attrs);
            terminalPane.setCaretPosition(terminalDoc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void appendToTerminalColored(String text, Color color) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 13);
        
        try {
            terminalDoc.insertString(terminalDoc.getLength(), text, attrs);
            terminalPane.setCaretPosition(terminalDoc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void clearTerminal() {
        try {
            terminalDoc.remove(0, terminalDoc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void setHeroName(String name) {
        heroSpritePanel.setCharacterName(name);
    }
    
    public void setEnemyName(String name) {
        enemySpritePanel.setCharacterName(name);
    }
    
    public void updateHeroBars(int hp, int maxHp, int mana, int maxMana) {
        heroSpritePanel.updateBars(hp, maxHp, mana, maxMana);
    }
    
    public void updateEnemyBars(int hp, int maxHp, int mana, int maxMana) {
        enemySpritePanel.updateBars(hp, maxHp, mana, maxMana);
    }
    
    public void setBackground(String bgName) {
        bgPanel.loadBackground(bgName);
    }
    
    public void showSymbols() {
        heroSpritePanel.showSymbol();
        enemySpritePanel.showSymbol();
    }
    
    public void clearButtons() {
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    public void addButton(String text, Color color, Runnable action) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.addActionListener((ActionEvent e) -> action.run());
        buttonPanel.add(btn);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
}