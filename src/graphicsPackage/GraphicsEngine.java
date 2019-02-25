package graphicsPackage;

import backgroundPackage.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mainPackage.ResourceLoader;
import objectPackage.GameObject;
import spritePackage.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphicsEngine
{
    HashMap<GameObject,Views> imageMap = new HashMap<>();
    GraphicsContext gc;
    Background background;
    Image [] backgroundImage;

    Views hero_view;
    Views monster_view;
    Views boss_view;

    public void render( double time)
    {
        //Rendering background
        for(int i=0;i<2;i++)
        {
            double positionX = background.getBackgroundX();
            if(i<1)
            {
                gc.drawImage(backgroundImage[i],positionX*(i+1),background.getBackgroundY());
                gc.drawImage(backgroundImage[i],(positionX-900)*(i+1),background.getBackgroundY());
                gc.drawImage(backgroundImage[i],(positionX+900)*(i+1),background.getBackgroundY());
            }

            else
            {
                gc.drawImage(backgroundImage[i],(positionX+backgroundImage[0].getWidth()),background.getBackgroundY());
                //gc.drawImage(backgroundImage[i],(positionX-900)*(i+1),background.getBackgroundY());
                gc.drawImage(backgroundImage[i],(positionX+backgroundImage[0].getWidth())+900,background.getBackgroundY());
            }

        }

        //Rendering sprites
        for(Map.Entry imEntry:imageMap.entrySet())
        {
            Sprite sprite = (Sprite) imEntry.getKey();
            int status = sprite.getStatus();

            Views view = (Views) imEntry.getValue();

            if(sprite.isAlive() && status!=5)
            {
                //gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY(),sprite.getHeight(),sprite.getWidth());
                if(sprite instanceof Boss)gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY()-40);
                else gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY());
                draw_health_bar(sprite);
            }

            else if(sprite.isAlive() && status==5)
            {
                gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY()-40);

                draw_health_bar(sprite);
            }

            if(sprite instanceof Protagonist)
            {
                Protagonist hero = (Protagonist) sprite;

                if(hero.getDistance()>=4400)
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
                }
            }
        }
    }

    public void draw_health_bar(Sprite obj)
    {
        if(obj instanceof Protagonist)
        {
            gc.setFill(Color.YELLOW);
            gc.fillRect(20,20,100,10);
            gc.setFill(Color.RED);
            gc.fillRect(20,20,100*obj.getPercentage()/100,10);
        }

        else if(!(obj instanceof Boss) && obj instanceof Enemy)
        {
            gc.setFill(Color.YELLOW);
            gc.fillRect(obj.getPosX()+100,obj.getPosY()-10,100,5);
            gc.setFill(Color.RED);
            gc.fillRect(obj.getPosX()+100,obj.getPosY()-10,100*obj.getPercentage()/100,5);
        }

        else if((obj instanceof Boss))
        {
            gc.setFill(Color.YELLOW);
            gc.fillRect(100,453,700,5);
            gc.setFill(Color.RED);
            gc.fillRect(100,453,700*obj.getPercentage()/100,5);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font(20));
            gc.fillText("Shakchunni, Reaper of the forest",100,442);
        }
    }

    public  GraphicsEngine(GraphicsContext gc, Protagonist hero, ArrayList<Enemy> monsters, Background background)
    {
        this.gc = gc;
        initialize();

        imageMap.put(hero,hero_view);

        for(Enemy enemy: monsters)
        {
            if(enemy instanceof Boss)imageMap.put(enemy,boss_view);
            else imageMap.put(enemy,monster_view);
            //imageMap.put(enemy,monster_view);
        }

        this.background = background;

    }

    private void initialize()
    {
        String file_path = "Nilkomol/nilkomol";
        String monster_file_path = "monster/monster";

        Image hero_img_right = new Image(file_path+"_s.png");
        Image hero_img_left = new Image(file_path+"_l_s.png");

        Image monster_img = new Image(ResourceLoader.load("monster.png"));


        backgroundImage = new Image[2];

        for(int i=0;i<1;i++)
        {
            backgroundImage[0] = new Image(ResourceLoader.load("forest0.png"));
           backgroundImage[1] = new Image(ResourceLoader.load("forest1.jpg"));
        }

        Image [] imageArr = new Image[8];

        for(int i=0;i<8;i++)
        {
            imageArr[i] = new Image(ResourceLoader.load(file_path +"_" + i + ".png" ));
        }

        Image [] imageArr1 = new Image[8];

        for(int i=0;i<8;i++)
        {
            imageArr1[i] = new Image(ResourceLoader.load(file_path + "_l_" + i + ".png" ));
        }

        Image [] imageArr2 = new Image[5];

        for(int i=0;i<5;i++)
        {
            imageArr2[i] = new Image(ResourceLoader.load(file_path + "fight_" + i + ".png" ));
        }

        Image [] imageArr3 = new Image[7];

        for(int i=0;i<7;i++)
        {
            imageArr3[i] = new Image( ResourceLoader.load("monster/monster_"+ + i + ".png") );
        }

        Image [] imageArr4 = new Image[8];

        for(int i=0;i<8;i++)
        {
            //imageArr4[i] = new Image( ResourceLoader.load(file_path+ "_jump_" +  i + ".png") );
            imageArr4[i] = hero_img_right;
        }

        Image boss_img = new Image("file:D:/SPL1/resources/shakchunni_gif.gif");

        Image [] imageArr5 = new Image[1];

        for(int i=0;i<1;i++)
        {
            //imageArr4[i] = new Image( ResourceLoader.load(file_path+ "_jump_" +  i + ".png") );
            imageArr5[i] = boss_img;
        }

        double duration = 0.100;

        AnimatedImage hero_right_motion = new AnimatedImage(imageArr,duration);

        AnimatedImage hero_left_motion = new AnimatedImage(imageArr1,duration);

        AnimatedImage hero_fight_motion = new AnimatedImage(imageArr2,duration);

        AnimatedImage monster_fight_motion = new AnimatedImage(imageArr3,duration);

        AnimatedImage hero_jump_motion = new AnimatedImage(imageArr4,duration);



        AnimatedImage boss_fight_motion = new AnimatedImage(imageArr5,duration);

        hero_view = new Views(hero_img_left,hero_img_right,hero_left_motion,hero_right_motion,hero_fight_motion,hero_jump_motion);

        monster_view = new Views(null,monster_img,null,null,monster_fight_motion,hero_jump_motion);

        boss_view = new Views(null,boss_img,null,null,boss_fight_motion,null);


    }
}
