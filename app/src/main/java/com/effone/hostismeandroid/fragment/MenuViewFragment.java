package com.effone.hostismeandroid.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.activity.MenuExpandableListAdapter;
import com.effone.hostismeandroid.activity.ViewCartActivity;
import com.effone.hostismeandroid.asyncs.SocketServerTask;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.common.UpdateableFragment;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.json.JsonDataToSend;
import com.effone.hostismeandroid.model.HISMenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class MenuViewFragment extends Fragment implements View.OnClickListener,UpdateableFragment {
   /* private ExpandableListView mExpandableMenu;
    private TextView mTvConfirm;
    private TextView  mTvMenuHeading;
    private TextView mTvSumaryDetails;
    private String mMealHeading;
    private HashMap<String, List<HISMenuItem>> mHashMap;
    private MenuExpandableListViewAdapter mMenuExpandableListViewAdapter;*/
    // TODO: Rename parameter arguments, choose names that match


    private static final String EXCEPT = "Exception";
    private static final String ERROR = "ERROR";
    private static final String PRICE = "Price";
    private SqlOperations sqliteoperation;

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> menuCollection;

    ExpandableListView expListView;

    private JSONObject jsonData;
    private JsonDataToSend jsonDataToSend;
    private Context c = getActivity();

    private SocketServerTask serverAsyncTask;
    private RequestQueue mQueue;
    private MenuExpandableListAdapter expListAdapter;


    private String qrResult;
    private String qrComplement;
    Toast mCurrentToast;
    private TextView mTvConfirm,mTvSumaryDetails;
    private AppPreferences appPreferences;
    private ProgressDialog pDialog;
    private  TextView mTvHeading;
    private String heading;
    private String itemCountOfCart;

    public MenuViewFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();
        showDialogOrder(1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =inflater.inflate(R.layout.content_menu, container, false);
       /* mExpandableMenu=(ExpandableListView)root.findViewById(R.id.elv_menu);
        init(root);
        setItems();*/
        appPreferences=new AppPreferences(getActivity());
        Intent intent = getActivity().getIntent();
        qrResult = intent.getStringExtra("qrResult");
        qrComplement = intent.getStringExtra("qrComplement");

        mQueue = Volley.newRequestQueue(getActivity());

        expListView = (ExpandableListView)root.findViewById(R.id.elv_menu);

        mTvConfirm=(TextView)root.findViewById(R.id.tv_confirm);
        mTvSumaryDetails=(TextView)root.findViewById(R.id.tv_summary_details);
        mTvHeading=(TextView)root.findViewById(R.id.tv_menu_heading); 
        mTvHeading.setText(heading);


        mTvConfirm.setOnClickListener(this);

        //float action button
        showDialogOrder(0);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading menu....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        webserveris();
        return root;
    }
    private void setToolbar() {
        ((AppCompatActivity)getActivity()). getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(getActivity()).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        ((AppCompatActivity)getActivity()).  getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.title_menu));
        TextView cust_sub_ttile=(TextView)((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText(appPreferences.getRESTAURANT_NAME());
        ImageView iv_back=(ImageView) ((AppCompatActivity)getActivity()).getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }
    private  void setItems(){

        // Array list for header
    /*    ArrayList<String> header = new ArrayList<String>();

        Iterator it = mHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            header.add((String) pair.getKey());

        }

        mMenuExpandableListViewAdapter = new MenuExpandableListViewAdapter(getActivity(), header, mHashMap);
        mExpandableMenu.setAdapter(mMenuExpandableListViewAdapter);
*/
    }

    private void init(View root) {
      /*  mTvConfirm=(TextView)root.findViewById(R.id.tv_confirm);
        mTvSumaryDetails=(TextView)root.findViewById(R.id.tv_summary_details);
        mTvMenuHeading=(TextView)root.findViewById(R.id.tv_menu_heading);
        mTvMenuHeading.setText(mMealHeading);
   *//*     mTvSumaryDetails.getDr*//*
        mTvConfirm.setOnClickListener(this);*/
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    private void webserveris() {
        menuCollection = new LinkedHashMap<>();
        groupList = new ArrayList<>();
        String JSON_URL="http://arslanaybars.com/MenuDroid/menuDroid-menu.json";
        StringRequest stringRequest = new StringRequest(JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonField = new JSONObject(response);
                            Log.d("JSON", jsonField.get("success").toString());
                            String success = jsonField.get("success").toString();
                            if  ("1".equals(success)) {
                    /* Detect field success has value 1*/
                                JSONArray CategoriesArray = jsonObject.getJSONArray("categories"); /*getting the JSON Array with the key categories */
                                if (CategoriesArray != null) {
                                    for (int i = 0; i < CategoriesArray.length(); i++) { //Search inner the Categories array
                                        String categoryName = CategoriesArray.getJSONObject(i).getString("name");
                                        groupList.add(categoryName); //add to the  groupList
                                        Log.d("CATEGORY", "The category is " + categoryName);
                                        JSONArray foodNameArray = new JSONArray(CategoriesArray.getJSONObject(i).getString("content"));
                                        childList = new ArrayList<String>();

                                        for (int j = 0; j < foodNameArray.length(); j++) {
                                            String foodname = foodNameArray.getJSONObject(j).getString("food");//get the value from key food
                                            String price = foodNameArray.getJSONObject(j).getString("price");//get the value from key price
                                            Log.d("Food", "The food from category " + categoryName + " is " + foodname + " this cost : " + price);
                                            childList.add(foodname + "||" + price); //add the food in the childList
                                        }
                                        menuCollection.put(categoryName, childList);

                                    }
                                }
                            }
                            pDialog.dismiss();
                        } catch (JSONException e) {
                            //jsonResponse = e.toString();
                            pDialog.dismiss();
                            Log.d(EXCEPT, "" + e.toString());
                        }

                        expListAdapter = new MenuExpandableListAdapter(getActivity(), groupList, menuCollection);
                        try {


                        expListAdapter.setOnDataChangeListener(new OnDataChangeListener() {
                            @Override
                            public void onDataChanged(int size) {
                                showDialogOrder(size);
                            }
                        });
                        }catch (Exception e){
                            Toast.makeText(getContext(),"Sumanth :"+e,Toast.LENGTH_SHORT).show();
                        }
                        expListView.setAdapter(expListAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
        mQueue.add(stringRequest);


       /* expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(groupPosition, childPosition);

            };
        });*/
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_confirm:
                if(showDialogOrder(0) != 0) {
                    Intent intent = new Intent(getActivity(), ViewCartActivity.class);
                    startActivity(intent);
                }else  {
                    Toast.makeText(getActivity(),"Soory we couldnot find any items in the cart...",Toast.LENGTH_SHORT).show();
                }
                break;
        }
   /*     HashMap<String, List<HISMenuItem>> hashMap = new HashMap<String, List<HISMenuItem>>();
        ArrayList<HISMenuItem> hisMenuItems;
        for (int i = 0; i < mMenuExpandableListViewAdapter .getGroupCount(); i++){
            hisMenuItems= new ArrayList<HISMenuItem>();
            for(int j=0;j<mMenuExpandableListViewAdapter.getChildrenCount(i);j++){
                hisMenuItems.add((HISMenuItem)mMenuExpandableListViewAdapter.getChild(i,j));
            }
            hashMap .put((String) mMenuExpandableListViewAdapter .getGroup(i),hisMenuItems);
        }

        Intent intent=new Intent(getContext(),ViewCartActivity.class);
        Bundle extras = new Bundle();
        extras.putSerializable("HashMap",hashMap);
        intent.putExtras(extras);
        startActivity(intent);*/
    }

   /* public void setHeading(String heading) {
        mMealHeading=heading;
    }

    public void setValues(HashMap<String, List<HISMenuItem>> hisMenuItems) {
        mHashMap=hisMenuItems;
    }*/


    private float showDialogOrder(int value) {
        float totalNumberOfItems = 0;
        //here get the order
        sqliteoperation = new SqlOperations(getActivity());
        sqliteoperation.open();
        List<HashMap<String, String>> dictionary = sqliteoperation.getOrder("SUMANTH");
        sqliteoperation.close();

        String totalbyFood;
        String quantity;
        String foodName;
        String messageOrder;
        String price;
        messageOrder = "\nOrder\nYour ordered";
        float totalbyOrder = 0;


        int j;

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < dictionary.size(); i++) {

            j = i + 1;
            /*I start at index 0 and finish at the penultimate index */
            HashMap<String, String> map = dictionary.get(i); //Get the corresponding map from the index
            totalbyFood = map.get("totalByFood");
            price = map.get("price");
            quantity = map.get("quantity");
            foodName = map.get("food_name");
            messageOrder += "\n " + j + " - " + foodName + " (" + price + " $  x  " + quantity + ")  " + totalbyFood + "$";
            totalbyOrder += Float.parseFloat(totalbyFood);
            totalNumberOfItems += Float.parseFloat(quantity);

            JSONObject food = new JSONObject();
            try {
                food.put("totalByFood", totalbyFood);
                food.put(PRICE, price);
                food.put("quantity", quantity);
                food.put("food_name", foodName);
            } catch (JSONException e) {
                Log.d(ERROR, e.toString());
                throw new RuntimeException(e);
            }
            jsonArray.put(food);

        }


        //get the qrResult and the qrComplement to create the new JSON
        jsonData = new JSONObject();
        jsonDataToSend = new JsonDataToSend(); //instantiate the new JSON object
        jsonDataToSend.setRequest("O-");
        jsonDataToSend.setMessage(qrResult);
        jsonDataToSend.setMessageJsonArray(jsonArray);//now the JSON is complete
        jsonData = jsonDataToSend.getOurJson();// pass the json object to this variable.
        String jsonStr = jsonData.toString();
        Log.d("JSON", jsonStr);


        this.itemCountOfCart=Math.round(totalNumberOfItems)+" Items in Cart \n"+" Total = " + totalbyOrder +"\n Plus Chargers";
        mTvSumaryDetails.setText(itemCountOfCart);
        return totalNumberOfItems;
    }

    public void setHeading(String heading) {
       this.heading=heading;
    }

    public void setValues(HashMap<String, List<HISMenuItem>> hisMenuItems) {

    }


    public void setUpdateCartSummary() {

//        showDialogOrder(1);
    }

    @Override
    public void update() {
   showDialogOrder(0);
    }
}
