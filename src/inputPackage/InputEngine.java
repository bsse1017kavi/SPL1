package inputPackage;

import backgroundPackage.Background;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import spritePackage.Enemy;
import spritePackage.Protagonist;
import java.util.HashMap;

public class InputEngine
{
    HashMap<String,Boolean> input = new HashMap<String, Boolean>();

    Protagonist hero;

    Scene scene;

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

    public void action(Enemy [] monsters, Background background,double time)
    {
        if((int)time%3==0 && hero.getPosY()<=200)
        {
            hero.setStatus(1);
            hero.setPosY(230);
        }

        if(hero.getStatus()==3 && !input.containsKey("RIGHT"))
        {
            hero.setStatus(1);
        }

        else if(hero.getStatus()==4 && !input.containsKey("LEFT"))
        {
            hero.setStatus(2);
        }

        else if(hero.getStatus()==5 && (!input.containsKey("CONTROL")))
        {
            hero.setStatus(1);
        }


        if(input.containsKey("LEFT") && !input.containsKey("CONTROL"))
        {
            hero.setStatus(4);

            if(hero.getPosX()>background.getLower_bound()&& (hero.getDistance()<400 || hero.getDistance()>3000))hero.translate(-5);
            else if(!(hero.getDistance()<400 || hero.getDistance()>3000))
            {
                background.translate(5);
                for(int i=0;i<3;i++)monsters[i].translate(5);
            }

            if(hero.getDistance()>0) hero.setDistance(hero.getDistance()-5);

        }

        else if(input.containsKey("RIGHT") && !input.containsKey("CONTROL"))
        {
            hero.setStatus(3);

            if(hero.getPosX()<background.getUpper_bound() && (hero.getDistance()<400 || hero.getDistance()>3000))hero.translate(5);
            else if(!(hero.getDistance()<400 || hero.getDistance()>3000))
            {
                background.translate(-5);
                for(int i=0;i<3;i++)monsters[i].translate(-5);
            }

            if(hero.getDistance()<3420) hero.setDistance(hero.getDistance()+5);

        }

        else if(hero.isAlive() && removeActiveKey("CONTROL")/*removeActiveKey("CONTROL")*/)
        {
            //hero.setStatus(5);

            for(int i=0;i<monsters.length;i++)
                hero.attack(monsters[i],time);
        }

        else if(removeActiveKey("UP"))
        {
            hero.setStatus(6);
            //System.out.println((int)time);
            if(hero.getPosY()>200)hero.ascend(30);
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
