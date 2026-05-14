package Enemy;
import Heroes.Hero;

public class Enemy {
    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyDamage;
    private int maxHp;
    private static int dialogueCounter = 0;
    
    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.maxHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 50;
    }
    
    public String getEnemyName() { return enemyName; }
    public int getEnemyHp() { return enemyHp; }
    public int getEnemyMana() { return enemyMana; }
    public int getEnemyDamage() { return enemyDamage; }
    public int getMaxHp() { return maxHp; }
    
    public void setEnemyHp(int hp) { this.enemyHp = Math.max(0, Math.min(maxHp, hp)); }
    public void setEnemyMana(int mana) { this.enemyMana = Math.max(0, Math.min(100, mana)); }
    
    public void takeDamage(int damage) { this.enemyHp = Math.max(0, this.enemyHp - damage); }
    
    public void heal(int amount) { this.enemyHp = Math.min(maxHp, this.enemyHp + amount); }
    
    public void drainMana(int amount) { this.enemyMana = Math.max(0, this.enemyMana - amount); }
    
    public void enemyAttack(Hero hero) {
        int damage = this.enemyDamage;
        hero.takeDamage(damage);
        System.out.println();
        System.out.print(enemyName + " has attacked you! Dealt " + damage + " damage");
    }
    
    public void enemyDefeatedDialogue() {
        String[] deathDialogues = {
            "Enemy: Nooo... my gold...",
            "Enemy: Impossible... I'm... strong...",
            "Enemy: My spells... failed me...",
            "Enemy: Death... takes me again...",
            "Enemy: I... am... eternal..."
        };
        System.out.printf("\n%s\n", deathDialogues[dialogueCounter % deathDialogues.length]);
        dialogueCounter++;
    }
    
    public boolean isBoss() { return maxHp >= 150; }
    public double getHpPercentage() { return (double) enemyHp / maxHp * 100; }
}