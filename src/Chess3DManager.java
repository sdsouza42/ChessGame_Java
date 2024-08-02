public class Chess3DManager {
    private ChessBoard boardLevel1;
    private ChessBoard boardLevel2;
    private ChessBoard boardLevel3;
    private ChessRules chessRules;
    private int effectiveEndX;
    private int effectiveEndY;

    public Chess3DManager(ChessRules chessRules, ChessBoard boardLevel1, ChessBoard boardLevel2, ChessBoard boardLevel3) {
        this.chessRules = chessRules;
        this.boardLevel1 = boardLevel1; // Initialize the chess board for level 1
        this.boardLevel2 = boardLevel2; // Initialize the chess board for level 2
        this.boardLevel3 = boardLevel3; // Initialize the chess board for level 3
    }

    public ChessPieces getPiece(int level, int x, int y) {
        switch (level) {
            case 1:
                return boardLevel1.getPiece(x, y);
            case 2:
                return boardLevel2.getPiece(x, y);
            case 3:
                return boardLevel3.getPiece(x, y);
            default:
                throw new IllegalArgumentException("Invalid level");
        }
    }

    public void setPiece(int level, int x, int y, ChessPieces piece) {
        switch (level) {
            case 1:
                boardLevel1.placePiece(piece, x, y);
                break;
            case 2:
                boardLevel2.placePiece(piece, x, y);
                break;
            case 3:
                boardLevel3.placePiece(piece, x, y);
                break;
            default:
                throw new IllegalArgumentException("Invalid level");
        }
    }

    public boolean makeMove(int startLevel, int startX, int startY, int endLevel, int endX, int endY) {
        //System.out.println(startLevel + " " + startX + " " + startY + " " + endLevel + " " + endX + " " + endY);
        ChessPieces piece = getPiece(startLevel, startX, startY);
        /*if (piece == null) {
            System.out.println("piece null ran");
            return false; // No piece at the starting position
        }*/

        boolean moveSuccessful = false;

        // Handle move within the same level
        if (startLevel == endLevel) {
            moveSuccessful = chessRules.move(startX, startY, endX, endY);
        } else {
            System.out.println("Enter Else");
            // Handle move between levels
            int levelDifference = Math.abs(endLevel - startLevel);
            if (levelDifference > 1) {
                System.out.println("Skip ran");
                return false; // Cannot skip levels
            }

            // Adjust the effective end position for pieces that can move more than one space
            effectiveEndX = endX;
            effectiveEndY = endY;
            if (piece.getMoveMax() > 1) {
                System.out.println("change movement ran");
                effectiveEndY -= (endLevel - startLevel); // Adjust Y based on level change
            }

            // Attempt the move with the adjusted coordinates
            moveSuccessful = chessRules.move(startX, startY, effectiveEndX, effectiveEndY);
        }

        if (moveSuccessful) {
            System.out.println("Board update ran");
            // Update the boards after a successful move
            setPiece(endLevel, endX, endY, piece);
            setPiece(startLevel, startX, startY, null);
            //System.out.println(getPiece(endLevel, endX, endY));
        }

        return moveSuccessful;
    }

    public int getEffectiveEndX() {
        return effectiveEndX;
    }

    public int getEffectiveEndY() {
        return effectiveEndY;
    }
}

