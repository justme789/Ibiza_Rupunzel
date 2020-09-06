import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Location {
    public String name;
    public int distance;
    public Coord coord;
    public ArrayList<Character> lives = new ArrayList<Character>();
    public ArrayList<Location> locations = new ArrayList<>();

    public Location(String name, Coord c, ArrayList<Character> lives){
        this.lives = lives;
        this.name = name;
        coord = c;
    }

    public boolean isConnected(Location l){
        if(!locations.isEmpty()){
        for(int i = 0; i<locations.size(); i++){
            if(locations.get(i).getName().equals(l.getName())){
                return true;
            }
        }
    }
    return false;
    }

    public void canGo(Location l){
        locations.add(l);
        l.locations.add(this);
    }

    public String getName(){
        return name;
    } 

    public int getEuclidDistance(Location l){
        System.out.println(distance);
       // System.out.println(x);
        if(l.isConnected(this)){
            distance = l.coord.getEuclid(this.coord);
            System.out.println(distance);
            return distance;
        }
        else{
            return 0;
        }
    }
    public int getEuclidDistance(String s){
        
        for(int i = 0; i<locations.size(); i++){
            if(locations.get(i).getName().equals(s) && locations.get(i).isConnected(this)){
                distance = locations.get(i).coord.getEuclid(this.coord);
                return locations.get(i).coord.getEuclid(this.coord);               
            }
        }  
        return 0;
     }
    public int getDistance(Location l){
        return distance;
    }

    public int getDistance(String s){
        for(int i = 0; i<locations.size(); i++){
            //System.out.println(locations.get(i).getName()+"   "+ s);
            if(locations.get(i).getName().equals(s)){
               // System.out.println();
                return this.getEuclidDistance(s);                
            }
        }
        return 0;
    }

    public void reducDistance(int someReduc){
        //System.out.println(distance);
        distance = distance - someReduc;
    }

    public Coord getCoord(){
        return coord;
    }

    public ArrayList<Character> getLives(){
        return lives;
    }

    public Character getLife(int i){
        return lives.get(i);
    }

    public int findLife(Character c){
        for(int i = 0; i<lives.size(); i++){
            if(lives.get(i).getName().equals(c.getName())){
                return i;
            }
        }
        return -1;
    }

    public void move(Character c, Location to){
        this.getLives().remove(findLife(c));
        to.getLives().add(c);
    }

    public void addLife(Character c){
        lives.add(c);
    }

    public static void main(String[] args) {
        Character mouse = new Character("Mouse", 5, 20);
        Character wolf = new Character("Wolf", 10, 40);
        Character bigHonkers = new Character("Big Honkers", 25, 100);
        Character fmouse = new Character("Mouse", 5, 20);
        Character fwolf = new Character("Wolf", 10, 40);
        Character fbigHonkers = new Character("Big Honkers", 25, 100);
        ArrayList<Character> lLives = new ArrayList<Character>(Arrays.asList(
            mouse, wolf, bigHonkers
        ));
        ArrayList<Character> fLives = new ArrayList<Character>(Arrays.asList(
            fmouse, fwolf, fbigHonkers
        ));
        Location l = new Location("l", new Coord(10,10), lLives);
        Location f = new Location("f", new Coord(10,10), fLives);
        l.move(mouse, f);

        Location UK = new Location("UK", new Coord(0,0), lLives);
        Location France = new Location("France", new Coord(30, -10), lLives);
        Location Spain = new Location("Spain", new Coord(50, -20), fLives);
        Location Italy = new Location("Italy", new Coord(80, -43), fLives);

        UK.canGo(France);
        France.canGo(Spain);
        Spain.canGo(Italy);;
        System.out.println(UK.isConnected(France));
        System.out.println(UK.getDistance(France.getName()));
        System.out.println(UK.getDistance(France));
        System.out.println(France.getDistance(Spain));
        System.out.println(France.getDistance(UK));
        System.out.println(Spain.getDistance(Italy));
        System.out.println(UK.getDistance(UK));

    }

}