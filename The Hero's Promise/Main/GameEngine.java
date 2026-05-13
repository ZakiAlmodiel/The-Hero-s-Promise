package Main;
import Heroes.*;
import Enemy.Enemy;
import Levels.*;
import javax.swing.*;
import java.awt.*;

public class GameEngine implements Runnable {
    private final GameGUI gui;
    private Thread gameThread;
    private Hero currentHero;
    private String currentHeroName;
    
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }
    
    public void start() {
        gameThread = new Thread(this, "GameEngine");
        gameThread.start();
    }
    
    private int heroSelectionMenu() {
        String[] names = {"Kyle", "Zaki", "Kurt", "Joshua", "Adrian"};
        return JOptionPane.showOptionDialog(gui, "Choose your hero", "Hero Selection",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
    }
    
    private Hero createHero(int opt) {
        switch (opt) {
            case 0: return new Kyle("Kyle");
            case 1: return new Zaki("Zaki");
            case 2: return new Kurt("Kurt");
            case 3: return new Joshua("Joshua");
            case 4: return new Adrian("Adrian");
            default: return new Kyle("Kyle");
        }
    }

    @Override
    public void run() {
        gui.appendToTerminal("==================================\n");
        gui.appendToTerminal("       THE HERO'S PROMISE        \n");
        gui.appendToTerminal("==================================\n\n");
        
        int choice = heroSelectionMenu();
        currentHero = createHero(choice);
        currentHeroName = currentHero.getHeroName();
        
        gui.setHeroName(currentHeroName);
        gui.appendToTerminal("Selected: " + currentHero.getHeroName() + "\n");
        
        currentHero.preBattleDialogue();
        
        boolean level1Complete = Level1.battleMechanic1(currentHero);
        if (!level1Complete) {
            gui.appendToTerminal("\nGame Over - Restart the game.\n");
            return;
        }
        
        gui.appendToTerminal("\nLevel 1 Complete! Proceeding to Level 2...\n");
        currentHero.fullRestore();
        
        boolean level2Complete = Level2.battleMechanic2(currentHero);
        if (!level2Complete) {
            gui.appendToTerminal("\nGame Over - Restart the game.\n");
            return;
        }
        
        gui.appendToTerminal("\nLevel 2 Complete! Proceeding to Final Boss...\n");
        currentHero.fullRestore();
        
        Finalboss.battleMechanicFinal(currentHero);
    }
}