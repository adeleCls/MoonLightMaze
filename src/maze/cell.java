/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.util.Arrays;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *This class represents a single cell of the board.
 * 
 * @author adele
 */
public class cell {

    public Boolean[] wall;

    public ImageView[] images;
    public Boolean ennemie;
    public Boolean tresure;

    /**
     * Constructor
     * @param b1 presence of a top wall
     * @param b2 presence of a left wall
     * @param b3 presence of a bottom wall
     * @param b4 presence of a right wall
     * @param b5 presence of an enemy
     * @param b6 presence of a treasure
     */
    public cell(Boolean b1, Boolean b2, Boolean b3, Boolean b4, Boolean b5, Boolean b6) {
        this.wall = new Boolean[4];
        this.images = new ImageView[4];
        this.wall[0] = b1;


        this.wall[1] = b2;


        this.wall[2] = b3;

        this.wall[3] = b4;
        this.ennemie=b5;
        this.tresure=b6;
                

        putImage();

    }

    /**
     * Simple constructor
     */
    public cell() {
        this.wall = new Boolean[4];
        this.images = new ImageView[4];
        this.ennemie = false;
        this.tresure=false;
        
        
        for (int i = 0; i < 4; i++) {
            Random rand = new Random();
            int x = rand.nextInt(10);
            if (x > 6) {
                this.wall[i] = true;
         

            } else {
                this.wall[i] = false;
         
                
            }

        }
        putImage();

    }
    /**
     * check if a cell is completely surrounded by walls
     * @return true if the cell is surrounded
     */
    public Boolean completeWall(){
        if (this.wall[0]==true & this.wall[1]==true & this.wall[2]==true & this.wall[3]==true){
        return true;
    }
        else{
            return false;
        }
        
    }

    /**
     * display the image of a cell
     */
    public void putImage() {
        Image imageOdd = new Image("/wallimpair.png", 13, 50, false, false);
        Image imageEven = new Image("/wallpair.png", 70, 13, false, false);
        Image blankOdd = new Image("/notWallImpair.png", 13,50, false, false);
        Image blankEven = new Image("/notWallPair.png", 70, 13, false, false);
        if (wall[0] == true) {
            images[0] = new ImageView();
            images[0].setImage(imageEven);
        } else {
            images[0] = new ImageView();
            images[0].setImage(blankEven);
        }
        if (wall[1] == true) {
            images[1] = new ImageView();
            images[1].setImage(imageOdd);
        } else {
            images[1] = new ImageView();
            images[1].setImage(blankOdd);
        }
        if (wall[2] == true) {
            images[2] = new ImageView();
            images[2].setImage(imageEven);
        } else {
            images[2] = new ImageView();
            images[2].setImage(blankEven);
        }
        if (wall[3] == true) {
            images[3] = new ImageView();
            images[3].setImage(imageOdd);
        } else {
            images[3] = new ImageView();
            images[3].setImage(blankOdd);
        }

    }
    


}
