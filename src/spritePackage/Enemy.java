package spritePackage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Timer;

public class Enemy extends Sprite
{
    private double detectionRadius = 10;
    private double patrolRadius = 500;

    private double relativeX;

    private boolean chk = true;

    public Enemy( double health, double baseDamage, double attackRadius, double posX, double posY, double detectionRadius) {
        super(health, baseDamage, attackRadius, posX, posY);
        this.detectionRadius = detectionRadius;
        this.relativeX = 0;
    }

    public boolean detect(Protagonist obj)
    {
        if((Math.abs((this.getPosX()-obj.getPosX()))<=detectionRadius) /*|| (Math.abs(this.getPosX()+319-obj.getPosX()))<=detectionRadius*/)
        {
            return true;
        }

        else return false;
    }

    @Override
    public void attack(Sprite obj,double t)
    {

        if(this.withinRange(obj) && obj.isAlive() && this.isAlive())
        {
            setStatus(5);
            obj.takeDamage(this.getBaseDamage());
        }

        else setStatus(1);
    }


    public double getRelativeX() {
        return relativeX;
    }

    public void patrol(Protagonist obj)
    {
        if(!withinRange(obj))
        {
            double patrolSpeed = 2;

            if(relativeX<-patrolRadius) chk = false;
            else if(relativeX>patrolRadius) chk = true;

            if( chk)
            {
                this.translate(-(patrolSpeed));
                this.relativeX-=patrolSpeed;
            }

            else  if(!chk)
            {
                //if(relativeX>100) chk=true;
                this.translate(patrolSpeed);
                this.relativeX+=patrolSpeed;
            }
        }
    }
}
