package chess;

public class Position{
    public final int x,y;
    public Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    public Position(Position p){
        this.x=p.x;
        this.y=p.y;
    }
    public Position addCheck(Position o){
        return new Position(this.x+o.x,this.y+o.y);
    }

    //I have no idea why i created this useless function: turns out, i need this
    public static boolean boundCheck(Position pos){
        return pos.x>=0 && pos.x<8 && pos.y>=0 && pos.y<8;
    }
    public boolean boundCheck(){
        return this.x>=0 && this.x<8 && this.y>=0 && this.y<8;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }
    @Override
    public int hashCode() {
        return 31 * x + y;
    }
    @Override
    public String toString(){
        return "("+x+","+y+")";
    }
}