package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import helperclasses.GPSSearchStore;

import static com.android.volley.Request.Method.GET;

public class GPSBasedStoreSearch extends Activity implements LocationListener {

    ListView listStore;
    ArrayList<GPSSearchStore> storeArray=new ArrayList<>();
    StoreListAdapter storeListAdapter;

    LocationManager manager;
    static double latitude;
    static double longitude;
    String lat="";
    String log="";
    String url="";
    String area="1000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsbased_store_search);
        listStore=(ListView)findViewById(R.id.listView);
        storeListAdapter=new StoreListAdapter(this,R.layout.gpscustomstorelist,storeArray);
        listStore.setAdapter(storeListAdapter);

        ///-------
        manager=(LocationManager)getSystemService(LOCATION_SERVICE);
        Location loc=manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(loc!=null)
        {
            latitude=loc.getLatitude();
            longitude=loc.getLongitude();
        }
        //set code that execute on change of location
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 500, this);
///-------
    //    lat=Double.toString(latitude);
     //   log=Double.toString(longitude);
        lat="18.3912915";
        log="-65.9688829";
        Log.e("lattitude", ""+latitude);
        url="http://api.bestbuy.com/v1/stores(area("+lat+","+log+","+area+"))?format=json&show=storeId,name,distance&apiKey=rw5mk6btukthdwu45xbwcssx";

        String urlNew=url.replaceAll(" ","%20");
        //-----
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (GET, urlNew, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            storeArray.clear();
                            JSONArray jsonArray = response.getJSONArray("stores");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String storeName = object.getString("name");
                                String distance = object.getString("distance");
                                String storeId = object.getString("storeId");

                                storeArray.add(new GPSSearchStore(storeName,distance,storeId));
                            }
                            storeListAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(GPSBasedStoreSearch.this).addToRequestQueue(jsObjRequest);
    }//end of Oncreate

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            this.finish();
            Intent intent = new Intent(GPSBasedStoreSearch.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class StoreListAdapter extends BaseAdapter
    {
        Context context;
        int layoutId;
        ArrayList<GPSSearchStore> listCategory;
        public StoreListAdapter(Context context, int layoutId,
                                ArrayList<GPSSearchStore> listCategory)
        {
            super();
            this.context = context;
            this.layoutId = layoutId;
            this.listCategory = listCategory;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listCategory.size();
        }

        @Override
        public GPSSearchStore getItem(int position) {
            // TODO Auto-generated method stub
            return listCategory.get(position);
        }


        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final GPSSearchStore store=listCategory.get(position);
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView=inflater.inflate(layoutId, null);
            TextView textViewStoreName=(TextView) itemView.findViewById(R.id.textView2);
            TextView textViewDistance=(TextView) itemView.findViewById(R.id.textView4);
            textViewStoreName.setText(store.getStoreName());
            Log.e("result",store.getStoreName());
            textViewDistance.setText(store.getDistance());
            return itemView;
        }
    }
}
