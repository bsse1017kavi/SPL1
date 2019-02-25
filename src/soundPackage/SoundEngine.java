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

    public SoundEngine()
    {
        initialize();
    }

    private void initialize()
    {
        String audio1  = "/audio1.mp3";
        String audio2 = "/audio2.mp3";

        this.fileUrl = Background.class.getResource(audio1);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(0)));
        this.fileUrl = Background.class.getResource(audio2);
        medias.add(new Media(fileUrl.toExternalForm()));
        mediaPlayers.add(new MediaPlayer(medias.get(1)));

    }

    public void playAudio(int num)
    {
        if(num==1)
        {
            mediaPlayers.get(1).stop();
            mediaPlayers.get(0).play();
        }

        else
        {
            mediaPlayers.get(0).stop();
            mediaPlayers.get(1).play();
        }
    }
}
