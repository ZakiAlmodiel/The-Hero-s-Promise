package Heroes;

public class Zaki extends Hero {
    public Zaki(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("Zaki's backstory - exiled prince seeking revenge.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1: Phantom Dagger");
        System.out.println("Skill 2: Smoke Bomb");
        System.out.println("Skill 3: Shadow Fury");
        System.out.println("Skill 4: Heal HP");
        System.out.println("Skill 5: Heal Mana");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nZaki: The shadows are my ally.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nZaki: Job well done.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nZaki: Not... like this...\n");
    }
}