package Heroes;

public class Zaki extends Hero {
    private static final int DAMAGE_SKILL1 = 20;
    private static final int DAMAGE_SKILL2 = 10;
    private static final int DAMAGE_SKILL3 = 25;
    private static final int MANA_COST1 = 8;
    private static final int MANA_COST2 = 6;
    private static final int MANA_COST3 = 22;

    public Zaki(String name) {
        super(name, "Phantom Dagger", "Smoke Bomb", "Shadow Fury", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Zaki was a prince whose kingdom was attacked by invaders.");
        System.out.println("Forced to flee, he survived by his wits and stealth, vowing to grow strong enough to reclaim his throne.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Phantom Dagger) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana");
        System.out.println("Skill 2 (Smoke Bomb) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana");
        System.out.println("Skill 3 (Shadow Fury) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana");
        System.out.println("Skill 4 (Heal HP) - Restores 35 HP");
        System.out.println("Skill 5 (Heal Mana) - Restores 35 MP");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nZaki: The shadows are my ally. You won't see me coming.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nZaki: Another job well done. One step closer to reclaiming my throne.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nZaki: Not... like this... I have... people counting on me...\n");
    }
}