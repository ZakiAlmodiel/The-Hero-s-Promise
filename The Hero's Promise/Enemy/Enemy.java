package Enemy;
import Heroes.Hero;

public class Enemy {
    // Private fields - ENCAPSULATION
    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyDamage;
    private static int dialogueCounter = 0;
    
    // Constructor
    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 0; // Default value
    }
    
    // GETTERS - Controlled access
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
    
    // SETTERS with validation
    public void setEnemyHp(int hp) {
        this.enemyHp = Math.max(0, hp);
    }
    
    public void setEnemyMana(int mana) {
        this.enemyMana = Math.max(0, mana);
    }
    
    // Business methods
    public void takeDamage(int damage) {
        this.enemyHp = Math.max(0, this.enemyHp - damage);
    }
    
    public void heal(int amount) {
        this.enemyHp = Math.min(150, this.enemyHp + amount); // Max 150 for boss
    }
    
    public void drainMana(int amount) {
        this.enemyMana = Math.max(0, this.enemyMana - amount);
    }
    
    public void enemyAttack(Hero hero) {
        int damage = this.enemyDamage;
        hero.takeDamage(damage); // Use hero's method
        System.out.println();
        System.out.print(
            enemyName + " has attacked you! Dealt " +
            damage + " damage"
        );
    }
    
    public void enemyDefeatedDialogue() {
        String[] deathDialogues = {
            "Enemy: Nooo... my gold...",
            "Enemy: Impossible... I'm... strong...",
            "Enemy: My spells... failed me...",
            "Enemy: Death... takes me again...",
            "Enemy: I... am... eternal..."
        };
        System.out.printf("\n%s\n", deathDialogues[dialogueCounter]);
        dialogueCounter = (dialogueCounter + 1) % deathDialogues.length; // Prevent array index out of bounds
    }
}