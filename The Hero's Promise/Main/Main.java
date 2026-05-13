package Main;

public class Main {
    public static void main(String[] args) {
        System.out.println("The Hero's Promise - Alpha v0.2");
        System.out.println("Loading game...");
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            GameEngine engine = new GameEngine(gui);
            engine.start();
        });
    }
}