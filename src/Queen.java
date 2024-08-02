public class Queen implements ChessPieces {
    private final Team team;

    public Queen(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public int getMoveMax() {
        // The queen can move across the board, so the max move would be the board's dimension
        // For an 8x8 board, it's 7 (as it's zero-based index)
        return 7;
    }

    @Override
    public moveType getMoveType() {
        return moveType.QUEEN_MOVE; // Assuming MoveType is an enum with QUEEN_MOVE
    }

    @Override
    public pieceType getPieceType() {
        return pieceType.QUEEN; // Assuming PieceType is an enum with QUEEN
    }

    public boolean canMove(ChessBoard board, int startX, int startY, int endX, int endY) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        // Check if the move is along a rank, file, or diagonal
        if (deltaX == deltaY || startX == endX || startY == endY) {
            // Use the centralized path checking logic in ChessRules
            return ChessRules.isPathClear(board, startX, startY, endX, endY);
        }

        return false; // Not a valid move for a queen
    }

    // Additional methods...
}

