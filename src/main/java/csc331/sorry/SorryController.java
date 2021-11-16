package csc331.sorry;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;

import static javafx.scene.layout.GridPane.setColumnIndex;

public class SorryController {

    @FXML
    private ImageView cardImage;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label turnLabel;

    @FXML
    private ImageView bluePiece1;

    @FXML
    private ImageView bluePiece2;

    @FXML
    private ImageView bluePiece3;

    @FXML
    private ImageView bluePiece4;

    @FXML
    private ImageView greenPiece1;

    @FXML
    private ImageView greenPiece2;

    @FXML
    private ImageView greenPiece3;

    @FXML
    private ImageView greenPiece4;

    @FXML
    private ImageView redPiece1;

    @FXML
    private ImageView redPiece2;

    @FXML
    private ImageView redPiece3;

    @FXML
    private ImageView redPiece4;

    @FXML
    private ImageView yellowPiece1;

    @FXML
    private ImageView yellowPiece2;

    @FXML
    private ImageView yellowPiece3;

    @FXML
    private ImageView yellowPiece4;

    @FXML
    private Label instructionsLabel;

    private boolean canDraw = true; // Temporary - set to true on initialization but false once a card is drawn

    private int card;

    private String[] labelTurn = {"Blue Turn", "Green Turn", "Red Turn", "Yellow Turn"};

    private String[] idTurn = {"blue", "green", "red", "yellow"};

    //The list below are the Row, Column coordinates of the start positions for each color, in the order above
    private final int[] pawnStartRow = {1, 14, 11, 4 };
    private final int[] pawnStartColumn = {4, 11, 1, 14};
    /*
    The list below are the Row, Column coordinates of each color, in the order above, AFTER moved from start position (when it begins counting)
    These are the same coordinates for when a one initially moves a pawn from start
     */
    private final int[]pawnInitialRow = {0, 15, 11, 4};
    private final int[] pawnInitialColumn = {4, 11, 0, 15};

    //IMPORTANT NOTE: Since the position index orders line up with the turn orders, we can simply use the same counter to locate the position within list.

    private int counter = 0;

    @FXML
    void onCardClicked(MouseEvent event) {
        card = (int) (Math.random() * 13 + 1);
        // Pick random card from 1 to 13; 1-12 are numbers, 13 is the Sorry card
        if (canDraw) {
            canDraw = false;
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/" +
                    (card == 13 ? "Sorry" : card) + ".png"))));

            String labelText = switch (card) {
                case 1 -> "Click a pawn to move forward one space.";
                case 2 -> "Click a pawn to move forward two spaces.";
                case 3 -> "Click a pawn to move forward three spaces.";
                case 4 -> "Click a pawn to move back four spaces";
                case 5 -> "Click a pawn to move forward five spaces";
                case 6 -> "Click a pawn to move back three space";
                case 7 -> "Click a pawn to move 1-7 spaces forward";
                case 8 -> "Click a pawn to move eight spaces forward";
                case 9 -> "Click a pawn to move five spaces back";
                case 10 -> "Click a pawn to move ten spaces forward";
                case 11 -> "Click a pawn to move ten spaces backwards";
                case 12 -> "Click a pawn to move twelve spaces forward";
                case 13 -> "Click pawn from start to opponent pawn space";
                default -> "";
            };
            instructionsLabel.setText(labelText);
        }
    }


    @FXML
    void onPieceClick(MouseEvent event) {
        ImageView pieceClicked = (ImageView) event.getSource();
        String id = pieceClicked.getId();
        String currentTurn = turnLabel.getText();
        int row = GridPane.getRowIndex(pieceClicked);
        int col = GridPane.getColumnIndex(pieceClicked);
        if (currentTurn.equals(labelTurn[counter]) && id.startsWith(idTurn[counter])) {
            if (card == 1) {
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    GridPane.setRowIndex(pieceClicked, pawnInitialRow[counter]);
                    GridPane.setColumnIndex(pieceClicked, pawnInitialColumn[counter]);
                } else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 2) {
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    GridPane.setRowIndex(pieceClicked, pawnInitialRow[counter]);
                    GridPane.setColumnIndex(pieceClicked, pawnInitialColumn[counter]);
                } else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 3){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    GridPane.setRowIndex(pieceClicked, pawnInitialRow[counter]);
                    GridPane.setColumnIndex(pieceClicked, pawnInitialColumn[counter]);
                }else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 4) {
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverBackward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 5){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }

            }
            if (card == 6){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverBackward(card - 3, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            //if (card == 7){

            //}
            if (card == 8){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 9){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverBackward(card - 4, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 10){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 11){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverBackward(card - 1, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            if (card == 12){
                if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                }else {
                    int[] newPosition = pieceMoverForward(card, row, col);
                    GridPane.setRowIndex(pieceClicked, newPosition[0]);
                    GridPane.setColumnIndex(pieceClicked, newPosition[1]);
                }
            }
            //if (card == 13){
            //4; 6 -> 3; 9 -> 5; 11 -> 10
            //}
            canDraw = true;
            counter = (counter + 1) % 4;
            turnLabel.setText(labelTurn[counter]);
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/back.png"))));
        }
    }

    public void checkWin(){
    }
    public int[] pieceMoverForward(int card, int row, int col) {
        if (row == 0) {
            for (int i = 0; i < card; i++) {
                if (col == 15) {
                    row = row + 1;
                } else {
                    col = col + 1;
                }
            }
            return new int[]{row, col};
        }
        if (col == 15) {
            for (int i = 0; i < card; i++) {
                if (row == 15) {
                    col = col - 1;
                } else {
                    row = row + 1;
                }
            }
            return new int[]{row, col};
        }
        if (row == 15) {
            for (int i = 0; i < card; i++) {
                if (col == 0) {
                    row = row - 1;
                } else {
                    col = col - 1;
                }
            }
            return new int[]{row, col};
        }
        if (col == 0) {
            for (int i = 0; i < card; i++) {
                if (row == 0) {
                    col = col + 1;
                } else {
                    row = row - 1;
                }
            }
            return new int[]{row, col};
        }
        //Should probably add debug for invalid move
        return new int[]{row, col};
    }
    public int[] pieceMoverBackward(int card, int row, int col) {
        if (row == 0) {
            for (int i = 0; i < card; i++) {
                if (col == 0) {
                    row = row + 1;
                } else {
                    col = col - 1;
                }
            }
            return new int[]{row, col};
        }
        if (col == 0) {
            for (int i = 0; i < card; i++) {
                if (row == 15) {
                    col = col + 1;
                } else {
                    row = row + 1;
                }
            }
            return new int[]{row, col};
        }
        if (row == 15) {
            for (int i = 0; i < card; i++) {
                if (col == 15) {
                    row = row - 1;
                } else {
                    col = col + 1;
                }
            }
            return new int[]{row, col};
        }
        if (col == 15) {
            for (int i = 0; i < card; i++) {
                if (row == 0) {
                    col = col - 1;
                } else {
                    row = row - 1;
                }
            }
            return new int[]{row, col};
        }
        //Should probably add debug for invalid move
        return new int[]{row, col};
    }
    public void initialize() {
        gridPane.setBackground(new Background(
                new BackgroundImage(new Image(String.valueOf(getClass().getResource("Board.png"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));



    }

}
