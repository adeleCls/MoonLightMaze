package maze;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 * This class implements the main menu of the game. 
 * 
 * @author chris
 */
public class MainMenue extends Application {

    private Map map;
    private Character perso;
    public static double diff = 3.5;

    private VBox menu;
    public Stage mainStage;
    public Stage primaryStage;

    // Difficulty
    public static int difficulty = 0;
    public final static String[] DIFFICULTIES = {"", "Easy", "Normal", "Hard"};

    // initializing two doubles for the x and y screen offsets (dragging the window)
    private double xOffset = 0;
    private double yOffset = 0;

    
    public Parent createContent() {

        map = new Map(9);
        Pane root = new Pane();
        root.setPrefSize(1000, 650);

        Image fond = new Image("/main-bg-img.png", 1000, 650, false, false);
        ImageView img = new ImageView(fond);
        Image lapinImF = new Image("/rabbit.png", 171.5, 220, false, false);
        ImageView lapinFace = new ImageView(lapinImF);
        Image lapinImB = new Image("/rabbitBack.png", 40, 40, false, false);
        ImageView lapinBack = new ImageView(lapinImB);
        Character lapin = new Character(lapinImB, lapinImB, 2000, 0, "Jokie");
        this.setPerso(lapin);
        root.getChildren().add(img);

        Button newGame = new Button("PLAY NEW GAME");
        Button Character = new Button("CHARACTER");
        Button maze = new Button("CREATE A MAZE");
        Button help = new Button("HELP / ABOUT");
        Button Difficulty = new Button("LEVEL");
        Button itemQuit = new Button("QUIT GAME");

        itemQuit.setOnMouseClicked(event -> System.exit(0));

        newGame.setOnMouseClicked(event -> {
            System.out.println(perso.getName());
            System.out.println(perso.getMoveIm());

            Control ct = new Control(map, diff, perso);

            Scene scene = new Scene(ct.windowG, 1100, 660);

            scene.getStylesheets()
                    .add("/maze/MazeStyle.css");
            ct.pause(scene, ct);

            ct.startGame(scene, ct);
            Stage gameStage = new Stage();
            gameStage.setTitle(
                    " Moonlight Maze!");
            gameStage.setScene(scene);

            gameStage.show();

            Stage stage = (Stage) newGame.getScene().getWindow();

            stage.close();
        });

        maze.setOnMouseClicked(event -> {
            CreateMaze mazeC = new CreateMaze(this);
            Scene scene = new Scene(mazeC, 2000, 1000);
            Stage createMaze = new Stage();
            scene.getStylesheets().add("/maze/MazeStyle.css");
            mazeC.pause(scene);
            createMaze.setTitle("Create a maze");
            createMaze.setScene(scene);
            createMaze.show();
            Stage stage = (Stage) maze.getScene().getWindow();
            stage.close();

        });

        Character.setOnMouseClicked(event -> {
            CharacterCreation charC = new CharacterCreation(this, getPerso());
            Scene scene = new Scene(charC, 950, 600);
            Stage createMaze = new Stage();
            scene.getStylesheets().add("/maze/MazeStyle.css");
            createMaze.setTitle("Change Character");
            createMaze.setScene(scene);
            createMaze.show();
            Stage stage = (Stage) Character.getScene().getWindow();
            stage.close();

        });

        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MenuAbout();
            }
        });

        menu = new VBox(newGame, Character, maze, help, Difficulty, itemQuit);
        menu.setSpacing(10);
        menu.setTranslateX(400);
        menu.setTranslateY(250);
        menu.setAlignment(Pos.CENTER);
        menu.setLayoutY(30);
        Difficulty.setOnAction(e -> changeDifficulty(Difficulty));

        root.getChildren().addAll(menu);
        return root;

    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Scene scene = new Scene(createContent());
