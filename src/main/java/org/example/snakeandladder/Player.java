package org.example.snakeandladder;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Player
{
    private final Circle coin;
    private final String name;
    private int currentPosition;
    private static final Board gameBoard=new Board();

    public Player(int tileSize, Color coinColor, String playerName)
    {
        coin=new Circle(tileSize/2.0);
        coin.setFill(coinColor);
        currentPosition=0;
        movePlayer(1);
        name=playerName;
    }
    public void movePlayer(int diceValue)
    {
       if(currentPosition+diceValue<=100)
        {
            currentPosition+=diceValue;
            TranslateTransition secondMove=null, firstMove = translateAnimation(diceValue);

            int newPosition=gameBoard.getNewPosition(currentPosition);
            if(newPosition!=currentPosition && newPosition!=-1)
            {
                currentPosition=newPosition;
                secondMove =translateAnimation(6);
            }
            if(secondMove==null)
            {
                firstMove.play();
            }
            else
            {
                SequentialTransition sequentialTransition=new SequentialTransition(firstMove,new PauseTransition(Duration.millis(500)),secondMove);
                sequentialTransition.play();
            }
        }
//        int x=gameBoard.getXCoordinates(currentPosition);
//        int y=gameBoard.getYCoordinates(currentPosition);
//        coin.setTranslateX(x);
//        coin.setTranslateY(y);
    }
    private TranslateTransition translateAnimation(int diceValue)
    {
        TranslateTransition animate=new TranslateTransition(Duration.millis(diceValue*200),coin);
        animate.setToX(gameBoard.getXCoordinates(currentPosition));
        animate.setToY(gameBoard.getYCoordinates(currentPosition));
        animate.setAutoReverse(false);
        return animate;
    }
    public void startingPosition()
    {
        currentPosition=0;
        movePlayer(1);
    }
    boolean isWinner()
    {
        return currentPosition == 100;
    }
    //Getters
    public Circle getCoin() {
        return coin;
    }
    public String getName() {
        return name;
    }
    public int getCurrentPosition() {
        return currentPosition;
    }
}
