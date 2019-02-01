package spritePackage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Timer;

public class Enemy extends Sprite
{
    private double detectionRadius = 15;

    public Enemy(Image view_right, Image view_left, double health, double baseDamage, double attackRadius, double posX, double posY, AnimatedImage right_motion, AnimatedImage left_motion, double detectionRadius) {
        super(view_right, view_left, health, baseDamage, attackRadius, posX, posY, right_motion, left_motion);
        this.detectionRadius = detectionRadius;
    }

    public boolean detect(Sprite obj)
    {
        if((Math.abs((this.getPosX()-obj.getPosX()))<=detectionRadius) || (Math.abs(this.getPosX()+319-obj.getPosX()))<=detectionRadius)
        {
            return true;
        }

        else return false;
    }

    public void attack(Sprite obj)
    {
        if(this.withinRange(obj)) obj.takeDamage(this.getBaseDamage());
    }

    @Override
    public void draw(GraphicsContext gc)
    {
        if(this.getHealth()<=0) setAlive(false);

        if(isAlive())
        {
            gc.setFill(Color.YELLOW);
            gc.fillRect(this.getPosX()+100,this.getPosY()-10,100,5);
            gc.setFill(Color.RED);
            gc.fillRect(this.getPosX()+100,this.getPosY()-10,100*this.getPercentage()/100,5);

            if(!dir)gc.drawImage(this.getView_right(),getPosX(),getPosY());

            else gc.drawImage(this.getView_left(),getPosX(),getPosY());
        }

    }
}
