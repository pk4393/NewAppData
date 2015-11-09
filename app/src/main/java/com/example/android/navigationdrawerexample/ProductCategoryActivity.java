package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import helperclasses.Category;

import static com.android.volley.Request.Method.GET;

public class ProductCategoryActivity extends Activity {

    ListView listViewCategory;
    ArrayList<Category> categoryArray = new ArrayList<Category>();
    String url = "";
    UserListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        listViewCategory=(ListView)findViewById(R.id.listView);
        listAdapter=new UserListAdapter(ProductCategoryActivity.this,R.layout.customcategorylistview,categoryArray);
        listViewCategory.setAdapter(listAdapter);

        //Volley Api Json Code
        url = "http://api.bestbuy.com/v1/categories?format=json&apiKey=rw5mk6btukthdwu45xbwcssx";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("categories");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("name");
                                String id=object.getString("id");

                                categoryArray.add(new Category(name,id));
                            }
                            listAdapter.notifyDataSetChanged();
                            Log.e("result", categoryArray.toString());
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
        MySingleton.getInstance(ProductCategoryActivity.this).addToRequestQueue(jsObjRequest);

        listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category cat = listAdapter.getItem(i);
                String catId = cat.getCategoryId();
                Intent intent = new Intent(ProductCategoryActivity.this, ProductActivity.class);
                intent.putExtra("categoryId", catId);
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
            Intent intent = new Intent(ProductCategoryActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    class UserListAdapter extends BaseAdapter {
        Context context;
        int layoutId;
        ArrayList<Category> listCategory;

        public UserListAdapter(Context context, int layoutId,
                               ArrayList<Category> listCategory) {
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
        public Category getItem(int position) {
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
            final Category category = listCategory.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(layoutId, null);
            TextView textViewName = (TextView) itemView.findViewById(R.id.textView);
            textViewName.setText(category.getName());
            return itemView;
        }
    }
}
