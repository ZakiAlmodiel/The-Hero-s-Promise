package Console_Levels;
import java.util.Scanner;

import Console_Enemy.Console_Enemy;
import Console_Heroes.Console_Hero;
import Console_Story.Console_Details;

public class Console_Level2 {

    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic1(Console_Hero hero) {
        boolean allEnemiesDefeated = true; // Track if all enemies were defeated
        String[] monsterNames = {"Shadowblade", "Frostlord", "Demonking"};

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Console_Enemy enemy = new Console_Enemy(
                monsterNames[difficulty-1],
                40 + (difficulty * 8),
                8 + (difficulty * 2)
            );

            boolean currentBattleWon = false;
            Console_Details.displayCurrentDetails(hero, enemy);

            do {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");
                
                while (!sc.hasNextInt()) {
                    System.out.println(
                        "Invalid input. Please enter a number."
                    );
                    sc.next();
                }
                int skillNum = sc.nextInt();

                switch (skillNum) {
                    case 1:
                        if (hero.heroMana >= hero.skillOneManaCost) {
                            System.out.println();
                            hero.skillOne(enemy, hero);
                        } else {
                            System.out.println();
                            System.out.println(
                                "Not enough mana for Skill 1! Replenish mana"
                            );
                        }
                        break;

                    case 2:
                        if (hero.heroMana >= hero.skillTwoManaCost) {
                            System.out.println();
                            hero.skillTwo(enemy, hero);
                        } else {
                            System.out.println();
                            System.out.println(
                                "Not enough mana for Skill 2! Replenish mana"
                            );
                        }
                        break;

                    case 3:
                        if (hero.heroMana >= hero.skillThreeManaCost) {
                            System.out.println();
                            hero.skillThree(enemy, hero);
                        } else {
                            System.out.println();
                            System.out.println(
                                "Not enough mana for Skill 3! Replenish mana"
                            );
                        }
                        break;

                    case 4:
                        hero.healHP(hero);
                        break;

                    case 5:
                        hero.healMana(hero);
                        break;

                    default:
                        System.out.println(
                            "Invalid skill choice. Please pick a valid option."
                        );
                        break;
                }

                enemy.enemyAttack(hero);

                if (enemy.enemyHp <= 0) {
                    enemy.enemyHp = 0;
                    currentBattleWon = true;
                    Console_Details.displayCurrentDetails(hero, enemy);
                    hero.victoryDialogue();
                    System.out.println(
                        "\nCONGRATULATIONS! You have defeated the monster " +
                        enemy.enemyName
                    );
                    break;
                } else if (hero.heroHp <= 0) {
                    System.out.println();
                    hero.defeatDialogue();
                    System.out.println(
                        "---------You have been defeated!---------"
                    );
                    System.out.println(
                        "----------------GAME OVER!---------------"
                    );
                    allEnemiesDefeated = false;
                    return false;
                } else {
                    Console_Details.displayCurrentDetails(hero, enemy);
                }

            } while (!currentBattleWon);

            if (!currentBattleWon || hero.heroHp <= 0) {
                allEnemiesDefeated = false;
                break;
            }
        }

        if (allEnemiesDefeated && hero.heroHp > 0) {
            System.out.println(
                "\n----CONGRATULATIONS! YOU HAVE FINISHED LEVEL 2----"
            );
        }

        return allEnemiesDefeated && hero.heroHp > 0;
    }
}
