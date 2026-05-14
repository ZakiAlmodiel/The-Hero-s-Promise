package Main;

import Heroes.*;
import Enemy.Enemy;
import Exceptions.InsufficientManaException;
import Exceptions.InvalidSkillException;
import Levels.*;
import javax.swing.*;
import java.awt.*;

public class GameEngine implements Runnable {
    private final GameGUI gui;
    private Thread gameThread;
    private final java.util.concurrent.SynchronousQueue<Integer> inputQueue = new java.util.concurrent.SynchronousQueue<>();
    private String currentHeroName = "", currentEnemyName = "";
    private int currentEnemyMaxHp = 100;
    private int dialogueCounter = 0;
    private static final String[] DEATH_DIALOGUES = {"Enemy: Nooo... my gold...", "Enemy: Impossible... I'm... strong...", "Enemy: My spells... failed me...", "Enemy: Death... takes me again...", "Enemy: I... am... eternal..."};
    private static final String[] STORY_BEFORE_L1 = {"The trail led to the edge of Elden Forest, where twilight never ends.", "Beneath dying stars, beasts tainted by dark magic roamed free.", "The air grew heavy with decay... something wicked was watching.", "The hero drew their weapon as the first enemy appeared..."};
    private static final String[] STORY_BEFORE_L2 = {"Past the cursed forest lay the Ruins of Nareth...", "Echoes of lost souls haunted the streets.", "The hero's light flickered, but courage burned stronger still.", "A new threat awaited... larger, smarter, and hungry for fear."};
    private static final String[] STORY_BEFORE_BOSS = {"At the heart of the shattered mountain stood the Temple of Ash.", "The Final Warden awaited... sworn to end all who broke the ancient Oath.", "The air burned with power. Destiny pulled tighter.", "Every wound, every victory... led to this single moment."};
    private static final String[] STORY_VICTORY = {"As the Warden fell, the world exhaled. Shadows began to fade.", "The Oath Stone glowed brilliantly, spreading light across the horizon.", "The hero stood weary but unbroken,", "as dawn broke for the first time in years.", "", "Peace had returned... but darkness never truly dies.", "And so the tale of The Hero's Promise began anew..."};
    private static final String[] STORY_THANK_YOU = {"", "Thank you for playing", "The Hero's Promise"};
    private static final String[] BACKSTORY_KYLE = {"~ Kyle ~", "", "Kyle was a loyal captain of the royal guard,", "sworn to protect the kingdom at all costs.", "When the king's corruption led to innocent bloodshed,", "he defected, becoming a wandering warrior seeking true justice.", "", "Now he fights not for a crown,", "but for the people crushed beneath it."};
    private static final String[] BACKSTORY_ZAKI = {"~ Zaki ~", "", "Zaki grew up surviving by his wits and stealth.", "After his younger brother was taken by slave traders,", "he vowed to become strong enough", "to protect those who cannot protect themselves.", "", "Now his name is whispered in gratitude among the people,", "and feared by the corrupt."};
    private static final String[] BACKSTORY_KURT = {"~ Kurt ~", "", "Kurt was a blacksmith's son,", "learning the craft from his father", "until raiders destroyed their village.", "Consumed by rage, he took up the hammer", "not just to forge weapons, but to wield them.", "", "Now he channels his fury into protecting others", "from suffering the same fate."};
    private static final String[] BACKSTORY_JOSHUA = {"~ Joshua ~", "", "Joshua was raised in a secluded monastery,", "studying ancient texts and divine arts.", "When darkness spread across the land,", "the elders sent him forth as their champion.", "", "Armed with unwavering faith and holy power,", "he walks the path of light,", "healing the wounded and smiting evil wherever it lurks."};
    private static final String[] BACKSTORY_ADRIAN = {"~ Adrian ~", "", "Adrian was born with an innate connection to the arcane,", "a secret he guarded from a world that feared magic.", "After his village was razed by anti-magic fanatics,", "he embraced his power fully,", "", "vowing to master every spell", "to ensure no one ever suffers as his family did."};

