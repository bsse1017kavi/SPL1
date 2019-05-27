package spritePackage;

public class Protagonist extends Sprite
{
    private double distance = 0;

    private boolean dodging = false;

    private double stamina = 100;

    private double focus = 0;

    public double getFocus() {
        return focus;
    }

    public Protagonist(double health, double baseDamage, double attackRadius, double posX, double posY, double height, double width) {
        super( health, baseDamage, attackRadius, posX, posY,height,width);
    }

    public double getStamina() {
        return stamina;
    }

    public void generate_focus(double amount)
    {
        if(focus<100)focus+=amount;

        else focus = 100;
    }

    public void heal()
    {
        if(focus==100)
        {
            double healed_amount = this.getHealth()+1000;
            if(healed_amount >= this.getMax_health()) healed_amount = this.getMax_health();
            setHealth(healed_amount);
            focus = 0;
        }
    }

    public void setStamina(double stamina) {
        this.stamina = stamina;
    }

    public void stamina_deplete(double amount)
    {
        this.stamina-=amount;
    }

    public void stamina_regen()
    {
        if(stamina<=100)
        {
            stamina+=0.5;
        }
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
        setHealth(1000000000);
    }

    public boolean isDodging() {
        return dodging;
    }

    public void setDodging(boolean dodging) {
        this.dodging = dodging;
    }

    @Override
    public void attack(Sprite obj,double t)
    {
        if(this.getHealth()<=0) setAlive(false);
        //setStatus(STATUS.FIGHTING_RIGHT);
        if(withinRange(obj) && ((int)t%1==0) && obj.isAlive())
        {
            obj.takeDamage(this.getBaseDamage());
            generate_focus(5);
        }
    }

    @Override
    public void respawn()
    {
        super.respawn();
        setDistance(0);
        setFocus(0);
    }

    public void setFocus(double focus) {
        this.focus = focus;
    }
}
