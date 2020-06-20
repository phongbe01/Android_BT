package com.example.testlan1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResAdapter extends BaseAdapter implements Filterable {
    private ArrayList<Restaurant> ress;
    private Activity context;

    private RestaurantFilter filter;
    private ArrayList<Restaurant> filterlist;

    public ResAdapter(ArrayList<Restaurant> ress, Activity context) {
        this.ress = ress;
        this.context = context;
        this.filterlist = ress;
    }

    @Override
    public int getCount() {
        return ress.size();
    }

    @Override
    public Object getItem(int position) {
        return ress.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.single_row, null);
        }
        Restaurant res  = (Restaurant) getItem(position);
        if ((res != null))
        {
            TextView textAddr = (TextView) view.findViewById(R.id.txtAddr);
            TextView textRate = (TextView) view.findViewById(R.id.txtRate);
            TextView textName = (TextView) view.findViewById(R.id.txtName);

            textAddr.setText(res.getDiaChi());
            textRate.setText(res.getDanhGia()+ "");
            textName.setText(res.getTen());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        {
            filter = new RestaurantFilter();
        }
        return filter;
    }

    public class RestaurantFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint!= null && constraint.length() > 0)
            {
                constraint = constraint.toString().toUpperCase();
                ArrayList<Restaurant> filters = new ArrayList<Restaurant>();
                for (int i = 0; i < filterlist.size(); i++)
                {
                    if (filterlist.get(i).getTen().toUpperCase().contains(constraint)){
                        Restaurant res = new Restaurant(filterlist.get(i).getMa() , filterlist.get(i).getTen(), filterlist.get(i).getDiaChi(), filterlist.get(i).getDanhGia());
                        filters.add(res);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterlist.size();
                results.values = filterlist;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ress = (ArrayList<Restaurant>) results.values;
            notifyDataSetChanged();
        }
    }
}