    public GameEngine(GameGUI gui) { this.gui = gui; }
    public void start() { gameThread = new Thread(this, "GameEngine"); gameThread.setDaemon(true); gameThread.start(); }
    private int waitForInput(String[] labels) { gui.showButtons(labels, (index, label) -> { try { inputQueue.put(index); } catch (InterruptedException ignored) {} }); try { return inputQueue.take(); } catch (InterruptedException e) { return 0; } }
    private void println(String t) { gui.appendToTerminal(t + "\n"); }
    private void println() { gui.appendToTerminal("\n"); }
    private void printHeading(String t) { gui.appendToTerminalColored("\n" + t + "\n", GameGUI.ORANGE_BRIGHT); }
    private void printSuccess(String t) { gui.appendToTerminalColored(t + "\n", new Color(120, 220, 120)); }
    private void printDanger(String t) { gui.appendToTerminalColored(t + "\n", new Color(220, 80, 80)); }
    private void printWarning(String t) { gui.appendToTerminalColored(t + "\n", new Color(255, 200, 50)); }
    private void printAction(String t) { gui.appendToTerminalColored(t + "\n", new Color(255, 140, 0)); }
    private void setHeroIdle() { gui.setHeroSprite(currentHeroName, "idle"); }
    private void setHeroAttack() { gui.setHeroSprite(currentHeroName, "attack"); }
    private void setHeroHurt() { gui.setHeroSprite(currentHeroName, "hurt"); }
    private void setEnemyIdle() { gui.setEnemySprite(currentEnemyName, "idle"); }
    private void setEnemyAttack() { gui.setEnemySprite(currentEnemyName, "attack"); }
    private void setEnemyHurt() { gui.setEnemySprite(currentEnemyName, "hurt"); }
    private void refreshBars(Hero hero, Enemy enemy) { gui.updateHeroBars(hero.getHeroHp(), hero.getMaxHp(), hero.getHeroMana(), hero.getMaxMana()); if (enemy != null) gui.updateEnemyBars(enemy.getEnemyHp(), currentEnemyMaxHp, enemy.getEnemyMana(), 100); }
    private void sleep(int ms) { try { Thread.sleep(ms); } catch (InterruptedException ignored) {} }
    private String backstoryBg(Hero hero) { switch (hero.getHeroName().toLowerCase()) { case "kyle": return "kylebackground_story"; case "zaki": return "zakibackground_story"; case "kurt": return "kurtbackground_story"; case "joshua": return "joshuabackground_story"; case "adrian": return "adrianbackground_story"; default: return "chooseherobackground"; } }
    private String[] backstoryLines(Hero hero) { switch (hero.getHeroName().toLowerCase()) { case "kyle": return BACKSTORY_KYLE; case "zaki": return BACKSTORY_ZAKI; case "kurt": return BACKSTORY_KURT; case "joshua": return BACKSTORY_JOSHUA; case "adrian": return BACKSTORY_ADRIAN; default: return new String[]{ hero.getHeroName() + " backstory not found." }; } }

