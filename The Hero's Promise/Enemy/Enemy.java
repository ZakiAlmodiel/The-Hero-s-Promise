package Enemy;
import Heroes.Hero;

public class Enemy {
    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyDamage;
    private int maxHp;
    private int maxMana;
    private String[] abilities;
    private static int dialogueCounter = 0;
    private String[] deathDialogues;
    private boolean isBossEnemy;
    
    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.maxHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 60;
        this.maxMana = 100;
        this.isBossEnemy = false;
        this.abilities = new String[]{"Normal Attack", "Heavy Strike"};
        this.deathDialogues = new String[]{
            "Enemy: Nooo... my gold...",
            "Enemy: Impossible... I'm... strong...",
            "Enemy: My spells... failed me...",
            "Enemy: Death... takes me again...",
            "Enemy: I... am... eternal..."
        };
    }
    
    public Enemy(String name, int hp, int attack, boolean isBoss) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.maxHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 80;
        this.maxMana = 150;
        this.isBossEnemy = isBoss;
        this.abilities = isBoss ? new String[]{"Normal Attack", "Heavy Strike", "Rift Blast"} : new String[]{"Normal Attack", "Heavy Strike"};
        this.deathDialogues = new String[]{
            "Enemy: Nooo... my gold...",
            "Enemy: Impossible... I'm... strong...",
            "Enemy: My spells... failed me...",
            "Enemy: Death... takes me again...",
            "Enemy: I... am... eternal..."
        };
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
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getMaxMana() {
        return maxMana;
    }
    
    public String[] getAbilities() {
        return abilities;
    }
    
    public boolean isBoss() {
        return isBossEnemy;
    }
    
    public void setEnemyHp(int hp) {
        this.enemyHp = Math.max(0, Math.min(maxHp, hp));
    }
    
    public void setEnemyMana(int mana) {
        this.enemyMana = Math.max(0, Math.min(maxMana, mana));
    }
    
    public void takeDamage(int damage) {
        this.enemyHp = Math.max(0, this.enemyHp - damage);
    }
    
    public void heal(int amount) {
        this.enemyHp = Math.min(maxHp, this.enemyHp + amount);
    }
    
    public void drainMana(int amount) {
        this.enemyMana = Math.max(0, this.enemyMana - amount);
    }
    
    public void restoreMana(int amount) {
        this.enemyMana = Math.min(maxMana, this.enemyMana + amount);
    }
    
    public void enemyAttack(Hero hero) {
        int damage = this.enemyDamage;
        hero.takeDamage(damage);
        System.out.println();
        System.out.print("⚔️ " + enemyName + " attacks! Dealt " + damage + " damage!");
    }
    
    public void heavyAttack(Hero hero) {
        int damage = this.enemyDamage + 10;
        if (enemyMana >= 15) {
            enemyMana -= 15;
            hero.takeDamage(damage);
            System.out.println();
            System.out.print("💥 " + enemyName + " uses HEAVY STRIKE! Dealt " + damage + " damage!");
        } else {
            enemyAttack(hero);
        }
    }
    
    public void riftBlast(Hero hero) {
        if (isBossEnemy && enemyMana >= 30) {
            int damage = this.enemyDamage + 20;
            enemyMana -= 30;
            hero.takeDamage(damage);
            hero.setHeroMana(hero.getHeroMana() - 10);
            System.out.println();
            System.out.print("🌊 " + enemyName + " unleashes RIFT BLAST! Dealt " + damage + " damage and drained 10 MP!");
        } else {
            heavyAttack(hero);
        }
    }
    
    public void enemyDefeatedDialogue() {
        System.out.printf("\n💀 %s\n", deathDialogues[dialogueCounter % deathDialogues.length]);
        dialogueCounter++;
    }
    
    public double getHpPercentage() {
        return (double) enemyHp / maxHp * 100;
    }
    
    public boolean isLowHealth() {
        return enemyHp < maxHp * 0.25;
    }
    
    public boolean isCriticalHealth() {
        return enemyHp < maxHp * 0.10;
    }
    
    public void resetForNewBattle() {
        this.enemyHp = maxHp;
        this.enemyMana = isBossEnemy ? 80 : 60;
    }
}