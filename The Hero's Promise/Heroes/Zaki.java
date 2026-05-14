package Heroes;

public class Zaki extends Hero {
    private static final int DAMAGE_SKILL1 = 85;
    private static final int DAMAGE_SKILL2 = 10;
    private static final int DAMAGE_SKILL3 = 5;
    private static final int MANA_COST1 = 5;
    private static final int MANA_COST2 = 5;
    private static final int MANA_COST3 = 5;

    public Zaki(String name) {
        super(name, "Phantom Dagger", "Smoke Bomb", "Shadow Fury", DAMAGE_SKILL1, DAMAGE_SKILL2, DAMAGE_SKILL3, MANA_COST1, MANA_COST2, MANA_COST3);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println();
        System.out.println("Zaki was a prince whose kingdom was attacked by invaders. Forced to flee, he survived by his wits and stealth, vowing to grow strong enough to reclaim his throne and protect his people. Now his name is whispered in hope among the survivors, and feared by the usurpers.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Phantom Dagger) | " + getSkillOneMultiplier() + " damage | " + getSkillOneManaCost() + " Mana cost");
        System.out.println("Skill 2 (Smoke Bomb) | " + getSkillTwoMultiplier() + " damage | " + getSkillTwoManaCost() + " Mana cost");
        System.out.println("Skill 3 (Shadow Fury) | " + getSkillThreeMultiplier() + " damage | " + getSkillThreeManaCost() + " Mana cost");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() { System.out.printf("\nZaki: The shadows are my ally.\n"); }
    @Override
    public void victoryDialogue() { System.out.printf("\nZaki: Another job well done.\n"); }
    @Override
    public void defeatDialogue() { System.out.printf("\nZaki: Not... like this...\n"); }
}