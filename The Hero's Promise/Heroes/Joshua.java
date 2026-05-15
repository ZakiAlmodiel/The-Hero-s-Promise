package Heroes;

public class Joshua extends Hero {
    private static final int DAMAGE_SKILL1 = 12;
    private static final int DAMAGE_SKILL2 = 18;
    private static final int DAMAGE_SKILL3 = 30;
    private static final int MANA_COST1 = 12;
    private static final int MANA_COST2 = 16;
    private static final int MANA_COST3 = 28;

    public Joshua(String name) {
        super(name, "Holy Smite", "Divine Shield", "Judgment", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    JOSHUA - THE PALADIN                    ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║  Joshua was raised in a secluded monastery, studying      ║");
        System.out.println("║  ancient texts and divine arts. When darkness spread      ║");
        System.out.println("║  across the land, the elders sent him forth as their      ║");
        System.out.println("║  champion. Armed with unwavering faith and holy power,    ║");
        System.out.println("║  he walks the path of light, smiting evil wherever it     ║");
        System.out.println("║  lurks.                                                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("\n┌─────────────────────────────────────────────────┐");
        System.out.println("│                 JOSHUA'S SKILLS                  │");
        System.out.println("├─────────────────────────────────────────────────┤");
        System.out.printf("│  1. %-12s │ %3d DMG │ %2d MP │\n", "Holy Smite", getSkillOneMultiplier(), getSkillOneManaCost());
        System.out.printf("│  2. %-12s │ %3d DMG │ %2d MP │\n", "Divine Shield", getSkillTwoMultiplier(), getSkillTwoManaCost());
        System.out.printf("│  3. %-12s │ %3d DMG │ %2d MP │\n", "Judgment", getSkillThreeMultiplier(), getSkillThreeManaCost());
        System.out.println("│  4. Heal HP                    │ +35 HP │         │");
        System.out.println("│  5. Heal Mana                  │ +35 MP │         │");
        System.out.println("└─────────────────────────────────────────────────┘");
    }

    @Override
    public void preBattleDialogue() {
        System.out.println("\n⚜️ Joshua: \"The light shall prevail over your darkness!\"");
    }

    @Override
    public void victoryDialogue() {
        System.out.println("\n✨ Joshua: \"The righteous always triumph. May you find peace.\"");
    }

    @Override
    public void defeatDialogue() {
        System.out.println("\n🙏 Joshua: \"Forgive... me... I have failed...\"");
    }
}