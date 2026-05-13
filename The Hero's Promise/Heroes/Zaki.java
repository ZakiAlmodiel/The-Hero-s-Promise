package Heroes;

public class Zaki extends Hero {
    public Zaki(String name) {
        super(name);
    }

    @Override
    public void displayBackStory() {
        System.out.println("-----------------BACKSTORY-------------------");
        System.out.println("Zaki is a prince whose kingdom was attacked by invaders.");
        System.out.println("Now his name is whispered in hope among survivors.");
    }

    @Override
    public void displaySkillOptions() {
        System.out.println("Skill 1 (Phantom Dagger) | 10 damage | 10 Mana");
        System.out.println("Skill 2 (Smoke Bomb) | 15 damage | 15 Mana");
        System.out.println("Skill 3 (Shadow Fury) | 20 damage | 20 Mana");
        System.out.println("Skill 4 (Heal HP)");
        System.out.println("Skill 5 (Heal Mana)");
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("\nZaki: The shadows are my ally.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("\nZaki: Another job well done.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("\nZaki: Not like this...\n");
    }
}