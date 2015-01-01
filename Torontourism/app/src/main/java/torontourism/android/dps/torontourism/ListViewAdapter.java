package torontourism.android.dps.torontourism;

/**
 * Created by Kabeer on 12/24/2014.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by Kabeer on 11/8/2014.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<HashMap<String,String>> xmldata;
    ImageLoader imageLoader;
    HashMap<String,String> hashMap = new HashMap<String, String>();

    public ListViewAdapter(Context context,ArrayList<HashMap<String,String>> arrayList)
    {
        this.context = context;
        xmldata = arrayList;
        imageLoader = new ImageLoader(context);

    }
    @Override
    public int getCount() {
        return xmldata.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView Event;
        TextView Area;
        TextView Category;
        TextView Location;
        TextView Phone;
        TextView EventURL;
        ImageView Image;

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.listview_item, parent, false);
        hashMap = xmldata.get(position);

        Event = (TextView) view.findViewById(R.id.Event);
       // Area = (TextView) view.findViewById(R.id.Area);
        Category = (TextView) view.findViewById(R.id.Category);
        Location = (TextView) view.findViewById(R.id.Location);
        Phone = (TextView) view.findViewById(R.id.Phone);
        EventURL = (TextView) view.findViewById(R.id.EventURL);

        Image = (ImageView) view.findViewById(R.id.Image);

        Event.setText(hashMap.get(TorontourismActivity.Event));
        Category.setText(hashMap.get(TorontourismActivity.Category));
        Location.setText(hashMap.get(TorontourismActivity.Location));
        //Phone.setText(hashMap.get(TorontourismActivity.Phone));


        imageLoader.DisplayImage(hashMap.get(TorontourismActivity.Image),Image);

        Log.i("Event:", hashMap.get(TorontourismActivity.Event));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                hashMap = xmldata.get(position);
                Intent intent = new Intent(context,SingleItemView.class);

                intent.putExtra("Event", hashMap.get(TorontourismActivity.Event));
               // intent.putExtra("Area", hashMap.get(TorontourismActivity.Area));
                intent.putExtra("Category", hashMap.get(TorontourismActivity.Category));
                intent.putExtra("Location", hashMap.get(TorontourismActivity.Location));
                intent.putExtra("Phone", hashMap.get(TorontourismActivity.Phone));
                intent.putExtra("EventURL", hashMap.get(TorontourismActivity.EventURl));
                intent.putExtra("Image", hashMap.get(TorontourismActivity.Image));

                context.startActivity(intent);
            }

        });

        return view;
    }
}
