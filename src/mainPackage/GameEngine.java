package mainPackage;

import backgroundPackage.Background;
import graphicsPackage.GraphicsEngine;
import inputPackage.InputEngine;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import soundPackage.SoundEngine;
import spritePackage.Boss;
import spritePackage.Enemy;
import spritePackage.Protagonist;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class GameEngine extends Thread
{
    GraphicsContext gc;
    GraphicsEngine graphicsEngine;
    InputEngine inputEngine;
    Scene scene;

    Protagonist hero;
    ArrayList<Enemy> monsters = new ArrayList<>();
    Boss shakchunni;
    Background background;
    SoundEngine soundEngine;

    private int level;

    private boolean isPaused = false;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GameEngine(GraphicsContext gc, Scene scene, int level) throws MalformedURLException {
        this.level = level;
        this.gc = gc;
        this.scene = scene;
        initialize_by_level();
    }

    public void play()
    {
        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                if(graphicsEngine.getLevel()!=level)
                {
                    level = graphicsEngine.getLevel();
                    for(MediaPlayer mediaPlayer:soundEngine.getMediaPlayers())
                    {
                        mediaPlayer.stop();
                    }
                    //soundEngine.setLevel(graphicsEngine.getLevel());
                    initialize_by_level();
                }

                if(!hero.isAlive())
                {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    background.reload();
                    hero.respawn();
                    for(Enemy enemy:monsters)
                    {
                        enemy.respawn();
                    }
                    graphicsEngine.setSignal(false);
                }

                long currentNanoTime = System.nanoTime();
                double t = (currentNanoTime-startNanoTime)/1000000000.0;

                for(int i = 0; i< monsters.size(); i++)
                {
                    monsters.get(i).patrol(hero);
                    monsters.get(i).attack(hero,t);
                }

                /*if(hero.getDistance()>2700 && level==1)
                {
                    soundEngine.playAudio();
                    //System.out.println("Something wrong");
                }
                else if(hero.getDistance()<=2700 && level==1) soundEngine.playAudio(1);
                else if(hero.getDistance()<=2700 && level==2)
                {
                    soundEngine.playAudio(3);
                    //System.out.println("Something right");
                }
                else soundEngine.playAudio(4);*/

                if(shakchunni.withinRange(hero))
                {
                    graphicsEngine.setSignal(true);
                }

                if(hero.getDistance()<2700)
                {
                    soundEngine.playAudio(true);
                }

                else
                {
                    soundEngine.playAudio(false);
                }

                graphicsEngine.render(t);
                inputEngine.takeInput();
                inputEngine.action(monsters,background,t);

            }
        }.start();
    }

    public synchronized void initialize_by_level()
    {
        if(this.level==1)
        {
            hero =  new Protagonist(2000,150,100,0,230,150,200);
            monsters.add( new Enemy(500, 180 / 60.0, 100, 1000, 200, 319, 308, 300));
            monsters.add( new Enemy(500, 180 / 60.0, 100, 1600, 200, 319, 308, 300));
            monsters.add( new Enemy(500, 180 / 60.0, 100, 2700, 200, 319, 308, 300));
            // monsters.add(new Enemy(500,80/60.0,100,3200,200,319,308,300));
            //monsters.add(new Enemy(500,80/60.0,100,800,200,319,308,300));
            //monsters[3] = new Enemy(500,80/60.0,100,700,200,319,308,300);
            shakchunni = new Boss(2500,380/60.0,150,3500,200,500,500,300);
            monsters.add(shakchunni);
            //monsters[3] =  new Enemy(2500,380/60.0,200,2200,200,500,500,300);

            Image backgroundImage = new Image("forest0.png");


            background = new Background(0,0);

            graphicsEngine = new GraphicsEngine(gc,hero,monsters,background,level);
            //graphicsEngine = new GraphicsEngine(gc,hero,monsters,background);
            inputEngine = new InputEngine(hero,scene);
            soundEngine = new SoundEngine(level);
        }

        if(this.level==2)
        {
            hero =  new Protagonist(2000,150,100,0,230,150,200);
            monsters.add( new Enemy(750, 380 / 60.0, 100, 1000, 200, 319, 308, 300));
            monsters.add( new Enemy(750, 380 / 60.0, 100, 1600, 200, 319, 308, 300));
            monsters.add( new Enemy(750, 380 / 60.0, 100, 2700, 200, 319, 308, 300));
            // monsters.add(new Enemy(500,80/60.0,100,3200,200,319,308,300));
            //monsters.add(new Enemy(500,80/60.0,100,800,200,319,308,300));
            //monsters[3] = new Enemy(500,80/60.0,100,700,200,319,308,300);
            shakchunni = new Boss(3500,680/60.0,150,3500,200,500,500,300);
            monsters.add(shakchunni);
            //monsters[3] =  new Enemy(2500,380/60.0,200,2200,200,500,500,300);

            Image backgroundImage = new Image("underwater0.png");


            background = new Background(0,0);

            graphicsEngine = new GraphicsEngine(gc,hero,monsters,background,level);
            //graphicsEngine = new GraphicsEngine(gc,hero,monsters,background);
            inputEngine = new InputEngine(hero,scene);
            soundEngine = new SoundEngine(level);
        }
    }

    @Override
    public void run() {

    }
}
