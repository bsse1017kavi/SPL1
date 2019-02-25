package spritePackage;

import objectPackage.GameObject;

public abstract class Sprite extends GameObject
{
    private double health;
    private double baseDamage;
    private double attackRadius;

    private double percentage;

    public int getStatus() {
        return status;
    }

    private boolean alive = true;

    private double max_health;


    private double init_posX;

    boolean dir = false;

    public void setStatus(int status) {
        this.status = status;
    }

    int status = 1;



    public void translate(double x)
    {
        this.setPosX(this.getPosX()+x);
    }

    public double getPercentage() {
        return percentage = this.health/this.max_health*100;
    }

    public Sprite( double health, double baseDamage, double attackRadius, double posX, double posY,double height,double width)
    {
        super(posX,posY,height,width);
        this.health = health;
        this.baseDamage = baseDamage;
        this.attackRadius = attackRadius;
        this.init_posX = posX;
        this.max_health = health;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }


    public abstract  void attack(Sprite obj,double t);

    public void takeDamage(double damage)
    {
        if(health>0)this.health-=damage;
        else health=0;
        if(health<=0) setAlive(false);
    }


    public void setDir(boolean dir) {
        this.dir = dir;
    }

    /*public boolean isIntersectedRight(Sprite obj)
    {
        if((Math.abs(posX-obj.posX)<=5  )) return true;

        else return false;
    }*/

   /* public boolean isIntersectedLeft(Sprite obj)
    {
        if((Math.abs(posX-obj.posX-obj.getView_right().getWidth())<=5))return true;

        else return false;
    }*/


    public double getHealth() {
        return health;
    }

    public double getAttackRadius() {
        return attackRadius;
    }

    public void setHealth(double health) {
        this.health = health;
    }



    public double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public boolean withinRange(Sprite obj)
    {
        if(((Math.abs(this.getPosX()-obj.getPosX())<=this.attackRadius
                /*|| (Math.abs((this.posX)-(obj.posX+obj.getView_right().getWidth())))<=this.attackRadius)
                || (Math.abs((this.posX)-(obj.posX+obj.getView_left().getWidth())))<=this.attackRadius*/)))
            return true;

        return false;
    }

    public void ascend(double y)
    {
        setPosY(getPosY()-y);
    }

    public double getMax_health() {
        return max_health;
    }

    public void respawn()
    {
        setPosX(init_posX);
        setAlive(true);
        setHealth(max_health);
    }

}
