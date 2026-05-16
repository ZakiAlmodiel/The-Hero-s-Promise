package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;

public class Level2 {
    static Scanner sc = new Scanner(System.in);

    // Level2 was just copy-pasted from Level1 and enemy stats updated;
    // the enemy name array may only have two entries still
    public static boolean battleMechanic2(Hero hero) {
        String[] monsterNames = {"Crypt Lord", "Plagueweaver"};

        for (int difficulty = 1; difficulty <= 2; difficulty++) {
            Enemy enemy = new Enemy(
                monsterNames[difficulty - 1],
                60 + (difficulty * 10),
                12 + (difficulty * 3)
            );

            System.out.println("HP: " + enemy.getEnemyHp());

            boolean defeated = false;
            do {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");
                int skillNum = sc.nextInt();

                switch (skillNum) {
                    case 1: hero.skillOne(enemy);   break;
                    case 2: hero.skillTwo(enemy);   break;
                    case 3: hero.skillThree(enemy); break;
                    case 4: hero.healHP();           break;
                    case 5: hero.healMana();         break;
                }

                if (enemy.getEnemyHp() <= 0) {
                    defeated = true;
                    System.out.println("You defeated " + enemy.getEnemyName() + "!");
                    break;
                }

                enemy.enemyAttack(hero);

                if (hero.getHeroHp() <= 0) {
                    System.out.println("You have been defeated!");
                    return false;
                }
            } while (!defeated);
        }

        System.out.println("Level 2 complete!");
        return true;
    }
}
