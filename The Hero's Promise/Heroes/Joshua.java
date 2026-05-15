package Heroes;

public class Joshua extends Hero {
    public Joshua(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("Joshua's backstory - holy paladin from monastery.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1: Holy Smite");
        System.out.println("Skill 2: Divine Shield");
        System.out.println("Skill 3: Judgment");
        System.out.println("Skill 4: Heal HP");
        System.out.println("Skill 5: Heal Mana");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nJoshua: The light shall prevail.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nJoshua: Righteous triumph.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nJoshua: Forgive... me...\n");
    }
}