package Main;

import Heroes.Hero;
import Heroes.Kyle;
import Heroes.Zaki;
import Heroes.Kurt;
import Heroes.Joshua;
import Heroes.Adrian;
import Enemy.Enemy;
import Levels.Finalboss;
import Levels.Level1;
import Levels.Level2;
import Story.Details;
import Story.Story;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.util.List;
import java.util.ArrayList;

public class Main extends JFrame {
    // === Game State ===
    private enum GameState {
        TITLE, CHARACTER_SELECT, CHARACTER_CHOSEN, BATTLE, GAME_OVER
    }
    private GameState currentState = GameState.TITLE;

    // === Game Data ===
    private int heroOption = 0;
    private Hero hero = null;
    private boolean backstoryRead = false;
    private int currentEnemyIndex = 0;
    private static final String[] MONSTER_NAMES = {"Shadowblade", "Frostlord", "Demonking"};
    private static final int[] MONSTER_HP = {48, 56, 64};
    private static final int[] MONSTER_DMG = {10, 12, 14};
    private int currentLevel = 1;
    private boolean level1Complete = false;
    private boolean level2Complete = false;
    private boolean inBattle = false;

    // === UI Components ===
    private JTextArea terminalText;
    private JPanel buttonPanel;
    private JButton btnBeginJourney;
    private JButton btnExit;
    private List<JButton> characterButtons;
    private JButton btnBackstory;
    private JButton btnProceed;
    private List<JButton> skillButtons;
    private JButton btnHealHP;
    private JButton btnHealMP;
    private JButton btnGameOverExit;
    private JButton btnGameOverRestart;
    private JLabel titleLabel;
    private JTextArea descriptionArea;
    private JPanel enemyStatusPanel;
    private JLabel enemyNameLabel;
    private JProgressBar enemyHpBar;
    private JLabel enemyHpText;
    private JPanel heroStatusPanel;
    private JLabel heroNameLabel;
    private JLabel heroHpLabel;
    private JLabel heroMpLabel;
    private JProgressBar heroHpBar;
    private JProgressBar heroMpBar;

    private StringBuilder terminalBuffer = new StringBuilder();

    public Main() {
        setupFrame();
        setupUI();
        updateState(GameState.TITLE);
        TerminalMusic.playMusic();
    }

    private void setupFrame() {
        setTitle("The Hero's Promise");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(10, 5, 5));

