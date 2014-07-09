package com.example.owl.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.owl.R;
import com.example.owl.model.VideoItem;
import com.example.owl.utils.Utils;

public class SwipeAdapter extends ArrayAdapter<VideoItem> {

    private List<VideoItem> _list;
    private final Activity _context;
    private static LayoutInflater _inflater = null;


    public SwipeAdapter(Activity context, List<VideoItem> lst) {
        super(context, R.layout.row_swipe, lst);
        this._context = context;
        _list = lst;


        _inflater = this._context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = _inflater.inflate(R.layout.row_swipe, null);
        }

        Utils.setFontAllView(parent);

        VideoItem vidItem = _list.get(position);
        Integer id = vidItem.get_id();

        View layer1 = view.findViewById(R.id.view_layer1);

        TextView tvTitle = (TextView) view.findViewById(R.id.text_name);
        TextView tvDesc = (TextView) view.findViewById(R.id.text_desc);
        ImageView iv = (ImageView) view.findViewById(R.id.image);

        view.setId(id);
        tvTitle.setText(vidItem.get_title());
        tvDesc.setText(vidItem.get_desc());

        Bitmap bmp = Utils.GetImageFromAssets(this._context, "images/"
                + vidItem.get_image());
        iv.setImageBitmap(bmp);

        layer1.setVisibility(View.VISIBLE);

        return view;
    }

}
