package Main;
import Heroes.*;
import javax.swing.*;

public class GameEngine implements Runnable {
    private final GameGUI gui;
    private Thread gameThread;
    private Hero currentHero;
    
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }
    
    public void start() {
        gameThread = new Thread(this, "GameEngine");
        gameThread.start();
    }
    
    private void selectHero() {
        String[] heroes = {"Kyle", "Zaki", "Kurt", "Joshua", "Adrian"};
        int choice = JOptionPane.showOptionDialog(gui, "Choose your hero", "Hero Selection",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, heroes, heroes[0]);
        
        switch(choice) {
            case 0: currentHero = new Kyle("Kyle"); break;
            case 1: currentHero = new Zaki("Zaki"); break;
            case 2: currentHero = new Kurt("Kurt"); break;
            case 3: currentHero = new Joshua("Joshua"); break;
            case 4: currentHero = new Adrian("Adrian"); break;
            default: currentHero = new Kyle("Kyle");
        }
        
        gui.appendToTerminal("Selected: " + currentHero.getHeroName() + "\n");
    }

    @Override
    public void run() {
        selectHero();
        gui.appendToTerminal("\nGame starting...\n");
    }
}