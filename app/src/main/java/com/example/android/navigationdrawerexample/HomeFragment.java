package com.example.android.navigationdrawerexample;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import helperclasses.Category;

import static com.android.volley.Request.Method.GET;

/**
 * Created by prashantkumar on 04/11/15.
 */
public class HomeFragment extends Fragment {
    TextView mTextView;
    EditText editText;
    Button buttonSearch;
    ImageView imageSwitcher;

    ListView listViewCategory;
    ArrayList<Category> listCategory = new ArrayList<Category>();
    String url = "";
    UserListAdapter listAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View fragView = inflater.inflate(R.layout.homefragment, container, false);
        imageSwitcher = (ImageView) fragView.findViewById(R.id.imageView);
        listViewCategory = (ListView) fragView.findViewById(R.id.listView);
        mTextView = (TextView) fragView.findViewById(R.id.textView);
        editText = (EditText) fragView.findViewById(R.id.editText);
        buttonSearch = (Button) fragView.findViewById(R.id.button);
        listAdapter = new UserListAdapter(getActivity(), R.layout.customlistview, listCategory);
        listViewCategory.setAdapter(listAdapter);
        //Image Switcher code

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

                                    listCategory.add(new Category(name,id));
                                }
                                listAdapter.notifyDataSetChanged();
                                Log.e("result", listCategory.toString());
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
            MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

            buttonSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String searchResult = editText.getText().toString();
                    String searchUrl = "http://api.bestbuy.com/v1/categories(name=" + searchResult + ")?format=json&apiKey=rw5mk6btukthdwu45xbwcssx";
                    String urlNew = searchUrl.replaceAll(" ", "%20");
                    JsonObjectRequest jsObjRequest1 = new JsonObjectRequest
                            (GET, urlNew, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        listCategory.clear();
                                        // listAdapter.notifyDataSetChanged();
                                        JSONArray jsonArray = response.getJSONArray("categories");
                                        JSONObject object = jsonArray.getJSONObject(0);
                                        String name = object.getString("name");
                                        String id=object.getString("id");
                                        listCategory.add(new Category(name,id));
                                        listAdapter.notifyDataSetChanged();
                                        Log.e("result", listCategory.toString());
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
                    MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest1);
                }
            });

            listViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Category cat = listAdapter.getItem(i);
                    String catId = cat.getCategoryId();
                    Intent intent = new Intent(getActivity(), SubcategoryActivity.class);
                    intent.putExtra("categoryId", catId);
                    startActivity(intent);
                }
            });
            return fragView;
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
                final Category user = listCategory.get(position);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View itemView = inflater.inflate(layoutId, null);
                TextView textViewName = (TextView) itemView.findViewById(R.id.textView);
                textViewName.setText(user.getName());
                return itemView;
            }
        }
    }