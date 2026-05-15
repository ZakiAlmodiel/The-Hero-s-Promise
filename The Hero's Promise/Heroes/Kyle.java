package Heroes;

public class Kyle extends Hero {
    public Kyle(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println("Kyle was a loyal captain of the royal guard.");
        System.out.println("Now he fights for the people crushed beneath the crown.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Steel Strike) | 10 damage | 10 Mana");
        System.out.println("Skill 2 (Shield Bash) | 15 damage | 15 Mana");
        System.out.println("Skill 3 (Warbringer) | 20 damage | 20 Mana");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nKyle: I fight for those who cannot.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nKyle: Justice has been served.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nKyle: I will not fall here...\n");
    }
}
