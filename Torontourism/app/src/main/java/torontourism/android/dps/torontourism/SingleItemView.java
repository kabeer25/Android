package torontourism.android.dps.torontourism;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
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
    String EventURL;
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
        EventURL = intent.getStringExtra("EventURL");
        Image = intent.getStringExtra("Image");

        TextView textView = (TextView) findViewById(R.id.Event);
        TextView textView3 = (TextView) findViewById(R.id.Category);
        TextView textView2 = (TextView) findViewById(R.id.Location);
        TextView textView5 = (TextView) findViewById(R.id.Phone);
        TextView textView1 = (TextView) findViewById(R.id.EventURL);

        ImageView imageView = (ImageView) findViewById(R.id.Image);

        textView.setText(Event);
        textView3.setText(Category);
        textView2.setText(Location);
        textView5.setText(Phone);
        textView1.setText(EventURL);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broswerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(EventURL));
                startActivity(broswerIntent);
            }
        });

        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_DIAL);
                intent1.setData(Uri.parse("tel:+" + Phone.trim()));
                startActivity(intent1);
            }
        });


        imageLoader.DisplayImage(Image, imageView);
    }
}