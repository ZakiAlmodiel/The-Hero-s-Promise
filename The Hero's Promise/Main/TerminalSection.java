package Main;
import javax.swing.*;
import java.awt.*;

public class TerminalSection extends JPanel {
    private JTextPane terminalPane;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    
    public TerminalSection() {
        setLayout(new BorderLayout());
        setBackground(new Color(0, 0, 0, 0));
        
        terminalPane = new JTextPane();
        terminalPane.setEditable(false);
        terminalPane.setBackground(Color.BLACK);
        terminalPane.setForeground(new Color(220, 190, 130));
        terminalPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        scrollPane = new JScrollPane(terminalPane);
        scrollPane.setOpaque(false);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(0, 60));
        
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public JTextPane getTerminalPane() {
        return terminalPane;
    }
    
    public JPanel getButtonPanel() {
        return buttonPanel;
    }
}