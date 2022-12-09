/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Shape;

/**
 *This class implements all the functions necessary to control the maze, for the
 * game as well as the maze editor. It includes the drag and drop features,
 * the draw wall HCI as well as the music.
 * 
 * @author adele
 */
public class Control implements Initializable {

    public GridPane board;
    public StackPane windowG;
    public Map map;
    public ImageView charView;
    public int[] position;
    private int[] init;
    private int direction;
    private ImageView tresure;
    public Shape night;
    private GridPane hatPane;
    public MediaPlayer music;
    private Boolean rot;
    private Boolean tim;
    private Boolean move;

    public Timeline boucle;
    public Boolean on;
    private FlowPane screenStart;
    private int speed;
    private double waitTime;
    public Character perso;
    /**
     * Constructor 
     * 
     * @param mapi the initialization map
     * @param s The time between two movements
     * @param per the selected character
     */
    public Control(Map mapi, double s, Character per) {
        this.board = new GridPane();
        this.map = mapi;
        this.windowG = new StackPane();
        this.board.getStyleClass().add("grid2");
        this.direction = 1;
        this.on = false;
        this.perso = per;
        this.waitTime = s;
        this.speed = perso.getSpeed();
        this.charView = new ImageView();
        this.move = true;
        this.tim = true;
        this.move = true;
        System.out.println(this.waitTime);
        initialize();

    }
    /**
     * Initalizes the game
     */
    public void initialize() {
        position = new int[2];
        init = new int[2];
        init[0] = 4;
        init[1] = 3;
        position = init;
        hatPane = new GridPane();
        hatPane.getStyleClass().add("grid1");
//        Image charImage = new Image("/hat.png", 40, 40, false, false);
//        this.charView = new ImageView();
//        this.charView.setImage(charImage);
        this.charView = new ImageView();
        Image moveChar = perso.getMoveIm();
        this.charView.setImage(moveChar);
        
        //PUTTING THE MUSIQUE
        
        
                
        Media media;
        try {
            media = new Media(Control.class.getResource("/musicWin.mp3").toURI().toString());
            this.music = new MediaPlayer(media);
            music.setCycleCount(MediaPlayer.INDEFINITE);
            music.play();
        
        } catch (URISyntaxException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Media media =  new Media(Paths.get("/musicWin.mp3").toUri().toString());

//        URL Url = Control.class.getResource("/src/musicWin.mp3");
//        URI uri = null;
////        uri = new URI(Url.toString());
////        Media media = new Media(Control.class.getResource("/src/musicWin.mp3").toURI().toString);
////        Media media =  new Media(Paths.get("src/musicWin.mp3").toUri().toString());
//        this.music = new MediaPlayer(media);
//        music.setCycleCount(MediaPlayer.INDEFINITE);
//        music.play();

        Image nightImage = new Image("/night.png", 2000, 1300, false, false);
        
        /** create the overlaping image that is going to represente the "light" 
        reatcangle and the circle!**/
        Image fondImage = new Image("/fondNight.png", 900, 700, false, false);
        
        ImageView fond = new ImageView(fondImage);

        Circle circle1 = new Circle();

        circle1.setCenterX(1000.0f);
        circle1.setCenterY(650.0f);

        circle1.setRadius(90.0f);
        Rectangle rect = new Rectangle(2000, 1300);

        System.out.println("les dim" + this.windowG.getWidth() + "  " + this.windowG.getHeight());

        ImagePattern nightpat = new ImagePattern(nightImage);

        this.night = Shape.subtract(rect, circle1);
        this.night.setFill(nightpat);

        Image tresImage = new Image("/moon2.png", 30, 30, false, false);
        this.tresure = new ImageView();
        this.tresure.setImage(tresImage);
        Image start = new Image("/press-any-key.png", 1100, 660, false, false);
        ImageView startView = new ImageView();
        startView.setImage(start);
        screenStart = new FlowPane();
        screenStart.setId("press-key");
        

        this.windowG.getChildren().addAll(fond, this.hatPane, this.night, this.board, screenStart);
        this.drawmap();

        this.update();

        boucle = new Timeline(new KeyFrame(Duration.seconds(this.waitTime), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                checkCell();
                if (on) {
                    move();

                }
            }
        }));
        boucle.setCycleCount(Timeline.INDEFINITE);

    }
    
    // Putting the ennemies and the tresure.
    
    public void drawmap() {
        Image blankOdd = new Image("/notWallImpair.png", 13, 50, false, false);
        Image blankEven = new Image("/notWallPair.png", 70, 13, false, false);
        for (int i = 0; i < map.getL(); i++) {
            for (int j = 0; j < map.getW(); j++) {
//          
                BorderPane bp1 = new BorderPane();

                bp1.getStyleClass().add("cell");

                bp1.setTop(new ImageView(blankEven));
                bp1.setBottom(new ImageView(blankEven));
                bp1.setRight(new ImageView(blankOdd));
                bp1.setLeft(new ImageView(blankOdd));

                if (map.getMap()[i][j].ennemie) {
                    ImageView en = new ImageView();
                    Image enImage = new Image("/blade.png", 30, 30, false, false);

                    en.setImage(enImage);

                    RotateTransition rotation = new RotateTransition(Duration.seconds(1), en);
                    rotation.setCycleCount(Animation.INDEFINITE);
                    rotation.setByAngle(360);
                    en.setRotationAxis(Rotate.Z_AXIS);
                    rotation.play();
                    bp1.setCenter(en);

                }

                if (map.getMap()[i][j].tresure) {
                    //bp.setCenter(this.tresure);
                    bp1.setCenter(this.tresure);

                }

                if (i == init[0] && j == init[1]) {
                    bp1.setCenter(this.charView);
                }
                //

                hatPane.add(bp1, i, j);

            }
        }

    }
    /**
     * Update the walls when there are drag and drops
     */
    public void update() {

        board.getChildren().clear();

        for (int i = 0; i < map.getL(); i++) {
            for (int j = 0; j < map.getW(); j++) {
//                if (i != init[0] || j != init[1]) {
                BorderPane bp = new BorderPane();

                try {

                    bp.setTop(map.getMap()[i][j].images[0]);
                    bp.setBottom(map.getMap()[i][j].images[2]);
                    bp.setRight(map.getMap()[i][j].images[1]);
                    bp.setLeft(map.getMap()[i][j].images[3]);

                } catch (Exception e) {
                    System.out.println("error");

                }
                board.add(bp, i, j);
                for (int a = 0; a < 4; a++) {

                    moveWall(i, j, a);
//                    }
                }

            }
        }

    }
    /**
     * Implementation of drag and drop
     * 
     * @param x abciss of new pos of wall
     * @param y ordinates of new pos of wall
     * @param a index of the wall
     */
    public void moveWall(int x, int y, int a) {

        Node source = map.getMap()[x][y].images[a];
        map.getMap()[x][y].images[a].setOnDragDetected((MouseEvent event) -> {
            if (map.getMap()[x][y].wall[a] == true) {
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
                    update();
                  

                }
      
                event.consume();

            }
        });

        Node target = map.getMap()[x][y].images[a];

        map.getMap()[x][y].images[a].setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getDragboard().hasImage() && map.getMap()[x][y].wall[a] == false) {

                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();

            }
        });

        map.getMap()[x][y].images[a].setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
 /* if there is a string data on dragboard, read it and use it */

                Dragboard db = event.getDragboard();
                boolean success = false;

                if (db.hasImage() && map.getMap()[x][y].wall[a] == false) {
                    map.getMap()[x][y].wall[a] = true;
                    map.getMap()[x][y].putImage();
                    update();
                    success = true;
                }
                /* let the source know whether the string was successfully 
         * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

    }
    /**
     * Returns a specific node
     * @param gridPane The grid containing the node
     * @param col Column of the node
     * @param row Row of the node
     * @return 
     */
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //the routine that is making the character mooves => gives him the direction to go next.

    /**
     * Implements the movement of the character
     */
    public void move() {
        this.checkCell();

        System.out.println("on va dans la direction " + this.direction);

        switch (this.direction) {
            case 1:
                if (position[0] == map.getL() - 1 && map.getMap()[position[0]][position[1]].wall[1] == false) {
                    lostCall("You fell from the maze");
                } else {
                    if (map.getMap()[position[0]][position[1]].wall[1] == false && map.getMap()[position[0] + 1][position[1]].wall[3] == false) {

                        this.direction = 1;
                        translate(0);

                    } else if (map.getMap()[position[0]][position[1]].wall[2] == false && map.getMap()[position[0]][position[1] + 1].wall[0] == false) {

                        this.direction = 2;
                        translate(1);

                    } else if (map.getMap()[position[0]][position[1]].wall[3] == false && map.getMap()[position[0] - 1][position[1]].wall[1] == false) {

                        this.direction = 3;
                        translate(2);

                    } else if (map.getMap()[position[0]][position[1]].wall[0] == false && map.getMap()[position[0]][position[1] - 1].wall[2] == false) {

                        this.direction = 0;
                        translate(3);

                    } else {
                        lostCall("You are stuck between walls");

                    }

                }

                break;

            case 2:
                if (position[1] == map.getW() - 1 && map.getMap()[position[0]][position[1]].wall[2] == false) {
                    lostCall("You fell from the maze");
                } else {
                    if (map.getMap()[position[0]][position[1]].wall[2] == false && map.getMap()[position[0]][position[1] + 1].wall[0] == false) {

                        this.direction = 2;
                        translate(0);

                    } else if (map.getMap()[position[0]][position[1]].wall[3] == false && map.getMap()[position[0] - 1][position[1]].wall[1] == false) {

                        this.direction = 3;
                        translate(1);

                    } else if (map.getMap()[position[0]][position[1]].wall[0] == false && map.getMap()[position[0]][position[1] - 1].wall[2] == false) {

                        this.direction = 0;
                        translate(2);

                    } else if (map.getMap()[position[0]][position[1]].wall[1] == false && map.getMap()[position[0] + 1][position[1]].wall[3] == false) {

                        this.direction = 1;
                        translate(3);

                    } else {
                        lostCall("You are stuck between walls");

                    }

                }

                break;

            case 3:
                if (position[0] == 0 && map.getMap()[position[0]][position[1]].wall[3] == false) {
                    lostCall("You fell from the maze");

                } else {
                    if (map.getMap()[position[0]][position[1]].wall[3] == false && map.getMap()[position[0] - 1][position[1]].wall[1] == false) {

                        this.direction = 3;

                        translate(0);

                    } else if (map.getMap()[position[0]][position[1]].wall[0] == false && map.getMap()[position[0]][position[1] - 1].wall[2] == false) {

                        this.direction = 0;
                        translate(1);

                    } else if (map.getMap()[position[0]][position[1]].wall[1] == false && map.getMap()[position[0] + 1][position[1]].wall[3] == false) {

                        this.direction = 1;
                        translate(2);

                    } else if (map.getMap()[position[0]][position[1]].wall[2] == false && map.getMap()[position[0]][position[1] + 1].wall[0] == false) {

                        this.direction = 2;
                        translate(3);

                    } else {
                        lostCall("You are stuck between walls");

                    }
                }

                break;

            case 0:
                if (position[1] == 0 && map.getMap()[position[0]][position[1]].wall[0] == false) {
                    lostCall("You fell from the maze");
                } else {
                    if (map.getMap()[position[0]][position[1]].wall[0] == false && map.getMap()[position[0]][position[1] - 1].wall[2] == false) {
                        this.direction = 0;
                        translate(0);

                    } else if (map.getMap()[position[0]][position[1]].wall[1] == false && map.getMap()[position[0] + 1][position[1]].wall[3] == false) {

                        this.direction = 1;
                        translate(1);

                    } else if (map.getMap()[position[0]][position[1]].wall[2] == false && map.getMap()[position[0]][position[1] + 1].wall[0] == false) {

                        this.direction = 2;
                        translate(2);
                    } else if (map.getMap()[position[0]][position[1]].wall[3] == false && map.getMap()[position[0] - 1][position[1]].wall[1] == false) {

                        this.direction = 3;
                        translate(3);

                    } else {
                        lostCall("You are stuck between walls");

                    }
                }

                break;
            default:
                break;
//}
        }
    }
    /**
     * rotates and translate the character while moving
     * @param angle the angle of the rotation
     */
    public void translate(int angle) {
        TranslateTransition ttc;
        TranslateTransition ttn;
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), this.charView);
        rotation.setCycleCount(1);
        rotation.setByAngle(90 * angle);
        this.charView.setRotationAxis(Rotate.Z_AXIS);
        ParallelTransition pt = new ParallelTransition();
