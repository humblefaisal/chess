package chess;

import java.util.List;

public class Rook extends Piece{
    boolean castle=true;
    public Rook(Colour clr) {
        this.clr=clr;
        piece = PieceType.ROOK;
        directions.add(DIRECTION.HORIZONTAL);
        directions.add(DIRECTION.VERTICAL);
        Piece.setImageView(this);
    }

    public Rook(Position p,Colour clr){
        this.position=p;
        this.clr=clr;
        piece = PieceType.ROOK;
        directions.add(DIRECTION.HORIZONTAL);
        directions.add(DIRECTION.VERTICAL);
        Piece.setImageView(this);
    }

    @Override
    public void move( Position pos) {
        // Rook movement logic here
        Piece.board.posMap.remove(this.position);
        Piece p = Piece.board.posMap.getOrDefault(pos,null);
        if(p!=null){
            Piece.board.posMap.remove(pos);
        }
        Piece.board.posMap.put(pos, this);
        this.position=pos;
        castle=false;
    }

    @Override
    public List<Position> canMove(Position pos) {
        List<Position> moves = new java.util.ArrayList<>();
        if(directions.contains(DIRECTION.HORIZONTAL)){
            moves.addAll(Interact.allRight(this,false));
            moves.addAll(Interact.allLeft(this,false));
        }
        if(directions.contains(DIRECTION.VERTICAL)){
            moves.addAll(Interact.allInFront(this,false));
            moves.addAll(Interact.allInBack(this,false));
        }
        return moves;
    }
    @Override
    public List<Position> canKill(Position pos){
        List<Position> moves = new java.util.ArrayList<>();
        moves.addAll(Interact.allRight(this,true));
        moves.addAll(Interact.allLeft(this,true));
        moves.addAll(Interact.allInFront(this,true));
        moves.addAll(Interact.allInBack(this,true));
        return moves;
    }
}