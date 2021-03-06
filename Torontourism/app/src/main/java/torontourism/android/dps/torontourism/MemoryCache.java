package torontourism.android.dps.torontourism;

import android.graphics.Bitmap;
import android.os.DropBoxManager;
import android.util.Log;

import java.security.KeyStore;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 *
 * Code from :http://www.androidhive.info/2012/02/android-custom-listview-with-image-and-text/
 * Modified by Kabeer on 12/24/2014.
 *
 */
public class MemoryCache {
    public static final String MemCache = "MemoryCache";

    private Map<String,Bitmap> cache = Collections.synchronizedMap(new LinkedHashMap<String,
            Bitmap>(100,1.9f,true));

    private long size =0;
    private long limit = 90000000;

    public MemoryCache(){
        SetLimit(Runtime.getRuntime().maxMemory()/2);
    }

    public void SetLimit(long newLimit)
    {
        limit = newLimit;
        Log.i(MemCache, "MemoryCache will be up to " + limit  + "MB");
    }

    public Bitmap get(String stringid)
    {
        try {
            if(!cache.containsKey(stringid))
                return null;
            return cache.get(stringid);
        }catch (NullPointerException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void put(String stringid, Bitmap bitmap)
    {
        try {
            if(cache.containsKey(stringid))
                size -= getSizeInBytes(cache.get(stringid));
            cache.put(stringid,bitmap);
            size += getSizeInBytes(bitmap);
            checkSize();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
    }

    private void checkSize()
    {
        Log.i(MemCache, "Cache Size =" + size + "Length = " + cache.size());

        if(size>limit)
        {
            Iterator<Map.Entry<String,Bitmap>> iterator = cache.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String,Bitmap> entry = iterator.next();
                size -= getSizeInBytes(entry.getValue());
                iterator.remove();
                if(size <= limit)
                    break;
            }
            Log.i(MemCache, "Clean cache. New size " + cache.size());
        }
    }
    long getSizeInBytes(Bitmap bitmap)
    {
        if(bitmap == null)
            return 0;
        else
            return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public void clear()
    {
        try {
            cache.clear();
            size =0;
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }
}
