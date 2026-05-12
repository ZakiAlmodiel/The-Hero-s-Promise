package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;
import Story.Details;

public class Level2 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic2(Hero hero) {
        boolean allEnemiesDefeated = true;
        String[] monsterNames = {"Crypt Lord", "Plagueweaver", "Void Sentinel"};

        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(monsterNames[difficulty-1], 60 + (difficulty * 10), 12 + (difficulty * 3));
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
                        System.out.println("Invalid skill choice.");
                        break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    currentBattleWon = true;
                    enemy.enemyDefeatedDialogue();
                    Details.displayCurrentDetails(hero, enemy);
                    hero.victoryDialogue();
                    System.out.println("\nYou defeated " + enemy.getEnemyName() + "!");
                    break;
                }

                enemy.enemyAttack(hero);
                Details.displayCurrentDetails(hero, enemy);

                if (hero.getHeroHp() <= 0) {
                    hero.defeatDialogue();
                    System.out.println("---------GAME OVER!---------");
                    return false;
                }
            } while (!currentBattleWon);
        }

        System.out.println("\n----LEVEL 2 COMPLETE!----");
        return true;
    }
}
