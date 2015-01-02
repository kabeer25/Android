package torontourism.android.dps.torontourism;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * Code from :http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
 * Modified by Kabeer on 12/24/2014.
 *
 */
public class Utils {
    public static void CopyStream(InputStream inputStream, OutputStream outputStream)
    {
        final int bufferSize = 1024;
        try {
            byte[] bytes = new byte[bufferSize];
            for(;;)
            {
                int count = inputStream.read(bytes,0,bufferSize);
                if(count == -1)
                    break;
                outputStream.write(bytes,0,count);
            }
        }catch (Exception e){}
    }
}
