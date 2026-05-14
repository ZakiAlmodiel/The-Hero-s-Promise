package Heroes;

public class Adrian extends Hero {
    private static final int DAMAGE_SKILL1 = 12;
    private static final int DAMAGE_SKILL2 = 20;
    private static final int DAMAGE_SKILL3 = 28;
    private static final int MANA_COST1 = 10;
    private static final int MANA_COST2 = 18;
    private static final int MANA_COST3 = 25;

    public Adrian(String name) {
        super(name, "Arcane Bolt", "Mana Barrier", "Fireball", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                      ADRIAN - THE MAGE                     ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Adrian was born with an innate connection to the arcane,  ║");
        System.out.println("║  a secret he guarded from a world that feared magic.       ║");
        System.out.println("║  After his village was razed by anti-magic fanatics,       ║");
        System.out.println("║  he embraced his power fully, vowing to master every       ║");
        System.out.println("║  spell to ensure no one ever suffers as his family did.    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│                  ADRIAN'S SKILLS                 │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf("│  1. %-12s │ %3d DMG │ %2d MP │\n", "Arcane Bolt", getSkillOneMultiplier(), getSkillOneManaCost());
        System.out.printf("│  2. %-12s │ %3d DMG │ %2d MP │\n", "Mana Barrier", getSkillTwoMultiplier(), getSkillTwoManaCost());
        System.out.printf("│  3. %-12s │ %3d DMG │ %2d MP │\n", "Fireball", getSkillThreeMultiplier(), getSkillThreeManaCost());
        System.out.println("│  4. Heal HP                    │ +35 HP │         │");
        System.out.println("│  5. Heal Mana                  │ +35 MP │         │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    @Override
    public void preBattleDialogue() {
        System.out.println("\n🧙 Adrian: \"The arcane answers my call. Face my magic!\"");
    }

    @Override
    public void victoryDialogue() {
        System.out.println("\n✨ Adrian: \"A predictable outcome. Magic always prevails.\"");
    }

    @Override
    public void defeatDialogue() {
        System.out.println("\n📖 Adrian: \"My... studies... were... incomplete...\"");
    }
}