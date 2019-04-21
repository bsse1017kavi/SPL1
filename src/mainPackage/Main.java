package mainPackage;

import backgroundPackage.Background;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import spritePackage.AnimatedImage;
import spritePackage.Enemy;
import spritePackage.Protagonist;

import java.util.ArrayList;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setTitle("Thakurmar Jhuli");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        //primaryStage.setScene(scene);

        Parent root0 = FXMLLoader.load(getClass().getResource("/scene1.fxml"));
        Scene scene0 = new Scene(root0);
        primaryStage.setScene(scene0);

        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(900));
        scale.yProperty().bind(root.heightProperty().divide(473));
        root.getTransforms().add(scale);

        Canvas canvas = new Canvas(900,473);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameEngine gameEngine = new GameEngine(gc,scene,1);
        //gameEngine.play();

        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        //primaryStage.setFullScreen(false);
        primaryStage.show();
    }

    public static  void main(String [] args)
    {
        launch(args);
    }
}
