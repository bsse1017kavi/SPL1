package mainPackage;

import java.io.IOException;
import java.io.InputStream;

public final class ResourceLoader
{
    public static InputStream load(String path)
    {
        InputStream input = ResourceLoader.class.getResourceAsStream(path);
        if(input==null)
        {
            input = ResourceLoader.class.getResourceAsStream("/"+path);
        }

        return input;
    }

    public static String loadAudio(String path)
    {
        String input = ResourceLoader.class.getResource(path).toString();
        return input;
    }
}
