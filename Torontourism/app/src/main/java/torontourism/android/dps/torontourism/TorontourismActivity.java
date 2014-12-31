package torontourism.android.dps.torontourism;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ListAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;


public class TorontourismActivity extends Activity{

    ListView listView;
    ListViewAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<HashMap<String,String>> arrayList;
    static String Event = "Event-text";
    static String Area = "Area-text";
    static String Category = "CategoryList-text";
    static String Image = "Image";
    static String Location = "Location-text";
    static String Phone = "Phone-text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_main); //View from the layout file in res folder
        new DownloadXMLFile().execute();
    }
    private class DownloadXMLFile extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected  void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TorontourismActivity.this);
            progressDialog.setTitle("Torontourism the know it all for Toronto Events");
            progressDialog.setMessage("I'm loading....Give me a minute.");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            arrayList = new ArrayList<HashMap<String, String>>();

            TorontourismParser parser = new TorontourismParser();
            String xml = parser.getXMLfromURL("http://wx.toronto.ca/festevents.nsf/tpaview?readviewentries");
            Document document = parser.getElements(xml);

            try
            {
                NodeList nodeList = document.getElementsByTagName("viewentry");

                for(int i =0; i < nodeList.getLength(); i++)
                {
                    Element element = (Element) nodeList.item(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    map.put(Event,parser.getValue(element,"EventName"));
                    map.put(Area, parser.getValue(element,"Area"));
                    map.put(Category, parser.getValue(element, "CategoryList"));
                    map.put(Location,parser.getValue(element,"Location"));
                    map.put(Phone,parser.getValue(element,"OrgContactPhone"));
                    map.put(Image, parser.getValue(element, "Image"));

                    arrayList.add(map);
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void arguments)
        {
            listView = (ListView) findViewById(R.id.listView);
            adapter = new ListViewAdapter(TorontourismActivity.this, arrayList);
            listView.setAdapter(adapter);
            progressDialog.dismiss();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_torontourism, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
