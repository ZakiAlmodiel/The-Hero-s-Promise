package Heroes;

public class Kyle extends Hero {

    // Constants declared but balancing not finalized
    private static final int DAMAGE_SKILL1 = 15;
    private static final int DAMAGE_SKILL2 = 10;
    private static final int DAMAGE_SKILL3 = 35;

    private static final int MANA_COST1 = 10;
    private static final int MANA_COST2 = 5;
    private static final int MANA_COST3 = 35;

    public Kyle(String name) {

        super(name, "Steel Strike", "Shield Bash", "Warbringer",
              DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3,
              MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {

        // Placeholder text; final wording not yet polished
        System.out.println(
            "Kyle was once the captain of the royal guard who now fights " +
            "as a wandering warrior seeking justice."
        );
    }

    @Override
    public void displaySkillOptions() {

        // Raw println; no getters used yet (still hardcoded numbers)
        System.out.println("Skill 1 (Steel Strike) | 15 damage | 10 Mana cost");
        System.out.println("Skill 2 (Shield Bash) | 10 damage | 5 Mana cost");
        System.out.println("Skill 3 (Warbringer) | 35 damage | 35 Mana cost");

        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {

        // printf not used yet; no leading newline
        System.out.println("Kyle: I fight for those who cannot.");
    }

    @Override
    public void victoryDialogue() {

        System.out.println("Kyle: Justice has been served.");
    }

    @Override
    public void defeatDialogue() {

        // dramatic ellipsis phrasing not yet finalized
        System.out.println("Kyle: I... will not fall... here...");
    }
}
