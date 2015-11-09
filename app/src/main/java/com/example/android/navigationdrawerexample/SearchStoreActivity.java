package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.drive.query.Filters;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import helperclasses.StoreDetail;

import static com.android.volley.Request.Method.GET;

public class SearchStoreActivity extends Activity {

    ListView listStore;
    Button buttonSearch;
    EditText editTextSearch;

    ArrayList<StoreDetail> storeArray=new ArrayList<>();
    StoreListAdapter storeListAdapter;

    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store);

        listStore=(ListView)findViewById(R.id.listView);
        buttonSearch=(Button)findViewById(R.id.button);
        editTextSearch=(EditText)findViewById(R.id.editText);

        storeListAdapter=new StoreListAdapter(this,R.layout.searchstorecustomlist,storeArray);
        listStore.setAdapter(storeListAdapter);

        //button on click event

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cityName=editTextSearch.getText().toString();

                url = "http://api.bestbuy.com/v1/stores(city="+cityName+")?format=json&apiKey=rw5mk6btukthdwu45xbwcssx";
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
                                        String address = object.getString("address");
                                        String countryName = object.getString("country");
                                        String latitude = object.getString("lat");
                                        String longitude = object.getString("lng");

                                        storeArray.add(new StoreDetail(storeName, address, countryName, latitude, longitude));
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
                MySingleton.getInstance(SearchStoreActivity.this).addToRequestQueue(jsObjRequest);
            }
        });

        listStore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StoreDetail store=storeListAdapter.getItem(i);
                String latitude=store.getLatitude();
                String longitude=store.getLongitude();
                Intent intent=new Intent(SearchStoreActivity.this,MapsActivity.class);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",longitude);
                startActivity(intent);
            }
        });

    }//end of OnCreate

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            this.finish();
            Intent intent = new Intent(SearchStoreActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    class StoreListAdapter extends BaseAdapter
    {
        Context context;
        int layoutId;
        ArrayList<StoreDetail> listCategory;
        public StoreListAdapter(Context context, int layoutId,
                               ArrayList<StoreDetail> listCategory)
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
        public StoreDetail getItem(int position) {
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
            final StoreDetail store=listCategory.get(position);
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView=inflater.inflate(layoutId, null);
            TextView textViewStoreName=(TextView) itemView.findViewById(R.id.textView2);
            TextView textViewAddress=(TextView) itemView.findViewById(R.id.textView4);
            TextView textViewCountryName=(TextView) itemView.findViewById(R.id.textView6);
            textViewStoreName.setText(store.getStoreName());
            textViewAddress.setText(store.getAddress());
            textViewCountryName.setText(store.getCountryName());
            return itemView;
        }
    }
}
