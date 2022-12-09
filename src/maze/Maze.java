///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package maze;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ThreadFactory;
//import java.util.concurrent.TimeUnit;
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;
//
///** 
// *
// * @author adele
// */
//public class Maze extends Application {
////
//    public Control ct;
//    private Boolean boucle = false;
//    private CreateMaze maze;
//    public ScheduledExecutorService service;
//////
//    @Override
//    public void start(Stage primaryStage) {
////
//        maze = new CreateMaze();
//        Scene scene = new Scene(maze, 1000, 600);
//        scene.getStylesheets().add("/maze/MazeStyle.css");
//        primaryStage.setTitle("Create a maze");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
////    }
////Game
//    int[] pos = new int[2];
//    pos[0] = 4;
//    pos[1] = 3;
//
//        Map mp = new Map(9, 7, 6, pos);
//    ct  = new Control(mp, this, 3);
//
//    Scene scene = new Scene(ct.windowG, 1000, 600);
//
//    scene.getStylesheets ()
//
//     .add("/maze/MazeStyle.css");
//    ct.pause (scene, ct);
//
//    ct.startGame (scene, ct);
//
//    primaryStage.setTitle (
//
//    "Maze!");
//    primaryStage.setScene (scene);
//
//    primaryStage.show ();
//}
//
////scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
////
////            public void handle(KeyEvent ke) {
////                if (ke.getCode() == KeyCode.SPACE && boucle == false) {
////                    service = Executors
////                            .newSingleThreadScheduledExecutor(
////                                    new ThreadFactory() {
////                                public Thread newThread(Runnable r) {
////                                    Thread t = Executors.defaultThreadFactory().newThread(r);
////                                    t.setDaemon(true);
////                                    return t;
////                                }
////                            });
////
////                    service.scheduleAtFixedRate(runnable, 0, 4, TimeUnit.SECONDS);
////                    boucle = true;
////
////                }
////            }
////        });
////
////}
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    Runnable runnable = new Runnable() {
//        public void run() {
//            // task to run goes here
//            ct.move();
//        }
//    };
//
//}
