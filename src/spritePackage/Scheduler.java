package spritePackage;

import java.util.TimerTask;

public class Scheduler extends TimerTask
{
    Enemy sp;
    Protagonist h;


    public Scheduler(Protagonist h,Enemy sp) {
        this.sp = sp;
        this.h = h;
    }

    @Override
    public void run()
    {
        //sp.attack(h);
    }
}
