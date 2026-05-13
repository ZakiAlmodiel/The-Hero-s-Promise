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
    
    public void setEnemyMana(int mana) {
        this.enemyMana = Math.max(0, mana);
    }
    
    public void takeDamage(int damage) {
        this.enemyHp = Math.max(0, this.enemyHp - damage);
    }
    
    public void heal(int amount) {
        this.enemyHp = Math.min(150, this.enemyHp + amount);
    }
    
    public void drainMana(int amount) {
        this.enemyMana = Math.max(0, this.enemyMana - amount);
    }
    
    public void enemyAttack(Hero hero) {
        int damage = this.enemyDamage;
        hero.takeDamage(damage);
        System.out.print(enemyName + " attacked! Dealt " + damage + " damage");
    }
    
    public void enemyDefeatedDialogue() {
        String[] deathDialogues = {
            "Enemy: Nooo... my gold...",
            "Enemy: Impossible... I'm... strong..."
        };
        System.out.printf("\n%s\n", deathDialogues[dialogueCounter % 2]);
        dialogueCounter++;
    }
}