//        ttc = new TranslateTransition(Duration.millis(this.speed), this.charView);
//        ttn = new TranslateTransition(Duration.millis(this.speed), this.night);
        rot = true;
        rotation.play();

//        rotation.onFinishedProperty().set(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                rot = false;
//                System.out.println("et là le rot est" + rot);
//            }
//        });
        switch (this.direction) {

            case 1:
                ttc = new TranslateTransition(Duration.millis(this.speed), this.charView);
                ttc.setByX(70f);
                ttn = new TranslateTransition(Duration.millis(this.speed), this.night);

                ttn.setByX(70f);
                System.out.println("on va vers la droite");
                position[0]++;
                pt = new ParallelTransition();

                pt.getChildren().addAll(
                        ttc, ttn);

                pt.play();
                break;

            case 2:

                ttc = new TranslateTransition(Duration.millis(this.speed), this.charView);
                ttc.setByY(76f);
                ttn = new TranslateTransition(Duration.millis(this.speed), this.night);
                ttn.setByY(76f);

                System.out.println("on va vers le bas");
                //tt.setCycleCount((int) 4f);
                //tt.setAutoReverse(true);
                position[1] = position[1] + 1;

                pt = new ParallelTransition();
                pt.getChildren().addAll(
                        ttc, ttn);

                pt.play();
                break;
            case 3:

                ttc = new TranslateTransition(Duration.millis(this.speed), this.charView);
                ttc.setByX(-70f);
                ttn = new TranslateTransition(Duration.millis(this.speed), this.night);
                ttn.setByX(-70f);

                pt = new ParallelTransition();
                pt.getChildren().addAll(
                        ttc, ttn);
                System.out.println("on va vers la gauche");
                position[0]--;

                pt.play();
                break;

            case 0:
                ttc = new TranslateTransition(Duration.millis(this.speed), this.charView);
                ttc.setByY(-76f);
                ttn = new TranslateTransition(Duration.millis(this.speed), this.night);
                ttn.setByY(-76f);

                pt = new ParallelTransition();
                pt.getChildren().addAll(
                        ttc, ttn);

                System.out.println("on va vers le haut");
                position[1]--;

                pt.play();
                break;

        }

