package mainPackage;

import backgroundPackage.Background;
import graphicsPackage.GraphicsEngine;
import inputPackage.InputEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import soundPackage.SoundEngine;
import spritePackage.Enemy;
import spritePackage.Protagonist;

import java.net.MalformedURLException;

public class GameEngine
{
    //GraphicsContext gc;
    GraphicsEngine graphicsEngine;
    InputEngine inputEngine;

    Protagonist hero;
    Enemy [] monsters = new Enemy[3];
    Background background;
    SoundEngine soundEngine;

    public GameEngine(GraphicsContext gc, Scene scene) throws MalformedURLException {
        hero =  new Protagonist(2000,150,100,0,230);
        monsters[0] = new Enemy(500,80/60.0,100,1000,200,300);
        monsters[1] = new Enemy(500,80/60.0,100,1600,200,300);
        monsters[2] = new Enemy(500,80/60.0,100,2500,200,300);

        Image backgroundImage = new Image("forest0.png");


        background = new Background(backgroundImage,0,0);

        graphicsEngine = new GraphicsEngine(gc,hero,monsters,background,backgroundImage);
        inputEngine = new InputEngine(hero,scene);
        soundEngine = new SoundEngine();
    }

    public void play()
    {
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                long currentNanoTime = System.nanoTime();
                double t = (currentNanoTime-startNanoTime)/1000000000.0;

                for(int i=0;i<3;i++)
                {
                    monsters[i].patrol(hero);
                    monsters[i].attack(hero,t);
                }

                soundEngine.playAudio();
                graphicsEngine.render(t);
                inputEngine.takeInput();
                inputEngine.action(monsters,background,t);
            }
        }.start();
    }
}
