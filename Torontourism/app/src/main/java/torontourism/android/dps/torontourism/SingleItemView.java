package torontourism.android.dps.torontourism;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    String MapURL;
    String Admission;
    String EndDate;
    String StartDate;
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
        MapURL = intent.getStringExtra("MapURL");
        Image = intent.getStringExtra("Image");
        Admission = intent.getStringExtra("Admission");
        EndDate = intent.getStringExtra("EndDate");
        StartDate = intent.getStringExtra("StartDate");

        MapURL = MapURL.replace("[","");
        MapURL = MapURL.replace("]","");

        TextView textView = (TextView) findViewById(R.id.Event);
        TextView textView3 = (TextView) findViewById(R.id.Category);
        TextView textView2 = (TextView) findViewById(R.id.Location);
        TextView textView5 = (TextView) findViewById(R.id.Phone);
        TextView textView1 = (TextView) findViewById(R.id.EventURL);
        TextView textView4 = (TextView) findViewById(R.id.MapURL);
        TextView textView6 = (TextView) findViewById(R.id.Admission);
        TextView textView7 = (TextView) findViewById(R.id.EndDate);
        TextView textView8 = (TextView) findViewById(R.id.StartDate);
        Button button = (Button) findViewById(R.id.button);

        ImageView imageView = (ImageView) findViewById(R.id.Image);

        textView.setText(Event);
        textView3.setText(Category);
        textView2.setText(Location);
        textView5.setText(Phone);
        textView1.setText(EventURL);
        textView4.setText(Html.fromHtml(MapURL));
        textView4.setMovementMethod(LinkMovementMethod.getInstance());
        textView6.setText(Admission);
        textView7.setText(EndDate);
        textView8.setText(StartDate);

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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_INSERT);
                intent2.setType("vnd.android.cursor.item/event");
                intent2.putExtra(CalendarContract.Events.TITLE, Event);
                intent2.putExtra(CalendarContract.Events.EVENT_LOCATION,Location);
                startActivity(intent2);
            }
        });


    }
}