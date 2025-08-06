package chess;

public enum Colour{
    WHITE("White",-1),
    BLACK("Black",1);

    private final String name;
    private final int value; 

    Colour(String name,int value){
        this.name=name;
        this.value=value;
    }

    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }
    public Colour opposite(){
        return this.value==Colour.BLACK.value?Colour.WHITE:Colour.BLACK;
    }
}