package spritePackage;

import backgroundPackage.Background;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Protagonist extends Sprite
{
    private double distance = 0;


    public Protagonist( double health, double baseDamage, double attackRadius, double posX, double posY,double height,double width) {
        super( health, baseDamage, attackRadius, posX, posY,height,width);
    }


    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void godMode()
    {
        setBaseDamage(1000000000);
    }


    @Override
    public void attack(Sprite obj,double t)
    {
        if(this.getHealth()<=0) setAlive(false);
        setStatus(5);
        if(withinRange(obj) && ((int)t%1==0))obj.takeDamage(this.getBaseDamage());
    }


}
