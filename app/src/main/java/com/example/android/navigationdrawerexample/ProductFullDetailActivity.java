package com.example.android.navigationdrawerexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class ProductFullDetailActivity extends Activity {

    ImageView productImageView;
    TextView textViewName,textViewPrice;

    String url="";
    String productName;
    String productPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_full_detail);
        productImageView=(ImageView)findViewById(R.id.imageView);
        textViewName=(TextView)findViewById(R.id.textView2);
        textViewPrice=(TextView)findViewById(R.id.textView4);

        Intent intent=getIntent();
        productName=intent.getStringExtra("productName");
        productPrice=intent.getStringExtra("productPrice");
        url=intent.getStringExtra("imageUrl");

        // Retrieves an image specified by the URL, displays it in the UI.
        ImageRequest request = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        productImageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        productImageView.setImageResource(R.drawable.shirt);
                    }
                });
// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(request);

        textViewName.setText(productName);
        textViewPrice.setText(productPrice);
    }
}
