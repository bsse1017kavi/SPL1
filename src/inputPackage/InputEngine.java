package inputPackage;

import backgroundPackage.Background;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import spritePackage.Enemy;
import spritePackage.Protagonist;
import spritePackage.STATUS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InputEngine
{
    HashMap<String,Boolean> input = new HashMap<String, Boolean>();

    Protagonist hero;

    Scene scene;

    Stage stage = new Stage();

    static int dist = 0;

    public static int getDist() {
        return dist;
    }

    public static void setDist(int dist) {
        InputEngine.dist = dist;
    }

    //@FXML
    //TextField tf1;

    boolean floating = false;

    //Stage stage = new Stage();

    double gravity = 2;

    private boolean paused = false;

    public boolean isPaused() {
        return paused;
    }

    /*public void onAction(ActionEvent e)
    {
        String s = tf1.getText();
        System.out.println(s);
        //stage.close();
    }*/

    public InputEngine(Protagonist hero, Scene scene) {
        this.hero = hero;
        this.scene = scene;
    }

    public synchronized void takeInput()
    {
        //System.out.println("weird");
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

    public void checkPause()
    {
        if(removeActiveKey("P"))
        {
            //paused ^= true;
            //System.out.println(paused);
            if(isPaused()) paused = false;
            else paused = true;
        }
    }

    public synchronized void action(ArrayList<Enemy> monsters, Background background, double time)
    {
        //System.out.println("wth");
        if(removeActiveKey("D"))
        {
            /*Dummy d = new Dummy();
            Platform.runLater(d);*/

            TelController controller = new TelController();

            try
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/tel.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                controller = loader.getController();
                stage.setScene(scene);
                stage.show();

            }catch(IOException e)
            {

            }



        }

        /*if(removeActiveKey("P"))
        {
            //paused ^= true;
            //System.out.println(paused);
            if(isPaused()) paused = false;
            else paused = true;
        }*/

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

        if(hero.getStatus()==STATUS.BLOCKING_RIGHT && !input.containsKey("ALT"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);
        }

        if(hero.getStatus()==STATUS.BLOCKING_LEFT && !input.containsKey("ALT"))
        {
            hero.setStatus(STATUS.LEFT_FACING);
        }

        if(hero.getStatus()== STATUS.JUMPING_RIGHT && !input.containsKey("UP"))
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

        else if(hero.getStatus()==STATUS.FIGHTING_RIGHT && (!input.containsKey("CONTROL")))
        {
            hero.setStatus(STATUS.RIGHT_FACING);
        }

        else if(hero.getStatus()==STATUS.FIGHTING_LEFT && (!input.containsKey("CONTROL")))
        {
            hero.setStatus(STATUS.LEFT_FACING);
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

            hero.setDistance(hero.getDistance()-5);

            if(hero.getDistance()<=0)hero.setDistance(0);

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

            hero.setDistance(hero.getDistance()+5);

            if(hero.getDistance()>=5420) hero.setDistance(5420);

        }

        if((hero.getStatus()==STATUS.RIGHT_FACING || hero.getStatus()==STATUS.FIGHTING_RIGHT) && hero.getStamina()>15 && hero.isAlive() && removeActiveKey("CONTROL"))
        {
            //System.out.println("Duhhh");
            //hero.setStatus(5);

            hero.setStatus(STATUS.FIGHTING_RIGHT);

            for(int i = 0; i< monsters.size(); i++)
                hero.attack(monsters.get(i),time);

            hero.stamina_deplete(15);
        }


        else if((hero.getStatus()==STATUS.LEFT_FACING || hero.getStatus()==STATUS.FIGHTING_LEFT) && hero.getStamina()>15 && hero.isAlive() && removeActiveKey("CONTROL"))
        {
            //hero.setStatus(5);

            hero.setStatus(STATUS.FIGHTING_LEFT);

            for(int i = 0; i< monsters.size(); i++)
                hero.attack(monsters.get(i),time);

            hero.stamina_deplete(15);
        }

        if(!input.containsKey("RIGHT") && hero.getStatus()==STATUS.RIGHT_FACING && removeActiveKey("UP"))
        {
            hero.setStatus(STATUS.RIGHT_FACING);

            if(!floating)
            {
               hero.ascend(100);
            }

        }

        else if(!input.containsKey("LEFT") && hero.getStatus()==STATUS.LEFT_FACING && removeActiveKey("UP"))
        {
            hero.setStatus(STATUS.LEFT_FACING);

            if(!floating)
            {
                hero.ascend(100);
            }
        }

        if(input.containsKey("ALT") && (hero.getStatus()==STATUS.RIGHT_FACING || hero.getStatus()==STATUS.BLOCKING_RIGHT) && hero.getStamina()>5)
        {
            hero.setStatus(STATUS.BLOCKING_RIGHT);
            hero.setDodging(true);
            hero.stamina_deplete(1.05);
        }

        else if(input.containsKey("ALT") && (hero.getStatus()==STATUS.LEFT_FACING || hero.getStatus()==STATUS.BLOCKING_LEFT) && hero.getStamina()>5)
        {
            hero.setStatus(STATUS.BLOCKING_LEFT);
            hero.setDodging(true);
            hero.stamina_deplete(1.05);
        }

        else if(removeActiveKey("H"))
        {
            hero.heal();
        }

        //System.out.println(hero.getStatus());

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

class Dummy implements Runnable
{
    @Override
    public void run()
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("/tel.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch(IOException e)
        {

        }
    }


}
