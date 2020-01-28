package com.amineabbaoui.quizapp_o2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.amineabbaoui.quizapp_o2.Rest.UploadAPIs;
import com.amineabbaoui.quizapp_o2.SendImage.NetworkClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CameraActivity extends AppCompatActivity {
    //Adresse variables
    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitude;
    double latitude;
    String userCountry, userAddress;
    String adresseResult;
    //Fin adresse
    Button cameraButton;
    ImageView cameraImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        bindViews();
        getAdresse();
        onClikCameraButton();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap=(Bitmap)data.getExtras().get("data");
        File f;

        try {
            //"+MainActivity.NomUser+ "("+MainActivity.EmailUser+"), Adresse :
            //create a file to write bitmap data
            f = new File(CameraActivity.this.getCacheDir(), "profil");
            f.createNewFile();

//Convert bitmap to byte array

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
            uploadToServer(f);
        } catch (IOException e) {
            e.printStackTrace();
        }


        this.cameraImageView.setImageBitmap(bitmap);

        //Intent intent=new Intent(CameraActivity.this,QuizActivity.class);
        Intent intent1=new Intent(CameraActivity.this,GPSActivity.class);
        startActivity(intent1);
    }

    public void onClikCameraButton()
    {
        this.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);

            }
        });
    }


    public void bindViews()
    {
        this.cameraButton=findViewById(R.id.buttonCamera);
        this.cameraImageView=findViewById(R.id.imageview_camera);
    }

    private void uploadToServer(File filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        UploadAPIs uploadAPIs = retrofit.create(UploadAPIs.class);
        //Create a file object using file path
        File file =filePath;
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        Call call = uploadAPIs.uploadImage(MainActivity.NomUser+";"+adresseResult,part);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.v("image send","Okkkkkkkkkkke");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.v("image send","failureeeeeeeeeee");
            }
        });
    }


    private void getAdresse()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        try {

            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else {
            latitude = 0.0;
            longitude = 0.0;
        }

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);

        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                userCountry = addresses.get(0).getCountryName();
                userAddress = addresses.get(0).getAddressLine(0);
                adresseResult=userCountry + ", " + userAddress;
            }
            else {
                userCountry = "Non trouvé";
                adresseResult=userCountry;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
