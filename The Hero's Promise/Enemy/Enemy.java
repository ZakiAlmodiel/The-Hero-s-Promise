package Enemy;
import Heroes.Hero;

public class Enemy {
    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyDamage;
    private static int dialogueCounter = 0;
    
    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 0;
    }
    
    public String getEnemyName() {
        return enemyName;
    }
    
    public int getEnemyHp() {
        return enemyHp;
    }
    
    public int getEnemyMana() {
        return enemyMana;
    }
    
    public int getEnemyDamage() {
        return enemyDamage;
    }
    
    public void setEnemyHp(int hp) {
        this.enemyHp = Math.max(0, hp);
    }
    
    public void takeDamage(int damage) {
        this.enemyHp = Math.max(0, this.enemyHp - damage);
    }
    
    public void enemyAttack(Hero hero) {
        int damage = this.enemyDamage;
        hero.takeDamage(damage);
        System.out.print(enemyName + " attacked! Dealt " + damage + " damage");
    }
}