    @Override
    public void run() {
        TerminalMusic.playMusic();
        boolean exitGame = false;
        while (!exitGame) {
            gui.showSymbols();
            gui.setChooseHeroBackground();
            printHeading("==================================");
            printHeading("       THE HERO'S PROMISE        ");
            printHeading("==================================");
            Hero hero = null;
            while (hero == null) {
                int heroOption = heroSelectionMenu();
                hero = createHero(heroOption);
                currentHeroName = hero.getHeroName();
                gui.setHeroName(currentHeroName);
                gui.setHeroSprite(currentHeroName, "idle");
                gui.enemySpritePanel.showSymbol();
                gui.updateHeroBars(hero.getHeroHp(), hero.getMaxHp(), hero.getHeroMana(), hero.getMaxMana());
                gui.enemySpritePanel.hideBars();
                println();
                captureDialogue(hero::preBattleDialogue);
                displayPickingDetails(hero);
                boolean readyToProceed = false, goBack = false;
                while (!readyToProceed && !goBack) {
                    int choice = waitForInput(new String[]{"Read Back Story", "Proceed to Battle", "\u2190 Back"});
                    if (choice == 2) { goBack = true; }
                    else if (choice == 0) { gui.showStoryScreen(backstoryBg(hero), backstoryLines(hero), 36); gui.hideStoryScreen(); }
                    else { readyToProceed = true; }
                }
                if (goBack) { gui.clearTerminal(); gui.showSymbols(); gui.enemySpritePanel.hideBars(); gui.heroSpritePanel.hideBars(); hero = null; }
            }
            final Hero selectedHero = hero;
            gui.showStoryScreen("beforeLevel1_background", STORY_BEFORE_L1, 38);
            gui.hideStoryScreen();
            printHeading("\n----------------- BATTLE STARTED -------------------");
            displayPickingDetails(selectedHero);
            boolean l1 = Level1.battleMechanic1(selectedHero);
            if (!l1) { exitGame = handleGameOver(); if (!exitGame) { TerminalMusic.stopMusic(); gui.clearTerminal(); TerminalMusic.playMusic(); } continue; }
            println("\nYou survived Level 1! Restoring HP and MP...");
            selectedHero.fullRestore();
            boostHero(selectedHero, 25, 20);
            gui.updateHeroBars(selectedHero.getHeroHp(), selectedHero.getMaxHp(), selectedHero.getHeroMana(), selectedHero.getMaxMana());
            gui.showStoryScreen("beforeLevel2_background", STORY_BEFORE_L2, 38);
            gui.hideStoryScreen();
            printHeading("\n----------------- LEVEL 2 -------------------");
            displayPickingDetails(selectedHero);
            boolean l2 = Level2.battleMechanic2(selectedHero);
            if (!l2) { exitGame = handleGameOver(); if (!exitGame) { TerminalMusic.stopMusic(); gui.clearTerminal(); TerminalMusic.playMusic(); } continue; }
            println("\nYou survived Level 2! Restoring HP and MP...");
            selectedHero.fullRestore();
            boostHero(selectedHero, 30, 25);
            gui.updateHeroBars(selectedHero.getHeroHp(), selectedHero.getMaxHp(), selectedHero.getHeroMana(), selectedHero.getMaxMana());
            gui.showStoryScreen("beforeFinalBoss_background", STORY_BEFORE_BOSS, 38);
            gui.hideStoryScreen();
            printHeading("\n----------------- FINAL BOSS ENCOUNTER -------------------");
            displayPickingDetails(selectedHero);
            boolean bossWon = Finalboss.battleMechanicFinal(selectedHero);
            if (!bossWon) { exitGame = handleGameOver(); if (!exitGame) { TerminalMusic.stopMusic(); gui.clearTerminal(); TerminalMusic.playMusic(); } continue; }
            TerminalMusic.stopMusic();
            SoundManager.playVictory();
            gui.showStoryScreen("victorystory", STORY_VICTORY, 42);
            gui.showStoryScreen("victorystory", STORY_THANK_YOU, 60);
            sleep(800);
            System.exit(0);
        }
        println("\nThank you for playing The Hero's Promise!");
        sleep(2000);
        System.exit(0);
    }

