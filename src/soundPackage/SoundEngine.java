package soundPackage;

import backgroundPackage.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ArrayList;

public class SoundEngine
{
    private ArrayList<Media> medias = new ArrayList<>();
    private ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();
    URL fileUrl;
    private int current;
    int level;

    public SoundEngine(int level)
    {
        this.level=level;
        initialize();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    boolean toStop = false;

    private void initialize()
    {
        String audio1  = "/audio1.mp3";
        String audio2 = "/audio2.mp3";
        String audio3 = "/audio3.mp3";
        String audio4 = "/audio4.mp3";

        this.fileUrl = Background.class.getResource(audio1);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(0)));
        this.fileUrl = Background.class.getResource(audio2);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(1)));
        this.fileUrl = Background.class.getResource(audio3);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(2)));
        this.fileUrl = Background.class.getResource(audio4);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(3)));

    }

    public void playAudio(int num)
    {
        //System.out.println(num);
       // System.out.println(current+1);

        //if(level!=1) mediaPlayers.get(1).stop();

        if(num==1)
        {
            if(current!=0)mediaPlayers.get(current).stop();
            mediaPlayers.get(0).play();
            current = 0;
        }

        else if(num==2)
        {
            //System.out.println(current);
            if(current!=1)mediaPlayers.get(current).stop();
            mediaPlayers.get(1).play();
            current=1;
            //System.out.println(current);
        }

        else if(num==3)
        {
            //if(current!=2) System.out.println(current);
            if(current!=2)mediaPlayers.get(current).stop();
            mediaPlayers.get(2).play();
            current = 2;
        }

        else if(num==4)
        {
            if(current!=3)mediaPlayers.get(current).stop();
            mediaPlayers.get(3).play();
            current=3;
        }
    }

    public void setToStop(boolean toStop) {
        this.toStop = toStop;
    }
}
