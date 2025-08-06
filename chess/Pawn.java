package chess;

import java.util.*;

public class Pawn extends Piece{
    boolean moved;
    public boolean enpassent;
    public Pawn(Colour clr) {
        super();
        this.clr=clr;
        moved = false;
        piece = PieceType.PAWN;
        enpassent=false;
        directions.add(DIRECTION.VERTICAL);
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);
        Piece.setImageView(this);
    }
    public Pawn(Position p,Colour clr){
        super();
        this.clr=clr;
        directions.add(DIRECTION.VERTICAL);
        directions.add(DIRECTION.DIAGLEFT);
        directions.add(DIRECTION.DIAGRIGHT);
        this.position=p;
        moved = false;
        piece = PieceType.PAWN;
        enpassent=false;
        Piece.setImageView(this);
    }

    @Override
    public void move(Position pos) {
        // Pawn movement logic here
        if(Math.abs(this.position.y-pos.y)==2){
            enpassent=true;
        }
        Piece.board.posMap.remove(this.position);
        Piece p = Piece.board.posMap.getOrDefault(pos,null);
        if(p!=null){
            Piece.board.posMap.remove(pos);
        } else if(this.position.x!=pos.x){
            Piece.board.posMap.remove(new Position(pos.x,this.position.y));
        }
        if(pos.y!=0 && pos.y!=7){
            Piece.board.posMap.put(pos, this);
        }
        moved=true;
        this.position=pos;
    }
    
    @Override
    public List<Position> canMove(Position position) {
        List<Position> list = new ArrayList<>();
        Piece temp;
        //check diagonals
        //left
        boolean enpass=false;
        Piece enpassantLeftPiece = Piece.board.posMap.getOrDefault(new Position(this.position.x-1, this.position.y),null);
        if((this.clr==Colour.WHITE && this.position.y==3) || (this.clr==Colour.BLACK && this.position.y==4)){
            enpass=true;
        }
        if(directions.contains(DIRECTION.DIAGLEFT)){
            Position pos1 = new Position(this.position.x-1, this.position.y+this.clr.getValue());
            temp = Piece.board.posMap.getOrDefault(pos1,null);
            if((temp!=null && temp.clr!=this.clr && pos1.boundCheck())){
                list.add(pos1);
            } else if(enpass && enpassantLeftPiece!=null && enpassantLeftPiece.clr!=this.clr && enpassantLeftPiece.piece==PieceType.PAWN){
                Pawn p = (Pawn) enpassantLeftPiece;
                if(p.enpassent){
                    list.add(pos1);
                }
            }
        }
        //right
        if(directions.contains(DIRECTION.DIAGRIGHT)){
            Piece enpassRightPiece = Piece.board.posMap.getOrDefault(new Position(this.position.x+1, this.position.y),null);
            Position pos2 = new Position(this.position.x+1, this.position.y+this.clr.getValue());
            temp = Piece.board.posMap.getOrDefault(pos2,null);
            if(temp!=null && temp.clr!=this.clr && pos2.boundCheck()){
                list.add(pos2);
            } else if(enpass && enpassRightPiece!=null && enpassRightPiece.clr!=this.clr && enpassRightPiece.piece==PieceType.PAWN){
                Pawn p = (Pawn) enpassRightPiece;
                if(p.enpassent){
                    list.add(pos2);
                }
            }
        }
        
        //check front
        if(directions.contains(DIRECTION.VERTICAL)){
            Position pos3 = new Position(this.position.x, this.position.y+this.clr.getValue());
            temp = Piece.board.posMap.getOrDefault(pos3,null);
            if(temp==null && pos3.boundCheck()){
                list.add(pos3);
            }
            //if not moved yet;
            if(!moved){
                Position pos4 = new Position(this.position.x, this.position.y+2*this.clr.getValue());
                temp = Piece.board.posMap.getOrDefault(pos4,null);
                if(temp==null){
                    list.add(pos4);
                }
            }
        }

        return list;
    }

    @Override
    public List<Position> canKill(Position position){
        List<Position> list = new ArrayList<>();
        //check diagonals

        //left
        Position pos1;
        if(position==null){
            pos1 = new Position(this.position.x-1, this.position.y+this.clr.getValue());
        } else {
            pos1 = new Position(position.x-1, position.y+this.clr.getValue());
        }
        if(pos1.boundCheck()){
            list.add(pos1);
        }
        
        //right
        Position pos2;
        if(position==null){
            pos2 = new Position(this.position.x+1, this.position.y+this.clr.getValue());
        } else {
            pos2 = new Position(position.x+1, position.y+this.clr.getValue());
        }
        if(pos2.boundCheck()){
            list.add(pos2);
        }
        return list;
    }
}