    private int heroSelectionMenu() { println("\nChoose your hero:"); String[] names = {"Kyle", "Zaki", "Kurt", "Joshua", "Adrian"}; int idx = waitForInput(names); println("> Selected: " + names[idx]); return idx + 1; }
    private Hero createHero(int opt) { switch (opt) { case 1: return new Kyle("Kyle"); case 2: return new Zaki("Zaki"); case 3: return new Kurt("Kurt"); case 4: return new Joshua("Joshua"); case 5: return new Adrian("Adrian"); default: return new Kyle("Kyle"); } }
    private boolean runLevel1(Hero hero) { gui.setBackground("background_level1"); String[] monsters = {"Shadowblade", "Frostlord", "Demonking"}; int[] maxHps = {48, 56, 64}; for (int d = 1; d <= 3; d++) { Enemy e = new Enemy(monsters[d-1], 40 + d*8, 8 + d*2); currentEnemyName = monsters[d-1]; currentEnemyMaxHp = maxHps[d-1]; gui.setEnemyName(currentEnemyName); gui.showEnemy(); setEnemyIdle(); setHeroIdle(); refreshBars(hero, e); if (!runBattle(hero, e)) return false; } printSuccess("\n----CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1----"); return true; }
    private boolean runLevel2(Hero hero) { gui.setBackground("background_level2"); String[] spriteKeys = {"CryptLord", "Plagueweaver", "VoidSentinel"}; String[] displayNames = {"Crypt Lord", "Plagueweaver", "Void Sentinel"}; int[] maxHps = {70, 80, 90}; for (int d = 1; d <= 3; d++) { Enemy e = new Enemy(displayNames[d-1], 60 + d*10, 12 + d*3); currentEnemyName = spriteKeys[d-1]; currentEnemyMaxHp = maxHps[d-1]; gui.setEnemyName(displayNames[d-1]); gui.showEnemy(); setEnemyIdle(); setHeroIdle(); refreshBars(hero, e); if (!runBattle(hero, e)) return false; } printSuccess("\n----CONGRATULATIONS! YOU HAVE FINISHED LEVEL 2----"); return true; }
    private boolean runFinalBoss(Hero hero) { gui.setBackground("background_finalboss"); int bossMaxHp = 220; Enemy boss = new Enemy("Azelor, Rift Keeper", bossMaxHp, 18); currentEnemyName = "Azelor"; currentEnemyMaxHp = bossMaxHp; gui.setEnemyName("Azelor, Rift Keeper"); gui.showEnemy(); setEnemyIdle(); setHeroIdle(); refreshBars(hero, boss); printHeading("\n--- FINAL BOSS: " + boss.getEnemyName() + " ---"); int turn = 0; while (hero.getHeroHp() > 0 && boss.getEnemyHp() > 0) { turn++; int skill = pickSkill(hero); applyHeroSkill(hero, boss, skill); refreshBars(hero, boss); if (boss.getEnemyHp() <= 0) { setEnemyHurt(); sleep(500); setEnemyIdle(); printEnemyDeath(); refreshBars(hero, boss); printSuccess("\nCONGRATULATIONS! You defeated " + boss.getEnemyName()); captureDialogue(hero::victoryDialogue); printSuccess("\n==== YOU HAVE SAVED THE REALM! GAME COMPLETE! ===="); setHeroIdle(); return true; } if (turn % 5 == 0) { int dmg = boss.getEnemyDamage() + 15; SoundManager.playAttack(); setEnemyAttack(); sleep(300); setHeroHurt(); sleep(400); setHeroIdle(); setEnemyIdle(); hero.takeDamage(dmg, boss.getEnemyName() + " RIFT BLAST"); hero.setHeroMana(hero.getHeroMana() - 8); boss.heal(8); println("\n" + boss.getEnemyName() + " unleashes RIFT BLAST! Deals " + dmg + " damage, drains 8 MP."); println(boss.getEnemyName() + " regenerates 8 HP from the rift!"); } else { int dmg = boss.getEnemyDamage(); SoundManager.playAttack(); setEnemyAttack(); sleep(300); setHeroHurt(); sleep(400); setHeroIdle(); setEnemyIdle(); hero.takeDamage(dmg, boss.getEnemyName()); println("\n" + boss.getEnemyName() + " attacked! Dealt " + dmg + " damage."); } refreshBars(hero, boss); if (hero.getHeroHp() <= 0) { SoundManager.playDefeat(); captureDialogue(hero::defeatDialogue); printDanger("---------You have been defeated!---------"); printDanger("----------------GAME OVER!---------------"); return false; } } return false; }
    private boolean runBattle(Hero hero, Enemy enemy) { while (true) { int skill = pickSkill(hero); applyHeroSkill(hero, enemy, skill); refreshBars(hero, enemy); if (enemy.getEnemyHp() <= 0) { setEnemyHurt(); sleep(500); setEnemyIdle(); printEnemyDeath(); refreshBars(hero, enemy); captureDialogue(hero::victoryDialogue); printSuccess("\nCONGRATULATIONS! You defeated " + enemy.getEnemyName()); setHeroIdle(); sleep(400); return true; } int dmg = enemy.getEnemyDamage(); SoundManager.playAttack(); setEnemyAttack(); sleep(300); setHeroHurt(); sleep(400); setHeroIdle(); setEnemyIdle(); hero.takeDamage(dmg, enemy.getEnemyName()); println("\n" + enemy.getEnemyName() + " attacked! Dealt " + dmg + " damage."); refreshBars(hero, enemy); if (hero.getHeroHp() <= 0) { SoundManager.playDefeat(); captureDialogue(hero::defeatDialogue); printDanger("---------You have been defeated!---------"); printDanger("----------------GAME OVER!---------------"); return false; } } }
    private int pickSkill(Hero hero) { String[] btns = { "1: " + hero.getSkillOneName() + " (" + hero.getSkillOneMultiplier() + "dmg|" + hero.getSkillOneManaCost() + "MP)", "2: " + hero.getSkillTwoName() + " (" + hero.getSkillTwoMultiplier() + "dmg|" + hero.getSkillTwoManaCost() + "MP)", "3: " + hero.getSkillThreeName() + " (" + hero.getSkillThreeMultiplier() + "dmg|" + hero.getSkillThreeManaCost() + "MP)", "4: Heal HP  (+35)", "5: Heal Mana (+35)" }; printAction("\nChoose your action:"); return waitForInput(btns) + 1; }
    private void applyHeroSkill(Hero hero, Enemy enemy, int skill) { boolean isHeal = (skill == 4 || skill == 5); boolean canAfford = true; if (!isHeal) { int cost = (skill == 1) ? hero.getSkillOneManaCost() : (skill == 2) ? hero.getSkillTwoManaCost() : hero.getSkillThreeManaCost(); canAfford = hero.getHeroMana() >= cost; } if (!isHeal && canAfford) { SoundManager.playAttack(); setHeroAttack(); sleep(300); setEnemyHurt(); sleep(400); setHeroIdle(); setEnemyIdle(); } else if (isHeal) { SoundManager.playHeal(); } captureAction(() -> { try { hero.useSkill(enemy, skill); } catch (InsufficientManaException e) { printWarning("\u26A0 Not enough mana for \"" + e.getSkillName() + "\"! Need " + e.getRequiredMana() + " MP, have " + e.getCurrentMana() + " MP."); } catch (InvalidSkillException e) { printWarning("\u26A0 Invalid skill choice: " + e.getChosenSkill() + ". Valid range is " + e.getMinSkill() + " – " + e.getMaxSkill() + "."); } }); }
    private boolean handleGameOver() { printHeading("\n=================================="); printHeading("           GAME OVER"); printHeading("=================================="); int choice = waitForInput(new String[]{"Exit Game", "Start Over"}); if (choice == 0) System.exit(0); return false; }
    private void captureDialogue(Runnable r) { captureAction(r); }
    private void captureAction(Runnable r) { java.io.PrintStream old = System.out; java.io.ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream(); System.setOut(new java.io.PrintStream(buf)); try { r.run(); } finally { System.out.flush(); System.setOut(old); } String out = buf.toString().trim(); if (!out.isEmpty()) println(out); }
    private void printEnemyDeath() { println("\n" + DEATH_DIALOGUES[dialogueCounter]); dialogueCounter = (dialogueCounter + 1) % DEATH_DIALOGUES.length; }
    private void displayPickingDetails(Hero hero) { println(); println("--------------------------------------------------"); println("Hero : " + hero.getHeroName()); println("--------------------------------------------------"); println(); }
    private void boostHero(Hero hero, int hp, int mana) { hero.boostHero(hp, mana); println("\n*** HERO POWER UP! ***"); println("Max HP increased by " + hp + " -> " + hero.getMaxHp()); println("Max MP increased by " + mana + " -> " + hero.getMaxMana()); println("HP and MP fully restored!"); }
}