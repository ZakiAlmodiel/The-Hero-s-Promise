package Story;
import Enemy.Enemy;
import Heroes.Hero;

public class Details {
    public static void displayPickingDetails(Hero hero) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("You've picked " + hero.getHeroName());
        System.out.println("HP: " + hero.getHeroHp() + "/" + hero.getMaxHp());
        System.out.println("Mana: " + hero.getHeroMana() + "/" + hero.getMaxMana());
        System.out.println();
    }
    
    public static void displayCurrentDetails(Hero hero, Enemy enemy) {
        System.out.println();
        System.out.println("--------------------------------------------------");
        System.out.println("HERO: " + hero.getHeroName() + " | ENEMY: " + enemy.getEnemyName());
        System.out.println("HP: " + hero.getHeroHp() + " | HP: " + enemy.getEnemyHp());
        System.out.println("MP: " + hero.getHeroMana() + " | MP: " + enemy.getEnemyMana());
        System.out.println("--------------------------------------------------");
    }
}