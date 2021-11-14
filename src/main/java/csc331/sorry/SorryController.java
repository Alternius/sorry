package csc331.sorry;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class SorryController {

    @FXML
    private ImageView cardImage;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label turnLabel;

    @FXML
    private Label instructionsLabel;

    private boolean canDraw = true; // Temporary - set to true on initialization but false once a card is drawn

    @FXML
    void onCardClicked(MouseEvent event) {
        // Pick random card from 1 to 13; 1-12 are numbers, 13 is the Sorry card
        if (canDraw) {
            canDraw = false;
            int card = (int) (Math.random() * 13 + 1);
            cardImage.setImage(new Image(String.valueOf(getClass().getResource("cards/" +
                    (card == 13 ? "Sorry" : card) + ".png"))));

            String labelText = "";
            switch (card) {
                case 1:
                    labelText = "Click a pawn to move forward one space.";
                    break;
                case 2:
                    labelText = "Click a pawn to move forward two spaces.";
                    break;
                case 3:
                    labelText = "Click a pawn to move forward three spaces.";
                    break;
                case 4:
                    labelText = "Click a pawn to move back four spaces";
                    break;
                case 5:
                    labelText = "Click a pawn to move forward five spaces";
                    break;
                case 6:
                    labelText = "Click a pawn to move back three space";
                    break;
                case 7:
                    labelText = "Click a pawn to move 1-7 spaces forward";
                    break;
                case 8:
                    labelText = "Click a pawn to move eight spaces forward";
                    break;
                case 9:
                    labelText = "Click a pawn to move five spaces back";
                    break;
                case 10:
                    labelText = "Click a pawn to move ten spaces forward";
                    break;
                case 11:
                    labelText = "Click a pawn to move ten spaces backwards";
                    break;
                case 12:
                    labelText = "Click a pawn to move twelve spaces forward";
                    break;
                case 13:
                    labelText = "Click pawn from start to opponent pawn space";
                    break;
            }
            instructionsLabel.setText(labelText);
        }
    }

    @FXML
    void onPieceClick(MouseEvent event) {
        
    }

    public void initialize() {
        gridPane.setBackground(new Background(
                new BackgroundImage(new Image(String.valueOf(getClass().getResource("Board.png"))),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));
    }

}
