package torontourism.android.dps.torontourism;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Kabeer on 12/24/2014.
 */
public class FileCache {
    private File file;

    public FileCache(Context context)
    {
        if(android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            file = new File(android.os.Environment.getExternalStorageDirectory(),
                    "TorontourimsCacheDirectory");
        else
            file = context.getCacheDir();
        if(!file.exists())
            file.mkdir();
    }

    public File getFile(String url)
    {
        String fileName = String.valueOf(url.hashCode());
        File file1 = new File(file, fileName);
        return file1;
    }

    public void clear(){
        File[] files = file.listFiles();
        if(files == null)
            return;
        for(File file1 : files)
            file1.delete();
    }
}
