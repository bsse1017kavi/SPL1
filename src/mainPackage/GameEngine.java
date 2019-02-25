package mainPackage;

import backgroundPackage.Background;
import graphicsPackage.GraphicsEngine;
import inputPackage.InputEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import soundPackage.SoundEngine;
import spritePackage.Boss;
import spritePackage.Enemy;
import spritePackage.Protagonist;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class GameEngine
{
    //GraphicsContext gc;
    GraphicsEngine graphicsEngine;
    InputEngine inputEngine;

    Protagonist hero;
    ArrayList<Enemy> monsters = new ArrayList<>();
    Background background;
    SoundEngine soundEngine;

    public GameEngine(GraphicsContext gc, Scene scene) throws MalformedURLException {
        hero =  new Protagonist(2000,150,100,0,230,150,200);
        monsters.add( new Enemy(500, 80 / 60.0, 100, 1000, 200, 319, 308, 300));
        monsters.add( new Enemy(500, 80 / 60.0, 100, 1600, 200, 319, 308, 300));
        monsters.add( new Enemy(500, 80 / 60.0, 100, 2700, 200, 319, 308, 300));
       // monsters.add(new Enemy(500,80/60.0,100,3200,200,319,308,300));
        //monsters.add(new Enemy(500,80/60.0,100,800,200,319,308,300));
        //monsters[3] = new Enemy(500,80/60.0,100,700,200,319,308,300);
        Boss shakchunni = new Boss(2500,380/60.0,200,3500,200,500,500,300);
        monsters.add(shakchunni);
        //monsters[3] =  new Enemy(2500,380/60.0,200,2200,200,500,500,300);

        Image backgroundImage = new Image("forest0.png");


        background = new Background(0,0);

        graphicsEngine = new GraphicsEngine(gc,hero,monsters,background);
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

                for(int i = 0; i< monsters.size(); i++)
                {
                    monsters.get(i).patrol(hero);
                    monsters.get(i).attack(hero,t);
                }

                if(hero.getDistance()>2500)soundEngine.playAudio(2);
                else soundEngine.playAudio(1);
                graphicsEngine.render(t);
                inputEngine.takeInput();
                inputEngine.action(monsters,background,t);
            }
        }.start();
    }
}
