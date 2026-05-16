package Story;

public class Story {
    
    public static void beforeFight() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("In the land of Varethia, winds carried whispers of war.");
        System.out.println("Five heroes answered the same call...");
        System.out.println("--------------------------------------------------\n");
    }
    
    public static void beforeLevel1() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("The trail led to Elden Forest, where twilight never ends.");
        System.out.println("Beasts tainted by dark magic roamed free.");
        System.out.println("--------------------------------------------------\n");
    }
    
    public static void beforeLevel2() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("Past the cursed forest lay the Ruins of Nareth...");
        System.out.println("A new threat awaited...");
        System.out.println("--------------------------------------------------\n");
    }
    
    public static void beforeBossFight() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("At the Temple of Ash, the Final Warden awaited.");
        System.out.println("Every victory led to this moment.");
        System.out.println("--------------------------------------------------\n");
    }
    
    public static void afterBossFight() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("As the Warden fell, the shadows began to fade.");
        System.out.println("The hero stood weary but unbroken.");
        System.out.println("--------------------------------------------------\n");
    }
}
