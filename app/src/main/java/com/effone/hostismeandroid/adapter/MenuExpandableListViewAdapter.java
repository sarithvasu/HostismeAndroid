package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.model.HISMenuItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class MenuExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> header; // header titles
    // Child data in format of header title, child title
    private HashMap<String, List<HISMenuItem>> child;


    public MenuExpandableListViewAdapter(Context context, List<String> listDataHeader, HashMap<String, List<HISMenuItem>> listChildData) {
        this._context = context;
        this.header = listDataHeader;
        this.child = listChildData;
    }

    @Override
    public int getGroupCount() {
        // Get header size
        return this.header.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // return children count
        return this.child.get(this.header.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        // Get header position
        return this.header.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        // This will return the child
        return this.child.get(this.header.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Getting header title
        String headerTitle = (String) getGroup(groupPosition);

        // Inflating header layout and setting text
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.header_menu_item, parent, false);
        }

        TextView header_text = (TextView) convertView.findViewById(R.id.tv_menu_header);
        header_text.setText(headerTitle);

        // If group is expanded then change the text into bold and change the
        // icon
        ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // Getting child text
        final HISMenuItem childMenu= (HISMenuItem) getChild(groupPosition, childPosition);
        final ViewHolder holder;
        // Inflating child layout and setting textview
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item, parent, false);
            holder.child_text = (TextView) convertView.findViewById(R.id.tv_dish_name);
            holder.tv_add_item=(TextView)convertView.findViewById(R.id.tv_add_item);
            holder.tv_Add_Min_Quan=(LinearLayout)convertView.findViewById(R.id.btn_lay);
            holder.minusBtn=(TextView)convertView.findViewById(R.id.tv_minus);
            holder.tvPrice=(TextView)convertView.findViewById(R.id.tv_price);
            holder.tvQuatity=(TextView)convertView.findViewById(R.id.tv_qutity);
            holder.addBtn=(TextView)convertView.findViewById(R.id.tv_plus);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final int[] qty = new int[1];
        if(! holder.tvQuatity.getText().toString().equals(""))
            qty[0] =Integer.parseInt(holder.tvQuatity.getText().toString());

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(qty[0] >0){
                    qty[0]--;
                    holder.tvQuatity.setText(""+ qty[0]);
                    childMenu.setQty(qty[0]);

                }
            }
        });
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qty[0] <99){
                    qty[0]++;
                    holder.tvQuatity.setText(""+ qty[0]);
                    childMenu.setQty(qty[0]);
                }
            }
        });

        if(childMenu.isAllowMultiple()) {
            holder.tv_add_item.setVisibility(View.GONE);
            holder.tv_Add_Min_Quan.setVisibility(LinearLayout.VISIBLE);
        }else {
            holder.tv_Add_Min_Quan.setVisibility(LinearLayout.GONE);
            holder.tv_add_item.setVisibility(View.VISIBLE);
        }
        holder.child_text.setText(childMenu.getName());
        holder.tvPrice.setText("$ "+childMenu.getPrice());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        // TODO Auto-generated method stub
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