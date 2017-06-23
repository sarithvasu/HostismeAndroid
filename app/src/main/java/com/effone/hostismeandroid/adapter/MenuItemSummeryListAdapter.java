package com.effone.hostismeandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.effone.hostismeandroid.MainActivity;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.MenuActivity;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.CartItems;
import com.effone.hostismeandroid.model.TaxItems;
import com.effone.hostismeandroid.model_for_json.MenuTaxItens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.effone.hostismeandroid.activity.MenuActivity.taxList;
import static com.effone.hostismeandroid.db.DBConstant.ser;
import static com.effone.hostismeandroid.db.DBConstant.serviceTax;
import static com.effone.hostismeandroid.db.DBConstant.vatTax;


/**
 * Created by sarith.vasu on 17-04-2017.
 */

public class MenuItemSummeryListAdapter extends ArrayAdapter<CartItems> {
    private ArrayList<CartItems> orderedItemSummaries;
    private LayoutInflater inflater;
    private  Context mContext;
    private SqlOperation sqliteoperation;
    private  List<MenuTaxItens> taxItemses;
    private OnDataChangeListener mOnDataChangeListener;
    public MenuItemSummeryListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<CartItems> orderedItemSummaries, List<MenuTaxItens> taxItemses) {
        super(context, resource,  orderedItemSummaries);
        this.mContext=context;
        this.orderedItemSummaries =(ArrayList<CartItems>) orderedItemSummaries;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.taxItemses=taxItemses;
        this.mOnDataChangeListener= (OnDataChangeListener) context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final MenuItemSummeryListAdapter.FilterViewHolder holder;
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.summery_list_item, null);
            holder = new MenuItemSummeryListAdapter.FilterViewHolder();
            holder.tv_summery_item_name = (TextView) convertView.findViewById(R.id.tv_summery_item_name);
            holder.tv_item_total_price = (TextView) convertView.findViewById(R.id.tv_item_total_price);
            holder.tv_service_charges_value = (TextView) convertView.findViewById(R.id.tv_service_charges_value);
            holder.tv_vat_value = (TextView) convertView.findViewById(R.id.tv_vat_value);
            holder.tv_service_tax_value = (TextView) convertView.findViewById(R.id.tv_service_tax_value);
            holder.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            holder.tv_minus = (TextView) convertView.findViewById(R.id.tv_minus);
            holder.tv_add = (TextView) convertView.findViewById(R.id.tv_plus);
            holder.tv_quantity = (TextView) convertView.findViewById(R.id.tv_qutity);
            holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);
            holder.tv_taxfinalItem=(TextView) convertView.findViewById(R.id.tv_tax_items);

            holder.mTaxName1=(TextView) convertView.findViewById(R.id.tv_service_charges);
            holder.mTaxName2=(TextView) convertView.findViewById(R.id.tv_vat);
            holder.mTaxName3=(TextView) convertView.findViewById(R.id.tv_service_tax);
            holder.mTaxName4=(TextView) convertView.findViewById(R.id.tax4);

            holder.addChcekBox = (LinearLayout) convertView.findViewById(R.id.ll_checkboxItems);

            convertView.setTag(holder);
        } else
            holder = (MenuItemSummeryListAdapter.FilterViewHolder) convertView.getTag();

        final ArrayList<String> checkedCountries ;
           checkedCountries = new ArrayList<String>();
        if (orderedItemSummaries.size() <= 0) {
            holder.tv_summery_item_name.setText("No Data");

        } else {
            /***** Get each Model object from Arraylist ********/
            final CartItems value = (CartItems) orderedItemSummaries.get(position);
//            final TaxItems taxItems=(TaxItems) taxItemses.get(position);
            /************  Set Model values in Holder elements ***********/
            holder.tv_summery_item_name.setText(value.getItemName()+" ( "+value.getItemQuantity() +" X "+ value.getItemPrice()+" ) ");
            holder.tv_item_total_price.setText("$ "+value.getItemPrice()*value.getItemQuantity());
            double ammount=value.getItemPrice()*+value.getItemQuantity()+Float.parseFloat(taxList.get(0).getChargevalue());
            holder.mTaxName1.setText(taxList.get(0).getName());
            holder.tv_service_charges_value.setText("$ "+ammount);
            holder.tv_service_charges_value.setVisibility(View.GONE);
            holder.tv_vat_value.setText("$ "+(value.getItemPrice()*+value.getItemQuantity() / 100) * Float.parseFloat(taxList.get(1).getChargevalue()));
            holder.mTaxName2.setText(taxList.get(1).getName());
            holder.mTaxName2.setVisibility(View.GONE);
            double ammounts=value.getItemPrice()*+value.getItemQuantity()*+value.getItemQuantity()+Float.parseFloat(taxList.get(2).getChargevalue());
            holder.mTaxName3.setText(taxList.get(2).getName());
            holder.mTaxName3.setVisibility(View.GONE);
            holder.tv_service_tax_value.setText("$ "+ammounts);
            holder.tv_service_tax_value.setVisibility(View.GONE);
            holder.mTaxName4.setText(taxList.get(3).getName());
            holder.mTaxName4.setVisibility(View.GONE);
            holder.tv_taxfinalItem.setText("$ "+(value.getItemPrice()*+value.getItemQuantity() / 100) * Float.parseFloat(taxList.get(3).getChargevalue()));
            holder.tv_taxfinalItem.setVisibility(View.GONE);
            holder.tv_total.setText("$ "+totalAmmount(value.getItemQuantity()*value.getItemPrice()));
            holder.tv_quantity.setText(""+value.getItemQuantity());




            List<String> menuTypes = Arrays.asList(value.getMenuType().split(","));
            CheckBox[] dynamicCheckBoxes = new CheckBox[menuTypes.size()];
            CompoundButton.OnCheckedChangeListener checkListner=new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    String checkedText = compoundButton.getText()+"";

                    if(isChecked){
                        checkedCountries.add(checkedText);
                        Toast.makeText(getContext(), compoundButton.getText()+"is checked!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        checkedCountries.remove(checkedText);
                        Toast.makeText(getContext(), compoundButton.getText()+" is not checked!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            holder.addChcekBox.removeAllViews();
            if(!value.getMenuType().equals("")&&menuTypes.size()>0) {
                for (int i = 0; i < dynamicCheckBoxes.length; i++) {
                    CheckBox cb = new CheckBox(getContext());
                    cb.setText(menuTypes.get(i));
                    cb.setTextSize(9);
                    cb.setChecked(true);
                    dynamicCheckBoxes[i] = cb;
                    checkedCountries.add(menuTypes.get(i));
                    holder.addChcekBox.addView(cb);
                    cb.setOnCheckedChangeListener(checkListner);
                }
            }

        final int[] qty = new int[1];
        if(! holder.tv_quantity.getText().toString().equals(""))
            qty[0] =Integer.parseInt(holder.tv_quantity.getText().toString());
        holder.tv_close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                sqliteoperation = new SqlOperation(mContext);
                sqliteoperation.open();
                if(sqliteoperation.getItemCountInCart()!=1) {
                    //we are deleteing the item from the cart based on the item_id and ItemNamei

                    if(sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName(),value.getMenuType()))
                    {
                        Toast.makeText(mContext,"Item removed",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName(),value.getMenuType());
                    Intent intent=new Intent(mContext, MenuActivity.class);
                    intent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    mContext.startActivity(intent);
                }
                sqliteoperation.close();
                mOnDataChangeListener.onDataChanged(1);

            }
            });
        holder.tv_add.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] <99) {
                    qty[0]++;
                    holder.tv_quantity.setText("" + qty[0]);


                    sqliteoperation = new SqlOperation(mContext);
                    sqliteoperation.open();

                    sqliteoperation.AddOrSubstractProduct(value.getItemCatagerie(), value.getItemSubCat(),
                            value.getItemMenuCatId(), value.getItemName()
                            , checkedCountries, value.getItemIngred(), value.getSpecial(), Float.parseFloat(String.valueOf(value.getItemPrice())), qty[0], 1, 1);

                    sqliteoperation.close();
                    mOnDataChangeListener.onDataChanged(1);
                }                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 1));
            }
        });


        holder.tv_minus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(qty[0] >0) {
                    qty[0]--;
                    holder.tv_quantity.setText("" + qty[0]);

          //
                    sqliteoperation = new SqlOperation(mContext);
                    sqliteoperation.open();
                    sqliteoperation.AddOrSubstractProduct(value.getItemCatagerie(), value.getItemSubCat(),
                            value.getItemMenuCatId(), value.getItemName()
                            , checkedCountries, value.getItemIngred(), value.getSpecial(), Float.parseFloat(String.valueOf(value.getItemPrice())), qty[0], 1, 2);
                        if(qty[0] == 0){
                            if(sqliteoperation.getItemCountInCart()!=1) {
                                //we are deleteing the item from the cart based on the item_id and ItemName
                                sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName(), value.getMenuType());
                            }else{
                                sqliteoperation.cartItemDelete(value.getItemMenuCatId(), value.getItemName(), value.getMenuType());
                                Intent intent=new Intent(mContext, MenuActivity.class);
                                intent .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                            }
                        }

                    sqliteoperation.close();
                    mOnDataChangeListener.onDataChanged(1);
                // tvQuatity.setText( sqliteoperation.getCount(groupPosition, childPosition, details[0], Float.parseFloat(details[1]), 2));
                }
            }
        });
        }
        return convertView;

    }

    private double totalAmmount(double price) {
        double sum=0;
        for(int i=0;i<taxList.size();i++){
            if(taxList.get(i).getType().equals("Fixed")){
                sum   = sum+ price +Float.parseFloat(taxList.get(i).getChargevalue());
            }else{
                sum = sum + (price / 100.0f) * Float.parseFloat(taxList.get(i).getChargevalue());
            }
        }

        return sum;
    }

    public static  class FilterViewHolder {
        TextView tv_summery_item_name;
        TextView tv_item_total_price;
        TextView tv_service_charges_value;
        TextView tv_vat_value;
        TextView tv_service_tax_value;
        TextView tv_taxfinalItem;
        TextView tv_total;
        TextView tv_minus;
        TextView tv_add;
        TextView tv_quantity;
        TextView tv_close;
        LinearLayout addChcekBox;


        TextView mTaxName1;
        TextView mTaxName2;
        TextView mTaxName3;
        TextView mTaxName4;

    }
}

