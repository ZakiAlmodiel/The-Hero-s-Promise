package Heroes;

public class Kyle extends Hero {
    private static final int DAMAGE_SKILL1 = 15;
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
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                     KYLE - THE WARRIOR                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Kyle was a loyal captain of the royal guard, sworn to    ║");
        System.out.println("║  protect the kingdom at all costs. When the king's        ║");
        System.out.println("║  corruption led to innocent bloodshed, he defected,       ║");
        System.out.println("║  becoming a wandering warrior seeking true justice.       ║");
        System.out.println("║  Now he fights not for a crown, but for the people        ║");
        System.out.println("║  crushed beneath it.                                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│                  KYLE'S SKILLS                   │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf("│  1. %-12s │ %3d DMG │ %2d MP │\n", "Steel Strike", getSkillOneMultiplier(), getSkillOneManaCost());
        System.out.printf("│  2. %-12s │ %3d DMG │ %2d MP │\n", "Shield Bash", getSkillTwoMultiplier(), getSkillTwoManaCost());
        System.out.printf("│  3. %-12s │ %3d DMG │ %2d MP │\n", "Warbringer", getSkillThreeMultiplier(), getSkillThreeManaCost());
        System.out.println("│  4. Heal HP                    │ +35 HP │         │");
        System.out.println("│  5. Heal Mana                  │ +35 MP │         │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    @Override
    public void preBattleDialogue() {
        System.out.println("\n⚔️ Kyle: \"I fight for those who cannot fight for themselves.\"");
    }

    @Override
    public void victoryDialogue() {
        System.out.println("\n🛡️ Kyle: \"Justice has been served today.\"");
    }

    @Override
    public void defeatDialogue() {
        System.out.println("\n💔 Kyle: \"I... will not... fall here...\"");
    }
}
