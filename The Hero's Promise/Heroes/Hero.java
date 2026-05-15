package Heroes;
import Enemy.Enemy;
import Story.Dialogue;

public abstract class Hero extends Dialogue {
    private String heroName;
    private int maxHp = 100;
    private int maxMana = 100;
    private int heroHp = 100;
    private int heroMana = 100;
    private int skillOneMultiplier = 10;
    private int skillTwoMultiplier = 15;
    private int skillThreeMultiplier = 20;
    private int skillOneManaCost = 10;
    private int skillTwoManaCost = 15;
    private int skillThreeManaCost = 20;
    
    public Hero(String name) {
        this.heroName = name;
    }
    
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
    
    public void setHeroHp(int hp) {
        this.heroHp = Math.min(maxHp, Math.max(0, hp));
    }
    
    public void setHeroMana(int mana) {
        this.heroMana = Math.min(maxMana, Math.max(0, mana));
    }
    
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
    
    public void skillOne(Enemy enemy) {
        if (hasEnoughMana(skillOneManaCost)) {
            useMana(skillOneManaCost);
            enemy.takeDamage(skillOneMultiplier);
            System.out.println(heroName + " uses skill! Deals " + skillOneMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana!");
        }
    }
    
    public void skillTwo(Enemy enemy) {
        if (hasEnoughMana(skillTwoManaCost)) {
            useMana(skillTwoManaCost);
            enemy.takeDamage(skillTwoMultiplier);
            System.out.println(heroName + " uses skill! Deals " + skillTwoMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana!");
        }
    }
    
    public void skillThree(Enemy enemy) {
        if (hasEnoughMana(skillThreeManaCost)) {
            useMana(skillThreeManaCost);
            enemy.takeDamage(skillThreeMultiplier);
            System.out.println(heroName + " uses skill! Deals " + skillThreeMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana!");
        }
    }
    
    public void healHP() {
        if (heroHp >= maxHp) {
            System.out.printf("\nHP is full\n");
        } else {
            restoreHp(35);
            System.out.printf("\nHP restored by 35\n");
        }
    }
    
    public void healMana() {
        if (heroMana >= maxMana) {
            System.out.printf("\nMP is full\n");
        } else {
            restoreMana(35);
            System.out.printf("\nMP restored by 35\n");
        }
    }
    
    public abstract void displayBackStory();
    public abstract void displaySkillOptions();
}
