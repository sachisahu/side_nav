package com.sachi.sidenav.ui.ListAdapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sachi.sidenav.R;
import com.sachi.sidenav.ui.ImageDataClass;

import java.util.ArrayList;
import java.util.List;


public class ImageAdapter extends BaseAdapter {

    List<ImageDataClass> imageDataClasses = new ArrayList<>();
    Activity activity;

    public ImageAdapter(List<ImageDataClass> imageDataClasses, Activity activity) {
        this.imageDataClasses = imageDataClasses;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return imageDataClasses.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view1 = layoutInflater.inflate(R.layout.image_list_row,null,false);

        ImageDataClass imageDataClass =imageDataClasses.get(i);

        ImageView imageView = view1.findViewById(R.id.imgData);
        imageView.setImageURI(imageDataClass.uriImages);

        return view1;
    }
}
