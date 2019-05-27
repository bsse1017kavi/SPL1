package graphicsPackage;

import javafx.scene.image.Image;
import spritePackage.AnimatedImage;
import spritePackage.STATUS;

public class Views
{
    private Image left_view;
    private Image right_view;
    private Image blockRight;
    private Image blockLeft;

    AnimatedImage leftMotion;
    AnimatedImage rightMotion;
    AnimatedImage fightMotion_right;
    AnimatedImage fightMotion_left;
    AnimatedImage jumpMotion;

    public Views(Image left_view, Image right_view, AnimatedImage leftMotion,
                 AnimatedImage rightMotion, AnimatedImage fightMotion_right,AnimatedImage fightMotion_left,
                 AnimatedImage jumpMotion,Image blockRight,Image blockLeft)
    {
        this.left_view = left_view;
        this.right_view = right_view;
        this.leftMotion = leftMotion;
        this.rightMotion = rightMotion;
        this.fightMotion_right = fightMotion_right;
        this.fightMotion_left = fightMotion_left;
        this.jumpMotion = jumpMotion;
        this.blockLeft = blockLeft;
        this.blockRight = blockRight;
    }

    public Image getView(STATUS status, double time)
    {
        if(status==STATUS.RIGHT_FACING) return  right_view;

        else if(status==STATUS.LEFT_FACING) return left_view;

        else if(status==STATUS.RIGHT_MOTION) return rightMotion.getFrame(time);

        else if(status==STATUS.LEFT_MOTION) return leftMotion.getFrame(time);

        else if(status==STATUS.FIGHTING_RIGHT) return fightMotion_right.getFrame(time);

        else if(status==STATUS.FIGHTING_LEFT) return  fightMotion_left.getFrame(time);

        //else if(status==6) return jumpMotion.getFrame(time);

        else if(status==STATUS.JUMPING_RIGHT) return right_view;

        else if(status==STATUS.JUMPING_LEFT) return  left_view;

        else if(status==STATUS.BLOCKING_RIGHT) return blockRight;

        else if(status==STATUS.BLOCKING_LEFT) return blockLeft;

        return null;
    }



}
