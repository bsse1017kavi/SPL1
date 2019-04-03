package spritePackage;

public class Enemy extends Sprite
{
    private double detectionRadius = 10;
    private double patrolRadius = 500;

    private double relativeX;

    private boolean atLeft = true;

    public Enemy( double health, double baseDamage, double attackRadius, double posX, double posY,double height,double width, double detectionRadius) {
        super(health, baseDamage, attackRadius, posX, posY,height,width);
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
        Protagonist hero = (Protagonist)obj;

        if(this.withinRange(obj) && obj.isAlive() && this.isAlive() && !hero.isDodging())
        {
            setStatus(STATUS.FIGHTING);
            hero.takeDamage(this.getBaseDamage());
        }

        else setStatus(STATUS.RIGHT_FACING);
    }


    public double getRelativeX() {
        return relativeX;
    }

    public void patrol(Protagonist obj)
    {
        if(!withinRange(obj))
        {
            double patrolSpeed = 2;

            if(relativeX<-patrolRadius) atLeft = false;
            else if(relativeX>patrolRadius) atLeft = true;

            if(atLeft)
            {
                this.translate(-(patrolSpeed));
                this.relativeX-=patrolSpeed;
            }

            else if(!atLeft)
            {
                //if(relativeX>100) atLeft=true;
                this.translate(patrolSpeed);
                this.relativeX+=patrolSpeed;
            }
        }
    }
}
