public class Coord {
    int x;
    int y;

    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getEuclid(Coord c){
        return (int)Math.sqrt(Math.pow(this.getX()-c.getX(),2) +Math.pow(this.getY()-c.getY(),2));
    }
}