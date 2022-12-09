/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

/**
 *
 * @author adele
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *This class implements the winning and losing conditions for the game
 * 
 * @author adele
 */
public class EndGame extends BorderPane {

    private final Control game;
    private int loose;
    private Button butYes;
    private Button butNo;
    private Label lab;
    private ImageView fond;
    private String message;

    /**
     * Constructor
     * @param g the instance of the control of the game
     * @param l the end game code
     * @param mess the message of the end game
     */
    public EndGame(Control g, int l, String mess) {
        this.game = g;
        this.loose = l;
        this.message=mess;
        Initialize();
    }

    /**
     * Initialize the display for the end game window
     */
    public void Initialize() {
        butYes = new Button("Yes");
        butNo = new Button("No");
        

        butYes.setOnMouseClicked(event -> {
            game.music.stop();
            game.boucle.stop();
            Stage gameW = (Stage) game.windowG.getScene().getWindow();
            gameW.close();
            Control ct = new Control(game.map, 3, game.perso);

            Scene scene = new Scene(ct.windowG, 1000, 600);

            scene.getStylesheets()
                    .add("/maze/MazeStyle.css");
            ct.pause(scene, ct);

            ct.startGame(scene, ct);
            Stage gameStage = new Stage();
            gameStage.setTitle(
                    " Moonlight Maze!");
            gameStage.setScene(scene);

            gameStage.show();

            Stage stage = (Stage) butYes.getScene().getWindow();
            // do what you have to do
            stage.close();

        });

        StackPane cent = new StackPane();
        if (loose == 1) {
            Image good = new Image("/fondloose.png", 400, 560, false, false);
            fond = new ImageView(good);

            lab = new Label("\t Oh No... \n \n " + message + " \n \n You lost! \n \n Do you want to try again?");

        } else if (loose == 0) {
            Image good = new Image("/rabbitloonkingthemoon.gif", 400, 560, false, false);

            fond = new ImageView(good);
            lab = new Label("\t Congrats! You won! \n \n  The moon found her light again \n and came back to the sky \n  Do you want to try again?");

        } else if (loose == 2) {
            Image good = new Image("/fondwin.jpg", 400, 560, false, false);
            fond = new ImageView(good);
            butNo = new Button("Go to Main Menu");
            butYes = new Button("Resume");
            lab = new Label("\n \n \n \n \n \n \n \n \n \n To play, you have to drag and drop the wall \n to avoid ennemy and direct your character \n \n Quit the Game?");
            lab.setStyle("-fx-text-fill: black");
          
                    

            butYes.setOnMouseClicked(event -> {

                game.boucle.play();
                game.on = true;

                Stage stage = (Stage) butYes.getScene().getWindow();
                // do what you have to do
                stage.close();

            });

        }
        lab.getStyleClass().add("lab2");
        butNo.setOnMouseClicked((MouseEvent event) -> {
            Stage stage = (Stage) butNo.getScene().getWindow();

            stage.close();
            game.music.stop();
            game.boucle.stop();
            Stage gameStage = (Stage) game.windowG.getScene().getWindow();
            gameStage.close();

            MainMenue MM = new MainMenue();
            //MM.MainStage.show();
            
//            Scene scene = new Scene(MM.createContent());
//            scene.getStylesheets().add("/maze/style.css");
            Stage primaryStage = new Stage();
            try {
                MM.start(primaryStage);
            } catch (Exception ex) {
                Logger.getLogger(EndGame.class.getName()).log(Level.SEVERE, null, ex);
            }
//            primaryStage.setTitle("Moonlight Maze");
//            primaryStage.setScene(scene);
//            primaryStage.show();

        });

        HBox bot = new HBox();

        lab.getStyleClass().add("lab");
        cent.getChildren().addAll(fond, lab);
        bot.getChildren().addAll(butYes, butNo);
        lab.setContentDisplay(ContentDisplay.TOP);
        bot.getStyleClass().add("hbox2");
        this.setCenter(cent);
        this.setBottom(bot);
        this.getStyleClass().add("root2");

    }

}
