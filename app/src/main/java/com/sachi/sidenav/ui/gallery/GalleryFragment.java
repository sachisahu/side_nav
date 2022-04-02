package com.sachi.sidenav.ui.gallery;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.*;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sachi.sidenav.R;
import com.sachi.sidenav.databinding.FragmentGalleryBinding;
import com.sachi.sidenav.ui.ImageDataClass;
import com.sachi.sidenav.ui.ListAdapters.ImageAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    Activity activity;
    ContentResolver contentResolver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        contentResolver = getActivity().getContentResolver();
    }

    ImageView imageView;
    GridView listViewImage;

    int GALLARY_CODE = 101;
    int CAMERA_CODE = 201;
    int CAMERA_PERMISSION_CODE = 202;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery,null,false);
        TextView textView = view.findViewById(R.id.text_gallery);
        textView.setText("This Is gall");
        FloatingActionButton captureimage;
        captureimage = view.findViewById(R.id.CameraCapture);
        //imageView = view.findViewById(R.id.imgCapture);
        listViewImage = view.findViewById(R.id.imgCaptureList);


        captureimage.setOnClickListener(item ->{
            callAlertDialoge();
        });





        return view;
    }

    private void callAlertDialoge() {
        AlertDialog builder = new AlertDialog.Builder(activity).create();
        builder.setTitle("Select One");

        View customeDialg =  getLayoutInflater().inflate(R.layout.alert_dialog,null);
        builder.setView(customeDialg);
        builder.show();

        Button camera,gallary;
        camera = customeDialg.findViewById(R.id.alertCamera);
        gallary = customeDialg.findViewById(R.id.alertGallery);

        gallary.setOnClickListener(item ->{
            Intent i = new Intent(Intent.ACTION_PICK, Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(i,GALLARY_CODE);
            builder.dismiss();

        });

        camera.setOnClickListener(item->{
            openCamera();
            builder.dismiss();
        });

    }

    private void openCamera() {
        if(activity.checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }
        else {
            Intent i = new Intent(ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,CAMERA_CODE);
        }
    }

    public List<ImageDataClass> imgList = new ArrayList<>();




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageAdapter imageAdapter = new ImageAdapter(imgList,getActivity());

        if(requestCode == GALLARY_CODE && resultCode==RESULT_OK){
            imgList.add(new ImageDataClass(data.getData()));
            listViewImage.setAdapter(imageAdapter);
            Log.d("Camera Data",data.getData()+"");
        }

        if(requestCode == CAMERA_CODE && resultCode == RESULT_OK){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            Uri uri = createImageUri(bitmap);
            Log.d("Camera Uri",uri+"");
            imgList.add(new ImageDataClass(uri));
            listViewImage.setAdapter(imageAdapter);
        }
    }

    private Uri createImageUri(Bitmap bitmap) {

        //Saving to Defulte Location of android
        /*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        String imagepath = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"title","image captured");*/

        //Saving Image Custome Path
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);

        File imageFolder = new File(Environment.getExternalStorageDirectory(),"App_Images");
        Boolean b =  imageFolder.mkdirs();
        Uri uri = Uri.fromFile(new File(imageFolder,"IMG_"+System.currentTimeMillis()+".jpg"));
        //Intent data.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,uri);

        Log.d("Camera Data",uri+" created Folder:"+b);

        return uri;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(ACTION_IMAGE_CAPTURE);
                File imageFolder = new File(Environment.getExternalStorageDirectory(),"App_Images");
                Boolean b =  imageFolder.mkdirs();
                Uri uri = Uri.fromFile(new File(imageFolder,"IMG_"+System.currentTimeMillis()+".jpg"));
                i.putExtra(EXTRA_OUTPUT,uri);
                startActivityForResult(i,CAMERA_CODE);
            }else {
                Toast.makeText(activity, "permission Deined", Toast.LENGTH_SHORT).show();
            }
        }
    }
}