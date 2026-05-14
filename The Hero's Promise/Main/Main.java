package Main;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                              ║");
        System.out.println("║                 THE HERO'S PROMISE v0.5                     ║");
        System.out.println("║                    Loading game...                          ║");
        System.out.println("║                                                              ║");
        System.out.println("║  Features:                                                  ║");
        System.out.println("║    • 5 Unique Heroes with special abilities                 ║");
        System.out.println("║    • 3 Challenging levels + Final Boss                      ║");
        System.out.println("║    • Level up system with stat progression                  ║");
        System.out.println("║    • Dynamic turn-based combat                              ║");
        System.out.println("║    • Rich story with multiple chapters                      ║");
        System.out.println("║    • Full GUI with animated sprites                         ║");
        System.out.println("║                                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        TerminalMusic.playMusic();
        
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI();
            GameEngine engine = new GameEngine(gui);
            engine.start();
        });
    }
}