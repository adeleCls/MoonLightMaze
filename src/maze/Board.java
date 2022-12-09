/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *Board class represents the real-time board during a game. It includes functions
 * to initialize it and move walls
 * 
 * @author adele
 */
public class Board extends GridPane {

    public Map map;
    
    /**
     * Board constructor
     * 
     * @param mapi The map to initialize the board with.
     */
    public Board(Map mapi) {
        this.map = mapi;
        Initialize();

    }

    /**
     * Initialize the board by puttin all the walls of the map to their places
     */
    public void Initialize() {
        this.update();
        for (int i = 0; i < map.getL(); i++) {
            for (int j = 0; j < map.getW(); j++) {
                for (int a = 0; a < 4; a++) {
                    
                    moveWall(i, j, a);
                }

            }
        }

    }
    /**
     * Update the visuals of the board by moving walls 
     */
    public void update() {

        this.getChildren().clear();
        for (int i = 0; i < map.getL(); i++) {
            for (int j = 0; j < map.getW(); j++) {
                BorderPane bp = new BorderPane();
                try {
                    bp.setTop(map.getMap()[i][j].images[0]);
                    bp.setBottom(map.getMap()[i][j].images[2]);
                    bp.setRight(map.getMap()[i][j].images[1]);
                    bp.setLeft(map.getMap()[i][j].images[3]);

                } catch (Exception e) {

                }
                this.add(bp, i, j);

            }
        }
    }

    /**
     * move wall number a to position (x,y)
     * 
     * @param x the abciss of the new position of the wall
     * @param y the ordinates of the new position of the wall
     * @param a wall's index
     */
    public void moveWall(int x, int y, int a) {
        Board bd;
        bd = this;

      
            Node source = map.getMap()[x][y].images[a];
            map.getMap()[x][y].images[a].setOnDragDetected((MouseEvent event) -> {
                if (map.getMap()[x][y].images[a].getImage() != null) {
                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putImage((map.getMap()[x][y].images[a].getImage()));
                    db.setContent(content);
                    event.consume();
                }

            });

            map.getMap()[x][y].images[a].setOnDragDone(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getTransferMode() == TransferMode.MOVE) {
                        map.getMap()[x][y].wall[a] = false;
                        map.getMap()[x][y].putImage();
                        

                    }
                    
                    event.consume();

                }
            });
         
            System.out.println("null image");
            Node target = map.getMap()[x][y].images[a];

            map.getMap()[x][y].images[a].setOnDragOver(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    if (event.getDragboard().hasImage() && map.getMap()[x][y].images[a].getImage()==null ) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                    event.consume();

                }
            });

            map.getMap()[x][y].images[a].setOnDragDropped(new EventHandler<DragEvent>() {
                public void handle(DragEvent event) {
                    /* data dropped */

                    Dragboard db = event.getDragboard();
                    boolean success = false;

                    if (db.hasImage()) {
                        map.getMap()[x][y].wall[a] = true;
                        map.getMap()[x][y].putImage();
                        


                    }
                    /* let the source know whether the string was successfully 
         * transferred and used */
                    event.setDropCompleted(success);

                    event.consume();
                }
            });

        

    }
    /**
     * Returns a cell with given coordinates
     * 
     * @param gridPane The pane containing the cell
     * @param col The column of the cell
     * @param row The row of the cell
     * @return null.
     */
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

}
