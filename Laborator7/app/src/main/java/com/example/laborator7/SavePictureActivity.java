package com.example.laborator7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class SavePictureActivity extends AppCompatActivity {
    
    private static final String PHOTOS_DIRECTORY = "/Laborator7_Image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savepicture);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 50);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }
        ImageView photoView = findViewById(R.id.photo_view);
        photoView.setImageBitmap(MainActivity.bitmap);
        Button saveBtn =  findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               savePhoto(MainActivity.bitmap);
        }
        });
       
    }

    public String savePhoto(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File savedPhotosDirectory = new File(Environment.getExternalStorageDirectory() + PHOTOS_DIRECTORY);


        if (!savedPhotosDirectory.exists()) {

            savedPhotosDirectory.mkdirs();
        }

        try {
            File photoFile = new File(savedPhotosDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            photoFile.createNewFile();
            FileOutputStream photo_fo = new FileOutputStream(photoFile);
            photo_fo.write(bytes.toByteArray());

            MediaScannerConnection.scanFile(this,
                                             new String[]{photoFile.getPath()},
                                             new String[]{"image/jpeg"}, null);
            photo_fo.close();
            TextView textView = findViewById(R.id.info_text);
            String info = "File saved at" + photoFile.getAbsolutePath();
            textView.setText(info);

            return photoFile.getAbsolutePath();
        } catch (IOException error) {
            error.printStackTrace(); }
        return "";
    }
}
