package Heroes;

public class Joshua extends Hero {
    private static final int DAMAGE_SKILL1 = 12;
    private static final int DAMAGE_SKILL2 = 18;
    private static final int DAMAGE_SKILL3 = 28;
    private static final int MANA_COST1 = 12;
    private static final int MANA_COST2 = 18;
    private static final int MANA_COST3 = 28;

    public Joshua(String name) {
        super(name, "Holy Smite", "Divine Shield", "Judgment", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Joshua was raised in a secluded monastery, studying ancient texts and divine arts.");
        System.out.println("When darkness spread across the land, the elders sent him forth as their champion.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Holy Smite) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana");
        System.out.println("Skill 2 (Divine Shield) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana");
        System.out.println("Skill 3 (Judgment) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana");
        System.out.println("Skill 4 (Heal HP) - Restores 35 HP");
        System.out.println("Skill 5 (Heal Mana) - Restores 35 MP");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nJoshua: The light shall prevail over your darkness!\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nJoshua: The righteous always triumph. May you find peace.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nJoshua: Forgive... me... I have failed...\n");
    }
}