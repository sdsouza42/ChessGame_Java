public class ChessRules {
    private ChessBoard CB;
    private Team currentTurn;

    public ChessRules(ChessBoard chessBoard) {
        CB = chessBoard;
        this.currentTurn = Team.WHITE;
        start(); // You can optionally start the game setup here
    }


    // Initializes and starts the game
    public void start() {
        // Set up the pieces for both players
        setupPieces(Team.WHITE); // Assuming Team is an enum with WHITE and BLACK
        setupPieces(Team.BLACK);
    }

    private void setupPieces(Team team) {
        // Determine starting rows for pawns and other pieces based on the team
        int pawnRow = (team == Team.WHITE) ? 1 : 6;
        int otherRow = (team == Team.WHITE) ? 0 : 7;

        // Place pawns
        for (int i = 0; i < CB.getWidth(); i++) {
            CB.placePiece(new Pawn(team), i, pawnRow);
        }

        // Place rooks
        CB.placePiece(new Rook(team), 0, otherRow);
        CB.placePiece(new Rook(team), 7, otherRow);

        // Place knights
        CB.placePiece(new Knight(team), 1, otherRow);
        CB.placePiece(new Knight(team), 6, otherRow);

        // Place bishops
        CB.placePiece(new Bishop(team), 2, otherRow);
        CB.placePiece(new Bishop(team), 5, otherRow);

        // Place queen
        CB.placePiece(new Queen(team), 3, otherRow);

        // Place king
        CB.placePiece(new King(team), 4, otherRow);
    }


    // Handles a move from one tile to another
    public boolean move(int startX, int startY, int endX, int endY) throws IllegalArgumentException {
        ChessPieces piece = CB.getPiece(startX, startY);
        if (piece == null) {
            throw new IllegalArgumentException("No piece at starting position.");
        }
        if (piece.getTeam() != currentTurn) {
            throw new IllegalArgumentException("It's not " + piece.getTeam() + "'s turn.");
        }

        if (validMove(startX, startY, endX, endY)) {
            // Logic to move the piece
            CB.placePiece(piece, endX, endY); // Move the piece
            CB.placePiece(null, startX, startY); // Set the original spot to empty
            switchTurn();
            return true; // Move was successful
        } else {
            throw new IllegalArgumentException("Invalid move.");
        }
    }
    private void switchTurn() {
        currentTurn = (currentTurn == Team.WHITE) ? Team.BLACK : Team.WHITE;
    }

    public Team getCurrentTurn() {
        return currentTurn;
    }

    // Finalizes the game
    public void end() {
        // Handle end-of-game logic, such as determining the winner
        // Cleanup resources if needed
    }

    // Checks if a move is valid
    private boolean validMove(int startX, int startY, int endX, int endY) {
        ChessPieces piece = CB.getPiece(startX, startY);

        // Check if there's a piece at the start location
        if (piece == null) {
            return false;
        }

        // Check if the move is valid for the specific piece
        if (!piece.canMove(CB, startX, startY, endX, endY)) {
            return false;
        }

        // For pieces that require path clearance
        if (piece instanceof Rook || piece instanceof Bishop || piece instanceof Queen) {
            if (!isPathClear(CB, startX, startY, endX, endY)) {
                return false;
            }
        }

        // Check if the destination tile is appropriate (empty or enemy piece)
        ChessPieces destinationPiece = CB.getPiece(endX, endY);
        if (destinationPiece != null && destinationPiece.getTeam() == piece.getTeam()) {
            return false; // Can't capture your own piece
        }
/*
        if (piece instanceof Pawn) {
            if (isEnPassantMove(piece, startX, startY, endX, endY)) {
                return true; // En Passant move is valid
            }
        } else if (piece instanceof King) {
            if (isCastlingMove(startX, startY, endX, endY)) {
                return true; // Castling move is valid
            }
        }

 */

        return true;
    }

    // Method to check if the path between two points is clear
    public static boolean isPathClear(ChessBoard board, int startX, int startY, int endX, int endY) {
        int stepX = (endX - startX != 0) ? (endX - startX) / Math.abs(endX - startX) : 0;
        int stepY = (endY - startY != 0) ? (endY - startY) / Math.abs(endY - startY) : 0;

        int x = startX + stepX;
        int y = startY + stepY;

        while (x != endX || y != endY) {
            if (board.getPiece(x, y) != null) { // Check if the tile is occupied
                return false; // Path is blocked
            }
            x += (x != endX) ? stepX : 0;
            y += (y != endY) ? stepY : 0;
        }

        return true; // Path is clear
    }
/*
    private boolean isEnPassantMove(ChessPieces piece, int startX, int startY, int endX, int endY) {
        return false; // Placeholder
    }

    private boolean isCastlingMove(int startX, int startY, int endX, int endY) {
        return false; // Placeholder
    }
*/

        // Handles user clicks on the board
    public void userClick(int x, int y) {
        // Translate screen coordinates to board coordinates
        // Possibly handle selecting a piece or executing a move
    }

}
