package objectPackage;

public class GameObject
{
    private double posX;
    private double posY;
    private double height;

    public GameObject(double posX, double posY, double height, double width) {
        this.posX = posX;
        this.posY = posY;
        this.height = height;
        this.width = width;
    }

    private double width;

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
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


    public boolean isIntersectedRight(GameObject obj)
    {
        if((Math.abs(posX-obj.posX)<=5  )) return true;

        else return false;
    }

     public boolean isIntersectedLeft(GameObject obj,double length)
    {
        if((Math.abs(posX-obj.posX-length)<=5))return true;

        else return false;
    }
}
