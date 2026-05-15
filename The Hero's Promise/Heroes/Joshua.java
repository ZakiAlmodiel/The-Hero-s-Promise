package Heroes;

public class Joshua extends Hero {
    public Joshua(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println("Joshua was raised in a secluded monastery.");
        System.out.println("Armed with holy power, he smites evil wherever it lurks.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Holy Smite) | 10 damage | 10 Mana");
        System.out.println("Skill 2 (Divine Shield) | 15 damage | 15 Mana");
        System.out.println("Skill 3 (Judgment) | 20 damage | 20 Mana");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nJoshua: The light shall prevail.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nJoshua: The righteous always triumph.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nJoshua: Forgive... me...\n");
    }
}