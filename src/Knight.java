public class Knight implements ChessPieces {
    private final Team team;

    public Knight(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public int getMoveMax() {
        // The knight's movement isn't defined by a maximum distance, but a unique L-shape
        return -1; // Indicative value, not used for knights
    }

    @Override
    public moveType getMoveType() {
        return moveType.KNIGHT_MOVE; // Assuming MoveType is an enum with KNIGHT_MOVE
    }

    @Override
    public pieceType getPieceType() {
        return pieceType.KNIGHT; // Assuming PieceType is an enum with KNIGHT
    }

    public boolean canMove(ChessBoard board, int startX, int startY, int endX, int endY) {
        int deltaX = Math.abs(endX - startX);
        int deltaY = Math.abs(endY - startY);

        // Check for L-shaped movement (2 squares one direction, 1 square another)
        if ((deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2)) {
            // Knights can jump over other pieces, so no need to check the path
            return true;
        }

        return false; // Not a valid move for a knight
    }

    // Additional methods...
}

