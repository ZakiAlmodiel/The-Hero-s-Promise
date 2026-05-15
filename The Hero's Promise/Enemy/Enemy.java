package Enemy;
import Heroes.Hero;

public class Enemy {
    private String enemyName;
    private int enemyHp;
    private int enemyDamage;
    // enemyMana not yet added; heal() and drainMana() not yet thought of
    
    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.enemyDamage = attack;
    }
    
    public String getEnemyName() { return enemyName; }
    public int getEnemyHp()     { return enemyHp; }
    public int getEnemyDamage() { return enemyDamage; }
    
    public void setEnemyHp(int hp) {
        this.enemyHp = hp; // no Math.max(0, hp) guard yet
    }
    
    public void takeDamage(int damage) {
        this.enemyHp -= damage; // can go negative; not yet guarded
    }
    
    public void enemyAttack(Hero hero) {
        hero.takeDamage(enemyDamage);
        System.out.println(enemyName + " attacked! Dealt " + enemyDamage + " damage.");
        // no blank line formatting yet
    }
    
    public void enemyDefeatedDialogue() {
        // only one death line so far; rotation array not yet implemented
        System.out.println("Enemy: Nooo... my gold...");
    }
}
