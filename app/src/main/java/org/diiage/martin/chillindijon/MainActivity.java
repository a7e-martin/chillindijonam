package org.diiage.martin.chillindijon;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import org.diiage.martin.chillindijon.models.Poi;
import org.diiage.martin.chillindijon.utils.DatabaseHelper;
import org.diiage.martin.chillindijon.utils.PoiRetriever;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PoiRetriever.AsyncResponse {




    private static class PoiViewHolder{

        public TextView lblAddress;
        public TextView lblPostalCode;
        public TextView lblCity;
        public TextView lblType;
        public TextView lblName;

        public PoiViewHolder(TextView lblAddress, TextView lblPostalCode, TextView lblCity, TextView lblType, TextView lblName) {
            this.lblAddress = lblAddress;
            this.lblPostalCode = lblPostalCode;
            this.lblCity = lblCity;
            this.lblType = lblType;
            this.lblName = lblName;
        }
    }

    private static class PoiAdapter extends BaseAdapter {

        ArrayList<Poi> _pois;
        Activity _context;

        public PoiAdapter(Activity context, ArrayList<Poi> pois){
            this._pois = pois;
            this._context = context;
        }

        @Override
        public int getCount() {
            return _pois.size();
        }

        @Override
        public Object getItem(int i) {
            return _pois.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;

        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view;
            PoiViewHolder poiViewHolder;
            if(convertView != null){
                view = convertView;
                poiViewHolder = (PoiViewHolder)view.getTag();
            } else{
                LayoutInflater layoutInflater = this._context.getLayoutInflater();
                view = layoutInflater.inflate(R.layout.poi_item, parent, false);

                TextView lblAddress = view.findViewById(R.id.lblAddress);
                TextView lblPostalCode = view.findViewById(R.id.lblPostalCode);
                TextView lblCity = view.findViewById(R.id.lblCity);
                TextView lblType = view.findViewById(R.id.lblType);
                TextView lblName = view.findViewById(R.id.lblName);

                poiViewHolder = new PoiViewHolder(lblAddress, lblPostalCode, lblCity, lblType, lblName);

                view.setTag(poiViewHolder);
            }



            Poi poi = _pois.get(i);
            poiViewHolder.lblName.setText(poi.getName());
            poiViewHolder.lblType.setText(poi.getType());
            poiViewHolder.lblCity.setText(poi.getLocation().getCity());
            poiViewHolder.lblPostalCode.setText(poi.getLocation().getPostalCode());
            poiViewHolder.lblAddress.setText(poi.getLocation().getAddress());

            return view;
        }
    }



    ArrayList<Poi> pois = new ArrayList<Poi>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Uri.Builder b = new Uri.Builder();
        Uri u = b.scheme("content")
                .authority("org.diiage.martin.chillindijon.provider")
                .appendPath("bookmarks").build();


        ContentValues contentValues = new ContentValues();
        contentValues.put("id", "a-bbddd-dazdad-dqs");
        contentValues.put("note", 4);
        Uri insertedUri = getContentResolver().insert(u, contentValues);

        Cursor cursor = getContentResolver().query(
            u,
            new String[]{"id", "poi_id", "note", "created"},
            "note >= ?",
            new String[]{"4"},
            "note DESC"
        );

        while(cursor.moveToNext()){
            long id = cursor.getLong(0);
            long poi_id = cursor.getLong(1);
            long note = cursor.getLong(2);
            long created = cursor.getLong(3);
        }

        ContentValues updateValues = new ContentValues();
        updateValues.put("note", 5);
        Uri updateUri = b.scheme("content")
                .authority("org.diiage.martin.chillindijon.provider")
                .appendPath("bookmarks").build();

        getContentResolver().update(
            updateUri,
            updateValues,
            "id = ?",
            new String[]{"1"}
        );

        final String baseUrlApi = getResources().getString(R.string.base_url_api);

        PoiRetriever poiRetriever = new PoiRetriever();
        poiRetriever.execute(baseUrlApi);
        poiRetriever.delegate = this;

    }

    @Override
    public void processFinish(ArrayList<Poi> result) {
        this.pois = result;
        ListView lstPois = findViewById(R.id.lstPois);
        PoiAdapter poiAdapter = new PoiAdapter(this, this.pois);
        lstPois.setAdapter(poiAdapter);

    }
}
