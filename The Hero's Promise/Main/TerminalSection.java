package Main;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TerminalSection extends JPanel {
    private JTextPane terminalPane;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private StyledDocument doc;
    private Style defaultStyle;
    private BufferedImage bgImage;
    
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
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(100, 50, 0)));
        scrollPane.getVerticalScrollBar().setBackground(new Color(15, 10, 5));
        scrollPane.getVerticalScrollBar().setForeground(new Color(180, 80, 0));
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
        
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
        appendText(text, new Color(220, 190, 130));
    }
    
    public void appendText(String text, Color color) {
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
    
    public void clear() {
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}