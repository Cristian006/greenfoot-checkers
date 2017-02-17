import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.awt.Color;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 800, 1); 
        makeBoard();
        List<RedSquare> redsq = getObjects(RedSquare.class);
    }
    
    private void makeBoard()
    {
        int i, j;
        // red square
        for(j = 50; j < 800; j+= 200)
        {
            for(i = 50; i < 800; i+=200)
            {
                addObject(new RedSquare(), i, j);
                
            }
        }
        
        for(j = 150; j < 800; j+= 200)
        {
            for(i = 150; i < 800; i+= 200)
            {
                addObject(new RedSquare(), i, j);
            }
        }
        
        // black square
        for(j = 50; j < 800; j+= 200)
        {
            for(i = 150; i < 800; i+= 200)
            {
                addObject(new BlackSquare(), i, j);
            }
        }
        
        for(j = 150; j < 800; j+= 200)
        {
            for(i = 50; i < 800; i+= 200)
            {
                addObject(new BlackSquare(), i, j);
            }
        }
        
    }
}
