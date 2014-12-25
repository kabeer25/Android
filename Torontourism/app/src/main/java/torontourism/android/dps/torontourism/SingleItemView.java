package torontourism.android.dps.torontourism;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Kabeer on 12/24/2014.
 */
public class SingleItemView extends Activity {
    String Event;
    String Area;
    String Presented;
    String Category;
    String Image;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.singleitemview);

        Intent intent = getIntent();

        Event = intent.getStringExtra("Event");
        Area = intent.getStringExtra("Area");
        Category = intent.getStringExtra("Category");
        Image = intent.getStringExtra("Image");

        TextView textView = (TextView) findViewById(R.id.Event);
        TextView textView1 = (TextView) findViewById(R.id.Area);
        TextView textView3 = (TextView) findViewById(R.id.Category);

        ImageView imageView = (ImageView) findViewById(R.id.Image);

        textView.setText(Event);
        textView1.setText(Area);
        textView3.setText(Category);

        imageLoader.DisplayImage(Image, imageView);
    }

}

