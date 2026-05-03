package Main;
import java.util.InputMismatchException;
import java.util.Scanner;

import Heroes.Hero;
import Heroes.Kyle;
import Heroes.Zaki;
import Heroes.Kurt;
import Heroes.Joshua;
import Heroes.Adrian;
import Levels.Finalboss;
import Levels.Level1;
import Levels.Level2;
import Story.Details;
import Story.Story;

class InvalidChoiceException extends Exception {
    public InvalidChoiceException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean exitGame = false;

        while (!exitGame) {
            TerminalMusic.playMusic();

            System.out.println("==================================");
            System.out.println("     THE HERO'S PROMISE     ");
            System.out.println("==================================");
            System.out.println();

            Story.beforeFight();

            int heroOption = 0;

            while (true) {
                try {
                    System.out.println("Pick a Character: ");
                    System.out.println("1 - Kyle ");
                    System.out.println("2 - Zaki");
                    System.out.println("3 - Kurt");
                    System.out.println("4 - Joshua");
                    System.out.println("5 - Adrian");
                    System.out.print("Enter number: ");

                    heroOption = sc.nextInt();

                    if (heroOption < 1 || heroOption > 5) {
                        throw new InvalidChoiceException("Hero does not exist.");
                    }

                    break;

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number only.");
                    sc.nextLine();
                } catch (InvalidChoiceException e) {
                    System.out.println(e.getMessage());
                }
            }

            Hero hero = null;

            switch (heroOption) {
                case 1: hero = new Kyle("Kyle"); break;
                case 2: hero = new Zaki("Zaki"); break;
                case 3: hero = new Kurt("Kurt"); break;
                case 4: hero = new Joshua("Joshua"); break;
                case 5: hero = new Adrian("Adrian"); break;
            }

            if (hero == null) {
                System.out.println("Unexpected error: hero not created.");
                TerminalMusic.stopMusic();
                sc.close();
                return;
            }

            hero.preBattleDialogue();
            Details.displayPickingDetails(hero);

            int backstoryOption = 0;

            do {
                try {
                    System.out.println("\n1 - Read back story");
                    System.out.println("2 - Proceed to battle");
                    System.out.print("Enter number: ");
                    backstoryOption = sc.nextInt();

                    if (backstoryOption != 1 && backstoryOption != 2) {
                        throw new InvalidChoiceException("Invalid option. Please choose 1 or 2.");
                    }

                    if (backstoryOption == 1) {
                        hero.displayBackStory();
                        System.out.print("Proceed to battle? (Type 2): ");
                        backstoryOption = sc.nextInt();

                        if (backstoryOption != 2) {
                            throw new InvalidChoiceException("You must type 2 to proceed.");
                        }
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Numbers only.");
                    sc.nextLine();
                    continue;
                } catch (InvalidChoiceException e) {
                    System.out.println(e.getMessage());
                    continue;
                }

            } while (backstoryOption != 2);

            System.out.println("\n----------------- BATTLE STARTED -------------------");
            Details.displayPickingDetails(hero);
            Story.beforeLevel1();
            boolean level1Result = Level1.battleMechanic1(hero);

            if (!level1Result) {
                int choice = handleGameOver(sc);
                if (choice == 1) {
                    exitGame = true;
                    TerminalMusic.stopMusic();
                    continue;
                } else {
                    TerminalMusic.stopMusic();
                    continue;
                }
            }

            Story.afterLevel1();
            System.out.println("\nYou have survived Level 1! Restoring HP and MP...");
            hero.fullRestore();
            System.out.println("\nYou feel a surge of power as you grow stronger...");
            hero.boostHero(25, 20);

            System.out.println("\n----------------- LEVEL 2 -------------------");
            Details.displayPickingDetails(hero);
            Story.beforeLevel2();
            boolean level2Result = Level2.battleMechanic2(hero);

            if (!level2Result) {
                int choice = handleGameOver(sc);
                if (choice == 1) {
                    exitGame = true;
                    TerminalMusic.stopMusic();
                    continue;
                } else {
                    TerminalMusic.stopMusic();
                    continue;
                }
            }

            Story.afterLevel2();
            System.out.println("\nYou have survived Level 2! Restoring HP and MP...");
            hero.fullRestore();
            System.out.println("\nYou feel an even greater surge of power as you reach your full potential...");
            hero.boostHero(30, 25);

            System.out.println("\n----------------- FINAL BOSS ENCOUNTER -------------------");
            Details.displayPickingDetails(hero);
            Story.beforeBossFight();
            boolean bossResult = Finalboss.battleMechanicFinal(hero);

            if (!bossResult) {
                int choice = handleGameOver(sc);
                if (choice == 1) {
                    exitGame = true;
                    TerminalMusic.stopMusic();
                    continue;
                } else {
                    TerminalMusic.stopMusic();
                    continue;
                }
            }

            Story.afterBossFight();
            TerminalMusic.stopMusic();
            exitGame = true;
        }

        sc.close();
    }

    private static int handleGameOver(Scanner sc) {
        System.out.println("\n==================================");
        System.out.println("           GAME OVER");
        System.out.println("==================================");
        System.out.println("1 - Exit Game");
        System.out.println("2 - Start Over");

        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                if (choice == 1 || choice == 2) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number only.");
                sc.nextLine();
            }
        }
    }
}