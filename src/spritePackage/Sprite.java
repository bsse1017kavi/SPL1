package spritePackage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Sprite
{
    private Image view_right;
    private Image view_left;
    private double health;
    private double baseDamage;
    private double attackRadius;

    private double percentage;

    private double posX;
    private double posY;

    private boolean alive = true;

    private double max_health;


    AnimatedImage right_motion;
    AnimatedImage left_motion;
    AnimatedImage fight_motion;

    private double init_posX;

    boolean dir = false;

    public void translate(double x)
    {
        this.setPosX(this.getPosX()+x);
    }

    public double getPercentage() {
        return percentage = this.health/this.max_health*100;
    }

    public Sprite(Image view_right, Image view_left, double health, double baseDamage, double attackRadius, double posX, double posY, AnimatedImage right_motion, AnimatedImage left_motion,AnimatedImage fight_motion) {
        this.view_right = view_right;
        this.view_left = view_left;
        this.health = health;
        this.baseDamage = baseDamage;
        this.attackRadius = attackRadius;
        this.posX = posX;
        this.posY = posY;
        this.right_motion = right_motion;
        this.left_motion = left_motion;
        this.init_posX = posX;
        this.max_health = health;
        this.fight_motion = fight_motion;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }

    public Image getView_left() {
        return view_left;
    }

    abstract public void draw(GraphicsContext gc);


    public void animate(GraphicsContext gc,double t)
    {
        if(!dir)gc.drawImage(right_motion.getFrame(t),posX,posY);

        else gc.drawImage(left_motion.getFrame(t),posX,posY);
    }

    public void fight_animate(GraphicsContext gc,double t)
    {
        gc.drawImage(fight_motion.getFrame(t),posX,posY-50);
    }

    public abstract  void attack(Sprite obj,GraphicsContext gc,double t);

    public void takeDamage(double damage)
    {
        if(health>0)this.health-=damage;
        else health=0;
    }


    public void setDir(boolean dir) {
        this.dir = dir;
    }

    public boolean isIntersectedRight(Sprite obj)
    {
        if((Math.abs(posX-obj.posX)<=5  )) return true;

        else return false;
    }

    public boolean isIntersectedLeft(Sprite obj)
    {
        if((Math.abs(posX-obj.posX-obj.getView_right().getWidth())<=5))return true;

        else return false;
    }

    public Image getView_right() {
        return view_right;
    }

    public void setView_right(Image view_right) {
        this.view_right = view_right;
    }

    public double getHealth() {
        return health;
    }

    public double getAttackRadius() {
        return attackRadius;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(double baseDamage) {
        this.baseDamage = baseDamage;
    }

    public boolean withinRange(Sprite obj)
    {
        if(((Math.abs(this.getPosX()-obj.posX))<=this.attackRadius /*|| (Math.abs((this.posX)-(obj.posX+obj.getView_right().getWidth())))<=this.attackRadius) || (Math.abs((this.posX)-(obj.posX+obj.getView_left().getWidth())))<=this.attackRadius*/))
            return true;

        return false;
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
