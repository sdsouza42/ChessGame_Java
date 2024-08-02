public class ChessBoard implements Board {
    private final int length = 8;
    private final ChessPieces[][] numTiles;
    private final String[] colorTiles = {"WHITE", "BLACK"};

    public ChessBoard() {
        this.numTiles = new ChessPieces[length][length];  // 8x8 chess board
        // Initialize the board with empty spaces/nulls
        for (int i = 0; i < numTiles.length; i++) {
            for (int j = 0; j < numTiles[i].length; j++) {
                numTiles[i][j] = null;
            }
        }
    }

    public void placePiece(ChessPieces piece, int x, int y) {
        numTiles[y][x] = piece;
    }

    public ChessPieces getPiece(int x, int y) {
        return numTiles[y][x];
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return length;
    }

    public String[] getColorTiles() {
        return colorTiles;
    }

    // Method to check if a tile is occupied and by whom
    public ChessPieces tileOccupied(int x, int y) {
        return numTiles[y][x];
    }
}