//        pt.play();
    }
    /**
     * Display the loss window when needed
     * @param message the loss message
     */
    public void lostCall(String message) {

        boucle.stop();

        on = false;
        EndGame eg = new EndGame(this, 1, message);

        Stage StageEG = new Stage();
        StageEG.setResizable(false);
        StageEG.setTitle("End Game");
        Scene scene = new Scene(eg, 400, 620);
        scene.getStylesheets()
                .add("/maze/MazeStyle.css");
        
        StageEG.setScene(scene);
        StageEG.show();

    }
    /**
     * Check for each cell if there is an ennemy
     */
    public void checkCell() {
        if (this.map.getMap()[this.position[0]][this.position[1]].ennemie) {
            System.out.println("mais ça s'ouvre pas ce truc");
            lostCall("You found an ennemy");
        }

        if (this.map.getMap()[position[0]][position[1]].tresure) {
            boucle.stop();
            on = false;

            EndGame eg = new EndGame(this, 0, "ok");
            Stage StageEG = new Stage();
            StageEG.setResizable(false);
            StageEG.setTitle("End Game");
            Scene scene = new Scene(eg, 400, 620);
            scene.getStylesheets()
                    .add("/maze/MazeStyle.css");
            
            StageEG.setScene(scene);
            StageEG.show();

        }

    }
