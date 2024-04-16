package org.example.snakeandladder;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SnakeAndLadder extends Application
{
    public static final int tileSize=40,width=10,height=10;
    public static final int buttonLine=height*tileSize+50, infoLine= buttonLine-30;//just below last tile
    private static final Dice dice=new Dice();
    private Player player1,player2;
    private boolean gameStarted=false,playerOneTurn=false,playerTwoTurn=false;
    private Pane createContent()//starting point
    {
        Pane root=new Pane(); // -- blank  canvas
        root.setPrefSize(width*tileSize,height*tileSize+100);
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                Tile tile=new Tile(tileSize);
                tile.setTranslateX(j*tileSize);
                tile.setTranslateY(i*tileSize);
                root.getChildren().add(tile);
            }
        }
        Image img=new Image("C:\\Users\\yasha\\Desktop\\Yashas git\\Snake and Ladder\\SnakeAndLadder\\src\\main\\Snake ladder photo.jpg");
        ImageView board=new ImageView();
        board.setImage(img);
        board.setFitHeight(height*tileSize);
        board.setFitWidth(width*tileSize);

        //Buttons
        Button playerOneButton=new Button("Player 1");
        Button playerTwoButton=new Button("Player 2");
        Button startButton=new Button("Start");

        playerOneButton.setTranslateY(buttonLine);
        playerOneButton.setTranslateX(20);
        playerOneButton.setDisable(true);
        playerTwoButton.setTranslateY(buttonLine);
        playerTwoButton.setTranslateX(320);
        playerTwoButton.setDisable(true);
        startButton.setTranslateY(buttonLine);
        startButton.setTranslateX(180);

        //Info display
        Label playerOneLabel=new Label("");
        Label playerTwoLabel=new Label("");
        Label diceLabel=new Label("Press the Start Button");
        playerOneLabel.setTranslateY(infoLine);
        playerOneLabel.setTranslateX(20);
        playerTwoLabel.setTranslateY(infoLine);
        playerTwoLabel.setTranslateX(320);
        diceLabel.setTranslateY(infoLine);
        diceLabel.setTranslateX(140);

        // Create TextInputDialog for player 1 name
        TextInputDialog player1Dialog = new TextInputDialog();
        player1Dialog.setTitle("Player 1 Name");
        player1Dialog.setHeaderText(null);
        player1Dialog.setContentText("Enter Player 1 Name:");
        // Get player 1 name
        String player1Name = player1Dialog.showAndWait().orElse("");

        // Create TextInputDialog for player 2 name
        TextInputDialog player2Dialog = new TextInputDialog();
        player2Dialog.setTitle("Player 2 Name");
        player2Dialog.setHeaderText(null);
        player2Dialog.setContentText("Enter Player 2 Name:");
        // Get player 2 name
        String player2Name = player2Dialog.showAndWait().orElse("");

        // Create players with obtained names
        player1 = new Player(tileSize, Color.BLACK, player1Name);
        player2 = new Player(tileSize - 10, Color.WHITE, player2Name);
        //Player Action
        startButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                player1.startingPosition();
                player2.startingPosition();
                gameStarted=true;
                startButton.setDisable(true);
                diceLabel.setTranslateX(140);
                diceLabel.setText("Let The Games Begin!!");

                playerOneTurn=true;
                playerOneLabel.setText("Your Turn "+player1.getName());
                playerOneButton.setDisable(false);

                playerTwoTurn=false;
                playerTwoLabel.setText("");
                // Adjust playerTwoLabel position based on name length
                double playerNameWidth = new Text(player2Name).getBoundsInLocal().getWidth();
                double playerTwoLabelX = 320 - playerNameWidth;
                if (playerTwoLabelX < 280)
                {
                    playerTwoLabelX = 280; // Minimum X-coordinate
                }
                playerTwoLabel.setTranslateX(playerTwoLabelX);
                playerTwoButton.setDisable(true);
            }
        });
        playerOneButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if(gameStarted)
                {
                    if(playerOneTurn)
                    {
                        int diceValue = dice.getRolledDiceValue();//roll dice
                        diceLabel.setText("Dice Value :  " + diceValue);//rolled value
                        diceLabel.setTranslateX(160);
                        player1.movePlayer(diceValue);//moving
                        if(player1.isWinner())
                        {
                            diceLabel.setTranslateX(160);
                            diceLabel.setText("Winner is: " + player1.getName());

                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            playerOneTurn=false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted=false;
                        }
                        else
                        {
                            playerOneTurn = false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            playerTwoTurn = true;
                            playerTwoButton.setDisable(false);
                            playerTwoLabel.setText("Your Turn " + player2.getName());

                        }
                    }
                }

            }
        });
        playerTwoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if(gameStarted)
                {
                    if(playerTwoTurn)
                    {
                        int diceValue= dice.getRolledDiceValue();//roll dice
                        diceLabel.setText("Dice Value :  "+diceValue);//rolled value
                        diceLabel.setTranslateX(160);
                        player2.movePlayer(diceValue);//moving
                        //winning condition
                        if(player2.isWinner())
                        {
                            diceLabel.setTranslateX(160);
                            diceLabel.setText("Winner is: "+player2.getName());

                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            playerOneTurn=false;
                            playerOneButton.setDisable(true);
                            playerOneLabel.setText("");

                            startButton.setDisable(false);
                            startButton.setText("Restart");
                            gameStarted=false;
                        }
                        else
                        {
                            playerTwoTurn=false;
                            playerTwoButton.setDisable(true);
                            playerTwoLabel.setText("");

                            playerOneTurn=true;
                            playerOneButton.setDisable(false);
                            playerOneLabel.setText("Your Turn "+player1.getName());
                        }
                    }
                }
            }
        });

        root.getChildren().addAll(board,playerOneButton,startButton,playerTwoButton,
                playerOneLabel,playerTwoLabel,diceLabel,
                player1.getCoin(),player2.getCoin());
        return root;
    }
    @Override
    public void start(Stage stage)
    {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake & Ladder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}