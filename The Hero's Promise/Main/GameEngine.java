package Main;
import Heroes.Hero;

public class GameEngine implements Runnable {
    public void start() {
        System.out.println("Game Engine starting...");
    }

    @Override
    public void run() {
        System.out.println("Game Engine running...");
    }
}