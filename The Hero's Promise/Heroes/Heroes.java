package Heroes;
 
public class Hero {
    private String name;
    private int health;
    private int attack;
 
    public Hero(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
    }
 
    public void showStats() {
        System.out.println("Hero Name: " + name);
        System.out.println("Health: " + health);
        System.out.println("Attack: " + attack);
    }
 
    public void attack() {
        System.out.println(name + " attacks with " + attack + " power!");
    }
}
