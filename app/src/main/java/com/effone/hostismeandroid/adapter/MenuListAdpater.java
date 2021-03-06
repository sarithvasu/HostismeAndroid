package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.common.UpdateableInterface;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.Content;
import com.effone.hostismeandroid.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.effone.hostismeandroid.R.id.tv_ingredients;

/**
 * Created by sumanth.peddinti on 5/10/2017.
 */

public class MenuListAdpater extends BaseExpandableListAdapter {
    private HashMap<String, Content[]> childItems;
    private Context context;
    private String[] itemsname;
    private SqlOperation sqliteoperation;
    private String heading;
    private UpdateableInterface updateableInterface;


    public MenuListAdpater(@NonNull Context context, @LayoutRes int resource, String heading, String[] itemsname, @NonNull HashMap<String, Content[]> objects) {
        this.heading = heading;
        this.context = context;
        this.itemsname = itemsname;
        this.childItems = objects;
        clear(childItems);
        updateableInterface = (UpdateableInterface) context;
    }

    private void clear(HashMap<String, Content[]> childItems) {
        Iterator it = childItems.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Content[] contents= (Content[]) pair.getValue();
            for (int i = 0; i <contents.length ; i++) {
                contents[i].setQuantity(0);
            }

        }
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
        int quatity=food.getQuantity();
        final List<String> menuTypes = Arrays.asList(food.getMenu_types().split(","));
        View vi = convertView;
        final String sub_item_cat = itemsname[groupPosition];
        final ArrayList<String> checkedCountries = new ArrayList<String>();
        ;
        final FilterViewHolder holder;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vi = infalInflater.inflate(R.layout.menu_list_item, parent, false);
            holder = new FilterViewHolder();
            holder.child_text = (TextView) vi.findViewById(R.id.tv_dish_name);
            holder.tv_ingredients = (TextView) vi.findViewById(tv_ingredients);
            holder.tv_add_items = (TextView) vi.findViewById(R.id.tv_add_item);
            holder.tv_add_items.setVisibility(View.INVISIBLE);
            holder.dynamicCheckBoxes = new ArrayList<>();
            holder.addChcekBox = (TextView) vi.findViewById(R.id.ll_checkboxItems);
            holder.tv_Add_Min_Quan = (LinearLayout) vi.findViewById(R.id.btn_lay);
            holder.minusBtn = (TextView) vi.findViewById(R.id.tv_minus);
            holder.tvPrice = (TextView) vi.findViewById(R.id.tv_price);
            holder.tvQuatity = (TextView) vi.findViewById(R.id.tv_qutity);
            holder.addBtn = (TextView) vi.findViewById(R.id.tv_plus);
            vi.setTag(holder);
        } else
            holder = (FilterViewHolder) vi.getTag();


        //setting valuse into the textview of the exapanable listview
        if (food.getIs_special().equals("1")) {
            holder.child_text.setText("" + food.getName() + "( "+ context.getString(R.string.Special)+ ")");
            String text = food.getName() + "<font color=#ff0000 > ( "+ context.getString(R.string.Special)+ ") </font>";
            holder.child_text.setText(Html.fromHtml(text));
        } else {
            holder.child_text.setText("" + food.getName());
        }
        holder.tvQuatity.setTag(childPosition);
        holder.tvQuatity.setText(""+quatity);
        holder.tv_ingredients.setText("" + food.getIngredients());
        holder.tvPrice.setText("$ " + food.getPrice());
        holder.addChcekBox.setText(food.getMenu_types());
       /* CompoundButton.OnCheckedChangeListener checkListner = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String checkedText = compoundButton.getText() + "";

                if (isChecked) {
                    checkedCountries.add(checkedText);
                    // Toast.makeText(context, compoundButton.getText()+"is checked!!!", Toast.LENGTH_SHORT).show();
                } else {
                    checkedCountries.remove(checkedText);
                    //  Toast.makeText(context, compoundButton.getText()+" is not checked!!!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        holder.addChcekBox.removeAllViews();
        if (!food.getMenu_types().equals("") && menuTypes.size() > 0) {
            for (int i = 0; i < menuTypes.size(); i++) {
                CheckBox cb = new CheckBox(context);
                cb.setText(menuTypes.get(i));
                cb.setTextSize(9);
                holder.dynamicCheckBoxes.add(cb);
                ;
                holder.addChcekBox.addView(cb);
                cb.refreshDrawableState();
                cb.setOnCheckedChangeListener(checkListner);
            }
        }
*/
        final int[] qty = new int[1];
        if (!holder.tvQuatity.getText().toString().equals(""))
            qty[0] = Integer.parseInt(holder.tvQuatity.getText().toString());


        holder.addBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    Log.d("click", "click add for" + food);
                    if (qty[0] < 99) {
                        qty[0]++;
                        holder.tvQuatity.setText("" + qty[0]);
                        food.setQuantity(qty[0]);

                        sqliteoperation = new SqlOperation(context);
                        sqliteoperation.open();
                        sqliteoperation.AddOrSubstractProduct(heading, sub_item_cat,
                                food.getMenu_item_id(), food.getName(), food.getMenu_types()
                                , food.getIngredients(), food.getIs_special(), Float.parseFloat(String.valueOf(food.getPrice())), qty[0], 1, 1);

                        updateableInterface.update();
                        sqliteoperation.close();
                    }
            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (qty[0] > 0) {
                    qty[0]--;
                    holder.tvQuatity.setText("" + qty[0]);
                    food.setQuantity(qty[0]);
                }
                //Toast.makeText(context, "click remove", Toast.LENGTH_LONG).show();
                sqliteoperation = new SqlOperation(context);
                sqliteoperation.open();
                sqliteoperation.AddOrSubstractProduct(heading, sub_item_cat,
                        food.getMenu_item_id(), food.getName(), food.getMenu_types()
                        , food.getIngredients(), food.getIs_special(), Float.parseFloat(String.valueOf(food.getPrice())), qty[0], 1, 2);
                sqliteoperation.close();
                updateableInterface.update();
            }
        });


        return vi;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public static class FilterViewHolder {

        public TextView child_text;
        public TextView tv_ingredients;
        public TextView tv_add_items;
        public TextView addChcekBox;
        public LinearLayout tv_Add_Min_Quan;
        public TextView minusBtn;
        public TextView tvPrice;
        public TextView tvQuatity;
        public TextView addBtn;
        public ArrayList<CheckBox> dynamicCheckBoxes;
    }

}
