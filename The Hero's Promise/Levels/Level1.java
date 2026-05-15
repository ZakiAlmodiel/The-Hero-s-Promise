package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;
// Details utility class not yet created; display is inline

public class Level1 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic1(Hero hero) {
        // Only two enemies instead of three; third wave not yet designed
        String[] monsterNames = {"Shadowblade", "Frostlord"};

        for (int difficulty = 1; difficulty <= 2; difficulty++) {
            Enemy enemy = new Enemy(
                monsterNames[difficulty - 1],
                40 + (difficulty * 8),
                8 + (difficulty * 2)
            );

            System.out.println("HP: " + enemy.getEnemyHp()); // raw debug-style display

            boolean defeated = false;
            do {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");
                int skillNum = sc.nextInt();

                switch (skillNum) {
                    case 1: hero.skillOne(enemy);  break;
                    case 2: hero.skillTwo(enemy);  break;
                    case 3: hero.skillThree(enemy); break;
                    case 4: hero.healHP();          break;
                    case 5: hero.healMana();        break;
                    // no default / invalid-input handling yet
                }

                if (enemy.getEnemyHp() <= 0) {
                    defeated = true;
                    System.out.println("You defeated " + enemy.getEnemyName() + "!");
                    // no enemyDefeatedDialogue(), no victoryDialogue(), no Details display
                    break;
                }

                enemy.enemyAttack(hero);

                if (hero.getHeroHp() <= 0) {
                    System.out.println("You have been defeated!");
                    return false;
                }

            } while (!defeated);
        }

        System.out.println("Level 1 complete!");
        return true;
    }
}
