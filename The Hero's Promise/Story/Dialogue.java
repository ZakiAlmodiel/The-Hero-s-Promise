package Story;

public abstract class Dialogue {
    public abstract void preBattleDialogue();
    public abstract void victoryDialogue();
    public abstract void defeatDialogue();
    
    public void displayDialogue(String message, String speaker) {
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.printf("│ %-10s: %-30s │\n", speaker, message);
        System.out.println("└─────────────────────────────────────────┘");
    }
    
    public void displayNarration(String message) {
        System.out.println("✦ " + message);
    }
    
    public void displayBattleCry(String cry) {
        System.out.println("⚔️ " + cry.toUpperCase() + " ⚔️");
    }
}
