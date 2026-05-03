package Heroes;

public class Kurt extends Hero {
    private static final int DAMAGE_SKILL1 = 20;
    private static final int DAMAGE_SKILL2 = 10;
    private static final int DAMAGE_SKILL3 = 30;
    private static final int MANA_COST1 = 15;
    private static final int MANA_COST2 = 8;
    private static final int MANA_COST3 = 40;

    public Kurt(String name) {
        super(name,
              "Crushing Blow",
              "Stunning Shout",
              "Rampage",
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
            "Kurt was a blacksmith's son, learning the craft from " +
            "his father until raiders destroyed their village. " +
            "Consumed by rage, he took up the hammer not just to " +
            "forge weapons, but to wield them. Now he channels " +
            "his fury into protecting others from suffering the " +
            "same fate."
        );
    }

    @Override
    public void displaySkillOptions() {
        System.out.println(
            "Skill 1 (Crushing Blow) | " + getSkillOneMultiplier() +
            " damage | " + getSkillOneManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 2 (Stunning Shout) | " + getSkillTwoMultiplier() +
            " damage | " + getSkillTwoManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 3 (Rampage) | " + getSkillThreeMultiplier() +
            " damage | " + getSkillThreeManaCost() + " Mana cost"
        );
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nKurt: My hammer will crush you!\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nKurt: That's what you get for crossing me.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nKurt: I... wasn't... strong enough...\n");
    }
}
