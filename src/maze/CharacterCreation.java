/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.util.Arrays;
import java.util.Optional;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *This class implements the character selection screen
 * it includes the HCI to choose (a slider) and all the events related to it
 * 
 * 
 * @author adele
 */
public class CharacterCreation extends StackPane {

    private Character[] listCharacter;
    private VBox Vb;
    private Button del;
    private Button save;
    private Character perso;
    private MainMenue main;
    private Slider slid;

    private Label lab;
    private ImageView pres;
    private BorderPane BP;

    /**
     * The constructor
     * 
     * @param mm an instance of the main menu
     * @param p The selected character
     */
    public CharacterCreation(MainMenue mm, Character p) {

        this.main = mm;
        this.Vb = new VBox();
        this.perso = p;
        this.del = new Button("Cancel");
        this.save = new Button("Choose this Character");
        /** CREATE THE 4 CHARACTER POSSIBLE **/
        
        Image lapinImF = new Image("/rabbit.png", 171.5, 220, false, false);
        Image lapinImB = new Image("/rabbitBack.png", 40, 40, false, false);

        Character lapin = new Character(lapinImF, lapinImB, 1000, 0, "Jiko");
        Image ecImF = new Image("/squirel.png", 200, 200, false, false);
        Image ecImB = new Image("/squirelback.png", 40, 40, false, false);

        Character écureuil = new Character(ecImF, ecImB, 500, 1, "Ouna");

        Image herImF = new Image("/hedgehog.png", 155.9, 155.9, false, false);
        Image herImB = new Image("/hedgehogBack.png", 40, 40, false, false);

        Character herisson = new Character(herImF, herImB, 1500, 2, "Rutti");

        Image totoroImF = new Image("/totoro.gif", 275, 390, false, false);
        Image totoroImB = new Image("/totoroBack.png", 40, 40, false, false);

        Character totoro = new Character(totoroImF, totoroImB, 200, 3, "Totoro");
        this.perso = lapin;
        this.listCharacter = new Character[]{lapin, écureuil, herisson, totoro};
        this.slid = new Slider(0, 3.5, perso.getNum());
        this.lab = new Label();
        pres = new ImageView();

        this.initialize();

    }

    /**
     * Initialize the menu
     */
    public void initialize() {
        BP=new BorderPane();
        this.setPrefSize(950, 600);
        this.setId("bg-img");
        HBox top = new HBox();
        top.getStyleClass().add("hbox2");
        top.getChildren().addAll(del, save);
        BP.setBottom(top);
        lab.setText(perso.getName() + "\n \n");
        lab.setAlignment(Pos.TOP_CENTER);
        this.getStyleClass().add("root2");
        slid.setMaxWidth(350);
        slid.setBlockIncrement(1);
//        slid.setMajorTickUnit(1);
//        slid.setShowTickMarks(true);
//        slid.setMinorTickCount(0);
//        slid.setSnapToTicks(true);
//

        pres.setImage(perso.getPresIm());
        this.Vb.getChildren().addAll(this.lab, this.pres, this.slid);
        this.Vb.setAlignment(Pos.CENTER);

        this.getChildren().add(BP);
        BP.setCenter(this.Vb);


        this.slid.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                    Number oldValue, Number newValue) {
                //lab.setText(" " + newValue.intValue());

               changeChar(newValue.intValue());
            }
        });

        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Confirmation");
                alert.setContentText("You chose the character " + CharacterCreation.this.perso.getName() + ", \n continue?");
                alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");
                alert.getDialogPane().getStyleClass().add("root2");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    CharacterCreation.this.main.setPerso(CharacterCreation.this.perso);
                }
                CharacterCreation.this.main.setPerso(CharacterCreation.this.perso);
                main.mainStage.show();
                Stage stage = (Stage) save.getScene().getWindow();
                stage.close();
            }
        });

        del.setOnMouseClicked(event -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Confirmation");
            alert.setContentText("You are going back to the main with the default Character : Jiko, \n continue?");
            alert.getDialogPane().getScene().getStylesheets().add("/maze/MazeStyle.css");
            alert.getDialogPane().getStyleClass().add("root2");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                main.mainStage.show();
                Stage stage = (Stage) del.getScene().getWindow();
                stage.close();
            }

        });
    }

    /**
     * The function that sets the new selected character
     * @param indice the index of the new character
     */
    public void changeChar(int indice) {
 

        
        this.perso = listCharacter[indice];
        this.lab.setText(perso.getName() + "\n \n");
        this.pres.setImage(perso.getPresIm());


    }
}
