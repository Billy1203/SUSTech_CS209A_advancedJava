package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    /*@Override
    public void start(Stage primaryStage) throws Exception{
        *//*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*//*
        //creating a Group object
        Group group = new Group();

        Line line = new Line();
        line.setStartX(100.0);
        line.setEndY(150.0);
        line.setEndX(500.0);
        line.setEndY(170.0);
        Group root = new Group(line);

        //Creating a Scene by passing the group object, height and width
        Scene scene = new Scene(group, 600, 300);

        //setting color to the scene
        scene.setFill(Color.BROWN);

        //setting the title to Stage
        primaryStage.setTitle("Sample Application");

        //adding the scene to stage
        primaryStage.setScene(scene);

        primaryStage.show();
    }*/

    @Override
    public void start(Stage primaryStage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            primaryStage.setTitle("My Application");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
