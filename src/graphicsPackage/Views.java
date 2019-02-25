package graphicsPackage;

import javafx.scene.image.Image;
import mainPackage.ResourceLoader;
import spritePackage.AnimatedImage;

import java.util.ArrayList;

public class Views
{
    private Image left_view;
    private Image right_view;

    AnimatedImage leftMotion;
    AnimatedImage rightMotion;
    AnimatedImage fightMotion;
    AnimatedImage jumpMotion;

    public Views(Image left_view, Image right_view, AnimatedImage leftMotion,
                 AnimatedImage rightMotion, AnimatedImage fightMotion,AnimatedImage jumpMotion)
    {
        this.left_view = left_view;
        this.right_view = right_view;
        this.leftMotion = leftMotion;
        this.rightMotion = rightMotion;
        this.fightMotion = fightMotion;
        this.jumpMotion = jumpMotion;
    }

    public Image getView(int status, double time)
    {
        if(status==1) return  right_view;

        else if(status==2) return left_view;

        else if(status==3) return rightMotion.getFrame(time);

        else if(status==4) return leftMotion.getFrame(time);

        else if(status==5) return fightMotion.getFrame(time);

        //else if(status==6) return jumpMotion.getFrame(time);

        else if(status==6) return right_view;

        return null;
    }



}