        enemyStatusPanel = createEnemyStatusPanel();
        enemyStatusPanel.setVisible(false);
        mainPanel.add(enemyStatusPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(10, 5, 5));
        centerPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel titleDescPanel = new JPanel();
        titleDescPanel.setLayout(new BoxLayout(titleDescPanel, BoxLayout.Y_AXIS));
        titleDescPanel.setBackground(new Color(10, 5, 5));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
        titleLabel.setForeground(new Color(220, 50, 50));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(20, 0, 20, 0));

        descriptionArea = new JTextArea();
        descriptionArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        descriptionArea.setForeground(new Color(180, 160, 140));
        descriptionArea.setBackground(new Color(10, 5, 5));
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionArea.setBorder(new EmptyBorder(0, 20, 30, 20));

        terminalText = new JTextArea();
        terminalText.setFont(new Font("Monospaced", Font.PLAIN, 13));
        terminalText.setForeground(new Color(140, 200, 100));
        terminalText.setBackground(new Color(8, 4, 4));
        terminalText.setEditable(false);
        terminalText.setLineWrap(true);
        terminalText.setWrapStyleWord(true);
        terminalText.setBorder(BorderFactory.createLineBorder(new Color(80, 20, 20), 2));
        terminalText.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane terminalScroll = new JScrollPane(terminalText);
        terminalScroll.setPreferredSize(new Dimension(100, 180));
        terminalScroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(60, 15, 15), 1),
            "Terminal",
            0, 0,
            new Font("SansSerif", Font.BOLD, 11),
            new Color(150, 40, 40)
        ));

        titleDescPanel.add(titleLabel);
        titleDescPanel.add(descriptionArea);
        centerPanel.add(titleDescPanel, BorderLayout.NORTH);
        centerPanel.add(terminalScroll, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 5));
        bottomPanel.setBackground(new Color(10, 5, 5));
        heroStatusPanel = createHeroStatusPanel();
        bottomPanel.add(heroStatusPanel, BorderLayout.NORTH);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(10, 5, 5));
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        createButtons();
    }

    private JPanel createEnemyStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(15, 8, 8));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 20, 20), 2),
            new EmptyBorder(8, 15, 8, 15)
        ));

        enemyNameLabel = new JLabel("Enemy");
        enemyNameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        enemyNameLabel.setForeground(new Color(200, 80, 80));
        enemyNameLabel.setPreferredSize(new Dimension(150, 30));

        enemyHpBar = new JProgressBar(0, 100);
        enemyHpBar.setValue(100);
        enemyHpBar.setPreferredSize(new Dimension(300, 22));
        enemyHpBar.setBackground(new Color(60, 10, 10));
        enemyHpBar.setForeground(new Color(220, 60, 60));
        enemyHpBar.setStringPainted(true);
        enemyHpBar.setFont(new Font("SansSerif", Font.BOLD, 11));

        enemyHpText = new JLabel("100/100");
        enemyHpText.setFont(new Font("Monospaced", Font.BOLD, 13));
        enemyHpText.setForeground(new Color(220, 100, 100));
        enemyHpText.setPreferredSize(new Dimension(100, 25));

        JLabel enemyIcon = new JLabel("⚔");
        enemyIcon.setFont(new Font("Serif", Font.PLAIN, 24));
        enemyIcon.setForeground(new Color(180, 40, 40));
        enemyIcon.setBorder(new EmptyBorder(0, 10, 0, 5));

        panel.add(enemyIcon);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(enemyNameLabel);
        panel.add(Box.createRigidArea(new Dimension(15, 0)));
        panel.add(enemyHpBar);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(enemyHpText);

        return panel;
    }

    private JPanel createHeroStatusPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(new Color(15, 8, 8));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 20, 20), 2),
            new EmptyBorder(8, 15, 8, 15)
        ));

        heroNameLabel = new JLabel("Hero");
        heroNameLabel.setFont(new Font("Serif", Font.BOLD, 16));
        heroNameLabel.setForeground(new Color(220, 100, 100));
        heroNameLabel.setPreferredSize(new Dimension(150, 30));

        heroHpBar = new JProgressBar(0, 100);
        heroHpBar.setValue(100);
        heroHpBar.setPreferredSize(new Dimension(200, 20));
        heroHpBar.setBackground(new Color(60, 10, 10));
        heroHpBar.setForeground(new Color(220, 50, 50));
        heroHpBar.setStringPainted(true);
        heroHpBar.setFont(new Font("SansSerif", Font.BOLD, 10));

        heroHpLabel = new JLabel("HP: 100/100");
        heroHpLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
        heroHpLabel.setForeground(new Color(220, 80, 80));
        heroHpLabel.setPreferredSize(new Dimension(110, 22));

        heroMpBar = new JProgressBar(0, 100);
        heroMpBar.setValue(100);
        heroMpBar.setPreferredSize(new Dimension(200, 20));
        heroMpBar.setBackground(new Color(10, 10, 60));
        heroMpBar.setForeground(new Color(80, 80, 220));
        heroMpBar.setStringPainted(true);
        heroMpBar.setFont(new Font("SansSerif", Font.BOLD, 10));

        heroMpLabel = new JLabel("MP: 100/100");
        heroMpLabel.setFont(new Font("Monospaced", Font.BOLD, 11));
        heroMpLabel.setForeground(new Color(100, 100, 220));
        heroMpLabel.setPreferredSize(new Dimension(110, 22));

        panel.add(heroNameLabel);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(heroHpBar);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(heroHpLabel);
        panel.add(Box.createRigidArea(new Dimension(20, 0)));
        panel.add(heroMpBar);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        panel.add(heroMpLabel);

        return panel;
    }

    private void createButtons() {
        btnBeginJourney = createStyledButton("Begin Your Journey", 18);
        btnExit = createStyledButton("Exit", 18);

        String[] charNames = {"Kyle", "Zaki", "Kurt", "Joshua", "Adrian"};
        characterButtons = new ArrayList<>();
        for (String name : charNames) {
            JButton btn = createStyledButton(name, 16);
            characterButtons.add(btn);
        }

        btnBackstory = createStyledButton("Read Back Story", 18);
        btnProceed = createStyledButton("Proceed to Battle", 18);

        skillButtons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            JButton btn = createStyledButton("", 15);
            skillButtons.add(btn);
        }

        btnHealHP = createStyledButton("Heal HP (+20)", 15);
        btnHealMP = createStyledButton("Heal MP (+20)", 15);

        btnGameOverExit = createStyledButton("Exit Game", 18);
        btnGameOverRestart = createStyledButton("Start Again", 18);

        btnBeginJourney.addActionListener(e -> {
            appendTerminal("--- Journey begins ---\n");
            printStoryBeforeFight();
            updateState(GameState.CHARACTER_SELECT);
        });

        btnExit.addActionListener(e -> {
            appendTerminal("Exiting game. Farewell.\n");
            Timer timer = new Timer(1000, ev -> System.exit(0));
            timer.setRepeats(false);
            timer.start();
        });

        for (int i = 0; i < characterButtons.size(); i++) {
            int idx = i;
            characterButtons.get(i).addActionListener(ev -> selectCharacter(idx + 1));
        }

        btnBackstory.addActionListener(e -> {
            if (hero != null) {
                hero.displayBackStory();
                backstoryRead = true;
            }
        });

        btnProceed.addActionListener(e -> startBattle());

        btnHealHP.addActionListener(e -> performHealHP());
        btnHealMP.addActionListener(e -> performHealMP());

        btnGameOverExit.addActionListener(ev -> System.exit(0));
        btnGameOverRestart.addActionListener(ev -> restartGame());
    }

    private JButton createStyledButton(String text, int fontSize) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Serif", Font.BOLD, fontSize));
        btn.setForeground(new Color(220, 180, 160));
        btn.setBackground(new Color(100, 20, 20));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 80, 60), 2),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(150, 30, 30));
                btn.setForeground(new Color(255, 220, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(100, 20, 20));
                btn.setForeground(new Color(220, 180, 160));
            }
        });

        return btn;
    }

    private void updateState(GameState newState) {
        currentState = newState;
        buttonPanel.removeAll();

        switch (newState) {
            case TITLE:
                titleLabel.setText("THE HERO'S PROMISE");
                descriptionArea.setText(
                    "In the land of Varethia, the winds carried whispers of war and ruin.\n" +
                    "Ancient shadows stirred beneath broken castles, hungering once again for power.\n" +
                    "From across kingdoms and fallen cities, five heroes answered the same call...\n\n" +
                    "A silent plea echoing from the heart of the world itself."
                );
                terminalText.setText("");
                terminalBuffer.setLength(0);
                inBattle = false;
                buttonPanel.add(btnBeginJourney);
                buttonPanel.add(btnExit);
                enemyStatusPanel.setVisible(false);
                heroStatusPanel.setVisible(false);
                break;

            case CHARACTER_SELECT:
                titleLabel.setText("Pick Your Character");
                descriptionArea.setText("Choose wisely - each hero has unique skills and a destiny to fulfill.");
                for (JButton btn : characterButtons) {
                    buttonPanel.add(btn);
                }
                enemyStatusPanel.setVisible(false);
                heroStatusPanel.setVisible(false);
                break;

            case CHARACTER_CHOSEN:
                titleLabel.setText("Hero Selected: " + hero.getHeroName());
                descriptionArea.setText(
                    hero.getHeroName() + " stands ready.\n" +
                    "A dark journey awaits, filled with peril and glory."
                );
                buttonPanel.add(btnBackstory);
                buttonPanel.add(btnProceed);
                heroStatusPanel.setVisible(true);
                updateHeroStatus();
                enemyStatusPanel.setVisible(false);
                break;

            case BATTLE:
                titleLabel.setText("⚔ BATTLE ⚔");
                descriptionArea.setText(
                    "Enemy approaches! Choose your actions wisely.\n" +
                    "Use skills to defeat enemies, but watch your HP and MP!"
                );
                for (JButton btn : skillButtons) {
                    buttonPanel.add(btn);
                }
                buttonPanel.add(btnHealHP);
                buttonPanel.add(btnHealMP);
                enemyStatusPanel.setVisible(true);
                heroStatusPanel.setVisible(true);
                break;

            case GAME_OVER:
                titleLabel.setText("💀 GAME OVER 💀");
                descriptionArea.setText("Valiant effort! But the dark claims another victim.\nWill you rise again?");
                buttonPanel.add(btnGameOverExit);
                buttonPanel.add(btnGameOverRestart);
                enemyStatusPanel.setVisible(false);
                heroStatusPanel.setVisible(false);
                inBattle = false;
                break;
        }

        revalidate();
        repaint();
    }

    private void selectCharacter(int option) {
        heroOption = option;
        switch (option) {
            case 1: hero = new Kyle("Kyle"); break;
            case 2: hero = new Zaki("Zaki"); break;
            case 3: hero = new Kurt("Kurt"); break;
            case 4: hero = new Joshua("Joshua"); break;
            case 5: hero = new Adrian("Adrian"); break;
        }

        if (hero != null) {
            appendTerminal("=== " + hero.getHeroName() + " selected! ===\n");
            hero.preBattleDialogue();
            appendTerminal("---\nClass: " + hero.getHeroName() + "\n");
            backstoryRead = false;
            currentEnemyIndex = 0;
            level1Complete = false;
            level2Complete = false;
            updateState(GameState.CHARACTER_CHOSEN);
        }
    }

    private void printStoryBeforeFight() {
        appendTerminal("\n--------------------------------------------------\n");
        appendTerminal("--------------------------------------------------\n");
        appendTerminal("In the land of Varethia, the winds carried whispers of war and ruin.\n");
        appendTerminal("Ancient shadows stirred beneath broken castles, hungering once again for power.\n");
        appendTerminal("From across kingdoms and fallen cities, five heroes answered the same call...\n");
        appendTerminal("a silent plea echoing from the heart of the world itself.\n");
        appendTerminal("\nAnd so began their journey into the mist...\n");
        appendTerminal("The first step... survival.\n");
        appendTerminal("--------------------------------------------------\n\n");
    }

    private void startBattle() {
        inBattle = true;
        currentEnemyIndex = 0;
        currentLevel = 1;
        level1Complete = false;
        level2Complete = false;
        
        appendTerminal("\n----------------- BATTLE STARTED -------------------\n");
        printStoryBeforeLevel1();
        setupBattleLevel1();
    }

    private void printStoryBeforeLevel1() {
        appendTerminal("\n--------------------------------------------------\n");
        appendTerminal("--------------------------------------------------\n");
        appendTerminal("The trail led to the edge of Elden Forest, where twilight never ends.\n");
        appendTerminal("Beneath the canopy of dying stars, beasts tainted by dark magic roamed free.\n");
        appendTerminal("The air grew heavy with decay... something wicked was watching.\n");
        appendTerminal("The hero drew their weapon, steadying their breath as the first enemy appeared...\n");
        appendTerminal("--------------------------------------------------\n\n");
    }

    private void setupBattleLevel1() {
        Enemy enemy = new Enemy(
            MONSTER_NAMES[currentEnemyIndex],
            MONSTER_HP[currentEnemyIndex],
            MONSTER_DMG[currentEnemyIndex]
        );

        appendTerminal(">> A " + enemy.getEnemyName() + " appears!\n");
        updateEnemyStatus(enemy);
        updateHeroStatus();
        setupSkillButtons(hero, enemy);
        updateState(GameState.BATTLE);
    }

    private void setupSkillButtons(Hero hero, Enemy enemy) {
        String[] skillNames = {hero.getSkillOneName(), hero.getSkillTwoName(), hero.getSkillThreeName()};
        int[] skillMultipliers = {hero.getSkillOneMultiplier(), hero.getSkillTwoMultiplier(), hero.getSkillThreeMultiplier()};
        int[] skillCosts = {hero.getSkillOneManaCost(), hero.getSkillTwoManaCost(), hero.getSkillThreeManaCost()};

        for (int i = 0; i < 3; i++) {
            int skillIdx = i;
            final Enemy currentEnemy = enemy;
            skillButtons.get(i).setText(
                (i + 1) + ". " + skillNames[i] + " - Dmg:" + skillMultipliers[i] + " (MP:" + skillCosts[i] + ")"
            );
            for (ActionListener al : skillButtons.get(i).getActionListeners()) {
                skillButtons.get(i).removeActionListener(al);
            }
            skillButtons.get(i).addActionListener(ev -> {
                performSkill(skillIdx, hero, currentEnemy);
            });
        }
    }

    private void performSkill(int skillIndex, Hero hero, Enemy enemy) {
        if (!inBattle) return;

        final Enemy currentEnemy = enemy;
        final Hero currentHero = hero;

        switch (skillIndex) {
            case 0: hero.skillOne(enemy); break;
            case 1: hero.skillTwo(enemy); break;
            case 2: hero.skillThree(enemy); break;
        }

        updateEnemyStatus(enemy);
        updateHeroStatus();

        if (enemy.getEnemyHp() <= 0) {
            appendTerminal(">>> " + enemy.getEnemyName() + " has been defeated!\n");
            
            currentEnemyIndex++;
            if (currentEnemyIndex >= 3) {
                level1Complete = true;
                appendTerminal("\n----CONGRATULATIONS! YOU HAVE FINISHED LEVEL 1----\n");
                hero.fullRestore();
                updateHeroStatus();
                appendTerminal("\nYou have survived Level 1! Restoring HP and MP...\n");
                appendTerminal("\nYou feel a surge of power as you grow stronger...\n");
                hero.boostHero(25, 20);
                Timer timer = new Timer(3000, ev -> {
                    appendTerminal("\n----------------- LEVEL 2 -------------------\n");
                    printStoryBeforeLevel2();
                    startLevel2();
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                Timer timer = new Timer(2000, ev -> {
                    Enemy nextEnemy = new Enemy(
                        MONSTER_NAMES[currentEnemyIndex],
                        MONSTER_HP[currentEnemyIndex],
                        MONSTER_DMG[currentEnemyIndex]
                    );
                    appendTerminal("\n>> The next enemy approaches: " + nextEnemy.getEnemyName() + "!\n");
                    updateEnemyStatus(nextEnemy);
                    setupSkillButtons(hero, nextEnemy);
                    inBattle = true;
                });
                timer.setRepeats(false);
                timer.start();
            }
        } else {
            enemy.enemyAttack(hero);
            updateHeroStatus();
            inBattle = false;

            Timer timer = new Timer(1500, ev -> {
                updateHeroStatus();
                if (hero.getHeroHp() <= 0) {
                    hero.defeatDialogue();
                    appendTerminal("\n---------You have been defeated!---------\n");
                    appendTerminal("----------------GAME OVER!---------------\n");
                    updateState(GameState.GAME_OVER);
                    TerminalMusic.stopMusic();
                } else {
                    updateEnemyStatus(enemy);
                    inBattle = true;
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void printStoryBeforeLevel2() {
        appendTerminal("\n--------------------------------------------------\n");
        appendTerminal("--------------------------------------------------\n");
        appendTerminal("Past the cursed forest lay the Ruins of Nareth ... a city swallowed by the earth itself.\n");
        appendTerminal("Echoes of lost souls haunted the streets, and broken statues whispered forgotten names.\n");
        appendTerminal("The hero's light flickered, but courage burned stronger still.\n");
        appendTerminal("A new threat awaited... larger, smarter, and hungry for fear.\n");
        appendTerminal("--------------------------------------------------\n\n");
    }

    private void startLevel2() {
        currentLevel = 2;
        currentEnemyIndex = 0;

        Enemy enemy = new Enemy("Elite Shadowblade", 80, 18);
        appendTerminal(">> An Elite " + enemy.getEnemyName() + " stands before you!\n");
        updateEnemyStatus(enemy);
        updateHeroStatus();
        setupSkillButtons(hero, enemy);
        updateState(GameState.BATTLE);
        inBattle = true;
    }

    private void performHealHP() {
        if (hero == null) return;
        hero.healHP();
        updateHeroStatus();
    }

    private void performHealMP() {
        if (hero == null) return;
        hero.healMana();
        updateHeroStatus();
    }

    private void updateEnemyStatus(Enemy enemy) {
        enemyNameLabel.setText(enemy.getEnemyName());
        int hp = enemy.getEnemyHp();
        enemyHpBar.setMaximum(hp + 20);
        enemyHpBar.setValue(Math.max(0, hp));
        enemyHpText.setText(hp + "/" + (hp + 20));
        enemyHpBar.setString(hp + " / " + (hp + 20));
    }

    private void updateHeroStatus() {
        if (hero == null) return;
        heroNameLabel.setText(hero.getHeroName());
        heroHpBar.setMaximum(hero.getMaxHp());
        heroHpBar.setValue(hero.getHeroHp());
        heroHpLabel.setText("HP: " + hero.getHeroHp() + "/" + hero.getMaxHp());
        heroMpBar.setMaximum(hero.getMaxMana());
        heroMpBar.setValue(hero.getHeroMana());
        heroMpLabel.setText("MP: " + hero.getHeroMana() + "/" + hero.getMaxMana());
    }

    private void appendTerminal(String text) {
        terminalBuffer.append(text);
        terminalText.setText(terminalBuffer.toString());
        terminalText.setCaretPosition(terminalText.getDocument().getLength());
    }

    private void restartGame() {
        hero = null;
        heroOption = 0;
        backstoryRead = false;
        currentEnemyIndex = 0;
        currentLevel = 1;
        level1Complete = false;
        level2Complete = false;
        inBattle = false;
        terminalBuffer.setLength(0);
        terminalText.setText("");
        TerminalMusic.playMusic();
        updateState(GameState.TITLE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main game = new Main();
            game.setVisible(true);
        });
    }
}
