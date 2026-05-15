package Heroes;

public class Kyle extends Hero {
    private static final int DAMAGE_SKILL1 = 14;
    private static final int DAMAGE_SKILL2 = 12;
    private static final int DAMAGE_SKILL3 = 30;
    private static final int MANA_COST1 = 10;
    private static final int MANA_COST2 = 6;
    private static final int MANA_COST3 = 28;

    public Kyle(String name) {
        super(name, "Steel Strike", "Shield Bash", "Warbringer", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Kyle was a loyal captain of the royal guard, sworn to protect the kingdom at all costs.");
        System.out.println("When the king's corruption led to innocent bloodshed, he defected, becoming a wandering warrior seeking true justice.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Steel Strike) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana");
        System.out.println("Skill 2 (Shield Bash) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana");
        System.out.println("Skill 3 (Warbringer) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana");
        System.out.println("Skill 4 (Heal HP) - Restores 35 HP");
        System.out.println("Skill 5 (Heal Mana) - Restores 35 MP");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nKyle: I fight for those who cannot fight for themselves.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nKyle: Justice has been served today.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nKyle: I... will not... fall here...\n");
    }
}
