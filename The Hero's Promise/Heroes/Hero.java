package Heroes;
import Enemy.Enemy;
import Story.Dialogue;

public abstract class Hero extends Dialogue {
    private String heroName;
    private int heroHp = 100;
    private int heroMana = 100;
    
    public Hero(String name) {
        this.heroName = name;
    }
    
    public String getHeroName() {
        return heroName;
    }
    
    public int getHeroHp() {
        return heroHp;
    }
}
