package Heroes;
import Enemy.Enemy;
import Exceptions.InsufficientManaException;
import Exceptions.InvalidSkillException;
// Story.Dialogue superclass not yet introduced; Hero extends Object directly

public abstract class Hero {
    private String heroName;
    private String skillOneName;
    private String skillTwoName;
    private String skillThreeName;
    private int maxHp   = 100;
    private int maxMana = 100;
    private int heroHp   = 100;
    private int heroMana = 100;
    private int skillOneMultiplier;
    private int skillTwoMultiplier;
    private int skillThreeMultiplier;
    private int skillOneManaCost;
    private int skillTwoManaCost;
    private int skillThreeManaCost;

    public Hero(String name,
                String skillOneName, String skillTwoName, String skillThreeName,
                int skillOneMultiplier, int skillTwoMultiplier, int skillThreeMultiplier,
                int skillOneManaCost, int skillTwoManaCost, int skillThreeManaCost) {
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

    public String getHeroName()  { return heroName; }
    public int getHeroHp()       { return heroHp; }
    public int getHeroMana()     { return heroMana; }
    public int getMaxHp()        { return maxHp; }
    public int getMaxMana()      { return maxMana; }
    public String getSkillOneName()      { return skillOneName; }
    public String getSkillTwoName()      { return skillTwoName; }
    public String getSkillThreeName()    { return skillThreeName; }
    public int getSkillOneMultiplier()   { return skillOneMultiplier; }
    public int getSkillTwoMultiplier()   { return skillTwoMultiplier; }
    public int getSkillThreeMultiplier() { return skillThreeMultiplier; }
    public int getSkillOneManaCost()     { return skillOneManaCost; }
    public int getSkillTwoManaCost()     { return skillTwoManaCost; }
    public int getSkillThreeManaCost()   { return skillThreeManaCost; }

    public void setHeroHp(int hp) {
        this.heroHp = Math.min(maxHp, Math.max(0, hp));
    }

    public void setHeroMana(int mana) {
        this.heroMana = Math.min(maxMana, Math.max(0, mana));
    }

    public void takeDamage(int damage) {
        this.heroHp = Math.max(0, this.heroHp - damage);
    }

    // Overloaded takeDamage with enemy name exists but body is a stub —
    // the named parameter is accepted but not yet used in any message
    public void takeDamage(int damage, String enemyName) {
        this.heroHp = Math.max(0, this.heroHp - damage);
        // formatted print with enemyName not wired in yet
    }

    public boolean hasEnoughMana(int cost) { return heroMana >= cost; }

    public void useMana(int cost) {
        if (hasEnoughMana(cost)) this.heroMana -= cost;
    }

    public void restoreHp(int amount) {
        this.heroHp = Math.min(maxHp, this.heroHp + amount);
    }

    public void restoreMana(int amount) {
        this.heroMana = Math.min(maxMana, this.heroMana + amount);
    }

    public void fullRestore() {
        this.heroHp   = maxHp;
        this.heroMana = maxMana;
    }

    public void boostHero(int hpBoost, int manaBoost) {
        this.maxHp   += hpBoost;
        this.maxMana += manaBoost;
        this.heroHp   = this.maxHp;
        this.heroMana = this.maxMana;
        System.out.println("\n*** HERO POWER UP! ***");
        System.out.println("Max HP increased by " + hpBoost   + " to " + this.maxHp);
        System.out.println("Max MP increased by " + manaBoost + " to " + this.maxMana);
        System.out.println("HP and MP fully restored!");
    }

    // useSkill() unified method added but exception throwing is incomplete —
    // InvalidSkillException thrown correctly but InsufficientManaException
    // uses a plain String constructor (typed fields not yet added to exception class)
    public void useSkill(Enemy enemy, int skillNum)
            throws InsufficientManaException, InvalidSkillException {

        if (skillNum < 1 || skillNum > 5) {
            throw new InvalidSkillException(skillNum, 1, 5);
        }

        switch (skillNum) {
            case 1:
                if (!hasEnoughMana(skillOneManaCost)) {
                    throw new InsufficientManaException(
                        "Not enough mana for " + skillOneName + "!");
                    // typed constructor (skillName, requiredMana, currentMana) not yet used
                }
                useMana(skillOneManaCost);
                enemy.takeDamage(skillOneMultiplier);
                System.out.println(heroName + " uses " + skillOneName +
                    "! Deals " + skillOneMultiplier + " damage.");
                break;
            case 2:
                if (!hasEnoughMana(skillTwoManaCost)) {
                    throw new InsufficientManaException(
                        "Not enough mana for " + skillTwoName + "!");
                }
                useMana(skillTwoManaCost);
                enemy.takeDamage(skillTwoMultiplier);
                System.out.println(heroName + " uses " + skillTwoName +
                    "! Deals " + skillTwoMultiplier + " damage.");
                break;
            case 3:
                if (!hasEnoughMana(skillThreeManaCost)) {
                    throw new InsufficientManaException(
                        "Not enough mana for " + skillThreeName + "!");
                }
                useMana(skillThreeManaCost);
                enemy.takeDamage(skillThreeMultiplier);
                System.out.println(heroName + " uses " + skillThreeName +
                    "! Deals " + skillThreeMultiplier + " damage.");
                break;
            case 4:
                if (heroHp >= maxHp) {
                    System.out.printf("\nHP is currently full\n");
                } else {
                    restoreHp(35);
                    System.out.printf("\nHP replenished by 35\n");
                }
                break;
            case 5:
                if (heroMana >= maxMana) {
                    System.out.printf("\nMP is currently full\n");
                } else {
                    restoreMana(35);
                    System.out.printf("\nMP replenished by 35\n");
                }
                break;
        }
    }

    public void displayBackStory()  { System.out.println("No back story"); }
    public void displaySkillOptions() { System.out.println("No skills available"); }

    // Individual skill methods kept alongside useSkill() — not yet removed
    public void skillOne(Enemy enemy) {
        if (hasEnoughMana(skillOneManaCost)) {
            useMana(skillOneManaCost);
            enemy.takeDamage(skillOneMultiplier);
            System.out.println(heroName + " uses " + skillOneName +
                "! Deals " + skillOneMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillOneName + "!");
        }
    }

    public void skillTwo(Enemy enemy) {
        if (hasEnoughMana(skillTwoManaCost)) {
            useMana(skillTwoManaCost);
            enemy.takeDamage(skillTwoMultiplier);
            System.out.println(heroName + " uses " + skillTwoName +
                "! Deals " + skillTwoMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillTwoName + "!");
        }
    }

    public void skillThree(Enemy enemy) {
        if (hasEnoughMana(skillThreeManaCost)) {
            useMana(skillThreeManaCost);
            enemy.takeDamage(skillThreeMultiplier);
            System.out.println(heroName + " uses " + skillThreeName +
                "! Deals " + skillThreeMultiplier + " damage.");
        } else {
            System.out.println("Not enough mana for " + skillThreeName + "!");
        }
    }

    public void healHP() {
        if (heroHp >= maxHp) {
            System.out.printf("\nHP is currently full\n");
        } else {
            restoreHp(35);
            System.out.printf("\nHP replenished by 35\n");
        }
    }

    public void healMana() {
        if (heroMana >= maxMana) {
            System.out.printf("\nMP is currently full\n");
        } else {
            restoreMana(35);
            System.out.printf("\nMP replenished by 35\n");
        }
    }

    // Abstract dialogue methods declared directly; no Dialogue superclass yet
    public abstract void preBattleDialogue();
    public abstract void victoryDialogue();
    public abstract void defeatDialogue();
}
