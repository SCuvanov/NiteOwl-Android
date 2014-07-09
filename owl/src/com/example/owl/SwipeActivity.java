package com.example.owl;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.owl.adapter.SwipeAdapter;
import com.example.owl.model.VideoItem;
import com.example.owl.model.VideoList;
import com.example.owl.utils.Utils;

public class SwipeActivity extends Fragment implements OnClickListener,
        OnItemClickListener {

    ListView listView;
    int lastIndex = -1;
    ArrayList<VideoItem> lstVideos;
    View vw_layout;
    View vw_master;
    View vw_detail;

    // detail view
    TextView tvTitle, tvTitle1, tvPrice, tvDesc, tvTime;
    ImageView img;
    ImageButton btnBack, btnCreate;

    // animation
    private Animation mSlideInLeft;
    private Animation mSlideOutRight;
    private Animation mSlideInRight;
    private Animation mSlideOutLeft;

    boolean _isBack = true;

    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist. The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed. Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }

        LinearLayout theLayout = (LinearLayout) inflater.inflate(
                R.layout.activity_master_detail, container, false);

        this.vw_master = (View) theLayout.findViewById(R.id.master);
        this.vw_detail = (View) theLayout.findViewById(R.id.detail);

        // get list view
        listView = (ListView) this.vw_master.findViewById(R.id.lst_videos);

        // get detail controls
        tvTitle = (TextView) this.vw_detail.findViewById(R.id.text_title);
        tvDesc = (TextView) this.vw_detail.findViewById(R.id.text_desc);
        tvTime = (TextView) this.vw_detail.findViewById(R.id.text_time);
        img = (ImageView) this.vw_detail.findViewById(R.id.image);

        btnBack = (ImageButton) this.vw_detail.findViewById(R.id.btn_back);
        btnCreate = (ImageButton) this.vw_master.findViewById(R.id.btn_create);

        btnBack.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        this.vw_master.setVisibility(View.VISIBLE);
        this.vw_detail.setVisibility(View.GONE);

        lstVideos = VideoList.getVideoList();

        listView.setAdapter(new SwipeAdapter(getActivity(), lstVideos));
        listView.setOnItemClickListener(this);

        this.initAnimation();

        Utils.setFontAllView(theLayout);
        return theLayout;

    }

    private void initAnimation() {
        // animation
        mSlideInLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.cell_left_in);
        mSlideOutRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.cell_right_out);
        mSlideInRight = AnimationUtils.loadAnimation(getActivity(),
                R.anim.cell_right_in);
        mSlideOutLeft = AnimationUtils.loadAnimation(getActivity(),
                R.anim.cell_left_out);
    }

    @Override
    public void onItemClick(AdapterView<?> adp, View listview, int position,
                            long id) {
        this._isBack = false;
        showView(this._isBack);

        if (adp != null && adp.getAdapter() instanceof SwipeAdapter) {
            SwipeAdapter newsAdp = (SwipeAdapter) adp.getAdapter();
            VideoItem itm = newsAdp.getItem(position);

            tvTitle.setText(itm.get_title());
            tvDesc.setText(itm.get_desc());
            tvTime.setText(itm.get_time());
            Bitmap bmp = Utils.GetImageFromAssets(getActivity(), "images/"
                    + itm.get_image());
            img.setImageBitmap(bmp);
        }

    }

    private void showView(boolean isBack) {
        try {

            if (isBack) {
                this.vw_master.setVisibility(View.VISIBLE);
                this.vw_detail.setVisibility(View.GONE);
                this.vw_detail.startAnimation(mSlideOutRight);
                this.vw_master.startAnimation(mSlideInLeft);
            } else {
                this.vw_master.setVisibility(View.GONE);
                this.vw_detail.setVisibility(View.VISIBLE);
                this.vw_master.startAnimation(mSlideOutLeft);
                this.vw_detail.startAnimation(mSlideInRight);
            }

        } catch (Exception e) {

        }
    }

    public void onBackPressed() {
        if (!this._isBack) {
            this._isBack = !this._isBack;
            showView(this._isBack);
            return;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case (R.id.btn_create):
                Intent intent = new Intent(v.getContext(),
                        CreateEventActivity.class);
                // intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                v.getContext().startActivity(intent);

                break;

            case (R.id.btn_back):
                onBackPressed();
                break;

        }
    }
}
