package Levels;
import java.util.Scanner;
import Enemy.Enemy;
import Heroes.Hero;

public class Level2 {
    static Scanner sc = new Scanner(System.in);

    public static boolean battleMechanic2(Hero hero) {
        String[] monsterNames = {"Crypt Lord", "Plagueweaver", "Void Sentinel"};
        
        for (int difficulty = 1; difficulty <= 3; difficulty++) {
            Enemy enemy = new Enemy(monsterNames[difficulty-1], 60 + (difficulty * 10), 12 + (difficulty * 3));
            
            System.out.println("\n--- Battle with " + enemy.getEnemyName() + " ---");
            
            while (hero.getHeroHp() > 0 && enemy.getEnemyHp() > 0) {
                hero.displaySkillOptions();
                System.out.print("Pick a skill (1-5): ");
                int skillNum = sc.nextInt();
                
                if (skillNum == 1) {
                    enemy.takeDamage(10);
                    System.out.println("You attacked!");
                } else if (skillNum == 4) {
                    hero.restoreHp(35);
                    System.out.println("You healed HP!");
                }
                
                if (enemy.getEnemyHp() > 0) {
                    enemy.enemyAttack(hero);
                    System.out.println("\nYour HP: " + hero.getHeroHp());
                }
            }
            
            if (hero.getHeroHp() <= 0) {
                System.out.println("GAME OVER!");
                return false;
            }
            
            System.out.println("\nDefeated " + enemy.getEnemyName() + "!");
        }
        
        System.out.println("\n----LEVEL 2 COMPLETE----");
        return true;
    }
}
