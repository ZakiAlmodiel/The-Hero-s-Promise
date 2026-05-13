package Heroes;

public class Adrian extends Hero {
    public Adrian(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("Adrian's backstory - arcane mage from destroyed village.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1: Arcane Bolt");
        System.out.println("Skill 2: Mana Barrier");
        System.out.println("Skill 3: Fireball");
        System.out.println("Skill 4: Heal HP");
        System.out.println("Skill 5: Heal Mana");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nAdrian: The arcane answers my call.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nAdrian: Predictable.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nAdrian: My studies... incomplete...\n");
    }
}