package graphicsPackage;

import backgroundPackage.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import mainPackage.ResourceLoader;
import spritePackage.AnimatedImage;
import spritePackage.Enemy;
import spritePackage.Protagonist;
import spritePackage.Sprite;
import java.util.HashMap;
import java.util.Map;

public class GraphicsEngine
{
    HashMap<Sprite,Views> imageMap = new HashMap<>();
    GraphicsContext gc;
    Background background;
    Image backgroundImage;

    Views hero_view;
    Views monster_view;

    public void render( double time)
    {
        gc.drawImage(backgroundImage,background.getBackgroundX(),background.getBackgroundY());
        gc.drawImage(backgroundImage,background.getBackgroundX()-900,background.getBackgroundY());
        gc.drawImage(backgroundImage,background.getBackgroundX()+900,background.getBackgroundY());

        for(Map.Entry imEntry:imageMap.entrySet())
        {
            Sprite sprite = (Sprite) imEntry.getKey();
            int status = sprite.getStatus();

            Views view = (Views) imEntry.getValue();

            if(sprite.isAlive() && status!=5)
            {
                gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY());
                draw_health_bar(sprite);
            }

            if(sprite.isAlive() && status==5)
            {
                gc.drawImage(view.getView(status,time),sprite.getPosX(),sprite.getPosY()-40);
                draw_health_bar(sprite);
            }

            if(sprite instanceof Protagonist)
            {
                Protagonist hero = (Protagonist) sprite;

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

        else
        {
            gc.setFill(Color.YELLOW);
            gc.fillRect(obj.getPosX()+100,obj.getPosY()-10,100,5);
            gc.setFill(Color.RED);
            gc.fillRect(obj.getPosX()+100,obj.getPosY()-10,100*obj.getPercentage()/100,5);
        }
    }

    public  GraphicsEngine(GraphicsContext gc,Protagonist hero, Enemy[] monsters, Background background, Image backgroundImage)
    {
        this.gc = gc;
        initialize();

        imageMap.put(hero,hero_view);

        for(int i=0;i<monsters.length;i++)
        {
            imageMap.put(monsters[i],monster_view);
        }

        this.background = background;
        this.backgroundImage = backgroundImage;

    }

    private void initialize()
    {
        String file_path = "Nilkomol/nilkomol";
        String monster_file_path = "monster/monster";

        Image hero_img_right = new Image(file_path+"_s.png");
        Image hero_img_left = new Image(file_path+"_l_s.png");

        Image monster_img = new Image(ResourceLoader.load("monster.png"));

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
            imageArr4[i] = new Image( ResourceLoader.load(file_path+ "_jump_" +  i + ".png") );
        }

        double duration = 0.100;

        AnimatedImage hero_right_motion = new AnimatedImage(imageArr,duration);

        AnimatedImage hero_left_motion = new AnimatedImage(imageArr1,duration);

        AnimatedImage hero_fight_motion = new AnimatedImage(imageArr2,duration);

        AnimatedImage monster_fight_motion = new AnimatedImage(imageArr3,duration);

        AnimatedImage hero_jump_motion = new AnimatedImage(imageArr4,duration);

        hero_view = new Views(hero_img_left,hero_img_right,hero_left_motion,hero_right_motion,hero_fight_motion,hero_jump_motion);

        monster_view = new Views(null,monster_img,null,null,monster_fight_motion,hero_jump_motion);

    }
}
