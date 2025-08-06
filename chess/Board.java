package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Board {
    public Map<Position,Piece> posMap = new HashMap<>();
    private HashMap<Position,HashSet<PieceType>> pieceMap = new HashMap<>();
    private HashMap<Position,DIRECTION> directionMap = new HashMap<>();
    public Board() {
        //black
        List<Piece> black=new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos=new Position(i, 0);
            switch (i) {
                case 0, 7 -> black.add(new Rook(pos,Colour.BLACK));
                case 1, 6 -> black.add(new Knight(pos,Colour.BLACK));
                case 2, 5 -> black.add(new Bishop(pos,Colour.BLACK));
                case 3 -> black.add(new King(pos,Colour.BLACK));
                case 4 -> black.add(new Queen(pos,Colour.BLACK));
                default -> {
                }
            }
            posMap.put(pos, black.get(i));
        }
        //black pawns 
        List<Piece> pawnBlack = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(i, 1);
            pawnBlack.add(new Pawn(pos,Colour.BLACK));
            posMap.put(pos, pawnBlack.get(i));
        } 

        //white
        List<Piece> white=new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos=new Position(i, 7);
            switch (i) {
                case 0, 7 -> white.add(new Rook(pos,Colour.WHITE));
                case 1, 6 -> white.add(new Knight(pos,Colour.WHITE));
                case 2, 5 -> white.add(new Bishop(pos,Colour.WHITE));
                case 3 -> white.add(new King(pos,Colour.WHITE));
                case 4 -> white.add(new Queen(pos,Colour.WHITE));
                default -> {
                }
            }
            posMap.put(pos, white.get(i));

        }

        //Pawns White
        List<Piece> pawnWhite = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(i, 6);
            pawnWhite.add(new Pawn(pos,Colour.WHITE));
            posMap.put(pos, pawnWhite.get(i));
        } 

        List<Piece> empty = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            empty.add(new NoPiece());
        } 

        pieceMap.put(new Position(0,1),new HashSet<>(List.of(PieceType.ROOK,PieceType.QUEEN)));
        pieceMap.put(new Position(0,-1),new HashSet<>(List.of(PieceType.ROOK,PieceType.QUEEN)));
        pieceMap.put(new Position(1,0),new HashSet<>(List.of(PieceType.ROOK,PieceType.QUEEN)));
        pieceMap.put(new Position(-1,0),new HashSet<>(List.of(PieceType.ROOK,PieceType.QUEEN)));
        pieceMap.put(new Position(1,1),new HashSet<>(List.of(PieceType.BISHOP,PieceType.QUEEN)));
        pieceMap.put(new Position(1,-1),new HashSet<>(List.of(PieceType.BISHOP,PieceType.QUEEN)));
        pieceMap.put(new Position(-1,1),new HashSet<>(List.of(PieceType.BISHOP,PieceType.QUEEN)));
        pieceMap.put(new Position(-1,-1),new HashSet<>(List.of(PieceType.BISHOP,PieceType.QUEEN)));

        directionMap.put(new Position(0,1),DIRECTION.VERTICAL);
        directionMap.put(new Position(0,-1),DIRECTION.VERTICAL);
        directionMap.put(new Position(1,0),DIRECTION.HORIZONTAL);
        directionMap.put(new Position(-1,0),DIRECTION.HORIZONTAL);
        directionMap.put(new Position(1,1),DIRECTION.DIAGLEFT);
        directionMap.put(new Position(1,-1),DIRECTION.DIAGRIGHT);
        directionMap.put(new Position(-1,1),DIRECTION.DIAGRIGHT);
        directionMap.put(new Position(-1,-1),DIRECTION.DIAGLEFT);

    }

    

    public void setup(){
        //black
        List<Piece> black=new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos=new Position(i, 0);
            switch (i) {
                case 0, 7 -> black.add(new Rook(pos,Colour.BLACK));
                case 1, 6 -> black.add(new Knight(pos,Colour.BLACK));
                case 2, 5 -> black.add(new Bishop(pos,Colour.BLACK));
                case 3 -> black.add(new King(pos,Colour.BLACK));
                case 4 -> black.add(new Queen(pos,Colour.BLACK));
                default -> {
                }
            }
            posMap.put(pos, black.get(i));
            
        }
        //black pawns 
        List<Piece> pawnBlack = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(i, 1);
            pawnBlack.add(new Pawn(pos,Colour.BLACK));
            posMap.put(pos,pawnBlack.get(i));
            
        } 

        //white
        List<Piece> white=new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos=new Position(i, 7);
            switch (i) {
                case 0, 7 -> white.add(new Rook(pos,Colour.WHITE));
                case 1, 6 -> white.add(new Knight(pos,Colour.WHITE));
                case 2, 5 -> white.add(new Bishop(pos,Colour.WHITE));
                case 3 -> white.add(new King(pos,Colour.WHITE));
                case 4 -> white.add(new Queen(pos,Colour.WHITE));
                default -> {
                }
            }
            posMap.put(pos, white.get(i));

        }

        //Pawns White
        List<Piece> pawnWhite = new ArrayList<>(8);
        for (int i = 0; i < 8; i++) {
            Position pos = new Position(i, 6);
            pawnWhite.add(new Pawn(pos,Colour.WHITE));
            posMap.put(pos,pawnWhite.get(i));
        } 

    }

    boolean isSquareAttacked(Position pos, Colour clr){
        for(Map.Entry<Position,Piece> entry:posMap.entrySet()){
            Piece p = entry.getValue();
            if (p == null || p.clr != clr) continue;
            List<Position> poss = p.canKill(null);
            if(poss.contains(pos)){
                return true;
            }
        }
        return false;
    }

    public void pinPieces(King king){
        Position[] dp = {new Position(0,1),new Position(0,-1),new Position(1,0),new Position(-1,0),new Position(1,1),new Position(1,-1),new Position(-1,1),new Position(-1,-1)};
        for(int i = 0; i < 8; i++){
            boolean blocked = false;
            Piece p = null;
            for(Position pos = king.position.addCheck(dp[i]);pos.boundCheck();pos = pos.addCheck(dp[i])){
                if(posMap.containsKey(pos)){
                    Piece piece = posMap.get(pos);
                    if(piece.clr != king.clr && !blocked)break;
                    if(piece.clr != king.clr && blocked){
                        if(pieceMap.get(dp[i]).contains(piece.piece)){
                            Piece.pinnedPieces.add(p);
                            p.directions.clear();
                            p.directions.add(directionMap.get(dp[i]));
                            
                        } else break;
                    }
                    if(piece.clr == king.clr){
                        if(blocked)break;
                        blocked = true;
                        p = piece;
                    }
                    
                }
            }
        }
        // for(Piece p:Piece.pinnedPieces){
        //     System.out.print(p.piece.getName()+" "+p.clr.getName()+",    ");
        // }

    }

    public void releasePinnedPieces() {
        // Collect pieces to unpin to avoid modifying the list while iterating
        List<Piece> toUnpin = new ArrayList<>();

        for (Piece p : Piece.pinnedPieces) {
            boolean shouldUnpin = false;

            // Collect all direction vectors for this piece
            List<Position> directionsToCheck = new ArrayList<>();
            for (DIRECTION dir : p.directions) {
                directionsToCheck.add(dir.getDp1());
                directionsToCheck.add(dir.getDp2());
            }

            // Check each direction for unpinning conditions
            for (Position direction : directionsToCheck) {
                Position pos = p.position.addCheck(direction);
                boolean pieceFound=false;
                //System.out.println("Dir : "+direction.x+","+direction.y);
                while (pos.boundCheck()) {
                    if (posMap.containsKey(pos)) {
                        pieceFound=true;
                        Piece piece = posMap.get(pos);
                        //System.out.println(piece.piece.getName()+" "+piece.clr.getName());
                        boolean isFriendlyNonKing = (piece.clr == p.clr && piece.piece!=PieceType.KING);
                        boolean isEnemyButNotInPieceMap = (piece.clr != p.clr && !pieceMap.get(direction).contains(piece.piece));
                        if (isFriendlyNonKing || isEnemyButNotInPieceMap) {
                            //System.out.println("isFriendlyNonKing : "+isFriendlyNonKing+",isEnemyButNotInPieceMap "+isEnemyButNotInPieceMap+" "+pieceMap.get(direction).contains(piece.piece)+" "+piece.piece.getName());
                            shouldUnpin = true;
                            break;
                        }
                        break;
                    }
                    pos = pos.addCheck(direction);
                }
                if(!pieceFound){
                    shouldUnpin=true;
                    break;
                }
                if (shouldUnpin) break;
            }

            if (shouldUnpin) {
                toUnpin.add(p);
            }
        }

        // Unpin all marked pieces after iteration
        for (Piece p : toUnpin) {
            //System.out.print("Released : "+p.piece.getName()+" "+p.clr.getName()+",    ");
            Piece.pinnedPieces.remove(p);
            p.directions.clear();
            p.directions.addAll(List.of(DIRECTION.NONE, DIRECTION.VERTICAL, DIRECTION.DIAGLEFT, DIRECTION.DIAGRIGHT, DIRECTION.HORIZONTAL));
        }
    }
























































    // void dangerZoneSetBlack(Piece p,Position position){
    //     //Call this function before changing the position of the piece!
    //     if(p.clr==Colour.WHITE){
    //         List<Position> positionsRemove,positionsAdd;
    //         //remove danger square from that position 
    //         if(p instanceof Pawn obj){
    //             positionsRemove=obj.canKill(null);
    //         } else {
    //             positionsRemove=p.canMove(null);
    //         }
    //         for(Position pos:positionsRemove){
    //             dangerMapBlack.put(pos,dangerMapBlack.get(pos)-1);
    //         }

    //         //add danger squares for new position;
    //         if(p instanceof Pawn obj){
    //             positionsAdd = obj.canKill(position);
    //         } else{
    //             positionsAdd = p.canMove(position);
    //         }
    //         for(Position pos:positionsAdd){
    //             dangerMapBlack.put(pos,dangerMapBlack.get(pos)+1);
    //         }
    //     } else {
    //         List<Piece> blockedPieces = p.blockingPieces;
            
    //     }
    // }
    // void dangerZoneSetWhite(){

    // }

    
}


