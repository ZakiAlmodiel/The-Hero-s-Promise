package Main;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class TerminalSection extends JPanel {
    private JTextPane terminalPane;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private StyledDocument doc;
    
    public TerminalSection() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(Color.BLACK);
        terminalPane.setForeground(new Color(220, 190, 130));
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        doc = terminalPane.getStyledDocument();
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public JTextPane getTerminalPane() {
        return terminalPane;
    }
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
    
    public void appendColored(String text, Color color) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
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
}