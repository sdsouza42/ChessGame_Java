public class Rook implements ChessPieces {
    private final Team team;
    private boolean hasMoved;  // Track if the rook has moved

    public Rook(Team team) {
        this.team = team;
        this.hasMoved = false;  // Initially, the rook hasn't moved
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public int getMoveMax() {
        // The rook can move across the board, so the max move would be the board's dimension
        // For an 8x8 board, it's 7 (as it's zero-based index)
        return 7;
    }

    @Override
    public moveType getMoveType() {
        return moveType.ROOK_MOVE; // Assuming MoveType is an enum with ROOK_MOVE
    }

    @Override
    public pieceType getPieceType() {
        return pieceType.ROOK; // Assuming PieceType is an enum with ROOK
    }

    public boolean canMove(ChessBoard board, int startX, int startY, int endX, int endY) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        // Check if the move is horizontal or vertical
        if (deltaX == 0 || deltaY == 0) {
            // Use the centralized path checking logic in ChessRules
            return ChessRules.isPathClear(board, startX, startY, endX, endY);
        }

        return false; // Not a valid move for a rook
    }
    public boolean hasMoved() {
        return this.hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}

