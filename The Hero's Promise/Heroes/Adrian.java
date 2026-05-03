package Heroes;

public class Adrian extends Hero {
    private static final int DAMAGE_SKILL1 = 10;
    private static final int DAMAGE_SKILL2 = 20;
    private static final int DAMAGE_SKILL3 = 15;
    private static final int MANA_COST1 = 15;
    private static final int MANA_COST2 = 30;
    private static final int MANA_COST3 = 20;

    public Adrian(String name) {
        super(name,
              "Arcane Bolt",
              "Mana Barrier",
              "Fireball",
              DAMAGE_SKILL1,
              DAMAGE_SKILL2,
              DAMAGE_SKILL3,
              MANA_COST1,
              MANA_COST2,
              MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println(
            "Adrian was born with an innate connection to the arcane, " +
            "a secret he guarded from a world that feared magic. " +
            "After his village was razed by anti-magic fanatics, " +
            "he embraced his power fully, vowing to master every " +
            "spell to ensure no one ever suffers as his family did."
        );
    }

    @Override
    public void displaySkillOptions() {
        System.out.println(
            "Skill 1 (Arcane Bolt) | " + getSkillOneMultiplier() +
            " damage | " + getSkillOneManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 2 (Mana Barrier) | " + getSkillTwoMultiplier() +
            " damage | " + getSkillTwoManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 3 (Fireball) | " + getSkillThreeMultiplier() +
            " damage | " + getSkillThreeManaCost() + " Mana cost"
        );
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nAdrian: The arcane answers my call.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nAdrian: A predictable outcome.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nAdrian: My... studies... were... incomplete...\n");
    }
}
