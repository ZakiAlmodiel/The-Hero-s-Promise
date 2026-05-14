package Main;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameGUI extends JFrame {
    private JPanel mainPanel;
    private JTextPane terminalPane;
    private JPanel buttonPanel;
    private SpritePanel heroSpritePanel;
    private SpritePanel enemySpritePanel;
    private BackgroundPanel bgPanel;
    private StyledDocument terminalDoc;
    private JScrollPane scrollPane;
    private JLabel titleLabel;
    private JLabel statusLabel;
    
    public GameGUI() {
        setTitle("The Hero's Promise - v0.5");
        setSize(1280, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 8, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        
        bgPanel = new BackgroundPanel();
        bgPanel.setLayout(new BorderLayout());
        bgPanel.setPreferredSize(new Dimension(1280, 420));
        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 5, 20));
        
        titleLabel = new JLabel("⚔️ THE HERO'S PROMISE ⚔️");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 180, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        statusLabel = new JLabel("✨ Choose your hero ✨");
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(180, 120, 50));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        topBar.add(titleLabel, BorderLayout.NORTH);
        topBar.add(statusLabel, BorderLayout.SOUTH);
        
        JPanel spritePanel = new JPanel(new GridLayout(1, 2, 20, 0));
        spritePanel.setOpaque(false);
        spritePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        heroSpritePanel = new SpritePanel("HERO", true);
        enemySpritePanel = new SpritePanel("ENEMY", false);
        spritePanel.add(heroSpritePanel);
        spritePanel.add(enemySpritePanel);
        
        bgPanel.add(topBar, BorderLayout.NORTH);
        bgPanel.add(spritePanel, BorderLayout.CENTER);
        
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(new Color(0, 0, 0, 220));
        terminalPane.setForeground(new Color(220, 190, 130));
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
        terminalPane.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        terminalDoc = terminalPane.getStyledDocument();
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 50, 0), 2),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        scrollPane.setPreferredSize(new Dimension(1280, 320));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setBackground(new Color(15, 10, 5));
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
        
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
        statusLabel.setText("⚔️ Fighting: " + name + " ⚔️");
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
    
    public void fadeBackground(String bgName, int durationMs) {
        bgPanel.fadeToBackground(bgName, durationMs);
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
        JButton btn = createStyledButton(text, color);
        btn.addActionListener((ActionEvent e) -> action.run());
        buttonPanel.add(btn);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.brighter(), 1),
            BorderFactory.createEmptyBorder(8, 16, 8, 16)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
                SoundManager.playButton();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        return btn;
    }
    
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }
}