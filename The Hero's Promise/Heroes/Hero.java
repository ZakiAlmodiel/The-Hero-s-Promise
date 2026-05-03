package Heroes;
import Enemy.Enemy;
import Story.Dialogue;

public abstract class Hero extends Dialogue {
    // Private fields - ENCAPSULATION
    private String heroName;
    private String skillOneName;
    private String skillTwoName;
    private String skillThreeName;
    private int maxHp = 100;
    private int maxMana = 100;
    private int heroHp = 100;
    private int heroMana = 100;
    private int skillOneMultiplier;
    private int skillTwoMultiplier;
    private int skillThreeMultiplier;
    private int skillOneManaCost;
    private int skillTwoManaCost;
    private int skillThreeManaCost;
    
    // Constructor
    public Hero(
        String name, String skillOneName, 
        String skillTwoName, String skillThreeName, 
        int skillOneMultiplier, int skillTwoMultiplier, 
        int skillThreeMultiplier, int skillOneManaCost, 
        int skillTwoManaCost, int skillThreeManaCost
    ) {
        this.heroName = name;
        this.skillOneName = skillOneName;
        this.skillTwoName = skillTwoName;
        this.skillThreeName = skillThreeName;
        this.skillOneMultiplier = skillOneMultiplier;
        this.skillTwoMultiplier = skillTwoMultiplier;
        this.skillThreeMultiplier = skillThreeMultiplier;
        this.skillOneManaCost = skillOneManaCost;
        this.skillTwoManaCost = skillTwoManaCost;
        this.skillThreeManaCost = skillThreeManaCost;
    }
    
    // GETTERS
    public String getHeroName() {
        return heroName;
    }
    
    public int getHeroHp() {
        return heroHp;
    }
    
    public int getHeroMana() {
        return heroMana;
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getMaxMana() {
        return maxMana;
    }
    
    public String getSkillOneName() {
        return skillOneName;
    }
    
    public String getSkillTwoName() {
        return skillTwoName;
    }
    
    public String getSkillThreeName() {
        return skillThreeName;
    }
    
    public int getSkillOneMultiplier() {
        return skillOneMultiplier;
    }
    
    public int getSkillTwoMultiplier() {
        return skillTwoMultiplier;
    }
    
    public int getSkillThreeMultiplier() {
        return skillThreeMultiplier;
    }
    
    public int getSkillOneManaCost() {
        return skillOneManaCost;
    }
    
    public int getSkillTwoManaCost() {
        return skillTwoManaCost;
    }
    
    public int getSkillThreeManaCost() {
        return skillThreeManaCost;
    }
    
    // SETTERS with validation
    public void setHeroHp(int hp) {
        this.heroHp = Math.min(maxHp, Math.max(0, hp));
    }
    
    public void setHeroMana(int mana) {
        this.heroMana = Math.min(maxMana, Math.max(0, mana));
    }
    
    // Business methods
    public void takeDamage(int damage) {
        this.heroHp = Math.max(0, this.heroHp - damage);
    }
    
    public boolean hasEnoughMana(int cost) {
        return heroMana >= cost;
    }
    
    public void useMana(int cost) {
        if (hasEnoughMana(cost)) {
            this.heroMana -= cost;
        }
    }
    
    public void restoreHp(int amount) {
        this.heroHp = Math.min(maxHp, this.heroHp + amount);
    }
    
    public void restoreMana(int amount) {
        this.heroMana = Math.min(maxMana, this.heroMana + amount);
    }
    
    public void fullRestore() {
        this.heroHp = maxHp;
        this.heroMana = maxMana;
    }

    public void boostHero(int hpBoost, int manaBoost) {
        this.maxHp += hpBoost;
        this.maxMana += manaBoost;
        this.heroHp = this.maxHp;
        this.heroMana = this.maxMana;
        System.out.println("\n*** HERO POWER UP! ***");
        System.out.println("Max HP increased by " + hpBoost + " to " + this.maxHp);
        System.out.println("Max MP increased by " + manaBoost + " to " + this.maxMana);
        System.out.println("HP and MP fully restored!");
    }
    
    public void displayBackStory() {
        System.out.println("No back story");
    }
    
    public void displaySkillOptions() {
        System.out.println("No skills available");
    }
    
    public void skillOne(Enemy enemy) {
        if (hasEnoughMana(skillOneManaCost)) {
            useMana(skillOneManaCost);
            enemy.takeDamage(skillOneMultiplier);
            System.out.println(
                heroName + " uses " + skillOneName + 
                "! Deals " + skillOneMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillOneName + "!");
        }
    }
    
    public void skillTwo(Enemy enemy) {
        if (hasEnoughMana(skillTwoManaCost)) {
            useMana(skillTwoManaCost);
            enemy.takeDamage(skillTwoMultiplier);
            System.out.println(
                heroName + " uses " + skillTwoName + 
                "! Deals " + skillTwoMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillTwoName + "!");
        }
    }
    
    public void skillThree(Enemy enemy) {
        if (hasEnoughMana(skillThreeManaCost)) {
            useMana(skillThreeManaCost);
            enemy.takeDamage(skillThreeMultiplier);
            System.out.println(
                heroName + " uses " + skillThreeName + 
                "! Deals " + skillThreeMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillThreeName + "!");
        }
    }
    
    public void healHP() {
        if (heroHp >= maxHp) {
            System.out.printf("\nHP is currently full\n");
            heroHp = maxHp;
        } else {
            restoreHp(20);
            System.out.printf("\nHP replenished by 20\n");
        }
    }
    
    public void healMana() {
        if (heroMana >= maxMana) {
            System.out.printf("\nMP is currently full\n");
            heroMana = maxMana;
        } else {
            restoreMana(20);
            System.out.printf("\nMP replenished by 20\n");
        }
    }
    
    // Abstract methods from Dialogue
    @Override
    public abstract void preBattleDialogue();
    
    @Override
    public abstract void victoryDialogue();
    
    @Override
    public abstract void defeatDialogue();
}