package chess;

public enum PieceType {
    PAWN("Pawn", 1),
    KNIGHT("Knight", 3),
    BISHOP("Bishop", 3),
    ROOK("Rook", 5),
    QUEEN("Queen", 9),
    KING("King", Integer.MAX_VALUE),
    NOPIECE("Null",0);

    private final String name;
    private final int value;

    // Constructor
    PieceType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
