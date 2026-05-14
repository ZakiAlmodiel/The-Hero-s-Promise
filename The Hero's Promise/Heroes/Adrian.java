package Heroes;

public class Adrian extends Hero {
    private static final int DAMAGE_SKILL1 = 12;
    private static final int DAMAGE_SKILL2 = 18;
    private static final int DAMAGE_SKILL3 = 25;
    private static final int MANA_COST1 = 12;
    private static final int MANA_COST2 = 18;
    private static final int MANA_COST3 = 25;

    public Adrian(String name) {
        super(name, "Arcane Bolt", "Mana Barrier", "Fireball", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Adrian was born with an innate connection to the arcane, a secret he guarded from a world that feared magic.");
        System.out.println("After his village was razed by anti-magic fanatics, he embraced his power fully, vowing to master every spell.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Arcane Bolt) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana");
        System.out.println("Skill 2 (Mana Barrier) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana");
        System.out.println("Skill 3 (Fireball) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana");
        System.out.println("Skill 4 (Heal HP) - Restores 35 HP");
        System.out.println("Skill 5 (Heal Mana) - Restores 35 MP");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nAdrian: The arcane answers my call. Face my magic!\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nAdrian: A predictable outcome. Magic always prevails.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nAdrian: My... studies... were... incomplete...\n");
    }
}