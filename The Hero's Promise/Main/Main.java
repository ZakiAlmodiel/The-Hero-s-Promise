package Main;

public class Main {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    THE HERO'S PROMISE v0.6                 ║");
        System.out.println("║                                                              ║");
        System.out.println("║           A Dark Fantasy Adventure RPG                      ║");
        System.out.println("║           Featuring: Leveling System, XP, Boss Battles      ║");
        System.out.println("║           Exception Handling, Full GUI Integration          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        javax.swing.SwingUtilities.invokeLater(() -> new GameGUI());
    }
}