//        scene.getStylesheets().add("/maze/style.css");
//        primaryStage.setTitle("Moonlight Maze");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//        this.MainStage = primaryStage;
//
//    }
    private static class Title extends StackPane {

        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    private static class MenuBox extends VBox {

        public MenuBox(MenuItem... items) {
            getChildren().add(createSeperator());

            for (MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
                item.setAlignment(Pos.CENTER);
            }
        }

        public static void addItem(MenuItem itemNew) {

        }

        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;

        }

    }

    private static class MenuItem extends StackPane {

        public MenuItem(String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop[]{new Stop(0, Color.DARKBLUE), new Stop(0.1, Color.BLACK), new Stop(0.9, Color.BLACK),
                        new Stop(1, Color.DARKBLUE)});

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(1);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Helvetica", FontWeight.BOLD, 10));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);

            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.MIDNIGHTBLUE);
                bg.setOpacity(30);

            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });

        }
    }

// ****************************************// ABOUT WINDOW //****************************************
    public void MenuAbout() {
        Stage stage = new Stage();
        stage.setTitle("About Moonlight Maze");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryStage);

        Button closeWindow = new Button("Ok, Thanks!");
        closeWindow.setOnMouseClicked(e -> stage.close());
        //closeWindow.setTranslateY(200);

        StackPane vb = new StackPane();
        VBox v= new VBox();
        vb.setPrefSize(820, 730);
        vb.setId("bg-img");

        Label textA = new Label();
        textA.setId("about-text");
        textA.setTextAlignment(TextAlignment.CENTER);
        Label textB = new Label();
        textB.setId("about-textB");
        textB.setTextAlignment(TextAlignment.CENTER);

        textA.setText(
                "THE MOON LOST HER LIGT AND FELL FROM THE SKY. \n "
                + "YOU ARE THE ONLY ONE THAT CAN GIVE THE MOON BACK HER LIGHT.\n"
                + " GO ON AN ADVENTURE AND HELP YOUR CHARACTER FINDING THE MOON!\n"
                + "DRAG THE WALLS TO GUIDE YOUR CHARACTER. \n"
                + "AVOID ENEMIES AND FIND THE MOON! \n "
                + "Press on escape at any time to pause and get help. "
                
                
);
        textB.setText(                 "CREDITS: \n"
                + "Characters, maze & maze creation images: Adèle Colas \n"
                + "Main menu, aboutWindow images: Cristian Esanu \n"
                + "Music: Music from Totoro, piano version\n"
                + "Gif win Image: Lisa Angonese (Pinterest)\t Pause Image: tunnelinu (deviantART) \t Totoro Image: Jack Hays (Pinterest)");
        
        
        v.getChildren().addAll(textA,textB, closeWindow);
        vb.getChildren().add(v);
        v.setPrefSize(820, 730);
        v.setAlignment(Pos.CENTER);
        StackPane.setAlignment(vb, Pos.CENTER);

        textA.setPrefSize(550, 300);
         textB.setPrefSize(640, 200);
        
        stage.setScene(new Scene(vb));
        vb.getStylesheets().add("/maze/about-style.css");
        stage.show();

    }

    // ****************************************// NO TOP BAR + DRAG MAIN WINDOW //****************************************
    @Override
    public void start(Stage primaryStage) throws Exception {

//        primaryStage.setTitle("Moonlight Maze");
//        primaryStage.setScene(scene);
//        primaryStage.show();
        this.mainStage = primaryStage;
        Scene mainScene = new Scene(createContent());
        mainScene.getStylesheets().add("/maze/style.css");

        mainScene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        mainScene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        mainScene.getStylesheets().add("/maze/style.css");
        primaryStage.setTitle("MoonLight Maze");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(mainScene);
        primaryStage.show();

    }

    // Difficulty / Level selector button
    public static void changeDifficulty(Button button) {
        if (difficulty < 3) {
            difficulty++;
            diff = 3.5 - (difficulty - 1);

        } else {
            difficulty = 1;
            diff = 3.5;
        }
        button.setText("LEVEL: " + DIFFICULTIES[difficulty]);

    }

    public static void main(String[] args) {

        launch(args);
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * @return the perso
     */
    public Character getPerso() {
        return perso;
    }

    /**
     * @param perso the perso to set
     */
    public void setPerso(Character perso) {
        this.perso = perso;
    }
}
