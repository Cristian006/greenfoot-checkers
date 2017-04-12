import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CheckerBoard extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    private final int halfWidth = 50;
    private final int fullWidth = 100;
    private CheckersControl mainControl;
    private final int numberOfSquaresAcrossTheBoard  = 8;
    private final int numberOfSquaresDownTheBoard  = 8;
    private final int numberOfRowsToPutCheckersOn  = 3;
    private static CheckerPiece [][] checkAry;
    //private vars
    private boolean isRedTurn = false;
    private CheckerPiece selectedPiece = null;
    private ScoreBoard scoreBoard = null;

    ArrayList<Vector2> movableVectors = new ArrayList<Vector2>();
        
    public CheckerBoard()
    {    
        super(800, 800, 1); 

        
        putRedPiecesOnTheBoard();
        putBlackPiecesOnTheBoard();
    }

    public void putRedPiecesOnTheBoard()
    {
        int xLocation;
        int yLocation; 

        checkAry = new CheckerPiece[numberOfSquaresDownTheBoard][numberOfSquaresAcrossTheBoard  / 2];
        for (int i = 0; i < numberOfRowsToPutCheckersOn ; i++){
            for (int k = 0; k < numberOfSquaresAcrossTheBoard  / 2; k++){
                xLocation = ( ( ( (i + 1) % 2 ) + (k * 2) ) * (halfWidth * 2)) + halfWidth;
                yLocation = (i * (halfWidth * 2)) + halfWidth;
                
                checkAry[i][k] = new RedPiece(xLocation, yLocation);
                addObject(checkAry[i][k], xLocation, yLocation);  
            }
        }    
    }

    public void putBlackPiecesOnTheBoard()
    {
        // black needs to set values in the constructor l like the red does.
        int xLocation;
        int yLocation;
        int rowsForBlackCheckers  = numberOfSquaresDownTheBoard  - numberOfRowsToPutCheckersOn ;
        checkAry = new CheckerPiece[numberOfSquaresDownTheBoard][numberOfSquaresAcrossTheBoard  / 2];

        for (int i = rowsForBlackCheckers; i < numberOfSquaresDownTheBoard; i++){
            for (int k = 0; k < numberOfSquaresAcrossTheBoard  / 2; k++){
                xLocation = ( ( (i + 1) % 2 + (k * 2) ) * halfWidth * 2) + halfWidth;
                yLocation = (i * halfWidth * 2) + halfWidth;

                checkAry[i][k] = new BlackPiece(xLocation, yLocation);
                addObject(checkAry[i][k], xLocation , yLocation);
            }
        }
    }
    /*
    private ArrayList<AvailableLocation > getAllValidLocationsOhCheckerBoard()
    {
        ArrayList<AvailableLocation> locations = new ArrayList<AvailableLocation>();

        for(int x = 150; x <= 750; x += 200)
        {
            for(int y = 50; y <= 650; y += 200)
            {  
                locations.add(new AvailableLocation (x,y));
            }
        }

        for(int x = 50; x <= 650; x += 200)
        {
            for(int y = 150; y <= 750; y += 200)
            {    
                locations.add(new AvailableLocation (x,y));
            }
        }

        return locations ;
    }*/

    //is the current turn red
    public boolean getIsRedTurn(){
        return isRedTurn;
    }

    //sets is red turn to whatever the opposite is
    public void switchTurns(){
        isRedTurn = !isRedTurn;
    }

    //Returns whether the checker board has a currently selected piece
    public boolean hasSelectedCheckerPiece(){
        return (selectedPiece != null);
    }

    //Set the currently selected piece
    public void setSelected(CheckerPiece piece){
        //if the piece the player is trying to select is part of whoever's turn it is
        //then select the piece
        if(isRedTurn && piece.isRed()){
            unSelect();
            System.out.println("SELECTING RED");
            selectedPiece = piece;
            selectedPiece.selectPiece();
            setMovableSquares();
        }
        else if(!isRedTurn && !piece.isRed()){
            unSelect();
            System.out.println("SELECTING BLACK");
            selectedPiece = piece;
            selectedPiece.selectPiece();
            setMovableSquares();
        }
        else{
            System.out.println("CAN'T MOVE THAT OBJECT!");
            //don't do anything
            //the sent in piece is invalid
            //maybe unselect only????
            //UnSelect();
        }
    }

    //Create Movable Path for checker piece
    public void setMovableSquares(){
        System.out.println("SETTING MOVABLESQUARES");
        movableVectors = new ArrayList<Vector2>();
        int curX = selectedPiece.getX();
        int curY = selectedPiece.getY();
        
        //populate array list recursively with vector2's of available spaces
        getMovableSquares(curX, curY);
        
        for(int i = 0; i < movableVectors.size(); i++){
            //set yellow squares in world
            addYellow(movableVectors.get(i).getX(), movableVectors.get(i).getY());
            
        }
    }
    
    public void getMovableSquares(int x, int y){
        
        getMovableSquaresInDirection(true, true, x, y);
        
        getMovableSquaresInDirection(true, false, x, y);
        
        getMovableSquaresInDirection(false, true, x, y);
        
        getMovableSquaresInDirection(false, false, x, y);
        
        //if(checkPos(x-100, y-100)){
            //movableVectors.add(new Vector2(curX-100, curY-100));
        //}
        //getMovableSquares(dslkfjalkjfa.dfsaojahfla_)
    }
    
    public void getMovableSquaresInDirection(boolean posXDir, boolean posYDir, int x, int y){
        int xMovementSize = posXDir ? fullWidth : -fullWidth;
        int yMovementSize = posYDir ? fullWidth : -fullWidth;
        //check if we're in bounds
        if ((posXDir && x < ( ( fullWidth * numberOfSquaresAcrossTheBoard) - fullWidth ))||
            (!posXDir && x > fullWidth ) ||
            (posYDir && x < ( ( fullWidth * numberOfSquaresAcrossTheBoard) - fullWidth ) ||
            (!posYDir && y > fullWidth ))){
            
            if( checkPos(x + xMovementSize, y + yMovementSize)){
                movableVectors.add(new Vector2(x + xMovementSize, y + yMovementSize));
            }else{
                
                //if(
                
            }
        }
        
    }
    
    public boolean checkPos(int posX, int posY){ // Given in mouse pos
        posX = posX/(halfWidth * 2);
        posY = posY/(halfWidth * 2);
        //TODO: CHECK IF OUT OF BOUNDS
        if ((posX + posY) % 2 > 0){
            if (checkAry[posY][(posX / 2)] == null && (posX + posY)% 2 > 0) 
            {
                //the spot is open
                return true;
            }
            else if(checkAry[posY][(posX / 2)] != null && (posX + posY)% 2 > 0)
            {
                return (isRedTurn != checkAry[posY][(posX/2)].isRed());
            }
            else{
                return false;
            }
        } 
        else 
        { 
            return false; 
        }
    }

    //Remove Movable Path for checker piece
    public void removeMovableSquares(){
        removeYellow();
    }

    //Un Select the currently selected piece
    public void unSelect(){
        if(hasSelectedCheckerPiece()){
            //unselect object
            System.out.println("UN SELECTING OBJECT");
            selectedPiece.resetPiece();
            removeMovableSquares();
            selectedPiece = null;
        }
        //else nothing we have to do.
    }

    public void addYellow(int posX, int posY){ // Given in array pos

        addObject(new PathTile(),
            (posX * (halfWidth * 2)) + halfWidth,
            (posY * (halfWidth * 2)) + halfWidth);

    }

    public void removeYellow(){
        removeObjects(getObjects(PathTile.class));   
    }

    public void addCheckers(int posX, int posY, Checkers newCheck){
        addObject(newCheck, posX, posY );
    }

    public void removeCheckers(Checkers remCheck){
        removeObject(remCheck);
    }

    private void makeScoreBoard(){
        scoreBoard = new ScoreBoard(isRedTurn);
    }
}