//when you want to start: press any key!
    /**
     * Waits for the player to press a key before starting
     * @param scene The visual scene of the game
     * @param maze an instance of control
     */
    public void startGame(Scene scene, Control maze) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() != KeyCode.ESCAPE) {
                    System.out.println("on a press, boucle starts");

                    maze.windowG.getChildren().remove(maze.screenStart);

                    if (on != true) {
                        move();
                        boucle.play();
                        on = true;

                    }
                }
            }
        });

    }
    /**
     * Implements the pause
     * @param scene The visual scene of the game
     * @param maze an instance of control
     */
    public void pause(Scene scene, Control maze) {

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {

                    System.out.println("on a appuyé sur esc");
                    EndGame eg = new EndGame(maze, 2, "You are paused");
                    maze.boucle.pause();
                    maze.on = false;
                    Stage StageEG = new Stage();
                    StageEG.setResizable(false);

                    StageEG.setTitle("Pause Game");
                    Scene scene = new Scene(eg, 400, 630);
                    scene.getStylesheets()
                            .add("/maze/MazeStyle.css");
                    StageEG.setScene(scene);

                    StageEG.show();

                }
            }
        });

    }
    public void bouge(ParallelTransition t) {
        System.out.println("la rotation est" + rot);
        if (rot == false) {
            System.out.print("we moove");
            move = true;
            t.play();

            t.onFinishedProperty().set(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    move = false;
                }
            });

        }
        if (move == false) {
            tim = true;
            on = true;
            this.boucle.play();

        }
    }

}
