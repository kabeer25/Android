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
    String Location;
    String Category;
    String Image;
    String Phone;
    ImageLoader imageLoader = new ImageLoader(this);

    @Override
    public void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        setContentView(R.layout.singleitemview);

        Intent intent = getIntent();

        Event = intent.getStringExtra("Event");
        Category = intent.getStringExtra("Category");
        Location = intent.getStringExtra("Location");
        Phone = intent.getStringExtra("Phone");
        Image = intent.getStringExtra("Image");

        TextView textView = (TextView) findViewById(R.id.Event);
        TextView textView3 = (TextView) findViewById(R.id.Category);
        TextView textView2 = (TextView) findViewById(R.id.Location);
        TextView textView5 = (TextView) findViewById(R.id.Phone);

        ImageView imageView = (ImageView) findViewById(R.id.Image);

        textView.setText(Event);
        textView3.setText(Category);
        textView2.setText(Location);
        textView5.setText(Phone);

        imageLoader.DisplayImage(Image, imageView);
    }

}

