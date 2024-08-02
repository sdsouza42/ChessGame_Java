import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ChessBoardSwing {
    private Chess3DManager chess3DManager;
    private ChessRules chessRules;
    private ChessBoard chessBaseBoard;
    private ChessBoard chessMidBoard;
    private ChessBoard chessTopBoard;
    private JPanel boardPanelLevel1, boardPanelLevel2, boardPanelLevel3;
    private final int boardSize = 8; // Assuming a standard 8x8 chess board
    private JButton[][] basebuttons = new JButton[8][8];
    private JButton[][] midbuttons = new JButton[8][8];
    private JButton[][] topbuttons = new JButton[8][8];
    private JButton firstClicked = null;
    private JFrame frame; // Declare the JFrame as a member variable
    int slvl = 1;

    public ChessBoardSwing() {
        chessBaseBoard = new ChessBoard(); // Initialize the chess board
        chessMidBoard = new ChessBoard();
        chessTopBoard = new ChessBoard();

        chessRules = new ChessRules(chessBaseBoard);

        chess3DManager = new Chess3DManager(chessRules, chessBaseBoard, chessMidBoard, chessTopBoard);

        frame = new JFrame("Chess Board - Turn: White"); // Initialize the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new FlowLayout()); // Arrange boards side by side

        // Create and add three board panels
        boardPanelLevel1 = createBoardPanel(Color.RED, basebuttons, chessBaseBoard);
        boardPanelLevel2 = createBoardPanel(Color.BLUE, midbuttons);
        boardPanelLevel3 = createBoardPanel(Color.GREEN, topbuttons);

        frame.add(boardPanelLevel1);
        frame.add(boardPanelLevel2);
        frame.add(boardPanelLevel3);

        frame.pack();
        frame.setVisible(true);
    }
    private JPanel createBoardPanel(Color bcolor, JButton[][] lvlbtn, ChessBoard board){
        JPanel panel = new JPanel(new GridLayout(boardSize, boardSize));
        panel.setBorder(BorderFactory.createLineBorder(bcolor, 3));
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton btn = createButton(i, j);
                lvlbtn[i][j] = btn;
                panel.add(btn);
                updateButtonDisplay(board, btn, i, j); // Update button display with unicode symbols
            }
        }
        return panel;
    }

    private JPanel createBoardPanel(Color bcolor, JButton[][] lvlbtn) {
        JPanel panel = new JPanel(new GridLayout(boardSize, boardSize));
        panel.setBorder(BorderFactory.createLineBorder(bcolor, 3));
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                JButton btn = createButton(i, j);
                btn.setText("");
                lvlbtn[i][j] = btn;
                panel.add(btn);
            }
        }
        return panel;
    }

    private JButton createButton(int row, int col) {
        JButton btn = new JButton();
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
        btn.setFont(new Font("SansSerif", Font.BOLD, 24));
        btn.addActionListener(this::buttonClicked);
        return btn;
    }
    ChessBoard oldBoard;

    private void buttonClicked(ActionEvent e) {
        JButton clickedBtn = (JButton) e.getSource();
        JButton [][] Button = getButtonType(clickedBtn);
        ChessBoard board = getBoard (Button);
        int startLevel = getLevel(board);
        Point position = getTilePosition(clickedBtn, Button);

        if (firstClicked == null) {
            firstClicked = clickedBtn;
            // Check if the piece belongs to the player whose turn it is
            ChessPieces piece = board.getPiece(position.x, position.y);
            if (piece == null || piece.getTeam() != chessRules.getCurrentTurn()) {
                slvl = startLevel; //clear this when you can
                oldBoard = board;
                JOptionPane.showMessageDialog(frame, "It is " + chessRules.getCurrentTurn() + "'s turn. Only " + chessRules.getCurrentTurn() + " pieces may move.", "Wrong Turn", JOptionPane.ERROR_MESSAGE);
                firstClicked = null; // Clear the selection
                return;
            }
        } else {
            Point startPosition = getTilePosition(firstClicked, getButtonType(firstClicked));
            Point endPosition = position;
            int endLevel = getLevel(board);
            //System.out.println(slvl + " " + startPosition.x + " " + startPosition.y + " " + endLevel + " " + endPosition.x + " " + endPosition.y);

            try {
                if (chess3DManager.makeMove(slvl, startPosition.x, startPosition.y, endLevel, endPosition.x, endPosition.y)) {
                    refreshBoard();
                } else {
                    // Display an error message if the move is not successful
                    JOptionPane.showMessageDialog(null, "Illegal move or Path is blocked(intry)", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                // Display an error message for illegal moves
                JOptionPane.showMessageDialog(null, "Illegal move or Path is blocked(incatch)", "Invalid Move", JOptionPane.ERROR_MESSAGE);
            }
            firstClicked = null;
        }
    }

    private int getLevel (ChessBoard board){
        if(board == chessBaseBoard){
            return 1;
        }
        if(board == chessMidBoard){
            return 2;
        }
        if(board  == chessTopBoard){
            return 3;
        }
        return 0;
    }

    private ChessBoard getBoard(JButton [][] Button){
        if(Button == basebuttons){
            return chessBaseBoard;
        }
        if(Button == midbuttons){
            return chessMidBoard;
        }
        if(Button == topbuttons){
            return chessTopBoard;
        }
        return null;
    }
    private JButton[][] getButtonType(JButton button) {
        if (isButtonInArray(button, basebuttons)) {
            return basebuttons;
        }

        if (isButtonInArray(button, midbuttons)) {
            return midbuttons;
        }

        if (isButtonInArray(button, topbuttons)) {
            return topbuttons;
        }

        return null; // Should not happen if every button is accounted for
    }

    private boolean isButtonInArray(JButton button, JButton[][] buttons) {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (button == buttons[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    private void refreshBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                updateButtonDisplay(chessBaseBoard, basebuttons[row][col], row, col);
                updateButtonDisplay(chessMidBoard, midbuttons[row][col], row, col);
                updateButtonDisplay(chessTopBoard, topbuttons[row][col], row, col);
            }
        }
        String playerTurn = chessRules.getCurrentTurn() == Team.WHITE ? "White" : "Black";
        frame.setTitle("Chess Board - Turn: " + playerTurn);
    }


    private void updateButtonDisplay(ChessBoard board, JButton button, int row, int col) {
        ChessPieces piece = board.getPiece(col, row);
        if (piece != null) {
            String unicodeSymbol = getUnicodeSymbol(piece);
            String htmlText = String.format("<html><span style='color: %s; font-size: 24px;'>%s</span></html>",
                    piece.getTeam() == Team.WHITE ? "green" : "black",
                    unicodeSymbol);
            button.setText(htmlText);
        } else {
            button.setText("");
        }
    }

    private String getUnicodeSymbol(ChessPieces piece) {
        String symbol = "";
        switch (piece.getPieceType()) {
            case KING:
                symbol = piece.getTeam() == Team.WHITE ? "\u2654" : "\u265A";
                break;
            case QUEEN:
                symbol = piece.getTeam() == Team.WHITE ? "\u2655" : "\u265B";
                break;
            case ROOK:
                symbol = piece.getTeam() == Team.WHITE ? "\u2656" : "\u265C";
                break;
            case BISHOP:
                symbol = piece.getTeam() == Team.WHITE ? "\u2657" : "\u265D";
                break;
            case KNIGHT:
                symbol = piece.getTeam() == Team.WHITE ? "\u2658" : "\u265E";
                break;
            case PAWN:
                symbol = piece.getTeam() == Team.WHITE ? "\u2659" : "\u265F";
                break;
            // Add cases for any other piece types you might have
        }
        return symbol;
    }

        private Point getTilePosition(JButton button, JButton[][] buttons) {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                if (button == buttons[row][col]) {
                    return new Point(col, row);
                }
            }
         }
            return null; // Should not happen if every button is accounted for
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessBoardSwing::new);
    }
}
