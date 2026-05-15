package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;
// Details utility class not yet created; displayCurrentDetails calls are inline prints

public class Level1 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic1(Hero hero) {
        boolean allEnemiesDefeated = true;
        String[] monsterNames = {"Shadowblade", "Frostlord", "Demonking"};
        // All three enemies now present

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(
                monsterNames[difficulty - 1],
                40 + (difficulty * 8),
                8 + (difficulty * 2)
            );

            boolean currentBattleWon = false;

            // Inline display replacing the not-yet-created Details.displayCurrentDetails()
            System.out.println("--- Enemy: " + enemy.getEnemyName() +
                " | HP: " + enemy.getEnemyHp() + " ---");
            System.out.println("--- Your HP: " + hero.getHeroHp() +
                " | MP: " + hero.getHeroMana() + " ---");

            do {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");

                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
                    sc.next();
                }
                int skillNum = sc.nextInt();

                switch (skillNum) {
                    case 1: hero.skillOne(enemy);   break;
                    case 2: hero.skillTwo(enemy);   break;
                    case 3: hero.skillThree(enemy); break;
                    case 4: hero.healHP();           break;
                    case 5: hero.healMana();         break;
                    default:
                        System.out.println("Invalid skill choice.");
                        break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    currentBattleWon = true;
                    enemy.enemyDefeatedDialogue();
                    hero.victoryDialogue();
                    System.out.println("\nCONGRATULATIONS! You have defeated " + enemy.getEnemyName());
                    // Details.displayCurrentDetails not yet called here
                    break;
                }

                enemy.enemyAttack(hero);

                if (hero.getHeroHp() <= 0) {
                    System.out.println();
                    hero.defeatDialogue();
                    System.out.println("---------You have been defeated!---------");
                    System.out.println("----------------GAME OVER!---------------");
                    return false;
                } else {
                    System.out.println("Your HP: " + hero.getHeroHp() +
                        " | Enemy HP: " + enemy.getEnemyHp());
                }

            } while (!currentBattleWon);

            if (!currentBattleWon || hero.getHeroHp() <= 0) {
                allEnemiesDefeated = false;
                break;
            }
        }

        if (allEnemiesDefeated && hero.getHeroHp() > 0) {
            System.out.println("\n----CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1----");
        }

        return allEnemiesDefeated && hero.getHeroHp() > 0;
    }
}
