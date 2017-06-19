package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.UpdateableInterface;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.Content;

import java.util.HashMap;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class MenuListAdpater extends BaseExpandableListAdapter {
    private HashMap<String,Content[]> childItems;
    private Context context;
    private String[] itemsname;
    private SqlOperation sqliteoperation;
    private String heading;
    private UpdateableInterface updateableInterface;


    public MenuListAdpater(@NonNull Context context, @LayoutRes int resource, String heading, String[] itemsname, @NonNull HashMap<String, Content[]> objects) {
       this.heading=heading;
        this.context=context;
        this.itemsname=itemsname;
        this.childItems=objects;
        updateableInterface=(UpdateableInterface) context;
    }



    @Override
    public int getGroupCount() {
        return itemsname.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childItems.get(itemsname[groupPosition]).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return itemsname[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(itemsname[groupPosition])[childPosition];
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String foodName = itemsname[groupPosition];
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

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Content food = (Content) getChild(groupPosition, childPosition);
        final String sub_item_cat=itemsname[groupPosition];
        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.menu_list_item, parent, false);
            final TextView child_text = (TextView) convertView.findViewById(R.id.tv_dish_name);
            final TextView tv_ingredients=(TextView) convertView.findViewById(R.id.tv_ingredients);
            TextView tv_add_items = (TextView) convertView.findViewById(R.id.tv_add_item);
            tv_add_items.setVisibility(View.INVISIBLE);
            LinearLayout tv_Add_Min_Quan = (LinearLayout) convertView.findViewById(R.id.btn_lay);
            final TextView minusBtn = (TextView) convertView.findViewById(R.id.tv_minus);
            final TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            final TextView tvQuatity = (TextView) convertView.findViewById(R.id.tv_qutity);
            TextView addBtn = (TextView) convertView.findViewById(R.id.tv_plus);


            //setting valuse into the textview of the exapanable listview
            child_text.setText(""+food.getName());
            tv_ingredients.setText(""+food.getIngredients());
            tvPrice.setText("$ "+food.getPrice());





            final int[] qty = new int[1];
            if(!tvQuatity.getText().toString().equals(""))
                qty[0] =Integer.parseInt(tvQuatity.getText().toString());



            addBtn.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    Log.d("click", "click add for" + food);
                    if(qty[0] <99) {
                        qty[0]++;
                        tvQuatity.setText("" + qty[0]);
                    }
                    sqliteoperation = new SqlOperation(context);
                    sqliteoperation.open();
                    sqliteoperation.AddOrSubstractProduct(heading,sub_item_cat,
                            food.getMenu_item_id(),food.getName()
                            ,food.getIngredients(),Float.parseFloat(String.valueOf(food.getPrice())),qty[0],1,1);

                    updateableInterface.update();
                    sqliteoperation.close();
                }
            });
            minusBtn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        if(qty[0] >0) {
                            qty[0]--;
                            tvQuatity.setText("" + qty[0]);
                        }
                        //Toast.makeText(context, "click remove", Toast.LENGTH_LONG).show();
                        sqliteoperation = new SqlOperation(context);
                        sqliteoperation.open();
                        sqliteoperation.AddOrSubstractProduct(heading,sub_item_cat,
                                food.getMenu_item_id(),food.getName()
                                ,food.getIngredients(),Float.parseFloat(String.valueOf(food.getPrice())),qty[0],1, 2);
                        sqliteoperation.close();
                        updateableInterface.update();
                    }
            });


        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



}
