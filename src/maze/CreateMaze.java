/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.event.ActionListener;
import java.util.Optional;
import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * This class implements the create maze menu with the graphical interface as
 * well as all the listeners and calls the control class
 * 
 * @author Adele Colas
 */
public class CreateMaze extends BorderPane {

    private VBox option;

    private RadioButton rb2;
    private RadioButton rb1;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button del;
    private Button save;
    public GridPane board;
    private int mouse;
    private Map map;
    private int numberEnnemie;
    private Boolean tres;
    private ImageView tresure;
    private MainMenue main;

    /**
     * Constructor
     * @param mm the main menu instance
     */
    public CreateMaze(MainMenue mm) {
        this.board = new GridPane();
        map = new Map();
        this.main = mm;
        this.tres = false;

        this.initialize();

    }

    /**
     * Initialize the create a maze menu
     */
    public void initialize() {
        Image tresImage = new Image("/moon2.png", 30, 30, false, false);
        tresure = new ImageView();
        tresure.setImage(tresImage);

        Image enImage = new Image("/blade.png", 30, 30, false, false);
        Image nightImage = new Image("/nightcreatemaze2.png", 1000, 600, false, false);
        ImageView fond = new ImageView(nightImage);

        final ToggleGroup group = new ToggleGroup();
        option = new VBox();
        rb1 = new RadioButton("Draw");
        rb1.setToggleGroup(group);
        rb2 = new RadioButton("Enemie");
        rb2.setToggleGroup(group);
        rb3 = new RadioButton("Treasure");
        rb3.setToggleGroup(group);
        rb4 = new RadioButton("Erase");
        del = new Button("Cancel");
        rb4.setToggleGroup(group);
        save = new Button("Save");

        HBox top = new HBox();
        top.getStyleClass().add("hbox");
        top.getChildren().addAll(del, save);
        this.setBottom(top);
        StackPane structure = new StackPane();

        structure.getChildren().addAll(fond, board);
        option.getChildren().addAll(rb1, rb2, rb3, rb4);
        rb1.getStyleClass().add("radioButton");
        rb2.getStyleClass().add("radioButton");
        rb3.getStyleClass().add("radioButton");
        rb4.getStyleClass().add("radioButton");

        this.setLeft(option);
        option.getStyleClass().add("vbox");
        //structure.getChildren().addAll(layer1);
        this.setCenter(structure);
        this.board.setAlignment(Pos.CENTER);

        update();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    if (rb1.isSelected()) {
                        mouse = 1;

                    } else if (rb2.isSelected()) {

                        mouse = 2;
                    } else if (rb3.isSelected()) {
                        mouse = 3;

                    } else if (rb4.isSelected()) {
                        mouse = 0;

                    }
                }
            }
        }
        );
        save.setOnMouseClicked(event -> {
            if (this.map.getMap()[4][3].ennemie) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Careful!");
                alert.setContentText("You put an ennemy in the initial player position! \n Please modify your maze.");
                alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");

                alert.showAndWait();
            } else if (this.map.getMap()[4][3].tresure) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Careful!");
                alert.setContentText("You put the tresure in the initial player position! \n Please modify your maze.");
                alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");

                alert.showAndWait();
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("You will use this maze map for your game, continue?");
                alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    this.main.setMap(this.map);
                    main.mainStage.show();
                    Stage stage = (Stage) save.getScene().getWindow();
                    stage.close();
                }

            }

        });

        del.setOnMouseClicked(event -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("You are going back to the main menue without using this maze, continue?");
            alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                main.mainStage.show();
                Stage stage = (Stage) del.getScene().getWindow();
                stage.close();
            }

        }
        );

    }
    /**
     * Pauses the menu when escape is pressed to display help
     * @param scene the scene
     */
    public void pause(Scene scene) {

        scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText(null);
                    alert.setContentText("create a wall: draw button and use the mouse + control \n erase a wall: erase and use the mouse + control \n put enemie or treasure: button + click on the cell");
                    alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");
                    alert.showAndWait();

                }
            }
        });

    }

    /**
     * Updates the visual scene when modifications are made
     */
    public void update() {
        board.getChildren().clear();

        for (int i = 0; i < map.getL(); i++) {
            for (int j = 0; j < map.getW(); j++) {

                BorderPane bp = new BorderPane();

                try {

                    bp.setTop(map.getMap()[i][j].images[0]);
                    bp.setBottom(map.getMap()[i][j].images[2]);
                    bp.setRight(map.getMap()[i][j].images[1]);
                    bp.setLeft(map.getMap()[i][j].images[3]);
                    bp.setCenter(new StackPane());

                } catch (Exception e) {
                    System.out.println("error");

                }
                if (map.getMap()[i][j].tresure == true) {
                    bp.setCenter(tresure);
                } else if (map.getMap()[i][j].ennemie == true) {
                    Image enImage = new Image("/blade.png", 30, 30, false, false);
                    ImageView ennemie = new ImageView();
                    ennemie.setImage(enImage);
                    bp.setCenter(ennemie);
                }

                board.add(bp, i, j);

                drawItem(i, j, bp);
                for (int a = 0; a < 4; a++) {

                    drawWall(i, j, a);

                }

            }
        }

    }

    /**
     * Implements the function to add enemies or treasure
     * @param i abciss of the item
     * @param j ordinates of the item
     * @param bd the cell
     */
    public void drawItem(int i, int j, BorderPane bd) {
        bd.getCenter().setOnMouseClicked((MouseEvent event) -> {
            if (map.getMap()[i][j].tresure == false && map.getMap()[i][j].ennemie == false) {
                if (mouse == 2) {
                    map.getMap()[i][j].ennemie = true;
                    Image enImage = new Image("/blade.png", 30, 30, false, false);
                    ImageView ennemie = new ImageView();
                    ennemie.setImage(enImage);

                    bd.setCenter(ennemie);
                    numberEnnemie++;
                    update();
                }
                if (mouse == 3 && tres == false) {
                    map.getMap()[i][j].tresure = true;

                    bd.setCenter(tresure);
                    tres = true;
                    update();

                }
            } else {
                if (mouse == 0) {
                    if (map.getMap()[i][j].ennemie == true) {
                        map.getMap()[i][j].ennemie = false;
                        numberEnnemie--;
                    } else if (map.getMap()[i][j].tresure == true) {
                        map.getMap()[i][j].tresure = false;
                        tres = false;
                    }

                    //(StackPane)bd.getCenter().getChildren().clear();
                    update();

                }
            }

        });

    }

    /**
     * Function to draw walls
     * @param x abciss
     * @param y ordinates
     * @param a index of the wall
     */
    public void drawWall(int x, int y, int a) {

        map.getMap()[x][y].images[a].setOnMouseEntered((MouseEvent event) -> {
            if (event.isControlDown()) {
                if (map.getMap()[x][y].wall[a] == false && mouse == 1) {

                    map.getMap()[x][y].wall[a] = true;
                    map.getMap()[x][y].putImage();
                    update();

                }
                if (map.getMap()[x][y].wall[a] == true && mouse == 0) {
                    map.getMap()[x][y].wall[a] = false;
                    map.getMap()[x][y].putImage();
                    update();

                }
            }

        });

    }

//    public void drawWall() {
//
//    }
//    public void disableButtons(Boolean yes) {
//        if (yes) {
//            this.del.setDisable(true);
//            this.clone.setDisable(true);
//        } else {
//            this.del.setDisable(false);
//            this.clone.setDisable(false);
//        }
//
//    }
}
