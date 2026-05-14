package Story;
import Enemy.Enemy;
import Heroes.Hero;

public class Details {
    public static void displayPickingDetails(Hero hero) {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║                   HERO DETAILS                     ║");
        System.out.println("╠════════════════════════════════════════════════════╣");
        System.out.printf("║  Hero        : %-30s ║\n", hero.getHeroName());
        System.out.printf("║  Level       : %-30d ║\n", hero.getLevel());
        System.out.printf("║  HP          : %-3d / %-3d                        ║\n", hero.getHeroHp(), hero.getMaxHp());
        System.out.printf("║  MP          : %-3d / %-3d                        ║\n", hero.getHeroMana(), hero.getMaxMana());
        System.out.println("╚════════════════════════════════════════════════════╝");
    }

    public static void displayCurrentDetails(Hero hero, Enemy enemy) {
        System.out.println();
        System.out.println("╔════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║  %-20s ║  VS  ║  %-30s ║\n", hero.getHeroName(), enemy.getEnemyName());
        System.out.println("╠════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║  HP: %-3d / %-3d                    ║     ║  HP: %-3d / %-3d                    ║\n", 
                          hero.getHeroHp(), hero.getMaxHp(), enemy.getEnemyHp(), enemy.getMaxHp());
        System.out.printf("║  MP: %-3d / %-3d                    ║     ║  MP: %-3d / 100                   ║\n",
                          hero.getHeroMana(), hero.getMaxMana(), enemy.getEnemyMana());
        
        int heroPercent = (hero.getHeroHp() * 100) / hero.getMaxHp();
        int enemyPercent = (enemy.getEnemyHp() * 100) / enemy.getMaxHp();
        
        System.out.print("║  ");
        drawSmallBar(System.out, heroPercent, 20);
        System.out.print(" ║     ║  ");
        drawSmallBar(System.out, enemyPercent, 20);
        System.out.println(" ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════╝");
    }
    
    private static void drawSmallBar(java.io.PrintStream out, int percent, int width) {
        int filled = (percent * width) / 100;
        out.print("[");
        for (int i = 0; i < filled; i++) out.print("█");
        for (int i = filled; i < width; i++) out.print("░");
        out.print("] " + percent + "%");
    }
}