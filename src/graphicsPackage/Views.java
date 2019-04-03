package graphicsPackage;

import javafx.scene.image.Image;
import mainPackage.ResourceLoader;
import spritePackage.AnimatedImage;
import spritePackage.STATUS;

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

    public Image getView(STATUS status, double time)
    {
        if(status==STATUS.RIGHT_FACING) return  right_view;

        else if(status==STATUS.LEFT_FACING) return left_view;

        else if(status==STATUS.RIGHT_MOTION) return rightMotion.getFrame(time);

        else if(status==STATUS.LEFT_MOTION) return leftMotion.getFrame(time);

        else if(status==STATUS.FIGHTING) return fightMotion.getFrame(time);

        //else if(status==6) return jumpMotion.getFrame(time);

        else if(status==STATUS.JUMPING) return right_view;

        return null;
    }



}
