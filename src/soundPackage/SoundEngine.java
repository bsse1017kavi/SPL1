package soundPackage;

import backgroundPackage.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundEngine
{
    private Media media;
    private MediaPlayer mp;
    URL fileUrl;

    public SoundEngine()
    {
        initialize();
        this.media = new Media(fileUrl.toExternalForm());
        this.mp = new MediaPlayer(media);
    }

    private void initialize()
    {
        String audio1  = "/audio1.mp3";
        this.fileUrl = Background.class.getResource(audio1);
    }

    public void playAudio()
    {
        mp.setAutoPlay(true);
    }
}
