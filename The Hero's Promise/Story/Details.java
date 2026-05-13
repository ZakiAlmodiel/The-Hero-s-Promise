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
        System.out.printf("%-15s %20s\n", hero.getHeroName(), enemy.getEnemyName());
        System.out.printf("HP: %-10d HP: %d\n", hero.getHeroHp(), enemy.getEnemyHp());
        System.out.printf("MP: %-10d MP: %d\n", hero.getHeroMana(), enemy.getEnemyMana());
        System.out.println("--------------------------------------------------");
    }
}