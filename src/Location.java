import java.util.ArrayList;

public class Location {
    public String name;
    public int distance;
    public Coord coord;
    public ArrayList<Character> lives = new ArrayList<Character>();
    public ArrayList<Location> locations = new ArrayList<>();

    public Location(String name, Coord c, ArrayList<Character> lives) {
        this.lives = lives;
        this.name = name;
        coord = c;
    }

    public boolean isConnected(Location l) {
        if (!locations.isEmpty()) {
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).getName().equals(l.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void canGo(Location l) {
        locations.add(l);
        l.locations.add(this);
    }

    public String getName() {
        return name;
    }

    public int getEuclidDistance(Location l) {
        if (l.isConnected(this)) {
            distance = l.coord.getEuclid(this.coord);
            return distance;
        } else {
            return 0;
        }
    }

    public int getEuclidDistance(String s) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).isConnected(this)) {
                distance = locations.get(i).coord.getEuclid(this.coord);
                return locations.get(i).coord.getEuclid(this.coord);
            }
        }
        return 0;
    }

    public int getDistance(Location l) {
        return distance;
    }

    public int getDistance(String s) {
        for (int i = 0; i < locations.size(); i++) {
            if (locations.get(i).getName().toLowerCase().equals(s.toLowerCase())) {
                return this.getEuclidDistance(s);
            }
        }
        return 0;
    }

    public void reducDistance(int someReduc) {
        distance = distance - someReduc;
    }

    public Coord getCoord() {
        return coord;
    }

    public ArrayList<Character> getLives() {
        return lives;
    }

    public Character getLife(int i) {
        return lives.get(i);
    }

    public int findLife(Character c) {
        for (int i = 0; i < lives.size(); i++) {
            if (lives.get(i).getName().equals(c.getName())) {
                return i;
            }
        }
        return -1;
    }

    public void move(Character c, Location to) {
        this.getLives().remove(findLife(c));
        to.getLives().add(c);
    }

    public void addLife(Character c) {
        lives.add(c);
    }

}