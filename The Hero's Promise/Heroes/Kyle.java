package Heroes;

public class Kyle extends Hero {
    public Kyle(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("Kyle's backstory - exiled royal guard captain.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1: Steel Strike");
        System.out.println("Skill 2: Shield Bash");
        System.out.println("Skill 3: Warbringer");
        System.out.println("Skill 4: Heal HP");
        System.out.println("Skill 5: Heal Mana");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nKyle: I fight for the powerless.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nKyle: Justice served.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nKyle: I will not fall here...\n");
    }
}
