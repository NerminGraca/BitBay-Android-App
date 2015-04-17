package com.example.nermingraca.bitbayapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;

import com.example.nermingraca.bitbayapp.models.Product;
import com.example.nermingraca.bitbayapp.singletons.ProductFeed;

import java.util.ArrayList;
import java.util.List;


public class ShowProducts extends ActionBarActivity {

    private ListView mProductList;
    private EditText mFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_products);

        ProductFeed productFeed = ProductFeed.getInstance();
        productFeed.getFeed(getString(R.string.service_products));

        mProductList = (ListView)findViewById(R.id.list_view_posts);
        ProductsAdapter productsAdapter = new ProductsAdapter (
                this,
                android.R.layout.simple_list_item_1,
                (ArrayList<Product>)productFeed.getFeed()

        );

        mProductList.setAdapter(productsAdapter);

        mProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product clicked = (Product)parent.getItemAtPosition(position);
                Intent intent = new Intent(ShowProducts.this, ProductActivity.class);
                intent.putExtra("id", clicked.getmId());
                intent.putExtra("name", clicked.getmName());
                intent.putExtra("description", clicked.getmDescription());
                double priceDouble = clicked.getmPrice();
                String price = String.format( "$" + "%.2f", priceDouble );
                intent.putExtra("price", price);
                startActivity(intent);
            }
        });


        mFilter = (EditText)findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( (ArrayAdapter<Product>)mProductList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class ProductsAdapter extends ArrayAdapter<Product> {

        private ArrayList<Product> mListToShow;
        private Filter mFilter;

        private ProductsAdapter(Context context, int resource, ArrayList<Product> origin) {
            super(context, resource);
            mListToShow = origin;
        }

        @Override
        public Filter getFilter(){
            if(mFilter == null) {
                mFilter = new PostsFilter();
            }
            return mFilter;
        }

        @Override
        public int getCount() {
            if (mListToShow != null) {
                return mListToShow.size();
            } else {
                return 0;
            }

        }

        @Override
        public Product getItem(int position){
            return mListToShow.get(position);
        }

        private class PostsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                if(constraint == null || constraint.length() == 0) {
                    List<Product> origin = ProductFeed.getInstance().getFeed();
                    results.values = origin;
                    results.count = origin.size();
                } else {
                    String searchString = constraint.toString().toLowerCase();
                    ArrayList<Product> filteredList = new ArrayList<Product>();
                    for(int i = 0; i < mListToShow.size(); i++) {
                        Product p = mListToShow.get(i);
                        String postTitle = p.getmName().toLowerCase();

                        if(postTitle.contains(searchString)) {
                            filteredList.add(p);
                        }
                        results.values = filteredList;
                        results.count = filteredList.size();
                    }
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values != null) {
                    mListToShow = (ArrayList<Product>)results.values;
                    notifyDataSetChanged();
                }
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_product, menu);
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
