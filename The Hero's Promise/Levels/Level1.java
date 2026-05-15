package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;
import Story.Details;

public class Level1 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic1(Hero hero) {
        boolean allEnemiesDefeated = true;
        String[] monsterNames = {"Shadowblade", "Frostlord", "Demonking"};
        int[] monsterHp = {48, 56, 64};
        int[] monsterDmg = {10, 12, 14};

        Story.Story.beforeLevel1();
        hero.preBattleDialogue();

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(monsterNames[difficulty-1], monsterHp[difficulty-1], monsterDmg[difficulty-1]);
            boolean currentBattleWon = false;
            
            System.out.println("\n=================== BATTLE " + difficulty + " ===================");
            System.out.println("You face: " + enemy.getEnemyName() + " (HP: " + enemy.getEnemyHp() + ")");
            Details.displayCurrentDetails(hero, enemy);

            do {
                System.out.println("\n--- Your Turn ---");
                hero.displaySkillOptions();
                System.out.print("Choose action (1-5): ");
                
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Enter number 1-5:");
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
                        System.out.println("Invalid skill choice. Turn lost!");
                        break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    currentBattleWon = true;
                    enemy.enemyDefeatedDialogue();
                    System.out.println("\n✦ VICTORY! ✦ " + enemy.getEnemyName() + " has been defeated!");
                    hero.victoryDialogue();
                    hero.gainExperience(30);
                    System.out.println("+30 XP gained!");
                    break;
                }

                System.out.println("\n--- Enemy Turn ---");
                if (enemy.getEnemyHp() < enemy.getMaxHp() * 0.3 && enemy.getEnemyMana() >= 15) {
                    enemy.strongAttack(hero);
                    enemy.drainMana(15);
                } else {
                    enemy.enemyAttack(hero);
                }

                if (hero.getHeroHp() <= 0) {
                    System.out.println();
                    hero.defeatDialogue();
                    System.out.println("╔════════════════════════════════════╗");
                    System.out.println("║          GAME OVER                 ║");
                    System.out.println("║      You have been defeated!       ║");
                    System.out.println("╚════════════════════════════════════╝");
                    return false;
                }
                
                Details.displayCurrentDetails(hero, enemy);
                System.out.println("----------------------------------------");

            } while (!currentBattleWon);
        }

        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║     CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        Story.Story.afterLevel1();
        return true;
    }
}