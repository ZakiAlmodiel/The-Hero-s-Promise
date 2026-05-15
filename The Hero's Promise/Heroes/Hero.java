package Heroes;
import Enemy.Enemy;
import Story.Dialogue;

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
    private int experienceToNextLevel = 100;
    private int totalEnemiesDefeated = 0;
    private int totalDamageDealt = 0;
    
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
    
    public int getLevel() {
        return level;
    }
    
    public int getExperience() {
        return experience;
    }
    
    public int getExperienceToNextLevel() {
        return experienceToNextLevel;
    }
    
    public int getTotalEnemiesDefeated() {
        return totalEnemiesDefeated;
    }
    
    public int getTotalDamageDealt() {
        return totalDamageDealt;
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
    
    public void takeDamage(int damage, String source) {
        this.heroHp = Math.max(0, this.heroHp - damage);
        System.out.println("💔 " + source + " dealt " + damage + " damage!");
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
        int actualHeal = Math.min(amount, maxHp - heroHp);
        this.heroHp = Math.min(maxHp, this.heroHp + amount);
        System.out.println("❤️ Restored " + actualHeal + " HP!");
    }
    
    public void restoreMana(int amount) {
        int actualHeal = Math.min(amount, maxMana - heroMana);
        this.heroMana = Math.min(maxMana, this.heroMana + amount);
        System.out.println("💙 Restored " + actualHeal + " MP!");
    }
    
    public void fullRestore() {
        this.heroHp = maxHp;
        this.heroMana = maxMana;
        System.out.println("🌟 HP and MP fully restored!");
    }
    
    public void gainExperience(int xp) {
        this.experience += xp;
        this.totalEnemiesDefeated++;
        System.out.println("✨ +" + xp + " XP gained!");
        
        while (experience >= experienceToNextLevel) {
            levelUp();
        }
    }
    
    private void levelUp() {
        experience -= experienceToNextLevel;
        level++;
        
        int oldMaxHp = maxHp;
        int oldMaxMana = maxMana;
        
        maxHp += 20;
        maxMana += 15;
        skillOneMultiplier += 3;
        skillTwoMultiplier += 3;
        skillThreeMultiplier += 3;
        
        heroHp = maxHp;
        heroMana = maxMana;
        experienceToNextLevel = 100 + (level * 10);
        
        System.out.println("\n🎉🎉🎉 LEVEL UP! 🎉🎉🎉");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.printf("║  %s is now level %d!                    ║\n", heroName, level);
        System.out.printf("║  HP: %d → %d (+%d)                      ║\n", oldMaxHp, maxHp, maxHp - oldMaxHp);
        System.out.printf("║  MP: %d → %d (+%d)                      ║\n", oldMaxMana, maxMana, maxMana - oldMaxMana);
        System.out.printf("║  Skill damage increased!                ║\n");
        System.out.println("╚════════════════════════════════════════╝\n");
    }
    
    public void boostHero(int hpBoost, int manaBoost) {
        this.maxHp += hpBoost;
        this.maxMana += manaBoost;
        this.heroHp = this.maxHp;
        this.heroMana = this.maxMana;
        System.out.println("\n✨✨✨ HERO POWER UP! ✨✨✨");
        System.out.println("Max HP increased to " + this.maxHp);
        System.out.println("Max MP increased to " + this.maxMana);
        System.out.println("HP and MP fully restored!");
    }
    
    public void skillOne(Enemy enemy) {
        if (hasEnoughMana(skillOneManaCost)) {
            useMana(skillOneManaCost);
            enemy.takeDamage(skillOneMultiplier);
            totalDamageDealt += skillOneMultiplier;
            System.out.println("🔮 " + heroName + " uses " + skillOneName + "!");
            System.out.println("   Dealt " + skillOneMultiplier + " damage!");
        } else {
            System.out.println("⚠️ Not enough mana for " + skillOneName + "! (Need " + skillOneManaCost + " MP)");
        }
    }
    
    public void skillTwo(Enemy enemy) {
        if (hasEnoughMana(skillTwoManaCost)) {
            useMana(skillTwoManaCost);
            enemy.takeDamage(skillTwoMultiplier);
            totalDamageDealt += skillTwoMultiplier;
            System.out.println("⚡ " + heroName + " uses " + skillTwoName + "!");
            System.out.println("   Dealt " + skillTwoMultiplier + " damage!");
        } else {
            System.out.println("⚠️ Not enough mana for " + skillTwoName + "! (Need " + skillTwoManaCost + " MP)");
        }
    }
    
    public void skillThree(Enemy enemy) {
        if (hasEnoughMana(skillThreeManaCost)) {
            useMana(skillThreeManaCost);
            enemy.takeDamage(skillThreeMultiplier);
            totalDamageDealt += skillThreeMultiplier;
            System.out.println("💢 " + heroName + " uses " + skillThreeName + "!");
            System.out.println("   Dealt " + skillThreeMultiplier + " damage!");
        } else {
            System.out.println("⚠️ Not enough mana for " + skillThreeName + "! (Need " + skillThreeManaCost + " MP)");
        }
    }
    
    public void healHP() {
        if (heroHp >= maxHp) {
            System.out.println("❤️ HP is already full!");
        } else {
            restoreHp(35);
        }
    }
    
    public void healMana() {
        if (heroMana >= maxMana) {
            System.out.println("💙 MP is already full!");
        } else {
            restoreMana(35);
        }
    }
    
    public void displayStats() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.printf("║  %-30s ║\n", heroName);
        System.out.printf("║  Level: %d (XP: %d/%d)                ║\n", level, experience, experienceToNextLevel);
        System.out.printf("║  HP: %d / %d                           ║\n", heroHp, maxHp);
        System.out.printf("║  MP: %d / %d                           ║\n", heroMana, maxMana);
        System.out.printf("║  Enemies Defeated: %d                  ║\n", totalEnemiesDefeated);
        System.out.printf("║  Total Damage: %d                      ║\n", totalDamageDealt);
        System.out.println("╚════════════════════════════════════════╝");
    }
    
    public abstract void displayBackStory();
    public abstract void displaySkillOptions();
}
