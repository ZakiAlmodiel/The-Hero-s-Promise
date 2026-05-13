package Heroes;

public class Adrian extends Hero {
    public Adrian(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println("Adrian was born with an innate connection to the arcane.");
        System.out.println("After his village was razed, he embraced his power fully.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Arcane Bolt) | 10 damage | 10 Mana");
        System.out.println("Skill 2 (Mana Barrier) | 15 damage | 15 Mana");
        System.out.println("Skill 3 (Fireball) | 20 damage | 20 Mana");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nAdrian: The arcane answers my call.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nAdrian: A predictable outcome.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nAdrian: My studies... incomplete...\n");
    }
}