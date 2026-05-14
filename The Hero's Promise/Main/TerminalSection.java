package Main;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TerminalSection extends JPanel {
    private JTextPane terminalPane;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private StyledDocument doc;
    private Style defaultStyle;
    private Style errorStyle;
    private Style successStyle;
    private Style warningStyle;
    private BufferedImage bgImage;
    private ArrayList<String> commandHistory;
    private int historyIndex = 0;
    
    public TerminalSection() {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(5, 3, 0));
        
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(new Color(0, 0, 0, 200));
        terminalPane.setForeground(new Color(220, 190, 130));
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 13));
        terminalPane.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
        
        doc = terminalPane.getStyledDocument();
        
        defaultStyle = terminalPane.addStyle("Default", null);
        StyleConstants.setForeground(defaultStyle, new Color(220, 190, 130));
        StyleConstants.setFontFamily(defaultStyle, "Monospaced");
        StyleConstants.setFontSize(defaultStyle, 13);
        
        errorStyle = terminalPane.addStyle("Error", null);
        StyleConstants.setForeground(errorStyle, new Color(220, 80, 80));
        StyleConstants.setFontFamily(errorStyle, "Monospaced");
        StyleConstants.setFontSize(errorStyle, 13);
        StyleConstants.setBold(errorStyle, true);
        
        successStyle = terminalPane.addStyle("Success", null);
        StyleConstants.setForeground(successStyle, new Color(80, 200, 80));
        StyleConstants.setFontFamily(successStyle, "Monospaced");
        StyleConstants.setFontSize(successStyle, 13);
        
        warningStyle = terminalPane.addStyle("Warning", null);
        StyleConstants.setForeground(warningStyle, new Color(255, 200, 50));
        StyleConstants.setFontFamily(warningStyle, "Monospaced");
        StyleConstants.setFontSize(warningStyle, 13);
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(100, 50, 0)));
        scrollPane.getVerticalScrollBar().setBackground(new Color(15, 10, 5));
        scrollPane.getVerticalScrollBar().setForeground(new Color(180, 80, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
        
        commandHistory = new ArrayList<>();
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void setBackgroundImage(BufferedImage img) {
        this.bgImage = img;
        repaint();
    }
    
    public JTextPane getTerminalPane() {
        return terminalPane;
    }
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
    
    public void appendText(String text) {
        appendStyled(text, defaultStyle);
    }
    
    public void appendError(String text) {
        appendStyled(text, errorStyle);
    }
    
    public void appendSuccess(String text) {
        appendStyled(text, successStyle);
    }
    
    public void appendWarning(String text) {
        appendStyled(text, warningStyle);
    }
    
    public void appendColored(String text, Color color) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 13);
        
        try {
            doc.insertString(doc.getLength(), text, attrs);
            terminalPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    private void appendStyled(String text, Style style) {
        try {
            doc.insertString(doc.getLength(), text, style);
            terminalPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void clear() {
        try {
            doc.remove(0, doc.getLength());
            commandHistory.clear();
            historyIndex = 0;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    public void addToHistory(String command) {
        commandHistory.add(command);
        historyIndex = commandHistory.size();
    }
    
    public void clearButtons() {
        buttonPanel.removeAll();
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    public void addButton(String text, Color bgColor, Runnable action) {
        JButton btn = createTerminalButton(text, bgColor);
        btn.addActionListener(e -> action.run());
        buttonPanel.add(btn);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    private JButton createTerminalButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Monospaced", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.brighter(), 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    public void printAsciiArt(String art) {
        appendColored(art + "\n", new Color(100, 200, 255));
    }
    
    public void printSeparator() {
        appendColored("═══════════════════════════════════════════════════════════════════════\n", new Color(100, 50, 0));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            g2.setColor(new Color(0, 0, 0, 190));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}