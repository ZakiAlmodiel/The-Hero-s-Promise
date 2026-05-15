package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;
import Story.Details;
import Story.Story;

public class Level1 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic1(Hero hero) {
        String[] monsterNames = {"Shadowblade", "Frostlord", "Demonking"};
        int[] monsterHp = {48, 56, 64};
        int[] monsterDmg = {10, 12, 14};
        int[] xpRewards = {30, 40, 50};

        Story.beforeLevel1();
        hero.preBattleDialogue();

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(monsterNames[difficulty-1], monsterHp[difficulty-1], monsterDmg[difficulty-1]);
            boolean currentBattleWon = false;
            
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.printf("║                    BATTLE %d - %-20s            ║\n", difficulty, enemy.getEnemyName());
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            Details.displayCurrentDetails(hero, enemy);

            while (!currentBattleWon && hero.getHeroHp() > 0 && enemy.getEnemyHp() > 0) {
                System.out.println("\n┌─────────────────────────────────────────────────┐");
                System.out.println("│                   YOUR TURN                      │");
                System.out.println("└─────────────────────────────────────────────────┘");
                hero.displaySkillOptions();
                System.out.print("\n👉 Choose action (1-5): ");
                
                while (!sc.hasNextInt()) {
                    System.out.println("❌ Invalid input. Enter number 1-5:");
                    sc.next();
                }
                int skillNum = sc.nextInt();

                switch (skillNum) {
                    case 1:
                        hero.skillOne(enemy);
                        break;
                    case 2:
                        hero.skillTwo(enemy);
                        break;
                    case 3:
                        hero.skillThree(enemy);
                        break;
                    case 4:
                        hero.healHP();
                        break;
                    case 5:
                        hero.healMana();
                        break;
                    default:
                        System.out.println("❌ Invalid skill choice! Turn lost.");
                        break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    currentBattleWon = true;
                    enemy.enemyDefeatedDialogue();
                    System.out.println("\n🎉 VICTORY! 🎉");
                    System.out.println("✨ " + enemy.getEnemyName() + " has been defeated!");
                    hero.victoryDialogue();
                    hero.gainExperience(xpRewards[difficulty-1]);
                    break;
                }

                System.out.println("\n┌─────────────────────────────────────────────────┐");
                System.out.println("│                  ENEMY TURN                      │");
                System.out.println("└─────────────────────────────────────────────────┘");
                
                if (enemy.getHpPercentage() < 30 && enemy.getEnemyMana() >= 15) {
                    enemy.heavyAttack(hero);
                } else {
                    enemy.enemyAttack(hero);
                }

                if (hero.getHeroHp() <= 0) {
                    System.out.println();
                    hero.defeatDialogue();
                    System.out.println("\n╔════════════════════════════════════════════════╗");
                    System.out.println("║                   GAME OVER                     ║");
                    System.out.println("║           You have been defeated!               ║");
                    System.out.println("╚════════════════════════════════════════════════╝");
                    return false;
                }
                
                System.out.println();
                Details.displayCurrentDetails(hero, enemy);
            }
            
            if (hero.getHeroHp() > 0) {
                System.out.println("\n✨ You survived! Moving to next battle...\n");
                try { Thread.sleep(1500); } catch (InterruptedException e) {}
            }
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  LEVEL 1 COMPLETE! 🏆                       ║");
        System.out.println("║          CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1        ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        Story.afterLevel1();
        return true;
    }
}