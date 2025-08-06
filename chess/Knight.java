package chess;

import java.util.*;

public class Knight extends Piece{

    private final Position[] jump = {new Position(2,1),new Position(1,2),new Position(-1,2),new Position(-2,1)
                                    ,new Position(-2,-1),new Position(-1,-2),new Position(1,-2),new Position(2,-1)
                                };
    public Knight(Colour clr) {
        this.clr=clr;
        piece = PieceType.KNIGHT;
        directions.add(DIRECTION.NONE);
        Piece.setImageView(this);
    }

    public Knight(Position p,Colour clr){
        this.position=p;
        this.clr=clr;
        piece = PieceType.KNIGHT;
        directions.add(DIRECTION.NONE);
        Piece.setImageView(this);
    }

    @Override
    public void move( Position pos) {
        // Knight movement logic here
        Piece.board.posMap.remove(this.position);
        Piece p = Piece.board.posMap.getOrDefault(pos,null);
        if(p!=null){
            Piece.board.posMap.remove(pos);
        }
        Piece.board.posMap.put(pos, this);
        this.position=pos;
    }

    @Override
    public List<Position> canMove(Position position) {
        List<Position> list=new ArrayList<>();
        if(directions.contains(DIRECTION.NONE)){//NONE means all directions
            for (int i=0;i<8;i++) {
                Position checkPosition;
                if(position==null){
                    checkPosition = this.position.addCheck(jump[i]);
                } else {
                    checkPosition = position.addCheck(jump[i]);
                }
                if(!checkPosition.boundCheck())continue;
                Piece temp = Piece.board.posMap.getOrDefault(checkPosition, null);
                if((temp==null||temp.clr!=this.clr) ){
                    list.add(checkPosition);
                    
                }
            }
        }
        return list;
    }
    @Override
    public List<Position> canKill(Position position){
        List<Position> list=new ArrayList<>();
        for (int i=0;i<8;i++) {
            Position checkPosition;
            if(position==null){
                checkPosition = this.position.addCheck(jump[i]);
            } else {
                checkPosition = position.addCheck(jump[i]);
            }
            if(!checkPosition.boundCheck())continue;
            list.add(checkPosition);
        }
        return list;
    }
}
