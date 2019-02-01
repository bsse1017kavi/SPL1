package mainPackage;

import backgroundPackage.Background;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
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
        String file_path = "file:resources/B-man/bMan";

        String audio = "resources/audio1.mp3";

        primaryStage.setTitle("Thakurmar Jhuli");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Scale scale = new Scale(1, 1, 0, 0);
        scale.xProperty().bind(root.widthProperty().divide(900));
        scale.yProperty().bind(root.heightProperty().divide(473));
        root.getTransforms().add(scale);

        Canvas canvas = new Canvas(900,473);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image hero_img_right = new Image(file_path+"_S.png");
        Image hero_img_left = new Image(file_path+"1_S.png");

        Image monster_img = new Image("file:resources/monster.png");

        Image background_image = new Image("file:resources/forest0.png");

        ArrayList<String> input = new ArrayList<String>();

        Image [] imageArr = new Image[8];

        for(int i=0;i<8;i++)
        {
            imageArr[i] = new Image(file_path+ "_" + i + ".png" );
        }

        Image [] imageArr1 = new Image[8];

        for(int i=0;i<8;i++)
        {
            imageArr1[i] = new Image(file_path+ "1_" + i + ".png" );
        }

        double duration = 0.100;

        AnimatedImage hero_right_motion = new AnimatedImage(imageArr,duration);

        AnimatedImage hero_left_motion = new AnimatedImage(imageArr1,duration);

        Enemy [] monsters = new Enemy[3];

        Background background = new Background(background_image,0,0,audio);

        Protagonist hero = new Protagonist(hero_img_right, hero_img_left,2000,300/60.0,100,0,270,hero_right_motion , hero_left_motion, input);

        monsters[0] = new Enemy(monster_img,null,500,100/60.0,100,1000,200,null,null,100);
        monsters[1] = new Enemy(monster_img,null,500,100/60.0,100,1600,200,null,null,100);
        monsters[2] = new Enemy(monster_img,null,500,100/60.0,100,2500,200,null,null,100);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                String code = event.getCode().toString();
                if(!input.contains(code))hero.getInput().add(code);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent event)
            {
                String code = event.getCode().toString();
                hero.getInput().remove(code);
            }
        });

        final long startNanoTime = System.nanoTime();


        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {

                long currentNanoTime = System.nanoTime();

                double t = (currentNanoTime - startNanoTime)/1000000000.0;
                gc.clearRect(0,0,512,512);

                background.play();

                background.draw(gc);

                //gc.setFill(Color.TRANSPARENT);
                for(int i=0;i<3;i++)
                {
                    monsters[i].draw(gc);
                    if((int)t%3==0 && monsters[i].isAlive()) monsters[i].attack(hero);
                    if((int)t%2==0) hero.attack(monsters[i]);
                }
                if(hero.isAlive() && !hero.move(gc,t,background,monsters))hero.draw(gc);

                if(hero.getDistance()>=3400)
                {
                    gc.setFill(Color.CYAN);
                    gc.setFont(Font.font(100));
                    gc.fillText("LEVEL CLEAR",200,200);
                }

                if(!hero.isAlive())
                {
                    gc.setFill(Color.RED);
                    gc.setFont(Font.font(100));
                    gc.fillText("YOU DIED",250,200);
                    /*if((int)t%3==0)
                    {
                        background.reload();
                        hero.respawn();
                        monster.respawn();
                    }*/
                }

            }
        }.start();

        primaryStage.setResizable(true);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static  void main(String [] args)
    {
        launch(args);
    }
}