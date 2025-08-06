package chess;

import java.util.List;

public class Queen extends Piece{

    public Queen(Colour clr) {
        this.clr=clr;
        piece = PieceType.QUEEN;
        directions.add(DIRECTION.HORIZONTAL);
        directions.add(DIRECTION.VERTICAL);
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);    
        Piece.setImageView(this);
    }
    public Queen(Position p,Colour clr){
        this.position=p;
        this.clr=clr;
        piece = PieceType.QUEEN;    
        directions.add(DIRECTION.HORIZONTAL);
        directions.add(DIRECTION.VERTICAL);
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);
        Piece.setImageView(this);
    }
    @Override
    public void move(Position pos) {
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
        if(directions.contains(DIRECTION.HORIZONTAL)){
            moves.addAll(Interact.allRight(this,false));
            moves.addAll(Interact.allLeft(this,false));
        }
        if(directions.contains(DIRECTION.VERTICAL)){
            moves.addAll(Interact.allInFront(this,false));
            moves.addAll(Interact.allInBack(this,false));
        }
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
        moves.addAll(Interact.allRight(this,true));
        moves.addAll(Interact.allLeft(this,true));
        moves.addAll(Interact.allInFront(this,true));
        moves.addAll(Interact.allInBack(this,true));
        moves.addAll(Interact.allDiag(this, 1, 1,true));
        moves.addAll(Interact.allDiag(this, -1, -1,true));
        moves.addAll(Interact.allDiag(this, -1, 1,true));
        moves.addAll(Interact.allDiag(this, 1, -1,true));
        return moves;
    }
}
