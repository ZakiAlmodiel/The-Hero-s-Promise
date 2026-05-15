package Heroes;

public class Kyle extends Hero {
    public Kyle(String name) {
        super(name);
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("Kyle speaks.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("Kyle wins.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("Kyle loses.\n");
    }
}
