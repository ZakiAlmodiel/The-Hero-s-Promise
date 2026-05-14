package Main;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TerminalSection extends JPanel {
    private BufferedImage bgImage;
    private final JTextPane terminalPane;
    private final JScrollPane scrollPane;
    private final JPanel buttonPanel;

    public TerminalSection(GameGUI gui) {
        setLayout(new BorderLayout(0, 0));
        setOpaque(false);
        bgImage = gui.loadBg("background_terminal");
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setOpaque(false);
        terminalPane.setBackground(new Color(0, 0, 0, 0));
        terminalPane.setForeground(GameGUI.TEXT_COLOR);
        terminalPane.setFont(GameGUI.GAME_FONT);
        terminalPane.setMargin(new Insets(10, 16, 10, 16));
        terminalPane.setCaretColor(GameGUI.ORANGE_BRIGHT);
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, GameGUI.BORDER_COLOR));
        scrollPane.getVerticalScrollBar().setBackground(GameGUI.BG_BLACK);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() { @Override protected void configureScrollBarColors() { thumbColor = GameGUI.ORANGE_DIM; trackColor = new Color(15, 10, 5); } });
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6)) { @Override protected void paintComponent(Graphics g) { Graphics2D g2 = (Graphics2D) g; g2.setColor(new Color(0, 0, 0, 160)); g2.fillRect(0, 0, getWidth(), getHeight()); super.paintComponent(g); } };
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(6, 12, 10, 12));
        buttonPanel.setPreferredSize(new Dimension(0, 80));
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setBgImage(BufferedImage img) { bgImage = img; repaint(); }
    public JTextPane getTerminalPane() { return terminalPane; }
    public JPanel getButtonPanel() { return buttonPanel; }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (bgImage != null) { g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null); }
        else { g2.setColor(new Color(12, 8, 4)); g2.fillRect(0, 0, getWidth(), getHeight()); }
        super.paintComponent(g);
    }
}