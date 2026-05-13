package Heroes;

public class Adrian extends Hero {
    public Adrian(String name) {
        super(name);
    }

    @Override
    public void preBattleDialogue() {
        System.out.printf("Adrian speaks.\n");
    }

    @Override
    public void victoryDialogue() {
        System.out.printf("Adrian wins.\n");
    }

    @Override
    public void defeatDialogue() {
        System.out.printf("Adrian loses.\n");
    }
}