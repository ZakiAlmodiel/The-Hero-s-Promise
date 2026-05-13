package Main;
import javax.swing.*;

public class TerminalSection extends JPanel {
    private JTextArea terminalArea;
    
    public TerminalSection() {
        terminalArea = new JTextArea();
        terminalArea.setText("Terminal output will appear here.");
        add(terminalArea);
    }
}