package Main;
import Heroes.*;
import Levels.*;
import Story.Story;
import javax.swing.*;

public class GameEngine implements Runnable {
    private final GameGUI gui;
    private Thread gameThread;
    private Hero currentHero;
    private String currentHeroName;
    private boolean gameRunning = true;
    
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }
    
    public void start() {
        gameThread = new Thread(this, "GameEngine");
        gameThread.start();
    }
    
    private int heroSelectionMenu() {
        String[] heroDescriptions = {
            "Kyle - Warrior (Balanced fighter)",
            "Zaki - Assassin (High burst damage)",
            "Kurt - Berserker (High HP, strong attacks)",
            "Joshua - Paladin (Holy magic, balanced)",
            "Adrian - Mage (Arcane spells, mana-focused)"
        };
        
        return JOptionPane.showOptionDialog(gui, "Choose your hero wisely...", "Hero Selection",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, heroDescriptions, heroDescriptions[0]);
    }
    
    private Hero createHero(int opt) {
        switch (opt) {
            case 0: 
                gui.appendToTerminal("Kyle selected - The exiled captain\n");
                return new Kyle("Kyle");
            case 1: 
                gui.appendToTerminal("Zaki selected - The shadow prince\n");
                return new Zaki("Zaki");
            case 2: 
                gui.appendToTerminal("Kurt selected - The vengeful blacksmith\n");
                return new Kurt("Kurt");
            case 3: 
                gui.appendToTerminal("Joshua selected - The holy champion\n");
                return new Joshua("Joshua");
            case 4: 
                gui.appendToTerminal("Adrian selected - The arcane prodigy\n");
                return new Adrian("Adrian");
            default: 
                return new Kyle("Kyle");
        }
    }
    
    private void showHeroBackstory(Hero hero) {
        gui.appendToTerminal("\n");
        hero.displayBackStory();
        gui.appendToTerminal("\nPress any key to continue...\n");
    }
    
    private void displayStats() {
        gui.updateHeroBars(currentHero.getHeroHp(), currentHero.getMaxHp(), 
                          currentHero.getHeroMana(), currentHero.getMaxMana());
    }

    @Override
    public void run() {
        gui.appendToTerminal("==================================\n");
        gui.appendToTerminal("       THE HERO'S PROMISE        \n");
        gui.appendToTerminal("           Version 0.4           \n");
        gui.appendToTerminal("==================================\n\n");
        
        Story.beforeFight();
        
        int choice = heroSelectionMenu();
        currentHero = createHero(choice);
        currentHeroName = currentHero.getHeroName();
        
        gui.setHeroName(currentHeroName);
        gui.appendToTerminal("\nWelcome, " + currentHero.getHeroName() + "!\n");
        
        showHeroBackstory(currentHero);
        displayStats();
        
        gui.appendToTerminal("\nYour journey begins...\n");
        
        boolean level1Complete = Level1.battleMechanic1(currentHero);
        if (!level1Complete) {
            gameRunning = false;
            gui.appendToTerminal("\nGame Over - The darkness consumes all.\n");
            return;
        }
        
        gui.appendToTerminal("\n✦ Level 1 Complete! Restoring HP and MP... ✦\n");
        currentHero.fullRestore();
        currentHero.boostHero(25, 20);
        displayStats();
        
        boolean level2Complete = Level2.battleMechanic2(currentHero);
        if (!level2Complete) {
            gameRunning = false;
            gui.appendToTerminal("\nGame Over - The ruins claim another victim.\n");
            return;
        }
        
        gui.appendToTerminal("\n✦ Level 2 Complete! Restoring HP and MP... ✦\n");
        currentHero.fullRestore();
        currentHero.boostHero(30, 25);
        displayStats();
        
        gui.appendToTerminal("\n✦ The final confrontation approaches... ✦\n");
        
        Finalboss.battleMechanicFinal(currentHero);
        
        gui.appendToTerminal("\n==================================\n");
        gui.appendToTerminal("     Thanks for playing!         \n");
        gui.appendToTerminal("==================================\n");
        
        TerminalMusic.stopMusic();
    }
}