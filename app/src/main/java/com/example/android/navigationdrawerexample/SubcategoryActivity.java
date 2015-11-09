package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import helperclasses.Category;

import static com.android.volley.Request.Method.GET;

/**
 * Created by prashantkumar on 05/11/15.
 */
public class SubcategoryActivity extends Activity
{
    ListView listViewSubcategory;
    SubcategoryListAdapter listAdapter;
    ArrayList<Category> subcategoryList=new ArrayList<>();

    String url="";
    String categoryId="";
    String urlNew="";
    String finalUrl="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subcategory);
        listViewSubcategory=(ListView)findViewById(R.id.listView);
        listAdapter=new SubcategoryListAdapter(this,R.layout.subcategorycustomlistview,subcategoryList);
        listViewSubcategory.setAdapter(listAdapter);

        Intent intent=getIntent();
        categoryId=intent.getStringExtra("categoryId");

        url="http://api.bestbuy.com/v1/categories(id="+categoryId+")?format=json&apiKey=rw5mk6btukthdwu45xbwcssx&show=subCategories";

        urlNew=url.replaceAll(" ","%20");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (GET, urlNew, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("categories");
                            JSONObject object = jsonArray.getJSONObject(0);
                            JSONArray subcategory=object.getJSONArray("subCategories");
                            for (int i = 0; i <=10; i++)
                            {
                                JSONObject finalObject=subcategory.getJSONObject(i);
                                String name=finalObject.getString("name");
                                String id=finalObject.getString("id");
                                subcategoryList.add(new Category(name,id));
                            }
                            listAdapter.notifyDataSetChanged();
                        } catch(Exception e)
                        {
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
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }


    class SubcategoryListAdapter extends BaseAdapter
    {
        Context context;
        int layoutId;
        ArrayList<Category> listCategory;
        public SubcategoryListAdapter(Context context, int layoutId,
                               ArrayList<Category> listCategory)
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
            final Category user=listCategory.get(position);
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView=inflater.inflate(layoutId, null);
            TextView textViewName=(TextView) itemView.findViewById(R.id.textView);
            textViewName.setText(user.getName());
            return itemView;
        }
    }
}
