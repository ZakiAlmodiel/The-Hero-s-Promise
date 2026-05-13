package Enemy;
import Heroes.Hero;

public class Enemy {
    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyDamage;

    public Enemy(String name, int hp, int attack) {
        this.enemyName = name;
        this.enemyHp = hp;
        this.enemyDamage = attack;
        this.enemyMana = 0;
    }
    
    public String getEnemyName() {
        return enemyName;
    }
    
    public int getEnemyHp() {
        return enemyHp;
    }
}