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
            if (card == 1){
                if (row == 1 && col == 4){ //blue
                    GridPane.setColumnIndex(pieceClicked, col);
                    GridPane.setRowIndex(pieceClicked, 0);
                }
                if (row == 4 && col == 14){ //yellow
                    GridPane.setColumnIndex(pieceClicked, col + 1);
                    GridPane.setRowIndex(pieceClicked, row);
                }
                if (row == 11 && col == 1){ //red
                    GridPane.setColumnIndex(pieceClicked, 0);
                    GridPane.setRowIndex(pieceClicked, row);
                }
                if (row == 14 && col == 11){ //green
                    GridPane.setColumnIndex(pieceClicked, col);
                    GridPane.setRowIndex(pieceClicked, row + 1);
                }
                if (row == 0 && col != 15){
                    GridPane.setColumnIndex(pieceClicked, col + 1);
                }
                if (row == 15 && col != 0){
                    GridPane.setColumnIndex(pieceClicked, col - 1);
                }
                if (col == 0 && row != 0){
                    GridPane.setRowIndex(pieceClicked, row - 1);
                }
                if (col == 15 && row != 15){
                    GridPane.setRowIndex(pieceClicked, row + 1);
                }

            }
            if (card == 2){
                if (row == 1 && col == 4){ //blue
                    GridPane.setColumnIndex(pieceClicked, 5);
                    GridPane.setRowIndex(pieceClicked, 0);
                }
                if (row == 4 && col == 14){ //yellow
                    GridPane.setColumnIndex(pieceClicked, 15);
                    GridPane.setRowIndex(pieceClicked, 5);
                }
                if (row == 11 && col == 1){ //red
                    GridPane.setColumnIndex(pieceClicked, 0);
                    GridPane.setRowIndex(pieceClicked, 10);
                }
                if (row == 14 && col == 11){ //green
                    GridPane.setColumnIndex(pieceClicked, 10);
                    GridPane.setRowIndex(pieceClicked, 15);
                }

            }
            if (card == 3){
                if (row == 1 && col == 4){ //blue
                    GridPane.setColumnIndex(pieceClicked,6);
                    GridPane.setRowIndex(pieceClicked, 0);
                }
                if (row == 4 && col == 14){ //yellow
                    GridPane.setColumnIndex(pieceClicked, 15);
                    GridPane.setRowIndex(pieceClicked, 6);
                }
                if (row == 11 && col == 1){ //red
                    GridPane.setColumnIndex(pieceClicked, 0);
                    GridPane.setRowIndex(pieceClicked, 9);
                }
                if (row == 14 && col == 11){ //green
                    GridPane.setColumnIndex(pieceClicked, 9);
                    GridPane.setRowIndex(pieceClicked, 15);
                }

            }
            if (card == 4){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 5){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 6){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            //if (card == 7){

            //}
            if (card == 8){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 9){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 10){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 11){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }

            }
            if (card == 12){
                if (row == 1 && col == 4){ //blue
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 4 && col == 14){ //yellow
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 11 && col == 1){ //red
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
                if (row == 14 && col == 11){ //green
                    instructionsLabel.setText("In start and only move with 1,2 or 3 Skip turn");
                    turnLabel.setText(labelTurn[counter]);
                }
            }
            //if (card == 13){

            //}
            canDraw = true;
            counter = (counter + 1) % 4;
            turnLabel.setText(labelTurn[counter]);
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/back.png"))));
        }
    }

    public void checkWin(){
    }

    public void initialize() {
        gridPane.setBackground(new Background(
                new BackgroundImage(new Image(String.valueOf(getClass().getResource("Board.png"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));



    }

}
