package com.example.nermingraca.bitbayapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.nermingraca.bitbayapp.models.Product;
import com.example.nermingraca.bitbayapp.singletons.ProductFeed;

/**
 * Created by nermingraca on 17/04/15.
 */
public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Product> productItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private Filter mFilter;

    public CustomListAdapter(Activity activity, List<Product> productItems) {
        this.activity = activity;
        this.productItems = productItems;
    }

    @Override
    public int getCount() {
        return productItems.size();
    }

    @Override
    public Object getItem(int location) {
        return productItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Product product = productItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(product.getThumbnailUrl(), imageLoader);

        // name
        title.setText(product.getmName());

        // price
        double doublePrice = product.getmPrice();
        String price = String.format( "%.2f", doublePrice );
        rating.setText("Price: $" + price);

        // description
        String genreStr = product.getmDescription();

        // owner
        year.setText("Seller: " + String.valueOf(product.getmOwner()));

        return convertView;
    }

    public Filter getFilter(){
        if(mFilter == null) {
            mFilter = new ProductsFilter();
        }
        return mFilter;
    }

    private class ProductsFilter extends Filter {

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
                for(int i = 0; i < productItems.size(); i++) {
                    Product p = productItems.get(i);
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
                productItems = (ArrayList<Product>)results.values;
                notifyDataSetChanged();
            }
        }
    }

}
