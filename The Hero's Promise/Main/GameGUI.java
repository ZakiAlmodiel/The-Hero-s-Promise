package Main;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameGUI extends JFrame {

    public static final Color BG_BLACK = new Color(10, 8, 5);
    public static final Color ORANGE_BRIGHT = new Color(255, 140, 0);
    public static final Color ORANGE_DIM = new Color(180, 80, 0);
    public static final Color ORANGE_GLOW = new Color(255, 180, 60);
    public static final Color TEXT_COLOR = new Color(220, 190, 130);
    public static final Color BORDER_COLOR = new Color(100, 50, 0);

    public static Font GAME_FONT;
    static {
        Font f = null;
        String[] fontPaths = {"Main/PressStart2P-Regular.ttf", "The Hero's Promise/Main/PressStart2P-Regular.ttf", "Main/game_font.ttf"};
        for (String path : fontPaths) {
            try {
                f = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(Font.PLAIN, 16f);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(f);
                break;
            } catch (Exception ignored) {}
        }
        GAME_FONT = (f != null) ? f : new Font("Monospaced", Font.PLAIN, 16);
    }

    private final Map<String, BufferedImage> bgCache = new HashMap<>();
    private JPanel mainPanel, menuPanel, titlePanel, gamePanel;
    private JLayeredPane gameLayered;
    private BackgroundPanel bgPanel;
    JPanel spriteArea;
    SpritePanel heroSpritePanel, enemySpritePanel;
    private TerminalSection terminalSection;
    private JPanel storyOverlay;
    private JLabel storyTextLabel;
    private String storyBgName = "";
    JPanel buttonPanel;
    List<JButton> actionButtons = new ArrayList<>();
    List<Boolean> buttonIsHeal = new ArrayList<>();
    private final java.util.concurrent.LinkedBlockingQueue<String> typeQueue = new java.util.concurrent.LinkedBlockingQueue<>();
    private volatile boolean typing = false;
    private static final int TYPE_DELAY_MS = 16;
    GameEngine engine;

    public GameGUI() {
        setTitle("The Hero's Promise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 820);
        setMinimumSize(new Dimension(900, 700));
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(BG_BLACK);

        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(BG_BLACK);
        buildMenuScreen();
        buildTitleScreen();
        buildGameScreen();

        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(titlePanel, "TITLE");
        mainPanel.add(gamePanel, "GAME");
        setContentPane(mainPanel);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                SwingUtilities.invokeLater(() -> layoutGameLayers());
            }
        });
        addWindowStateListener(e -> SwingUtilities.invokeLater(() -> layoutGameLayers()));

        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "MENU");
        setVisible(true);
    }

    public BufferedImage loadBg(String name) {
        if (bgCache.containsKey(name)) return bgCache.get(name);
        String[] paths = {"Images/" + name + ".png", "The Hero's Promise/Images/" + name + ".png", name + ".png"};
        for (String p : paths) {
            try {
                BufferedImage img = ImageIO.read(new File(p));
                if (img != null) { bgCache.put(name, img); return img; }
            } catch (Exception ignored) {}
        }
        bgCache.put(name, null);
        return null;
    }

    private void buildMenuScreen() {
        menuPanel = new JPanel(null) {
            private BufferedImage bg;
            @Override public void addNotify() { super.addNotify(); bg = loadBg("menuscreen"); }
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if (bg != null) { g2.drawImage(bg, 0, 0, getWidth(), getHeight(), null); }
                else { g2.setColor(new Color(5, 3, 0)); g2.fillRect(0, 0, getWidth(), getHeight()); }
            }
        };
        menuPanel.setOpaque(true);
        final int BASE_W = 200, BASE_H = 200;
        JLabel playBtn = new JLabel() {
            private BufferedImage btnImg;
            private boolean hovered = false;
            private float currentScale = 1.0f;
            private final float TARGET_NORMAL = 1.0f;
            private final float TARGET_HOVER = 1.15f;
            private Timer animTimer;
            {
                String[] paths = {"Images/playbutton.png", "The Hero's Promise/Images/playbutton.png", "Main/playbutton.png"};
                for (String p : paths) {
                    try { BufferedImage img = ImageIO.read(new File(p)); if (img != null) { btnImg = img; break; } } catch (Exception ignored) {}
                }
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setOpaque(false);
                animTimer = new Timer(16, e -> {
                    float target = hovered ? TARGET_HOVER : TARGET_NORMAL;
                    float diff = target - currentScale;
                    if (Math.abs(diff) < 0.001f) { currentScale = target; ((Timer)e.getSource()).stop(); }
                    else { currentScale += diff * 0.18f; }
                    repositionBtn(this, BASE_W, BASE_H, currentScale);
                    repaint();
                });
                addMouseListener(new MouseAdapter() {
                    @Override public void mouseEntered(MouseEvent e) { hovered = true; animTimer.start(); }
                    @Override public void mouseExited(MouseEvent e) { hovered = false; animTimer.start(); }
                    @Override public void mouseClicked(MouseEvent e) {
                        SoundManager.playButton();
                        ((CardLayout) mainPanel.getLayout()).show(mainPanel, "GAME");
                        startHeroSelection();
                    }
                });
            }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                if (btnImg != null) { g2.drawImage(btnImg, 0, 0, getWidth(), getHeight(), null); }
                else { g2.setColor(new Color(130, 50, 0)); g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); }
            }
        };
        playBtn.setBounds((1100 - BASE_W) / 2, (int)(820 * 0.72) - BASE_H / 2, BASE_W, BASE_H);
        menuPanel.add(playBtn);
        menuPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) { repositionBtn(playBtn, BASE_W, BASE_H, 1.0f); }
        });
    }

    private void repositionBtn(JLabel btn, int baseW, int baseH, float scale) {
        int w = (int)(baseW * scale), h = (int)(baseH * scale);
        btn.setBounds((menuPanel.getWidth() - w) / 2, (int)(menuPanel.getHeight() * 0.72) - h / 2, w, h);
    }

    private void buildTitleScreen() {
        titlePanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(5, 3, 0), 0, getHeight(), new Color(40, 15, 0));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        titlePanel.setOpaque(true);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel titleLabel = new JLabel("THE HERO'S PROMISE");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 54));
        titleLabel.setForeground(ORANGE_GLOW);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, gbc);
    }

    private void buildGameScreen() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(BG_BLACK);
        gameLayered = new JLayeredPane();
        gameLayered.setBackground(BG_BLACK);
        gameLayered.setOpaque(true);
        bgPanel = new BackgroundPanel(this);
        bgPanel.setImage(loadBg("chooseherobackground"));
        spriteArea = new JPanel(new GridLayout(1, 2, 0, 0));
        spriteArea.setOpaque(false);
        heroSpritePanel = new SpritePanel("HERO", true);
        enemySpritePanel = new SpritePanel("ENEMY", false);
        heroSpritePanel.showSymbol();
        enemySpritePanel.showSymbol();
        spriteArea.add(heroSpritePanel);
        spriteArea.add(enemySpritePanel);
        terminalSection = new TerminalSection(this);
        buttonPanel = terminalSection.getButtonPanel();
        storyOverlay = new JPanel(null) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                BufferedImage bg = loadBg(storyBgName);
                if (bg != null) { g2.drawImage(bg, 0, 0, getWidth(), getHeight(), null); }
                else { g2.setColor(Color.BLACK); g2.fillRect(0, 0, getWidth(), getHeight()); }
            }
        };
        storyOverlay.setOpaque(true);
        storyOverlay.setVisible(false);
        storyTextLabel = new JLabel("", SwingConstants.CENTER);
        storyTextLabel.setFont(new Font("Serif", Font.ITALIC, 22));
        storyTextLabel.setForeground(new Color(235, 210, 150));
        storyTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        storyTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        storyOverlay.add(storyTextLabel);
        gameLayered.add(bgPanel, Integer.valueOf(0));
        gameLayered.add(spriteArea, Integer.valueOf(1));
        gameLayered.add(terminalSection, Integer.valueOf(2));
        gameLayered.add(storyOverlay, Integer.valueOf(3));
        gameLayered.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override public void componentResized(java.awt.event.ComponentEvent e) { layoutGameLayers(); }
        });
        gamePanel.add(gameLayered, BorderLayout.CENTER);
    }

    void layoutGameLayers() {
        int w = gameLayered.getWidth(), h = gameLayered.getHeight();
        if (w == 0 || h == 0) return;
        int topH = (int)(h * 0.52), bottomH = h - topH;
        bgPanel.setBounds(0, 0, w, topH);
        spriteArea.setBounds(0, 0, w, topH);
        terminalSection.setBounds(0, topH, w, bottomH);
        storyOverlay.setBounds(0, 0, w, h);
        int padX = (int)(w * 0.12), padY = (int)(h * 0.18);
        storyTextLabel.setBounds(padX, padY, w - padX * 2, h - padY * 2);
        bgPanel.revalidate(); bgPanel.repaint();
        spriteArea.revalidate(); spriteArea.repaint();
        terminalSection.revalidate(); terminalSection.repaint();
        storyOverlay.revalidate(); storyOverlay.repaint();
    }

    public void showStoryScreen(String bgName, String[] lines, int msPerChar) {
        storyBgName = bgName;
        SwingUtilities.invokeLater(() -> { storyTextLabel.setText(""); storyOverlay.setVisible(true); storyOverlay.repaint(); });
        SoundManager.fadeMusicOut(800);
        SoundManager.startTypingSound();
        String[] typedLines = new String[lines.length];
        for (int i = 0; i < lines.length; i++) typedLines[i] = "";
        for (int lineIdx = 0; lineIdx < lines.length; lineIdx++) {
            String line = lines[lineIdx];
            for (int charIdx = 0; charIdx <= line.length(); charIdx++) {
                typedLines[lineIdx] = line.substring(0, charIdx);
                final String html = buildHtml(typedLines);
                SwingUtilities.invokeLater(() -> storyTextLabel.setText(html));
                if (charIdx < line.length()) { try { Thread.sleep(msPerChar); } catch (InterruptedException ignored) {} }
            }
            try { Thread.sleep(280); } catch (InterruptedException ignored) {}
        }
        SoundManager.stopTypingSound();
        try { Thread.sleep(1800); } catch (InterruptedException ignored) {}
        SoundManager.fadeMusicIn(800);
    }

    private String buildHtml(String[] lines) {
        StringBuilder sb = new StringBuilder("<html><div style='text-align:center;'>");
        for (String l : lines) { if (l != null) { sb.append(l.isEmpty() ? "&nbsp;" : l.replace("...", "&#8230;")); sb.append("<br>"); } }
        sb.append("</div></html>");
        return sb.toString();
    }

    public void hideStoryScreen() { SwingUtilities.invokeLater(() -> { storyOverlay.setVisible(false); storyTextLabel.setText(""); }); }
    public void setChooseHeroBackground() { bgPanel.setImage(loadBg("chooseherobackground")); }
    public void setBackground(String bgName) { bgPanel.setImage(loadBg(bgName)); terminalSection.setBgImage(loadBg("background_terminal")); }
    private void startHeroSelection() { SwingUtilities.invokeLater(() -> { layoutGameLayers(); engine = new GameEngine(this); engine.start(); }); }
    public void appendToTerminal(String text) { typeQueue.add(text); if (!typing) startTypingWorker(); }
    public void appendToTerminalColored(String text, Color color) { typeQueue.add("\u0001" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + "\u0001" + text + "\u0002"); if (!typing) startTypingWorker(); }
    private void startTypingWorker() { typing = true; Thread t = new Thread(() -> { while (true) { String chunk = typeQueue.poll(); if (chunk == null) { typing = false; break; } typeChunk(chunk); } }, "TypingWorker"); t.setDaemon(true); t.start(); }
    private void typeChunk(String chunk) {
        if (chunk.startsWith("\u0001")) {
            int end = chunk.indexOf("\u0001", 1);
            if (end > 0) {
                String[] rgb = chunk.substring(1, end).split(",");
                Color color = new Color(Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()), Integer.parseInt(rgb[2].trim()));
                String text = chunk.substring(end + 1);
                if (text.endsWith("\u0002")) text = text.substring(0, text.length() - 1);
                typeCharacters(text, color, true);
                return;
            }
        }
        typeCharacters(chunk, TEXT_COLOR, false);
    }
    private void typeCharacters(String text, Color color, boolean bold) {
        JTextPane tp = terminalSection.getTerminalPane();
        for (int i = 0; i < text.length(); i++) {
            final char ch = text.charAt(i);
            SwingUtilities.invokeLater(() -> {
                StyledDocument doc = tp.getStyledDocument();
                SimpleAttributeSet a = new SimpleAttributeSet();
                StyleConstants.setForeground(a, color);
                StyleConstants.setFontFamily(a, GAME_FONT.getFamily());
                StyleConstants.setFontSize(a, GAME_FONT.getSize());
                StyleConstants.setBold(a, bold);
                try { doc.insertString(doc.getLength(), String.valueOf(ch), a); } catch (BadLocationException ignored) {}
                tp.setCaretPosition(doc.getLength());
            });
            int delay = (ch == '\n' || ch == ' ') ? 4 : TYPE_DELAY_MS;
            try { Thread.sleep(delay); } catch (InterruptedException ignored) {}
        }
    }
    public void clearTerminal() { typeQueue.clear(); SwingUtilities.invokeLater(() -> terminalSection.getTerminalPane().setText("")); }
    public void showButtons(String[] labels, ButtonClickListener listener) {
        SwingUtilities.invokeLater(() -> {
            buttonPanel.removeAll(); actionButtons.clear(); buttonIsHeal.clear();
            for (int i = 0; i < labels.length; i++) {
                final int idx = i;
                boolean isHeal = labels[i].startsWith("4: Heal HP") || labels[i].startsWith("5: Heal Mana");
                JButton btn = isHeal ? createColoredButton(labels[i], 190, 44, 12, new Color(20, 90, 30), new Color(60, 200, 80)) : createOrangeButton(labels[i], 190, 44, 12);
                btn.addActionListener(e -> { SoundManager.playButton(); appendToTerminal("> " + labels[idx] + "\n"); clearButtons(); listener.onButtonClick(idx, labels[idx]); });
                actionButtons.add(btn);
                buttonIsHeal.add(isHeal);
                buttonPanel.add(btn);
            }
            buttonPanel.revalidate(); buttonPanel.repaint();
        });
    }
    public void clearButtons() { SwingUtilities.invokeLater(() -> { buttonPanel.removeAll(); actionButtons.clear(); buttonIsHeal.clear(); buttonPanel.revalidate(); buttonPanel.repaint(); }); }
    public void setHeroSprite(String name, String state) { heroSpritePanel.setSprite(name, state); }
    public void setEnemySprite(String name, String state) { enemySpritePanel.setSprite(name, state); }
    public void setHeroName(String name) { heroSpritePanel.setCharacterName(name); }
    public void setEnemyName(String name) { enemySpritePanel.setCharacterName(name); }
    public void hideEnemy() { enemySpritePanel.hideSprite(); enemySpritePanel.hideBars(); }
    public void showEnemy() { enemySpritePanel.showSprite(); }
    public void showSymbols() { heroSpritePanel.showSymbol(); enemySpritePanel.showSymbol(); }
    public void updateHeroBars(int hp, int maxHp, int mana, int maxMana) { heroSpritePanel.updateBars(hp, maxHp, mana, maxMana); }
    public void updateEnemyBars(int hp, int maxHp, int mana, int maxMana) { enemySpritePanel.updateBars(hp, maxHp, mana, maxMana); }
    public static JButton createOrangeButton(String text, int w, int h, int fontSize) { return createColoredButton(text, w, h, fontSize, new Color(130, 50, 0), ORANGE_BRIGHT); }
    public static JButton createColoredButton(String text, int w, int h, int fontSize, Color bgColor, Color borderColor) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed() ? bgColor.darker() : getModel().isRollover() ? bgColor.brighter() : bgColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(borderColor);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 8, 8);
                g2.setFont(getFont());
                g2.setColor(borderColor.brighter());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
            }
        };
        btn.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        btn.setPreferredSize(new Dimension(w, h));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
    public interface ButtonClickListener { void onButtonClick(int index, String label); }
}