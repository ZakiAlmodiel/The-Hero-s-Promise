package Heroes;

public class Joshua extends Hero {
    private static final int DAMAGE_SKILL1 = 10;
    private static final int DAMAGE_SKILL2 = 15;
    private static final int DAMAGE_SKILL3 = 30;
    private static final int MANA_COST1 = 15;
    private static final int MANA_COST2 = 20;
    private static final int MANA_COST3 = 35;

    public Joshua(String name) {
        super(name,
              "Holy Smite",
              "Divine Shield",
              "Judgment",
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
            "Joshua was raised in a secluded monastery, studying " +
            "ancent texts and divine arts. When darkness spread " +
            "across the land, the elders sent him forth as their " +
            "champion. Armed with unwavering faith and holy power, " +
            "he walks the path of light, healing the wounded and " +
            "smiting evil wherever it lurks."
        );
    }

    @Override
    public void displaySkillOptions() {
        System.out.println(
            "Skill 1 (Holy Smite) | " + getSkillOneMultiplier() +
            " damage | " + getSkillOneManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 2 (Divine Shield) | " + getSkillTwoMultiplier() +
            " damage | " + getSkillTwoManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 3 (Judgment) | " + getSkillThreeMultiplier() +
            " damage | " + getSkillThreeManaCost() + " Mana cost"
        );
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nJoshua: The light shall prevail.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nJoshua: The righteous always triumph.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nJoshua: Forgive... me...\n");
    }
}
