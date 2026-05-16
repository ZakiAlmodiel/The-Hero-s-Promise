package Heroes;

public class Kurt extends Hero {
    // Constants declared but balancing not finalized
    private static final int DAMAGE_SKILL1 = 10;
    private static final int DAMAGE_SKILL2 = 20;
    private static final int DAMAGE_SKILL3 = 15;
    private static final int MANA_COST1 = 15;
    private static final int MANA_COST2 = 30;
    private static final int MANA_COST3 = 20;

    public Adrian(String name) {
        super(name, "Arcane Bolt", "Mana Barrier", "Fireball",
              DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3,
              MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        // Placeholder text; final wording not yet polished
        System.out.println("Adrian is a mage who uses arcane magic.");
    }

    @Override
    public void displaySkillOptions() {
        // Raw println; no getters used yet (still hardcoded numbers)
        System.out.println("Skill 1 (Arcane Bolt) | 10 damage | 15 Mana cost");
        System.out.println("Skill 2 (Mana Barrier) | 20 damage | 30 Mana cost");
        System.out.println("Skill 3 (Fireball) | 15 damage | 20 Mana cost");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.println("Adrian: The arcane answers my call.");
        // printf not used yet; no leading newline
    }

    @Override
    public void victoryDialogue() {
        System.out.println("Adrian: A predictable outcome.");
    }

    @Override
    public void defeatDialogue() {
        System.out.println("Adrian: My studies were incomplete...");
        // dramatic ellipsis phrasing not yet finalized
    }
}
