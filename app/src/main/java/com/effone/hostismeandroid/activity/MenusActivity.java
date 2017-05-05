package com.effone.hostismeandroid.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
import com.effone.hostismeandroid.asyncs.SocketServerTask;
import com.effone.hostismeandroid.common.AppPreferences;
import com.effone.hostismeandroid.common.OnDataChangeListener;
import com.effone.hostismeandroid.db.SqlOperations;
import com.effone.hostismeandroid.json.JsonDataToSend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MenusActivity extends AppCompatActivity implements SocketServerTask.OurTaskListener ,View.OnClickListener,OnDataChangeListener {

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
    private Context c = this;

    private SocketServerTask serverAsyncTask;
    private RequestQueue mQueue;
    private  MenuExpandableListAdapter expListAdapter;


    private String qrResult;
    private String qrComplement;
    Toast mCurrentToast;
    private TextView mTvConfirm,mTvSumaryDetails;
    private AppPreferences appPreferences;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        appPreferences=new AppPreferences(this);
        Intent intent = getIntent();
        qrResult = intent.getStringExtra("qrResult");
        qrComplement = intent.getStringExtra("qrComplement");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Menu");

        setToolbar();
        //set toolbar
        mQueue = Volley.newRequestQueue(this);

        expListView = (ExpandableListView) findViewById(R.id.elv_menu);

        mTvConfirm=(TextView)findViewById(R.id.tv_confirm);
        mTvSumaryDetails=(TextView)findViewById(R.id.tv_summary_details);

   /*     mTvSumaryDetails.getDr*/
        mTvConfirm.setOnClickListener(this);

        //float action button
        showDialogOrder(0);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading menu....");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        webserveris();
       /* MenuWebTask serverAsyncTask = new MenuWebTask(c);
        serverAsyncTask.execute();*/




    }


    private void setToolbar() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        LinearLayout customView = (LinearLayout)
                LayoutInflater.from(this).inflate(R.layout.default_custom_title,
                        null);
        ActionBar.LayoutParams params = new
                ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        getSupportActionBar().setCustomView(customView, params);
        TextView cust_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_titile);
        cust_ttile.setText(getString(R.string.title_menu));
        TextView cust_sub_ttile=(TextView)getSupportActionBar().getCustomView().findViewById(R.id.tv_custom_sub_titile);
        cust_sub_ttile.setText(appPreferences.getRESTAURANT_NAME());
        ImageView iv_back=(ImageView) getSupportActionBar().getCustomView().findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        showDialogOrder(0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_confirm:
              //  showDialogOrder(1);
                Intent intent=new Intent(MenusActivity.this,ViewCartActivity.class);

                startActivity(intent );
            default:
                break;
        }
    }



    private void showDialogOrder(int value) {

        //here get the order
        sqliteoperation = new SqlOperations(getApplicationContext());
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
        float totalNumberOfItems = 0;

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


            mTvSumaryDetails.setText(Math.round(totalNumberOfItems)+" Items in Cart \n"+" Total = " + totalbyOrder +"\n Plus Chargers");

    }

    private void CreateAlertAsOfNow(String messageOrder) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenusActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Your Order....");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        alertDialog.setIcon(R.mipmap.ic_food);
        alertDialog.setMessage(messageOrder);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                Intent intent=new Intent(MenusActivity.this,ViewCartActivity.class);

                startActivity(intent );
                // Write your code here to invoke YES event

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,	int which) {
                // Write your code here to invoke NO event

                dialog.cancel();
            }
        });
        alertDialog.show();
    }


    private void getJsonFromWeb() {
        menuCollection = new LinkedHashMap<>();
        groupList = new ArrayList<>();

        /* It would be better if  this process will be in a Thread.*/
        if (android.os.Build.VERSION.SDK_INT > 9) {
            /*To avoid the android.os.NetworkOnMainThreadException*/
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        webserveris();

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
                        expListAdapter = new MenuExpandableListAdapter(MenusActivity.this, groupList, menuCollection);
                        expListView.setAdapter(expListAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                      Toast.makeText(MenusActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
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

    public static String StreamToString(InputStream is) { //convert Stream to String
        //Create the  Buffer
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            //Read all the lines
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            Log.d(ERROR,e.toString());
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                Log.d(ERROR,e.toString());
            }
        }
        return sb.toString();
    }





    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }






    //showToast method
    void showToast(String text) {
        if (mCurrentToast != null) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        mCurrentToast.show();

    }

    @Override
    public void onDataChanged(int size) {

        showDialogOrder(0);
    }


    class MenuWebTask extends AsyncTask<Void, Void, String> {

        private ProgressDialog pDialog;
        private Context mContext;


        public MenuWebTask(Context context) {
            mContext = context;
        }


        @Override
        public void onPreExecute() {
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading menu....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            getJsonFromWeb();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();


            final MenuExpandableListAdapter expListAdapter = new MenuExpandableListAdapter(MenusActivity.this, groupList, menuCollection);
            expListView.setAdapter(expListAdapter);
            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    final String selected = (String) expListAdapter.getChild(groupPosition, childPosition);

                    Log.d("jj", "paretn is " + parent +
                            "\n gorup position is " + groupPosition +
                            "\n childposition " + childPosition +
                            " \n and the id is " + id);

                /*
                * Group position is the category (grouplist), example Soup,Breakfast
                * childposition is the foodname (childList ), example cheese pancake
                * */
                    Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG).show();
                    return true;
                }
            });
        }
    }


    /* INTERFACE METHODS FROM OurTaskListener-SocketServerTask */
    @Override
    public void onOurTaskStarted() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    @Override
    public void onOurTaskInProcess() {
        throw new UnsupportedOperationException("This method is not implemented yet.");
    }

    @Override
    public void onOurTaskFinished(String result) {
        if ("Connection Accepted".equals(result)) {
            showToast("order is ok");
            //we could send the order, so we need to empty it.
            SqlOperations mysqlOperation= new SqlOperations(MenusActivity.this);
            mysqlOperation.open();
            mysqlOperation.setEmptyOrder();
            mysqlOperation.close();
        }else{
            showToast("Unable to connect.");
        }
    }
}
