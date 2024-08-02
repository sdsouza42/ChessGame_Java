public class Pawn implements ChessPieces{
    private final Team team;
    private boolean hasMoved; // To track if the pawn has moved (for the first move rule)
    private boolean eligibleForSpecialMove; // Indicates eligibility for En Passant and Promotion

    // Constructor
    public Pawn(Team team) {
        this.team = team;
        this.hasMoved = false;
    }

    @Override
    public Team getTeam() {
        return this.team;
    }

    @Override
    public int getMoveMax() {
        return this.hasMoved ? 1 : 2; // 2 on first move, 1 afterwards
    }

    @Override
    public moveType getMoveType() {
        return moveType.PAWN_MOVE; // Assuming MoveType is an enum with PAWN_MOVE
    }

    @Override
    public pieceType getPieceType() {
        return pieceType.PAWN; // Assuming PieceType is an enum with PAWN
    }

    // Method to check if a move is valid for the pawn
    public boolean canMove(ChessBoard board, int startX, int startY, int endX, int endY) {
        int direction = (this.team == Team.WHITE) ? 1 : -1;
        int deltaY = endY - startY;
        int deltaX = endX - startX;

        // Standard forward move
        if (deltaX == 0) {
            if (deltaY == direction && board.getPiece(endX, endY) == null) { // Move 1 step forward
                this.hasMoved = true;
                return true;
            } else if (!this.hasMoved && deltaY == 2 * direction && board.getPiece(startX, startY + direction)
                    == null && board.getPiece(endX, endY) == null) { // Move 2 steps forward
                this.hasMoved = true;
                return true;
            }
        }

        // Capturing diagonally
        if (Math.abs(deltaX) == 1 && deltaY == direction) {
            ChessPieces targetPiece = board.getPiece(endX, endY);
            if (targetPiece != null && targetPiece.getTeam() != this.team) { // Ensure there's an opponent piece
                this.hasMoved = true;
                return true;
            }
        }

        // Add logic for en passant here (if applicable)

        // Check for promotion eligibility
        checkEligibilityForSpecialMove(startY, endY);

        return false; // If none of the valid conditions are met
    }

    private void checkEligibilityForSpecialMove(int startY, int endY) {
        int promotionRank = (this.team == Team.WHITE) ? 6 : 1; // Rank just before promotion
        if (startY == promotionRank) {
            this.eligibleForSpecialMove = true;
        } else {
            this.eligibleForSpecialMove = false;
        }
    }

    public boolean isEligibleForSpecialMove() {
        return this.eligibleForSpecialMove;
    }

}
