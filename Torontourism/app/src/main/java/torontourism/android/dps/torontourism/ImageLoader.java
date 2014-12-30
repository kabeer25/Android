package torontourism.android.dps.torontourism;

/**
 * Created by Kabeer on 12/24/2014.
 */
    import android.content.Context;
    import android.graphics.Bitmap;
    import android.graphics.BitmapFactory;
    import android.widget.ImageView;

    import java.io.File;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.FileOutputStream;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.net.HttpURLConnection;
    import java.net.MalformedURLException;
    import java.net.URL;
    import java.net.URLConnection;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.Map;
    import java.util.WeakHashMap;
    import java.util.concurrent.Executor;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import android.os.Handler;
    import java.util.logging.LogRecord;

    /**
     * Created by Kabeer on 11/11/2014.
     */
    public class ImageLoader {

        MemoryCache memoryCache = new MemoryCache();
        FileCache fileCache;

        private Map<ImageView, String> imageViewStringMap
                = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
        ExecutorService executorService;
        Handler handler = new Handler();

        public ImageLoader(Context context) {
            fileCache = new FileCache(context);
            executorService = Executors.newFixedThreadPool(5);
        }

        //final int NoImage_Id = R.drawable.temp_img;

        public void DisplayImage(String url, ImageView imageView) {
            imageViewStringMap.put(imageView, url);
            Bitmap bitmap = memoryCache.get(url);
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            } else {
                queuePhoto(url, imageView);
                //imageView.setImageDrawable(getResources().getDrawable());
                // imageView.setImageResource(R.drawable.temp_img);
            }
        }

        private void queuePhoto(String url, ImageView imageView)
        {
            PhotoToLoad photoToLoad = new PhotoToLoad(url,imageView);
            executorService.submit(new PhotoLoader(photoToLoad));
        }

        private Bitmap getBitmap(String url) {
            File file = fileCache.getFile(url);

            Bitmap bitmap = decodeFile(file);
            if (bitmap != null)
                return bitmap;

            try {
                Bitmap bitmap1 = null;
                URL imageurl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) imageurl.openConnection();
                connection.setConnectTimeout(900000);
                connection.setReadTimeout(900000);
                connection.setInstanceFollowRedirects(true);
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = new FileOutputStream(file);
                Utils.CopyStream(inputStream, outputStream);
                outputStream.close();
                connection.disconnect();
                bitmap = decodeFile(file);
                return bitmap;

            } catch (Throwable ex) {
                if (ex instanceof OutOfMemoryError)
                    memoryCache.clear();
                return null;
            }
        }


        private Bitmap decodeFile(File file) {
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                FileInputStream fileInputStream = new FileInputStream(file);
                BitmapFactory.decodeStream(fileInputStream, null, options);
                fileInputStream.close();

                final int Size = 1000;
                int width_temp = options.outWidth, height_temp = options.outHeight;
                int scale = 1;
                while (true) {
                    if (width_temp / 2 < Size || height_temp / 2 < Size)
                        break;
                    width_temp /= 2;
                    height_temp /= 2;
                    scale *= 2;

                }

                BitmapFactory.Options options1 = new BitmapFactory.Options();
                options1.inSampleSize = scale;
                FileInputStream inputStream = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options1);
                inputStream.close();
                return bitmap;
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private class PhotoToLoad
        {
            public String url;
            public ImageView imageView;

            public PhotoToLoad(String uri, ImageView ImView)
            {
                url = uri;
                imageView = ImView;
            }
        }
        class PhotoLoader implements Runnable
        {
            PhotoToLoad photoToLoad;

            PhotoLoader(PhotoToLoad photoToLoad){
                this.photoToLoad = photoToLoad;
            }

            @Override
            public void run() {
                try{
                    if(ImageViewReused(photoToLoad))
                        return;
                    Bitmap bitmap = getBitmap(photoToLoad.url);
                    memoryCache.put(photoToLoad.url, bitmap);
                    if(ImageViewReused(photoToLoad))
                        return;
                    BitmapDisplayer bitmapDisplayer = new BitmapDisplayer(bitmap,photoToLoad);
                    handler.post(bitmapDisplayer);
                } catch (Throwable throwable){
                    throwable.printStackTrace();
                }

            }
        }

        boolean ImageViewReused(PhotoToLoad photoToLoad)
        {
            String string = imageViewStringMap.get(photoToLoad.imageView);
            if(string == null || !string.equals(photoToLoad.url))
                return true;
            return false;
        }

        class BitmapDisplayer implements Runnable
        {
            Bitmap bitmap;
            PhotoToLoad photoToLoad;

            public BitmapDisplayer(Bitmap bitmap1, PhotoToLoad photoToLoad1)
            {
                bitmap = bitmap1;
                photoToLoad = photoToLoad1;
            }

            public void run() {
                if(ImageViewReused(photoToLoad))
                    return;
                if(bitmap != null){
                    photoToLoad.imageView.setImageBitmap(bitmap);
                }else{
                    //photoToLoad.imageView.setImageResource(NoImage_Id);
                }

            }
        }

        public void clearCache()
        {
            memoryCache.clear();
            fileCache.clear();
        }
}
