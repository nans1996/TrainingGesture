package com.example.client;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TranslateActivity extends Activity {

    private ImageView imageview;
    private  final int Pick_image = 1;
    Bitmap selectBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        imageview = (ImageView) findViewById(R.id.imageView);
        Button imagebutton = (Button)findViewById(R.id.buttonLoad);
        Button sendbutton = (Button) findViewById(R.id.buttonSend);

        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postImage();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent ImageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, ImageReturnedIntent);
        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = ImageReturnedIntent.getData();
                        final InputStream imageinputStream = getContentResolver().openInputStream(imageUri);
                        selectBitmap = BitmapFactory.decodeStream(imageinputStream);
                        imageview.setImageBitmap(selectBitmap);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
        }
    }

    protected  ImageDataClass convertBitmapToBite() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(selectBitmap.getWidth()*selectBitmap.getHeight());
        selectBitmap.compress(Bitmap.CompressFormat.JPEG, 100, buffer);

        ImageDataClass img = new ImageDataClass("image1", buffer.toByteArray());
        return  img;
    }

protected  void postImage() {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://localhost:44387/")
            .build();

    ImageInterface service = retrofit.create(ImageInterface.class);


    Call call = service.translateImage(convertBitmapToBite());
    call.enqueue(new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
            if(response.isSuccessful()) {
                Log.println(1, "запрос ","true");
            } else {
                Log.println(2,"запрос ","false");
            }
        }

        @Override
        public void onFailure(Call call, Throwable t) {

        }
    });
}
}
