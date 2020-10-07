public class Character {
    String name;
    int strength, money;
    int health, maxHealth;
    boolean enemy;
    Location mainCharLoc;
    String design;

    public Character() {
    }

    public Character(String name, int strength, int money, Location l, int health, String design) {
        this.name = name;
        this.strength = strength;
        this.money = money;
        maxHealth = health;
        this.health = health;
        mainCharLoc = l;
        this.design = design;
    }

    public Character(String name, int strength, int health, String design) {
        this.name = name;
        this.strength = strength;
        maxHealth = health;
        this.health = health;
        this.design = design;
    }

    public int getMoney() {
        return money;
    }

    public int getStrength() {
        return strength;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }

    public int attack() {
        return (int) (Math.random() * strength);
    }

    public String getDesign() {
        return design;
    }

    public void damageTaken(int damage) {
        health = health - damage;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void reset() {
        health = maxHealth;
    }

    public void setLocation(Location l) {
        mainCharLoc = l;
    }

    public Location getLocation() {
        return mainCharLoc;
    }

}