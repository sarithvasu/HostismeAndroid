package com.effone.hostismeandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuExpandableListViewAdapter;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperations;

import java.util.List;
import java.util.Map;

import static com.effone.hostismeandroid.R.id.tv_add_item;


public class MenuExpandableListAdapter extends BaseExpandableListAdapter  {
    private SqlOperations sqliteoperation;
    private Activity context;
    private Map<String, List<String>> menuCollections;
    private List<String> foods;
   private OnDataChangeListener mOnDataChangeListener;
    public MenuExpandableListAdapter(Activity context, List<String> foods, Map<String, List<String>> menuCollections) {
        this.context = context;
        this.menuCollections = menuCollections;
        this.foods = foods;
        this.mOnDataChangeListener= (OnDataChangeListener) context;

    }

    public Object getChild(int groupPosition, int childPosition) {
        return menuCollections.get(foods.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String food = (String) getChild(groupPosition, childPosition);
        MenuExpandableListViewAdapter.ViewHolder holder = null;
        if (convertView == null) {
            holder = new MenuExpandableListViewAdapter.ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item, parent, false);


            TextView child_text = (TextView) convertView.findViewById(R.id.tv_dish_name);
            TextView tv_add_items = (TextView) convertView.findViewById(tv_add_item);
            LinearLayout tv_Add_Min_Quan = (LinearLayout) convertView.findViewById(R.id.btn_lay);
            TextView minusBtn = (TextView) convertView.findViewById(R.id.tv_minus);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
             final TextView tvQuatity = (TextView) convertView.findViewById(R.id.tv_qutity);
            TextView addBtn = (TextView) convertView.findViewById(R.id.tv_plus);


            final String[] details = food.split("\\|\\|");
          //  tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 0));
            final int[] qty = new int[1];
            if(! tvQuatity.getText().toString().equals(""))
                qty[0] =Integer.parseInt(tvQuatity.getText().toString());

            addBtn.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Log.d("click", "click add for" + food);
                    if(qty[0] <99) {
                        qty[0]++;
                   tvQuatity.setText("" + qty[0]);
                    }

                    sqliteoperation = new SqlOperations(context);
                    sqliteoperation.open();
                    sqliteoperation.AddOrSubstractProduct(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 1);
                    mOnDataChangeListener.onDataChanged(1);
                    sqliteoperation.close();
                   // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 1));
                }
            });


            minusBtn.setOnClickListener(new OnClickListener() {

                public void onClick(View v) {
                    Log.d("click", "click remove for" + food);
                    if(qty[0] >0) {
                        qty[0]--;
                        tvQuatity.setText("" + qty[0]);
                    }
                    Toast.makeText(context, "click remove", Toast.LENGTH_LONG).show();
                    sqliteoperation = new SqlOperations(context);
                    sqliteoperation.open();
                    sqliteoperation.AddOrSubstractProduct(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 2);
                    sqliteoperation.close();
                   // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 2));

                }
            });

            ///to separte every || , the index 0 is the food and the index 1 is the price
            child_text.setText(details[0]);
            tvPrice.setText("$ "+details[1]+".00");
            tv_add_items.setVisibility(View.GONE);

        }
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return menuCollections.get(foods.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return foods.get(groupPosition);
    }

    public int getGroupCount() {
        return foods.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String foodName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_menu_item,
                    null);
        }
        TextView item = (TextView) convertView.findViewById(R.id.tv_menu_header);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(foodName);
        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public static class ViewHolder {

        public TextView child_text;
        public TextView tv_add_item;
        public LinearLayout tv_Add_Min_Quan;
        public TextView minusBtn;
        public TextView tvQuatity;
        public TextView tvPrice;
        public TextView addBtn;


    }
}
