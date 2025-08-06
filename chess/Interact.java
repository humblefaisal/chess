package chess;
import java.util.List;
import java.util.ArrayList;

//no longer need to define an interface
// interface Interaction{
//     //directions are the same for both black and white..
//     Position inFront(Piece p);
//     Position inBack(Piece p);
//     Position diagFR(Piece p);
//     Position diagFL(Piece p);
//     Position diagBR(Piece p);
//     Position diagBL(Piece p);
// }


class Interact{
    
    public static boolean kingMove(Position position){
        //throw 8 beams;
        return false;
    }

    // Returns all positions in front of the piece until blocked
    public static List<Position> allInFront(Piece p,boolean protecting) {
        List<Position> result = new ArrayList<>();
        int inc = p.clr.getValue();
        for (int i = p.position.y + inc; i < 8 && i >= 0; i += inc) {
            Position pos = new Position(p.position.x, i);
            Piece temp = Piece.board.posMap.getOrDefault(pos, null);
            if (temp == null) {
                result.add(pos);
            } else {
                if (temp.clr != p.clr || protecting) result.add(pos);
                if(temp.piece!=PieceType.KING || temp.clr==p.clr) break;
            }
        }
        return result;
    }
    // Returns all positions in back of the piece until blocked
    public static List<Position> allInBack(Piece p,boolean protecting) {
        List<Position> result = new ArrayList<>();
        int inc = -p.clr.getValue();
        for (int i = p.position.y + inc; i < 8 && i >= 0; i += inc) {
            Position pos = new Position(p.position.x, i);
            Piece temp = Piece.board.posMap.getOrDefault(pos, null);
            if (temp == null) {
                result.add(pos);
            } else {
                if (temp.clr != p.clr || protecting) result.add(pos);
                if(temp.piece!=PieceType.KING || temp.clr==p.clr) break;

            }
        }
        return result;
    }
    // Returns all positions to the right of the piece until blocked
    public static List<Position> allRight(Piece p,boolean protecting) {
        List<Position> result = new ArrayList<>();
        for (int i = p.position.x + 1; i < 8; i++) {
            Position pos = new Position(i, p.position.y);
            Piece temp = Piece.board.posMap.getOrDefault(pos, null);
            if (temp == null) {
                result.add(pos);
            } else {
                if (temp.clr != p.clr || protecting) result.add(pos);
                if(temp.piece!=PieceType.KING || temp.clr==p.clr) break;

            }
        }
        return result;
    }
    // Returns all positions to the left of the piece until blocked
    public static List<Position> allLeft(Piece p,boolean protecting) {
        List<Position> result = new ArrayList<>();
        for (int i = p.position.x - 1; i >= 0; i--) {
            Position pos = new Position(i, p.position.y);
            Piece temp = Piece.board.posMap.getOrDefault(pos, null);
            if (temp == null) {
                result.add(pos);
            } else {
                if (temp.clr != p.clr || protecting) result.add(pos);
                if(temp.piece!=PieceType.KING || temp.clr==p.clr) break;

            }
        }
        return result;
    }
    // Returns all positions in a diagonal direction until blocked
    public static List<Position> allDiag(Piece p, int dx, int dy,boolean protecting) {
        List<Position> result = new ArrayList<>();
        int x = p.position.x + dx;
        int y = p.position.y + dy;
        while (x >= 0 && x < 8 && y >= 0 && y < 8) {
            Position pos = new Position(x, y);
            Piece temp = Piece.board.posMap.getOrDefault(pos, null);
            if (temp == null) {
                result.add(pos);
            } else {
                if (temp.clr != p.clr || protecting) result.add(pos);
                if(temp.piece!=PieceType.KING || temp.clr==p.clr) break;

            }
            x += dx;
            y += dy;
        }
        return result;
    }
}