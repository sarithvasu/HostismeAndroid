package com.effone.hostismeandroid.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.effone.hostismeandroid.R;
import com.effone.hostismeandroid.adapter.MenuListAdpater;
import com.effone.hostismeandroid.db.SqlOperation;
import com.effone.hostismeandroid.model.Content;
import com.effone.hostismeandroid.model.Items;

import java.util.HashMap;


public class MenuViewFragment extends Fragment implements View.OnClickListener {
    ExpandableListView expListView;
    private Items[] mHisMenuItems;
    private int position;
    private ViewPager mViewPager;

    private Context c = getActivity();


    private RequestQueue mQueue;
    private MenuListAdpater expListAdapter;

    private TextView mTvConfirm, mTvSumaryDetails;

    private TextView mTvHeading;
    private TextView mImgLeftHand, mImgRightHand;
    private String heading,preHeading,postHeading;
    private String itemCountOfCart;
    private SqlOperation sqlOperation;
    private int mTotalPage;

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu_view, container, false);
        mTvHeading = (TextView) root.findViewById(R.id.tv_menu_heading);
        mTvHeading.setText(heading);
        mImgLeftHand = (TextView) root.findViewById(R.id.img_left_hand);
        mImgLeftHand.setOnClickListener(this);
        mImgRightHand = (TextView) root.findViewById(R.id.img_right_hand);
        mImgRightHand.setOnClickListener(this);
        mImgLeftHand.setText(preHeading);
        mImgRightHand.setText(postHeading);
        showingHands(position);
        mQueue = Volley.newRequestQueue(getActivity());
        sqlOperation = new SqlOperation(getActivity());
        expListView = (ExpandableListView) root.findViewById(R.id.elv_menu);
        String[] itemsname = new String[mHisMenuItems.length];
        HashMap<String, Content[]> mHashMap = new HashMap<>();
        for (int i = 0; i < mHisMenuItems.length; i++) {
            try {
                itemsname[i] = mHisMenuItems[i].getName();
                mHashMap.put(mHisMenuItems[i].getName(), mHisMenuItems[i].getContent());
            }catch (Exception e){

            }

        }
        MenuListAdpater adapter = new MenuListAdpater(getActivity(), android.R.layout.simple_list_item_1, heading, itemsname, mHashMap);
        expListView.setAdapter(adapter);


        return root;
    }

    private void showingHands(int pos) {
    /*    switch (position){
            case 0:

                break;
            case 2:
                break;
            default:
                break;*/
        /*}*/
        if (pos == 0) {
            mImgLeftHand.setVisibility(View.INVISIBLE);
        } else {
            mImgLeftHand.setVisibility(View.VISIBLE);
        }
        if (pos == (mTotalPage-1)) {
            mImgRightHand.setVisibility(View.INVISIBLE);
        } else {
            mImgRightHand.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View view) {
        //   OnHandClickInterface onHandClickInterface =(OnHandClickInterface) this;
        int pos = mViewPager.getCurrentItem();
        if (view.getId() == R.id.img_left_hand) {
            pos--;
            mViewPager.setCurrentItem(pos);
        } else if (view.getId() == R.id.img_right_hand) {
            pos++;
            mViewPager.setCurrentItem(pos);
        }

    }

    public void setHeading(String preheading, String postheading, String heading, int position) {
        this.heading = heading;
        this.position = position;
        this.preHeading=preheading;
        this.postHeading=postheading;
    }

    public void setParentView(ViewPager pager) {
        mViewPager = pager;
    }

    public void setValues(Items[] hisMenuItems,int totalPages) {
        mHisMenuItems = hisMenuItems;
        mTotalPage=totalPages;
    }


}
