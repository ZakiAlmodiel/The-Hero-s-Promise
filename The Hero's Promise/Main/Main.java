package Main;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║              THE HERO'S PROMISE v0.4               ║");
        System.out.println("║                  Loading game...                   ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        
        TerminalMusic.playMusic();
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            GameEngine engine = new GameEngine(gui);
            engine.start();
        });
    }
}