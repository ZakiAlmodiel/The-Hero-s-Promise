package Main;

public class Main {
    public static void main(String[] args) {
        System.out.println("The Hero's Promise - Alpha v0.3");
        System.out.println("Loading game...");
        System.out.println("Features: Basic battle system, 3 levels, 5 heroes");
        
        TerminalMusic.playMusic();
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            GameEngine engine = new GameEngine(gui);
            engine.start();
        });
    }
}