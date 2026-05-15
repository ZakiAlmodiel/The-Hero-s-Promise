package Story;

public abstract class Dialogue {
    public abstract void preBattleDialogue();
    public abstract void victoryDialogue();
    public abstract void defeatDialogue();
    
    public void displayDialogue(String message) {
        System.out.println(message);
    }
}
