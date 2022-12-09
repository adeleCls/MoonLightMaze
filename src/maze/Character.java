 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *The Character class represents a character , with all the images,
 * and its characteristics such as speed, name etc
 * 
 * @author adele
 */
public class Character {

    private Image presIm;
    private Image moveIm;
    private int speed;
    private int num;
    private String name;

    /**
     * Character constructor
     * @param face The image of the character not moving
     * @param mov the image of the character moving
     * @param s the speed of the character
     * @param n the index of the character
     * @param nom its name
     */
    public Character(Image face, Image mov, int s, int n, String nom) {
        this.presIm=face;
        this.num=n;
        this.moveIm= mov;
        this.name=nom;
        this.speed=s;
    }
    
    /**
     * 
     * @param n the index of the character
     * @param liste List of all characters
     * @return An instance of Character corresponding to the selected char
     */
    public Character getCharacterFromNum(int n, Character[] liste){
        for(int i=0; i<liste.length; i++){
            if (n==liste[i].getNum()){
                return liste[i];
            }
        }
        return null;
    }

    /**
     * @return the presIm
     */
    public Image getPresIm() {
        return presIm;
    }

    /**
     * @param presIm the presIm to set
     */
    public void setPresIm(Image presIm) {
        this.presIm = presIm;
    }

    /**
     * @return the moveIm
     */
    public Image getMoveIm() {
        return moveIm;
    }

    /**
     * @param moveIm the moveIm to set
     */
    public void setMoveIm(Image moveIm) {
        this.moveIm = moveIm;
    }

    /**
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
   
    
}
