package spritePackage;

import backgroundPackage.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Protagonist extends Sprite
{
    private ArrayList<String> input;

    private double distance = 0;

    public void drawHealthBar(GraphicsContext gc)
    {
        gc.setFill(Color.YELLOW);
        gc.fillRect(20,20,100,10);
        gc.setFill(Color.RED);
        gc.fillRect(20,20,100*this.getPercentage()/100,10);
    }

    public Protagonist(Image view_right, Image view_left, double health, double baseDamage, double attackRadius, double posX, double posY, AnimatedImage right_motion,AnimatedImage left_motion, AnimatedImage fight_motion, ArrayList<String> input) {
        super(view_right, view_left, health, baseDamage, attackRadius, posX, posY, right_motion, left_motion,fight_motion);
        this.input = input;
    }

    public ArrayList<String> getInput() {
        return input;
    }

    public double getDistance() {
        return distance;
    }

    public boolean move(GraphicsContext gc, double t, Background background,Enemy[] monsters)
    {
        drawHealthBar(gc);

        if(!isAlive()) return false;

        if(input.contains("LEFT") && !input.contains("CONTROL"))
        {
            for(int i=0;i<3;i++)
            {
                if(this.isIntersectedLeft(monsters[i]) && monsters[i].isAlive())
                {
                    animate(gc,t);
                    setDir(true);
                    return true;
                }
            }

            if(getPosX()>background.getLower_bound()&& (distance<400 || distance>3000))translate(-5);
            else if(!(distance<400 || distance>3000))
            {
                background.translate(5);
                for(int i=0;i<3;i++)monsters[i].translate(5);
            }
            animate(gc,t);
            setDir(true);
            if(distance>0) distance-=5;
            return true;
        }

        else if(input.contains("RIGHT") && !input.contains("CONTROL"))
        {
            for(int i=0;i<3;i++)
            {
                if(this.isIntersectedRight(monsters[i]) && monsters[i].isAlive())
                {
                    animate(gc,t);
                    setDir(false);
                    return true;
                }
            }
            if(getPosX()<background.getUpper_bound() && (distance<400 || distance>3000))translate(5);
            else if(!(distance<400 || distance>3000))
            {
                background.translate(-5);
                for(int i=0;i<3;i++)monsters[i].translate(-5);
            }
            animate(gc,t);
            setDir(false);
            if(distance<3420) distance+=5;
            return true;
        }


        return false;
    }

    @Override
    public void attack(Sprite obj,GraphicsContext gc,double t)
    {
        if(this.getHealth()<=0) setAlive(false);
        if(isAlive() && input.contains("CONTROL"))
        {
            fight_animate(gc,t);
            if(withinRange(obj) && ((int)t%1==0))obj.takeDamage(this.getBaseDamage());
        }
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.getHealth()<=0) setAlive(false);

        if(isAlive())
        {
            drawHealthBar(gc);

            if(!dir)gc.drawImage(this.getView_right(),getPosX(),getPosY());

            else gc.drawImage(this.getView_left(),getPosX(),getPosY());
        }

    }
}
