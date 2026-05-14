package Heroes;

public class Joshua extends Hero {
    public Joshua(String name) {
        super(name);
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("Joshua speaks.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("Joshua wins.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("Joshua loses.\n");
    }
}