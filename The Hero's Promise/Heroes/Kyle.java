package Heroes;

public class Kyle extends Hero {
    private static final int DAMAGE_SKILL1 = 15;
    private static final int DAMAGE_SKILL2 = 10;
    private static final int DAMAGE_SKILL3 = 35;
    private static final int MANA_COST1 = 10;
    private static final int MANA_COST2 = 5;
    private static final int MANA_COST3 = 35;

    public Kyle(String name) {
        super(name, "Steel Strike", "Shield Bash", "Warbringer", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Kyle was a loyal captain of the royal guard, sworn to protect the kingdom at all costs. When the king's corruption led to innocent bloodshed, he defected, becoming a wandering warrior seeking true justice. Now he fights not for a crown, but for the people crushed beneath it.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Steel Strike) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana cost");
        System.out.println("Skill 2 (Shield Bash) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana cost");
        System.out.println("Skill 3 (Hinokami Kagura) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana cost");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() { System.out.printf("\nKyle: I fight for those who cannot.\n"); }
    @Override
    public void victoryDialogue() { System.out.printf("\nKyle: Justice has been served.\n"); }
    @Override
    public void defeatDialogue() { System.out.printf("\nKyle: I... will not fall... here...\n"); }
}
