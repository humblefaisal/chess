package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* WHat i need to do with king :
 * 1. Blocked/Pinned pieces can't move in a way they get unpinned...
 *  in other words it can only move along the beam from king to the attacking piece.
 * 2. If the king is in check (Oh yeah check logic is another thing):
 *      --Either Move a Non pinned piece such that it blocks the check
 *      --Or move the king to a block where no piece is attacking.
 * 3. CheckMate logic = Check * No king move * No blocks Available ;
 * 4. Check Logic
 *      --*key points* : 1.For checks, I cant just update a "Stored Varible" when a piece moves wrt the piece.
 *                         Cuz of discovered checks. And for another, What should be this "stored var"?
 *                     : 2.Another option that I have, After every move, i can cast 8 beams along different 
 *                       sides of king to check if theres opp pices attacking:on diagonals-bishop,queen and pawn(right next).
 *                       rooks on files.. and then seprate check for knight.
 *                          
 * Stored Var Options ::  skip
 * 
 */


 // I think im gonna go with 2nd option where i cast 8 beams.. and add another "ENUM" for pinned where one could be 
 // pinned along diagonals or along horizontal or vertical files..
 // Another problem with soln : For the case when two pieces attack, i can create a list(map's better) of attaking pieces 
 // then i can easily check the  no. of pieces attacking
 // another probem!!! It doesnt define where a king may move! so i need to check this thing for all 8 probable positions
 // where king may move!
 // TC = (8*(4)) * 9 (appx) every move :: 8 beams * 4 (appx squares)* 9 positions = 288;
 // sp complexity = 9(Store 9 positions if king can move!);
 
 
 // first option is to go with a danger map :
 // TC = 12 * ((2*8+2*2)+(1*8+8*2+16*2+16*1))/2 = 12*(36)=432;
 // SC = big..

public class King extends Piece{
    boolean castle =true;
    public King(Colour clr) {
        this.clr=clr;
        piece = PieceType.KING;
        Piece.setImageView(this);
    }

    public King(Position p,Colour clr){
        this.position=p;
        this.clr=clr;
        piece = PieceType.KING; 
        Piece.setImageView(this);
    }

    @Override
    public void move(Position pos) {
        Piece.board.posMap.remove(this.position);
        int castleMove = this.position.x-pos.x;
        if(castleMove == 2){
            Rook rook = (Rook)Piece.board.posMap.get(new Position(0,this.position.y));
            Piece.board.posMap.remove(new Position(0,this.position.y));
            rook.position = new Position(2,this.position.y);
            Piece.board.posMap.put(rook.position,rook);
        }
        else if(castleMove == -2){
            Rook rook = (Rook)Piece.board.posMap.get(new Position(7,this.position.y));
            Piece.board.posMap.remove(new Position(7,this.position.y));
            rook.position = new Position(4,this.position.y);
            Piece.board.posMap.put(rook.position,rook);
        }
        Piece.board.posMap.remove(pos);
        Piece.board.posMap.put(pos,this);
        this.position = pos;
        castle = false;
    }

    @Override
    public List<Position> canMove(Position pos) {
        List<Position> moves = new java.util.ArrayList<>();
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(this.position.x + dx[i], this.position.y + dy[i]);
            if (!Position.boundCheck(newPos)) continue;
            Piece occupant = Piece.board.posMap.getOrDefault(newPos, null);
            if (occupant != null && occupant.clr == this.clr) continue;
            if (!Piece.board.isSquareAttacked(newPos, clr.opposite())) {
                moves.add(newPos);
            }
        }
        // Castling logic
        if (castle && !inCheck()) {
            // King-side castling
            if (canCastle(true)) {
                moves.add(new Position(this.position.x - 2, this.position.y));
            }
            // Queen-side castling
            if (canCastle(false)) {
                moves.add(new Position(this.position.x + 2, this.position.y));
            }
        }
        return moves;
    }

    // Helper to check if castling is possible (kingSide: true for king-side, false for queen-side)
    private boolean canCastle(boolean kingSide) {
        int y = this.position.y;
        int x = this.position.x;
        int rookX = kingSide ? 0 : 7;
        Piece rook = Piece.board.posMap.getOrDefault(new Position(rookX, y), null);
        if (!(rook instanceof Rook) || !((Rook)rook).castle) return false;
        int dir = kingSide ? -1 : 1;
        for (int i = 1; i <= (kingSide ? 2 : 3); i++) {
            Position checkPos = new Position(x + dir * i, y);
            if (Piece.board.posMap.getOrDefault(checkPos, null) != null) return false;
            if (i <= 2 && Piece.board.isSquareAttacked(checkPos, this.clr == Colour.WHITE ? Colour.BLACK : Colour.WHITE)) return false;
        }
        return true;
    }
    public boolean isStalemate(){
        if(inCheck()) return false;
        for(Piece p:Piece.board.posMap.values()){
            if(p==null || p==this || p.clr!=this.clr) continue;
            List<Position> moves = p.canMove(null);
            if(!moves.isEmpty()) return false;
        }
        return true;
    }
    // Checkmate detection: returns true if king is in check and has no legal moves
    public Map<Piece,List<Position>> isCheckmate() {
        HashMap<Piece,List<Position>> checkmateMap = new HashMap<>();
        if (!inCheck()) return null;
        // If king has any legal moves, not checkmate
        List<Position> temp = canMove(null);
        if(!temp.isEmpty()) {
            checkmateMap.put(this,temp);
        }
        HashMap<Position,Piece> originalMap = new HashMap<>(Piece.board.posMap);
        // Check if any friendly piece can block or capture the checking piece
        for (Piece p : originalMap.values()) {
            if (p == null || p == this || p.clr != this.clr) continue;
            List<Position> moves = p.canMove(null);
            for (Position move : moves) {
                // Simulate the move
                Position originalPos = p.position;
                Piece captured = Piece.board.posMap.get(move);
                Piece.board.posMap.remove(originalPos);
                p.position = move;
                Piece.board.posMap.put(move, p);

                boolean stillInCheck = Piece.board.isSquareAttacked(this.position, this.clr.opposite());

                // Undo the move
                Piece.board.posMap.remove(move);
                p.position = originalPos;
                Piece.board.posMap.put(originalPos, p);
                if (captured != null) {
                    Piece.board.posMap.put(move, captured);
                }

                if (!stillInCheck) {
                    checkmateMap.computeIfAbsent(p,key->new ArrayList<>()).add(move);
                }
            }
        }
        // for(Map.Entry<Piece,List<Position>> entry:checkmateMap.entrySet()){
        //     System.out.println(entry.getKey().piece.getName()+" "+entry.getKey().clr.getName()+": "+entry.getValue());
        // }
        return checkmateMap;
    }
    @Override
    public List<Position> canKill(Position pos){
        List<Position> moves = new java.util.ArrayList<>();
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < 8; i++) {
            Position newPos = new Position(this.position.x + dx[i], this.position.y + dy[i]);
            if (!Position.boundCheck(newPos)) continue;
            moves.add(newPos);
        }
        return moves;
    }
    boolean inCheck(){
        return Piece.board.isSquareAttacked(this.position, clr.opposite());
    }


}