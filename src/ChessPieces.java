public interface ChessPieces {
    Team getTeam();
    int getMoveMax();
    moveType getMoveType();
    pieceType getPieceType();

    boolean canMove(ChessBoard piece, int startX, int startY, int endX, int endY);
}
