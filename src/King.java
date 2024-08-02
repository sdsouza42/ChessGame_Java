public class King implements ChessPieces {
    private final Team team;
    private boolean isInCheck;
    private boolean hasMoved;  // To track if the king has moved for castling

    public King(Team team) {
        this.team = team;
        this.isInCheck = false;
        this.hasMoved = false;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public int getMoveMax() {
        return 1; // The king can only move one square at a time (except for castling)
    }

    @Override
    public moveType getMoveType() {
        return moveType.KING_MOVE;
    }

    @Override
    public pieceType getPieceType() {
        return pieceType.KING;
    }

    public boolean canMove(ChessBoard board, int startX, int startY, int endX, int endY) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        // Check for standard king move (one square in any direction)
        if (deltaX <= 1 && deltaY <= 1 && !(deltaX == 0 && deltaY == 0)) {
            this.hasMoved = true;  // Mark the king as having moved
            return true;  // A valid move for the king
        }

        // Add logic for castling here (if applicable)

        return false;
    }

    public boolean isInCheck() {
        return this.isInCheck;
    }

    public void setInCheck(boolean isInCheck) {
        this.isInCheck = isInCheck;
    }

    public boolean hasMoved() {
        return this.hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // Additional methods...
}
