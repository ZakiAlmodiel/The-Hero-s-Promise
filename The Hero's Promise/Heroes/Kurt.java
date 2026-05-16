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
              "Throat Slit", "Shank", "Mutilate",
              DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3,
              MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println(
            "Kurt was a blacksmith's son, learning the craft from " +
            "his father until raiders destroyed their home and killed his dad. " +
            "Consumed by revenge, he left everything behind and went on " +
            "to be a lone warrior, years later he becomes a gambler " +
            "gambling all his earned savings until one bandit spoke up. " +
            "The bandit mentioned about the his dad and how he lost everything. " +
            "inflicted by rage , he proceeds to kill everyone inside the tavern " +
            "after some thought he remembered his dad and how he wanted " +
            "to take revenge on his dad. He left the tavern and wants to protect everyone " +
            "his seek for revenge turned into protecting others from suffering the " +
            "same fate."
        );
    }

    @Override
    public void displaySkillOptions() {
        System.out.println(
            "Skill 1 (Throat Slit) | " + getSkillOneMultiplier() +
            " damage | " + getSkillOneManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 2 (Shank) | " + getSkillTwoMultiplier() +
            " damage | " + getSkillTwoManaCost() + " Mana cost"
        );
        System.out.println(
            "Skill 3 (Mutilate) | " + getSkillThreeMultiplier() +
            " damage | " + getSkillThreeManaCost() + " Mana cost"
        );
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nKurt: Okay, one at a time, one at a time~\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nKurt: That's what you get for crossing me.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nKurt: I... wasn't... strong enough... dad...\n");
    }
}
