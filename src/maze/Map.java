/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.util.Random;

/**
 *This class represents the board and the position of all elements on it.
 * 
 * @author adele
 */
public class Map {

    private int l;
    private int w;
    public cell[][] map;
    public int numberW;
    public int numberEnnemie;
    public int[] positioninit;

    /**
     * Constructor
     * @param numberEn The number of enemies on the map
     */
    public Map(int numberEn) {

        this.l = 9;
        this.w = 7;
        int[] pos = new int[2];
        pos[0] = 4;
        pos[1] = 3;
        this.positioninit = pos;
        this.numberEnnemie = numberEn;

        map = new cell[l][w];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
               
                map[i][j] = new cell();

            }
        }
        if(map[4][3].completeWall()){
            map[4][3].wall[1]=false;
            
        
    }

        numberW = this.countWall();
        this.checkEnnemie();
        this.checkTresure();

        //checkCorrect(this);
    }

    /**
     * Other constructor without the number of enemies
     */
    public Map() {
        this.l = 9;
        this.w = 7;
        map = new cell[l][w];
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                map[i][j] = new cell(false, false, false, false, false, false);

            }
        }

    }

    /**
     * Counts the wall in the map
     * @return the number of walls
     */
    public int countWall() {
        int number = 0;
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                for (int a = 0; a < 4; a++) {
                    if (map[i][j].wall[a] == true) {
                        number++;
                    }

                }
            }
        }
        return number;
    }

    /**
     * Checks if the map is legit (no treasure or enemy in the center, one treasure.)
     * @param mp an instance of the map
     */
    public void checkCorrect(Map mp) {
        int l = mp.getL();
        int w = mp.getW();
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < w; j++) {
                cell square = map[i][j];

                if (i != l - 1 && i != 0 && j != 0 && j != w - 1) {
                    if (square.wall[0] == true) {
                        map[i - 1][j].wall[2] = false;

                    }
                    if (square.wall[1] == true) {
                        map[i][j + 1].wall[3] = false;

                    }

                    if (square.wall[2] == true) {
                        map[i + 1][j].wall[0] = false;

                    }

                    if (square.wall[3] == true) {
                        map[i][j - 1].wall[1] = false;

                    }

                } else if (j != w - 1) {
                    if (square.wall[1] == true) {
                        map[i][j + 1].wall[3] = false;

                    }

                } else if (i != l - 1) {
                    if (square.wall[2] == true) {
                        map[i + 1][j].wall[0] = false;

                    }
                }
//                    
                square.putImage();
//
            }
        }
    }

    /**
     * Check if the treasure exists and is not in the center
     */
    public void checkTresure() {
        Random ligne = new Random();
        Random colonne = new Random();

        int x = ligne.nextInt(this.getL());
        int y = colonne.nextInt(this.getW());

        while (map[x][y].tresure == false && (x != positioninit[0] || y != positioninit[1])) {
            ligne = new Random();
            colonne = new Random();

            x = ligne.nextInt(this.getL());
            y = colonne.nextInt(this.getW());
            if (map[x][y].ennemie == false) {
                map[x][y].tresure = true;
            }

        }

    }

    /**
     * Check if there are no enemies in the center
     */
    public void checkEnnemie() {
        int count = 0;
        while (count < this.numberEnnemie) {

            Random ligne = new Random();
            Random colonne = new Random();

            int x = ligne.nextInt(this.getL());
            int y = colonne.nextInt(this.getW());
            if (map[x][y].ennemie != true && (x != positioninit[0] || y != positioninit[1])) {
                map[x][y].ennemie = true;
                count++;
            }

        }
    }

    /**
     * @return the l
     */
    public int getL() {
        return l;
    }

    /**
     * @param l the l to set
     */
    public void setL(int l) {
        this.l = l;
    }

    /**
     * @return the w
     */
    public int getW() {
        return w;
    }

    /**
     * @param w the w to set
     */
    public void setW(int w) {
        this.w = w;
    }

    /**
     * @return the map
     */
    public cell[][] getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(cell[][] map) {
        this.map = map;
    }

}
