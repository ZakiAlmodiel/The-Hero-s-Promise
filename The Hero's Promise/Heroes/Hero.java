package Heroes;
import Enemy.Enemy;
import Story.Dialogue;

public abstract class Hero extends Dialogue {
    private String heroName;
    private int maxHp = 100;
    private int maxMana = 100;
    private int heroHp = 100;
    private int heroMana = 100;
    
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
    
    public void setHeroHp(int hp) {
        this.heroHp = Math.min(maxHp, Math.max(0, hp));
    }
    
    public void setHeroMana(int mana) {
        this.heroMana = Math.min(maxMana, Math.max(0, mana));
    }
    
    public void takeDamage(int damage) {
        this.heroHp = Math.max(0, this.heroHp - damage);
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
    
    public abstract void displayBackStory();
    public abstract void displaySkillOptions();
}
