package chess;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

interface Move{
    void move(Position pos);
    List<Position> canKill(Position pos);
    List<Position> canMove(Position position);
}

enum DIRECTION{
    //clockwise
    HORIZONTAL("Horizontal",new Position(1,0),new Position(-1,0)),
    DIAGLEFT("DiagonalLeft",new Position(1,1),new Position(-1,-1)),
    VERTICAL("Vertical",new Position(0,1),new Position(0,-1)),
    DIAGRIGHT("DiagonalRight",new Position(1,-1),new Position(-1,1)),
    NONE("",new Position(0,0),new Position(0,0));
    String way;
    Position dp1,dp2;
    DIRECTION(String way,Position dp1,Position dp2){
        this.way=way;
        this.dp1=dp1;
        this.dp2=dp2;
    }
    public String getWay(){
        return this.way;
    }
    public Position getDp1(){
        return this.dp1;
    }
    public Position getDp2(){
        return this.dp2;
    }
    public void setPin(DIRECTION p){
        this.way=p.way;
        this.dp1=p.dp1;
        this.dp2=p.dp2;
    }
}

public abstract class Piece implements Move{
    static{
        whitePawn = new Image(Piece.class.getResource("/Images/Chess_plt60.png").toExternalForm());
        blackPawn = new Image(Piece.class.getResource("/Images/Chess_pdt60.png").toExternalForm());
        whiteKnight = new Image(Piece.class.getResource("/Images/Chess_nlt60.png").toExternalForm());
        blackKnight = new Image(Piece.class.getResource("/Images/Chess_ndt60.png").toExternalForm());
        whiteBishop = new Image(Piece.class.getResource("/Images/Chess_blt60.png").toExternalForm());
        blackBishop = new Image(Piece.class.getResource("/Images/Chess_bdt60.png").toExternalForm());
        whiteRook = new Image(Piece.class.getResource("/Images/Chess_rlt60.png").toExternalForm());
        blackRook = new Image(Piece.class.getResource("/Images/Chess_rdt60.png").toExternalForm());
        whiteQueen = new Image(Piece.class.getResource("/Images/Chess_qlt60.png").toExternalForm());
        blackQueen = new Image(Piece.class.getResource("/Images/Chess_qdt60.png").toExternalForm());
        whiteKing = new Image(Piece.class.getResource("/Images/Chess_klt60.png").toExternalForm());
        blackKing = new Image(Piece.class.getResource("/Images/Chess_kdt60.png").toExternalForm());
    }
    public PieceType piece;
    public static Board board = new Board();
    public Position position;
    public Colour clr;
    public HashSet<DIRECTION> directions;
    public static HashSet<Piece> pinnedPieces = new HashSet<>();
    public ImageView imageView;
    protected Image image;
    public static final Image whitePawn,blackPawn,whiteKnight,blackKnight,whiteBishop,blackBishop,whiteRook,blackRook,whiteQueen,blackQueen,whiteKing,blackKing;
    

    public Piece(){
        directions = new HashSet<>();
    }

    public static void setImageView(Piece p){
        switch(p.piece){
            case PAWN:
                p.image=p.clr==Colour.WHITE?whitePawn:blackPawn;
                break;
            case KNIGHT:
                p.image=p.clr==Colour.WHITE?whiteKnight:blackKnight;
                break;
            case BISHOP:
                p.image=p.clr==Colour.WHITE?whiteBishop:blackBishop;
                break;
            case ROOK:
                p.image=p.clr==Colour.WHITE?whiteRook:blackRook;
                break;
            case QUEEN:
                p.image=p.clr==Colour.WHITE?whiteQueen:blackQueen;
                break;
            case KING:
                p.image=p.clr==Colour.WHITE?whiteKing:blackKing;
                break;
            default:
        }
        ImageView imageView=new ImageView(p.image);
        imageView.setMouseTransparent(true);
        p.imageView = imageView;
    }
}

class NoPiece extends Piece{
    
    public NoPiece() {
        image=null;
        piece=PieceType.NOPIECE;
        // No specific piece type
    }
    public NoPiece(Position pos,Colour clr){
        this.clr=clr;
        this.position=pos;
        image=null;
    }
    @Override
    public void move(Position pos) {
        // No movement: maybe throw exception or simply ignore
    }
    @Override
    public List<Position> canKill(Position pos){
        return new ArrayList<>();
    }
    @Override
    public List<Position> canMove(Position position){
        return new ArrayList<>();
    }
}
