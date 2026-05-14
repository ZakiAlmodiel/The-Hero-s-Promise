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
    private long startTime;
    
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }
    
    public void start() {
        gameThread = new Thread(this, "GameEngine");
        gameThread.start();
    }
    
    private int heroSelectionMenu() {
        String[] heroDescriptions = {
            "⚔️ Kyle - The Warrior (Balanced fighter, good survivability)",
            "🗡️ Zaki - The Assassin (High burst damage, low mana costs)",
            "🔨 Kurt - The Berserker (High HP, strongest attacks)",
            "⚜️ Joshua - The Paladin (Holy magic, balanced stats)",
            "🔮 Adrian - The Mage (Arcane spells, mana-focused)"
        };
        
        return JOptionPane.showOptionDialog(gui, 
            "╔════════════════════════════════════════════════════════╗\n" +
            "║              CHOOSE YOUR HERO WISELY                    ║\n" +
            "║  Each hero has unique abilities and playstyle.         ║\n" +
            "║  Your choice will determine your journey!              ║\n" +
            "╚════════════════════════════════════════════════════════╝\n\n" +
            "Select your champion:",
            "Hero Selection",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            heroDescriptions, 
            heroDescriptions[0]);
    }
    
    private Hero createHero(int opt) {
        switch (opt) {
            case 0: 
                gui.appendToTerminal("⚔️ Kyle selected - The exiled captain\n");
                return new Kyle("Kyle");
            case 1: 
                gui.appendToTerminal("🗡️ Zaki selected - The shadow prince\n");
                return new Zaki("Zaki");
            case 2: 
                gui.appendToTerminal("🔨 Kurt selected - The vengeful blacksmith\n");
                return new Kurt("Kurt");
            case 3: 
                gui.appendToTerminal("⚜️ Joshua selected - The holy champion\n");
                return new Joshua("Joshua");
            case 4: 
                gui.appendToTerminal("🔮 Adrian selected - The arcane prodigy\n");
                return new Adrian("Adrian");
            default: 
                return new Kyle("Kyle");
        }
    }
    
    private void showHeroBackstory(Hero hero) {
        gui.appendToTerminal("\n📖 Reading backstory...\n\n");
        hero.displayBackStory();
        gui.appendToTerminal("\n✨ Press any key to continue... ✨\n");
    }
    
    private void displayWelcome() {
        gui.appendToTerminal("\n╔════════════════════════════════════════════════════════════╗\n");
        gui.appendToTerminal("║                     WELCOME, CHAMPION!                      ║\n");
        gui.appendToTerminal("║                                                              ║\n");
        gui.appendToTerminal("║  The fate of Varethia rests on your shoulders.              ║\n");
        gui.appendToTerminal("║  Battle through the realms, defeat the darkness,            ║\n");
        gui.appendToTerminal("║  and fulfill THE HERO'S PROMISE.                            ║\n");
        gui.appendToTerminal("║                                                              ║\n");
        gui.appendToTerminal("║  Good luck, " + currentHeroName + "!                         ║\n");
        gui.appendToTerminal("╚════════════════════════════════════════════════════════════╝\n\n");
    }
    
    private void displayStats() {
        gui.updateHeroBars(currentHero.getHeroHp(), currentHero.getMaxHp(), 
                          currentHero.getHeroMana(), currentHero.getMaxMana());
    }

    @Override
    public void run() {
        startTime = System.currentTimeMillis();
        
        gui.appendToTerminal("\n╔════════════════════════════════════════════════════════════╗\n");
        gui.appendToTerminal("║                   THE HERO'S PROMISE                        ║\n");
        gui.appendToTerminal("║                        Version 0.5                          ║\n");
        gui.appendToTerminal("║              A Dark Fantasy Adventure RPG                  ║\n");
        gui.appendToTerminal("╚════════════════════════════════════════════════════════════╝\n");
        
        Story.beforeFight();
        
        int choice = heroSelectionMenu();
        if (choice == JOptionPane.CLOSED_OPTION) {
            gui.appendToTerminal("\n❌ Game cancelled. Exiting...\n");
            System.exit(0);
        }
        
        currentHero = createHero(choice);
        currentHeroName = currentHero.getHeroName();
        
        gui.setHeroName(currentHeroName);
        displayWelcome();
        showHeroBackstory(currentHero);
        displayStats();
        
        gui.appendToTerminal("\n🌟 Your journey begins... 🌟\n");
        
        boolean level1Complete = Level1.battleMechanic1(currentHero);
        if (!level1Complete) {
            gameRunning = false;
            gui.appendToTerminal("\n💀 Game Over - The darkness consumes all. 💀\n");
            return;
        }
        
        gui.appendToTerminal("\n✨✨✨ Level 1 Complete! Restoring HP and MP... ✨✨✨\n");
        currentHero.fullRestore();
        currentHero.boostHero(25, 20);
        displayStats();
        
        boolean level2Complete = Level2.battleMechanic2(currentHero);
        if (!level2Complete) {
            gameRunning = false;
            gui.appendToTerminal("\n💀 Game Over - The ruins claim another victim. 💀\n");
            return;
        }
        
        gui.appendToTerminal("\n✨✨✨ Level 2 Complete! Restoring HP and MP... ✨✨✨\n");
        currentHero.fullRestore();
        currentHero.boostHero(30, 25);
        displayStats();
        
        gui.appendToTerminal("\n⚔️⚔️⚔️ The final confrontation approaches... ⚔️⚔️⚔️\n");
        
        boolean bossWon = Finalboss.battleMechanicFinal(currentHero);
        
        if (bossWon) {
            long totalTime = (System.currentTimeMillis() - startTime) / 1000;
            long minutes = totalTime / 60;
            long seconds = totalTime % 60;
            
            gui.appendToTerminal("\n╔════════════════════════════════════════════════════════════╗\n");
            gui.appendToTerminal("║                    GAME COMPLETED! 🏆                       ║\n");
            gui.appendToTerminal("╠════════════════════════════════════════════════════════════╣\n");
            gui.appendToTerminal("║                    FINAL STATISTICS                         ║\n");
            gui.appendToTerminal("╠════════════════════════════════════════════════════════════╣\n");
            gui.appendToTerminal(String.format("║  Final Level: %d                                  ║\n", currentHero.getLevel()));
            gui.appendToTerminal(String.format("║  Enemies Defeated: %d                             ║\n", currentHero.getTotalEnemiesDefeated()));
            gui.appendToTerminal(String.format("║  Total Damage Dealt: %d                           ║\n", currentHero.getTotalDamageDealt()));
            gui.appendToTerminal(String.format("║  Play Time: %d min %d sec                          ║\n", minutes, seconds));
            gui.appendToTerminal("╚════════════════════════════════════════════════════════════╝\n");
            gui.appendToTerminal("\n🌟 Thank you for playing The Hero's Promise! 🌟\n");
        }
        
        TerminalMusic.stopMusic();
    }
}