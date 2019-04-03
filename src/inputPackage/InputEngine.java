package inputPackage;

import backgroundPackage.Background;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import spritePackage.Enemy;
import spritePackage.Protagonist;
import spritePackage.STATUS;

import java.util.ArrayList;
import java.util.HashMap;

public class InputEngine
{
    HashMap<String,Boolean> input = new HashMap<String, Boolean>();

    Protagonist hero;

    Scene scene;

    boolean floating = false;

    double gravity = 2;



    public InputEngine(Protagonist hero, Scene scene) {
        this.hero = hero;
        this.scene = scene;
    }

    public void takeInput()
    {

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                if(!input.containsKey(code))input.put(code,true);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String code = event.getCode().toString();
                input.remove(code);
            }
        });
    }

    public void action(ArrayList<Enemy> monsters, Background background, double time)
    {
        if(hero.isDodging()) hero.setDodging(false);

        hero.stamina_regen();

        if(hero.getPosY()<=130) floating = true;
        if(hero.getPosY()>=230) floating = false;

        //Cheat Code: god mode
        if(input.containsKey("G"))
        {
            hero.godMode();
        }

        if(floating)
        {
            hero.ascend(-(gravity));
        }

        if(hero.getStatus()== STATUS.JUMPING && !input.containsKey("UP"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);
        }

        if(hero.getStatus()==STATUS.RIGHT_MOTION && !input.containsKey("RIGHT"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);
        }

        else if(hero.getStatus()==STATUS.LEFT_MOTION && !input.containsKey("LEFT"))
        {
            hero.setStatus(STATUS.LEFT_FACING);
        }

        //Jump right
        if(hero.getStatus()==STATUS.RIGHT_FACING && input.containsKey("RIGHT") && input.containsKey("UP"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);

            boolean move = true;

            if(!floating)
            {
                hero.ascend(100);
            }

            for(int i=0;i<monsters.size();i++)
            {
                if(hero.isIntersectedRight(monsters.get(i)) && monsters.get(i).isAlive()) move = false;
            }

            if(hero.getPosX()<background.getUpper_bound() && (hero.getDistance()<400 || hero.getDistance()>3000) && move)hero.translate(5);
            else if(!(hero.getDistance()<400 || hero.getDistance()>3000) && move)
            {
                background.translate(-5);
                for(int i=0;i<monsters.size();i++) monsters.get(i).translate(-5);
            }

            //if(hero.getDistance()<3420) hero.setDistance(hero.getDistance()+5);
            if(hero.getDistance()<4220) hero.setDistance(hero.getDistance()+5);
        }

        else if(hero.getStatus()==STATUS.FIGHTING && (!input.containsKey("CONTROL")))
        {
            hero.setStatus(STATUS.RIGHT_FACING);
        }

        /*else if(!input.containsKey("CONTROL") )
        {
            hero.setStatus(1);
            if(hero.getPosY()<=130) floating = true;
            else if(hero.getPosY()>=230) floating = false;
            if(floating)
            {
                hero.ascend(-(gravity));
            }
        }*/


        if(input.containsKey("LEFT") && !input.containsKey("CONTROL"))
        {
            //if((int)time%1==0) System.out.println(hero.getDistance());

            hero.setStatus(STATUS.LEFT_MOTION);

            if(hero.getPosX()>background.getLower_bound()&& (hero.getDistance()<400 || hero.getDistance()>4000))hero.translate(-5);
            else if(!(hero.getDistance()<400 || hero.getDistance()>4000))
            {
                background.translate(5);
                for(int i=0;i<monsters.size();i++) monsters.get(i).translate(5);
            }

            if(hero.getDistance()>0) hero.setDistance(hero.getDistance()-5);

        }

        else if(input.containsKey("RIGHT") && !input.containsKey("CONTROL") && !input.containsKey("UP"))
        {
            //if((int)time%1==0) System.out.println(hero.getDistance());

            hero.setStatus(STATUS.RIGHT_MOTION);

            boolean move = true;

            for(int i=0;i<monsters.size();i++)
            {
                if(hero.isIntersectedRight(monsters.get(i)) && monsters.get(i).isAlive()) move = false;
            }

            if(hero.getPosX()<background.getUpper_bound() && (hero.getDistance()<400 || hero.getDistance()>4000) && move)hero.translate(5);
            else if(!(hero.getDistance()<400 || hero.getDistance()>4000) && move)
            {
                background.translate(-5);
                for(int i=0;i<monsters.size();i++) monsters.get(i).translate(-5);
            }

            if(hero.getDistance()<5420) hero.setDistance(hero.getDistance()+5);

        }

        else if(hero.isAlive() && removeActiveKey("CONTROL")  && hero.getStamina()>15)
        {
            //hero.setStatus(5);

            for(int i = 0; i< monsters.size(); i++)
                hero.attack(monsters.get(i),time);

            hero.stamina_deplete(15);
        }

        else if(!input.containsKey("RIGHT") && removeActiveKey("UP"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);

            if(!floating)
            {
               hero.ascend(100);
            }

        }

        else if(input.containsKey("ALT") && hero.getStamina()>10)
        {
            hero.setDodging(true);
            hero.stamina_deplete(2.5);
        }

        else if(removeActiveKey("H"))
        {
            hero.heal();
        }

    }

    private boolean removeActiveKey(String codeString) {
        Boolean isActive = input.get(codeString);

        if (isActive != null && isActive) {
            input.put(codeString, false);
            return true;
        } else {
            return false;
        }
    }
}
