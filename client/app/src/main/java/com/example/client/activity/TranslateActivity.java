package com.example.client.activity;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.client.OkhttpClass;
import com.example.client.R;
import com.example.client.model.ImageDataClass;
import com.example.client.repos.ImageInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;

public class TranslateActivity extends AppCompatActivity {

    private ImageView imageview;
    private  final int Pick_image = 1;
    Bitmap selectBitmap;
    private FirebaseAuth mAuth;
    Button sendbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        imageview = (ImageView) findViewById(R.id.imageView);
        Button imagebutton = (Button)findViewById(R.id.buttonLoad);
        sendbutton = (Button) findViewById(R.id.buttonSend);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //[END initialize_auth]

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
                        imageview.setRotation(90);
                        imageview.setImageBitmap(selectBitmap);
                        sendbutton.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
        }
    }


    protected ImageDataClass convertBitmapToBite() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream(selectBitmap.getWidth() * selectBitmap.getHeight());
        selectBitmap.compress(Bitmap.CompressFormat.JPEG, 100, buffer);
        FirebaseUser currentuser = mAuth.getCurrentUser();
        boolean auth;
        if (currentuser != null) {
            auth = true;
        } else {
            auth = false;
        }
        ImageDataClass img = new ImageDataClass(currentuser.getUid(), buffer.toByteArray(), auth);
        return img;
    }

    protected void postImage() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://192.168.1.152:44387/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(OkhttpClass.getUnsafeOkHttpClient())
                    .build();

            ImageInterface service = retrofit.create(ImageInterface.class);


            Call call = service.translateImage(convertBitmapToBite());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response)  {
                    try (ResponseBody responseBody = (ResponseBody) response.body()) {
                        if (response.isSuccessful()) {
                            System.out.println("запрос true");
                            System.out.println(response.body());
                            System.out.println(responseBody);
                            String result = ((ResponseBody) response.body()).string();
                            int index = result.indexOf(",");
                            String res = result.substring(index+1, index+3);
                            if (Integer.parseInt(res) > 50) {
                                resultDialog(result);
                            } else {
                                resultDialog("Failed to recognize image");
                            }

                        } else {
                            System.out.println("запрос false");
                        }
                    } catch (Exception e) {

                    } finally {
                        response.raw().body().close();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                    System.out.println("mistake " + t);
                }
            });


}

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void resultDialog(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TranslateActivity.this);
        builder.setTitle("Result")
                .setIcon(R.drawable.zhest_icon)
                .setMessage(result)
                .setPositiveButton("Ok", null)
                .create().show();
    }
}
