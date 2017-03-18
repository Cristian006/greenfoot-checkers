 
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class CheckersControl here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CheckersControl extends Actor
{
    
    private final int  halfWidth = 50;
    
    /**
     * Act - do whatever the CheckersControl wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    private final int boardWidth;
    private final int boardLength;
    private static Checkers [][] checkAry;
    private static int [][] yellowPos = new int[16][2];
    private static int numOfYel = 0;
    private boolean selected = false;
    private int selPosX;
    private int selPosY;
    
    public CheckersControl(int length, int width, int playerRows){
        checkAry = new Checkers[length][width];
        boardWidth = width * 2;
        boardLength = length;
 
        // Create board
        for (int i = 0; i < length; i++){
            if (i < playerRows ){
                for (int k = 0; k < width; k++){
                    if ((k + i) % 2 > 0){
                        checkAry[i][k/2] = new Checkers(true);
                        ((MyWorld)getWorld()).addCheckers((( (i + 1) % 2 + (k * 2) ) * (halfWidth * 2))
                            + halfWidth, (i * (halfWidth * 2)) + halfWidth, checkAry[i][k/2]);
                    }
                }
            } else if (i >= length - playerRows){
                for (int k = 0; k < width; k++){
                    if ((k + i) % 2 > 0){
                        checkAry[i][k/2] = new Checkers(false);
                        ((MyWorld)getWorld()).addCheckers(( (i + 1) % 2 + (k * 2) ), i, checkAry[i][k/2]);
                    }
                }
            }
        }
    }
    
    public void act() 
    {
        MouseInfo currentMouse = Greenfoot.getMouseInfo();
        // If mouse is clicked
        if (currentMouse.getButton() == 1){
            int posX = currentMouse.getX();
            int posY = currentMouse.getY();
            if (selected = true){
                if (checkYellow(posX, posY)){
                    //Moving Checkers piece
                    checkAry[posY/(halfWidth * 2)][posX/(halfWidth * 4)] = new Checkers(
                        checkAry[selPosY/(halfWidth * 2)][selPosX/(halfWidth * 4)]);//,
                        //(selPosX - (selPosX % (halfWidth * 2))) + halfWidth,
                        //(selPosY - (selPosY % (halfWidth * 2))) + halfWidth);
                    checkAry[selPosY/(halfWidth * 2)][selPosX/(halfWidth * 4)] = null;
                }
                
                removeYellow();
                selPosX = -1;
                selPosY = -1;
                selected = false;
            }
            else {

                if (checkPos(posX, posY)){
                    if (checkForward(posX, posY,
                        checkAry[posY/(halfWidth * 2)][posX/(halfWidth * 4)].getUpFacing(),
                        checkAry[posY/(halfWidth * 2)][posX/(halfWidth * 4)].getUpgraded())){
                        selected = true;
                        selPosX = posX;
                        selPosY = posY;
                    } 
                } 
            }
                
            
        }
    }
    
    public boolean checkYellow(int posX, int posY){ // Given in mouse pos
        int tempPosY = (posY/ (halfWidth * 2));
        int tempPosX = (posX/ (halfWidth * 4)) + ((1 + posY) % 2);
        
        for (int i = 0; i < numOfYel; i++){
            if (yellowPos[i][0] == tempPosX && yellowPos[i][1] == tempPosY) {   return true;    }
        }
        return false;
    }
    
    public boolean checkPos(int posX, int posY){ // Given in mouse pos
        posX = posX/(halfWidth * 2);
        posY = posY/(halfWidth * 2);
        
        if ((posX + posY) % 2 > 0){
            if (checkAry[posY][(posX / 2)] != null && 
                (posX + posY)% 2 > 0) { return true; }
            
            else { return false; }
            
        } else { return false; }
            
    }

    public boolean checkForward(int posX,
        int posY, boolean facing, boolean upgraded){ // Given in mouse pos
        
        int arPosX = posX/(halfWidth * 4);
        int arPosY = posY/(halfWidth * 2);
        boolean hasDest = false;
        //boolean facing = checkAry[arPosY][arPosX].upFacing;
        //boolean upgraded = checkAry[arPosY][arPosX].upgraded;
        
        // Start going up     !!! Assuming that up is toward 0
        if ((facing || upgraded) && arPosY > 1){
            // Check left
            if (posX > 100){
                if (checkPos(posX - 100, posY - 100)){
                    if (posX > 200 && !checkPos(posX - 200, posY - 200)){
                        if (checkForward(posX - 200, posY - 200, facing, upgraded)){
                            hasDest = true;
                        }else { 
                            createYellow(arPosX, arPosY);
                            hasDest = true;
                        }
                    }
                }else {
                    createYellow(arPosX, arPosY);
                    hasDest = true;
                }
            }
            // Check right
            if (posX < (boardWidth - 1) * 100){
                if (checkPos(posX + 100, posY - 100)){
                    if (posX < (boardWidth - 2) * 100 && !checkPos(posX + 200, posY - 200)){
                        if (checkForward(posX + 200, posY - 200, facing, upgraded)){
                            hasDest = true;
                        }else { 
                            createYellow(arPosX, arPosY);
                            hasDest = true;
                        }
                    }
                }else {
                    createYellow(arPosX, arPosY);
                    hasDest = true;
                }
            }
        }
        if ((!facing || upgraded) && arPosY < boardLength - 2){
            // check Left
            if (posX > 100){
                if (checkPos(posX - 100, posY + 100)){
                    if (posX > 200 && !checkPos(posX - 200, posY + 200)){
                        if (checkForward(posX - 200, posY + 200, facing, upgraded)){
                            hasDest = true;
                        }else { 
                            createYellow(arPosX, arPosY);
                            hasDest = true;
                        }
                    }
                }else {
                    createYellow(arPosX, arPosY);
                    hasDest = true;
                }
            }
            // Check right
            if (posX < (boardWidth - 1) * 100){
                if (checkPos(posX + 100, posY + 100)){
                    if (posX < (boardWidth - 2) * 100 && !checkPos(posX + 200, posY + 200)){
                        if (checkForward(posX + 200, posY + 200, facing, upgraded)){
                            hasDest = true;
                        }else { 
                            createYellow(arPosX, arPosY);
                            hasDest = true;
                        }
                    }
                }else {
                    createYellow(arPosX, arPosY);
                    hasDest = true;
                }
            }
        }
        return hasDest;
    }
    
    public void removeYellow(){
        numOfYel = 0;
        ((MyWorld)getWorld()).removeYellow();
    }
    
    public void createYellow(int posX, int posY){ // Given in array pos
        ((MyWorld)getWorld()).addYellow(posX, posY); // Give array pos
        numOfYel++;
    }
}
