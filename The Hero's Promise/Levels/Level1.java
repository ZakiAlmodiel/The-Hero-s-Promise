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

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(
                monsterNames[difficulty-1],
                40 + (difficulty * 8),
                8 + (difficulty * 2)
            );

            boolean currentBattleWon = false;
            Details.displayCurrentDetails(hero, enemy);

            do {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");
                
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number.");
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
                        System.out.println("Invalid skill choice. Please pick a valid option.");
                        break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    currentBattleWon = true;
                    enemy.enemyDefeatedDialogue();
                    Details.displayCurrentDetails(hero, enemy);
                    hero.victoryDialogue();
                    System.out.println("\nCONGRATULATIONS! You have defeated the monster " + enemy.getEnemyName());
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
                    Details.displayCurrentDetails(hero, enemy);
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
