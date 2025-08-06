package chess;

import java.util.*;
public class Bishop extends Piece{

    public Bishop(Colour clr) {
        this.clr=clr;
        piece = PieceType.BISHOP;
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);
        Piece.setImageView(this);
        this.imageView.setVisible(true);
    }

    public Bishop(Position p,Colour clr){
        this.position=p;
        this.clr=clr;
        piece = PieceType.BISHOP;
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);
        Piece.setImageView(this);
        this.imageView.setVisible(true);
    }
    @Override
    public void move( Position pos) {
        // Bishop movement logic here\
        Piece.board.posMap.remove(this.position);
        Piece p = Piece.board.posMap.getOrDefault(pos,null);
        if(p!=null){
            Piece.board.posMap.remove(pos);
        }
        Piece.board.posMap.put(pos, this);
        this.position=pos;
    }

    @Override
    public List<Position> canMove(Position pos) {
        List<Position> moves = new java.util.ArrayList<>();
        if(directions.contains(DIRECTION.DIAGLEFT)){
            moves.addAll(Interact.allDiag(this, 1, 1,false));
            moves.addAll(Interact.allDiag(this, -1, -1,false));
        }
        if(directions.contains(DIRECTION.DIAGRIGHT)){
            moves.addAll(Interact.allDiag(this, -1, 1,false));
            moves.addAll(Interact.allDiag(this, 1, -1,false));
        }
        return moves;
    }   
    @Override
    public List<Position> canKill(Position pos){
        List<Position> moves = new java.util.ArrayList<>();
        moves.addAll(Interact.allDiag(this, 1, 1,true));
        moves.addAll(Interact.allDiag(this, -1, -1,true));
        moves.addAll(Interact.allDiag(this, -1, 1,true));
        moves.addAll(Interact.allDiag(this, 1, -1,true));
        return moves;
    }
}
