package backgroundPackage;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import mainPackage.ResourceLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Background
{
   // private Image[] view;
    private double backgroundX;
    private double backgroundY;

    private double init_posX;

    private double lower_bound = 10;
    private double upper_bound = 800;

    public void translate(double x)
    {
        this.backgroundX+=x;
    }

    /*public void setView(Image [] view) {
        this.view = view;
    }*/

    public void setBackgroundX(double backgroundX) {
        this.backgroundX = backgroundX;
    }

    public void setBackgroundY(double backgroundY) {
        this.backgroundY = backgroundY;
    }

    public double getLower_bound() {
        return lower_bound;
    }

    public void setLower_bound(double lower_bound) {
        this.lower_bound = lower_bound;
    }

    public double getUpper_bound() {
        return upper_bound;
    }

    public void setUpper_bound(double upper_bound) {
        this.upper_bound = upper_bound;
    }

  /*  public void draw(GraphicsContext gc)
    {
        //if(backgroundX<-890 || backgroundX>890) backgroundX = 900;
        gc.drawImage(view[0],backgroundX,backgroundY);
        gc.drawImage(view[0],backgroundX-900,backgroundY);
        gc.drawImage(view[0],backgroundX+900,backgroundY);
    }*/

    public double getBackgroundX()
    {
        return backgroundX;
    }

    public Background(double backgroundX, double backgroundY)
    {
        this.backgroundX = backgroundX;
        this.backgroundY = backgroundY;
        init_posX = backgroundX;
    }

    public double getBackgroundY() {
        return backgroundY;
    }



    public void reload()
    {
        setBackgroundX(init_posX);
    }
}
