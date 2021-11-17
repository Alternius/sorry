package csc331.sorry;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import java.lang.*;
import java.util.Optional;

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

    @FXML
    private  Label drawLabel;

    private boolean canDraw = true; // Temporary - set to true on initialization but false once a card is drawn

    private int card;

    //Lists to iterate over whose turn it is
    private final String[] labelTurn = {"Blue Turn", "Green Turn", "Red Turn", "Yellow Turn"};
    private final String[] idTurn = {"blue", "green", "red", "yellow"};

    //The list below are the Row, Column coordinates of the start positions for each color, in the order above
    private final int[] pawnStartRow = {1, 14, 11, 4 };
    private final int[] pawnStartColumn = {4, 11, 1, 14};
    /*
    The list below are the Row, Column coordinates of each color, in the order above, AFTER moved from start position (when it begins counting)
    These are the same coordinates for when a one initially moves a pawn from start
     */
    private final int[] pawnInitialRow = {0, 15, 11, 4};
    private final int[] pawnInitialColumn = {4, 11, 0, 15};

    private final int[] pawnHomeRow = {0, 15, 13, 2};
    private final int[] pawnHomeColumn = {2, 13, 0, 15};

    //IMPORTANT NOTE: Since the position index orders line up with the turn orders, we can simply use the same counter to locate the position within list.

    //Counter variable to tell iterate through list and determine the whose turn it is.
    private int counter = 0;

    public int blueCounter = 0;
    public int redCounter = 0;
    public int greenCounter = 0;
    public int yellowCounter = 0;



    @FXML
    void onCardClicked(MouseEvent event) {
        card = (int) (Math.random() * 12 + 1);
        // Pick random card from 1 to 13; 1-12 are numbers, 13 is the Sorry card
        if (canDraw) {
            canDraw = false;
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/" +
                    card + ".png"))));
            drawLabel.setText("");
            // changes the instruction label to whichever card is drawn
            String labelText = switch (card) {
                case 1 -> "Click a pawn to move forward one space.";
                case 2 -> "Click a pawn to move forward two spaces.";
                case 3 -> "Click a pawn to move forward three spaces.";
                case 4 -> "Click a pawn to move back four spaces";
                case 5 -> "Click a pawn to move forward five spaces";
                case 6 -> "Click a pawn to move back three space";
                case 7 -> "Click a pawn to move forward seven spaces";
                case 8 -> "Click a pawn to move eight spaces forward";
                case 9 -> "Click a pawn to move five spaces back";
                case 10 -> "Click a pawn to move ten spaces forward";
                case 11 -> "Click a pawn to move ten spaces backwards";
                case 12 -> "Click a pawn to move twelve spaces forward";
                default -> "";
            };
            instructionsLabel.setText(labelText);
        }
    }

    @FXML
    void onPieceClick(MouseEvent event) {
        //Gets id of the piece clicked
        ImageView pieceClicked = (ImageView) event.getSource();
        String id = pieceClicked.getId();
        String currentTurn = turnLabel.getText();
        //get row and column of piece clicked
        int row = GridPane.getRowIndex(pieceClicked);
        int col = GridPane.getColumnIndex(pieceClicked);
        //Determines whose turn it is (if the correct piece is being clicked)
        if (currentTurn.equals(labelTurn[counter]) && id.startsWith(idTurn[counter])) {
            //Moves the correctly colored piece to the correct space determined by the card the user pulled
            if (row == pawnStartRow[counter] && col == pawnStartColumn[counter]) {
                if (card == 1 || card == 2 || card == 3) {
                    GridPane.setRowIndex(pieceClicked, pawnInitialRow[counter]);
                    GridPane.setColumnIndex(pieceClicked, pawnInitialColumn[counter]);
                } else {
                    instructionsLabel.setText("Can only move pawn from start with 1,2 or 3; turn skipped");
                    turnLabel.setText(labelTurn[counter]);
                    drawLabel.setText("Draw a card");
                }
            } else {
                int[] newPosition;
                if (card == 4 || card == 6 || card == 9 || card == 11) {
                    int newCard = switch(card){
                        case 4 -> 4;
                        case 6 -> 3;
                        case 9 -> 5;
                        case 11 -> 10;
                        default -> 1;
                    };
                    newPosition = pieceMoverBackward(newCard, row, col);
                } else {
                    newPosition = pieceMoverForward(card, row, col, counter, pieceClicked);
                }

                // Check if piece is in the new space for bump back to home
                ImageView node = getPieceInBoard(newPosition[0], newPosition[1]);
                if (node != null) {
                    if (node.getId().startsWith("blue")) {
                        GridPane.setRowIndex(node, 1);
                        GridPane.setColumnIndex(node, 4);
                        System.out.println("Bumped blue piece back to home");
                    } else if (node.getId().startsWith("green")) {
                        GridPane.setRowIndex(node, 14);
                        GridPane.setColumnIndex(node, 11);
                        System.out.println("Bumped green piece back to home");
                    } else if (node.getId().startsWith("yellow")) {
                        GridPane.setRowIndex(node, 4);
                        GridPane.setColumnIndex(node, 14);
                        System.out.println("Bumped yellow piece back to home");
                    } else if (node.getId().startsWith("red")) {
                        GridPane.setRowIndex(node, 11);
                        GridPane.setColumnIndex(node, 1);
                        System.out.println("Bumped red piece back to home");
                    }
                }

                GridPane.setRowIndex(pieceClicked, newPosition[0]);
                GridPane.setColumnIndex(pieceClicked, newPosition[1]);
            }
            canDraw = true;
            counter = (counter + 1) % 4;
            turnLabel.setText(labelTurn[counter]);
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/back.png"))));
            checkWin();
        }
    }


    public int[] pieceMoverForward(int card, int row, int col, int counter, ImageView pieceClicked) {
        int[] position;
        if (row == 0) {
            for (int i = 0; i < card; i++) {
                if (counter == 0){
                    if (col == 2) {
                        System.out.println("Blue home");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (counter == 3){
                    if (row == 2) {
                        System.out.println("yellow home in corner");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (col == 15) {
                    row = row + 1;
                } else {
                    col = col + 1;
                }
            }
            return new int[]{row, col};
        }
        else if (col == 15) {
            for (int i = 0; i < card; i++) {
                if (counter == 3){
                    if (row == 2) {
                        System.out.println("yellow home");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (counter == 1) {
                    if (col == 13) {
                        System.out.println("green home in corner");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (row == 15) {
                    col = col - 1;
                } else {
                    row = row + 1;
                }
            }
            return new int[]{row, col};
        }
        else if (row == 15) {
            for (int i = 0; i < card; i++) {
                if (counter == 1) {
                    if (col == 13) {
                        System.out.println("green home");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (counter == 2){
                    if (row == 13) {
                        System.out.println("red home in corner");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (col == 0) {
                    row = row - 1;
                } else {
                    col = col - 1;
                }
            }
            return new int[]{row, col};
        }
        else if (col == 0) {
            for (int i = 0; i < card; i++) {
                if (counter == 2){
                    if (row == 13) {
                        System.out.println("red home");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (counter == 0){
                    if (col == 2) {
                        System.out.println("blue home in corner");
                        position = pieceMoverToHome(counter, card - i, row, col, pieceClicked);
                        return new int[]{position[0], position[1]};
                    }
                }
                if (row == 0) {
                    col = col + 1;
                } else {
                    row = row - 1;
                }
            }
            return new int[]{row, col};
        }else{
            position = pieceMoverToHome(counter, card, row, col, pieceClicked);
            return new int[]{position[0], position[1]};
        }
    }
    public int[] pieceMoverBackward(int card, int row, int col) {
        int[] position;
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
    public int[] pieceMoverToHome(int counter, int card, int row, int col, ImageView pieceClicked) {
        if (counter == 0) { // blue
            if (row + card == 6) {
                row = 6;
                pieceClicked.setDisable(true);
                blueCounter += 1;
            } else {
                for (int p = 0; p < card; p++) {
                    row = row + 1;
                    if (row == 6) {
                        if (card - p == 1) {
                            pieceClicked.setDisable(true);
                            blueCounter += 1;
                        } else {
                            row = 1;
                            p++;
                        }
                    }
                }
            }
            return new int[]{row, col};
        }
        if (counter == 1) { // green
            if (row - card == 9) {
                row = 9;
                pieceClicked.setDisable(true);
                greenCounter += 1;
            } else {
                for (int p = 0; p < card; p++) {
                    row = row - 1;
                    if (row == 9) {
                        if (card - p == 1) {
                            pieceClicked.setDisable(true);
                            greenCounter += 1;
                        } else {
                            row = 14;
                            p++;
                        }
                    }
                }
            }
            return new int[]{row, col};
        }
        if (counter == 2) { // red
            if (col + card == 6) {
                col = 6;
                pieceClicked.setDisable(true);
                redCounter += 1;
            } else {
                for (int p = 0; p < card; p++) {
                    col = col + 1;
                    if (col == 6) {
                        if (card - p == 1) {
                            pieceClicked.setDisable(true);
                            redCounter += 1;
                        } else {
                            col = 1;
                            p++;
                        }
                    }
                }
            }
            return new int[]{row, col};
        }
        if (counter == 3) { //yellow
            if (col - card == 9) {
                col = 9;
                pieceClicked.setDisable(true);
                yellowCounter += 1;
            } else {
                for (int p = 0; p < card; p++) {
                    col = col - 1;
                    if (col == 9) {
                        if (card - p == 1) {
                            pieceClicked.setDisable(true);
                            yellowCounter += 1;
                        } else {
                            col = 14;
                            p++;
                        }
                    }
                }
            }
        }
            return new int[]{row, col};
        }

    public ImageView getPieceInBoard(int row, int col) {
        // Check if the given row and column is a home spot
        if (row == 6 && col == 2 ||
            row == 2 && col == 9 ||
            row == 13 && col == 6 ||
            row == 9 && col == 13)
            return null;

        ObservableList<Node> children = gridPane.getChildren();
        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                if (node instanceof ImageView) {
                    return (ImageView) node;
                }
            }
        }
        return null;
    }
    // check for a win
    public void checkWin(){
        //when counter for amount of pieces hits 4 then that color wins
        if (blueCounter == 4){
            // set text to color that wins
            turnLabel.setText("Blue Wins");
            //when a color wins creates an alert with the winner
            Alert win = new Alert(Alert.AlertType.CONFIRMATION);
            win.setTitle("Blue Wins");
            win.setContentText("Blue Wins");
            win.showAndWait();
            System.exit(0); // exits the program
        }
        if (greenCounter == 4){
            turnLabel.setText("Green Wins");
            Alert win = new Alert(Alert.AlertType.CONFIRMATION);
            win.setTitle("Green Wins");
            win.setContentText("Green Wins");
            win.showAndWait();
            System.exit(0);

        }
        if (redCounter == 4){
            turnLabel.setText("Red Wins");
            Alert win = new Alert(Alert.AlertType.CONFIRMATION);
            win.setTitle("Red Wins");
            win.setContentText("Red Wins");
            win.showAndWait();
            System.exit(0);
        }
        if (yellowCounter == 4){
            turnLabel.setText("Yellow Wins");
            Alert win = new Alert(Alert.AlertType.CONFIRMATION);
            win.setTitle("Yellow Wins");
            win.setContentText("Yellow Wins");
            win.showAndWait();
            System.exit(0);
        }
    }

    public void initialize() {
        // sets the background of the board
        gridPane.setBackground(new Background(
                new BackgroundImage(new Image(String.valueOf(getClass().getResource("Board.png"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));
    }
}
