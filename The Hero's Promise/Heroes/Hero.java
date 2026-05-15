package Heroes;
import Enemy.Enemy;
import Story.Dialogue;
import Exceptions.InsufficientManaException;
import Exceptions.InvalidSkillException;

public abstract class Hero extends Dialogue {
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
    private int level = 1;
    private int experience = 0;
    
    public Hero(String name, String s1n, String s2n, String s3n, int s1m, int s2m, int s3m, int s1c, int s2c, int s3c) {
        this.heroName = name;
        this.skillOneName = s1n;
        this.skillTwoName = s2n;
        this.skillThreeName = s3n;
        this.skillOneMultiplier = s1m;
        this.skillTwoMultiplier = s2m;
        this.skillThreeMultiplier = s3m;
        this.skillOneManaCost = s1c;
        this.skillTwoManaCost = s2c;
        this.skillThreeManaCost = s3c;
    }
    
    public String getHeroName() { return heroName; }
    public int getHeroHp() { return heroHp; }
    public int getHeroMana() { return heroMana; }
    public int getMaxHp() { return maxHp; }
    public int getMaxMana() { return maxMana; }
    public String getSkillOneName() { return skillOneName; }
    public String getSkillTwoName() { return skillTwoName; }
    public String getSkillThreeName() { return skillThreeName; }
    public int getSkillOneMultiplier() { return skillOneMultiplier; }
    public int getSkillTwoMultiplier() { return skillTwoMultiplier; }
    public int getSkillThreeMultiplier() { return skillThreeMultiplier; }
    public int getSkillOneManaCost() { return skillOneManaCost; }
    public int getSkillTwoManaCost() { return skillTwoManaCost; }
    public int getSkillThreeManaCost() { return skillThreeManaCost; }
    public int getLevel() { return level; }
    public int getExperience() { return experience; }
    
    public void setHeroHp(int hp) { this.heroHp = Math.min(maxHp, Math.max(0, hp)); }
    public void setHeroMana(int mana) { this.heroMana = Math.min(maxMana, Math.max(0, mana)); }
    
    public void takeDamage(int damage) { this.heroHp = Math.max(0, this.heroHp - damage); }
    
    public void takeDamage(int damage, String enemyName) {
        this.heroHp = Math.max(0, this.heroHp - damage);
    }
    
    public boolean hasEnoughMana(int cost) { return heroMana >= cost; }
    
    public void useMana(int cost) {
        if (hasEnoughMana(cost)) { this.heroMana -= cost; }
    }
    
    public void restoreHp(int amount) { this.heroHp = Math.min(maxHp, this.heroHp + amount); }
    public void restoreMana(int amount) { this.heroMana = Math.min(maxMana, this.heroMana + amount); }
    public void fullRestore() { this.heroHp = maxHp; this.heroMana = maxMana; }
    
    public void gainExperience(int xp) {
        this.experience += xp;
        if (experience >= 100) {
            level++;
            experience -= 100;
            maxHp += 20;
            maxMana += 15;
            heroHp = maxHp;
            heroMana = maxMana;
            System.out.println("\n*** LEVEL UP! " + heroName + " is now level " + level + "! ***");
        }
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
    
    public void useSkill(Enemy enemy, int skillNum) throws InsufficientManaException, InvalidSkillException {
        if (skillNum < 1 || skillNum > 5) {
            throw new InvalidSkillException(skillNum, 1, 5);
        }
        
        switch (skillNum) {
            case 1:
                if (!hasEnoughMana(skillOneManaCost)) {
                    throw new InsufficientManaException(skillOneName, skillOneManaCost, heroMana);
                }
                useMana(skillOneManaCost);
                enemy.takeDamage(skillOneMultiplier);
                System.out.println(heroName + " uses " + skillOneName + "! Deals " + skillOneMultiplier + " damage.");
                break;
            case 2:
                if (!hasEnoughMana(skillTwoManaCost)) {
                    throw new InsufficientManaException(skillTwoName, skillTwoManaCost, heroMana);
                }
                useMana(skillTwoManaCost);
                enemy.takeDamage(skillTwoMultiplier);
                System.out.println(heroName + " uses " + skillTwoName + "! Deals " + skillTwoMultiplier + " damage.");
                break;
            case 3:
                if (!hasEnoughMana(skillThreeManaCost)) {
                    throw new InsufficientManaException(skillThreeName, skillThreeManaCost, heroMana);
                }
                useMana(skillThreeManaCost);
                enemy.takeDamage(skillThreeMultiplier);
                System.out.println(heroName + " uses " + skillThreeName + "! Deals " + skillThreeMultiplier + " damage.");
                break;
            case 4:
                if (heroHp >= maxHp) { System.out.printf("\nHP is currently full\n"); }
                else { restoreHp(35); System.out.printf("\nHP replenished by 35\n"); }
                break;
            case 5:
                if (heroMana >= maxMana) { System.out.printf("\nMP is currently full\n"); }
                else { restoreMana(35); System.out.printf("\nMP replenished by 35\n"); }
                break;
        }
    }
    
    public void skillOne(Enemy enemy) {
        if (hasEnoughMana(skillOneManaCost)) {
            useMana(skillOneManaCost);
            enemy.takeDamage(skillOneMultiplier);
            System.out.println(heroName + " uses " + skillOneName + "! Deals " + skillOneMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillOneName + "!");
        }
    }
    
    public void skillTwo(Enemy enemy) {
        if (hasEnoughMana(skillTwoManaCost)) {
            useMana(skillTwoManaCost);
            enemy.takeDamage(skillTwoMultiplier);
            System.out.println(heroName + " uses " + skillTwoName + "! Deals " + skillTwoMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillTwoName + "!");
        }
    }
    
    public void skillThree(Enemy enemy) {
        if (hasEnoughMana(skillThreeManaCost)) {
            useMana(skillThreeManaCost);
            enemy.takeDamage(skillThreeMultiplier);
            System.out.println(heroName + " uses " + skillThreeName + "! Deals " + skillThreeMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillThreeName + "!");
        }
    }
    
    public void healHP() {
        if (heroHp >= maxHp) { System.out.printf("\nHP is currently full\n"); heroHp = maxHp; }
        else { restoreHp(35); System.out.printf("\nHP replenished by 35\n"); }
    }
    
    public void healMana() {
        if (heroMana >= maxMana) { System.out.printf("\nMP is currently full\n"); heroMana = maxMana; }
        else { restoreMana(35); System.out.printf("\nMP replenished by 35\n"); }
    }
    
    public abstract void displayBackStory();
    public abstract void displaySkillOptions();
    
    @Override
    public abstract void preBattleDialogue();
    @Override
    public abstract void victoryDialogue();
    @Override
    public abstract void defeatDialogue();
}
