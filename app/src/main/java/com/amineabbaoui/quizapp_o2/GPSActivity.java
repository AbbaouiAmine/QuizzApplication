package com.amineabbaoui.quizapp_o2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amineabbaoui.quizapp_o2.Rest.API;
import com.amineabbaoui.quizapp_o2.Rest.Utilisateur;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GPSActivity extends AppCompatActivity {
    Location gps_loc;
    Location network_loc;
    Location final_loc;
    double longitude;
    double latitude;
    String userCountry, userAddress;
    Button btnVerifier;
    Boolean result=false;
    private  API jsonPlaceHolderApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        //Reference to textview
        TextView tv = findViewById(R.id.text_view);
        btnVerifier=(Button)findViewById(R.id.buttonVrgGPS);

        btnVerifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectRetrofit();
                getVerifyPhoto();

            }
        });

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
                tv.setText(userCountry + ", " + userAddress);
            }
            else {
                userCountry = "Unknown";
                tv.setText(userCountry);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void connectRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("https://jsonplaceholder.typicode.com/")
                .baseUrl(API.adresse)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(API.class);
    }

    private void getVerifyPhoto() {
        Call<Boolean> call = jsonPlaceHolderApi.getVerifyFace();

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (!response.isSuccessful()) {
                    //textViewResult.setText("Code: " + response.code());
                    return;
                }

                result = response.body();

                if(result)
                {

                    Toast t1 = Toast.makeText(GPSActivity.this, "Bon Quiz", Toast.LENGTH_SHORT);
                    t1.show();
                    Intent intent=new Intent(GPSActivity.this,QuizActivity.class);
                    //Intent intent1=new Intent(CameraActivity.this,GPSActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast t1 = Toast.makeText(GPSActivity.this, "Veuillez reprendre une autre photo SVP", Toast.LENGTH_SHORT);
                    t1.show();
                    Intent intent=new Intent(GPSActivity.this,CameraActivity.class);
                    startActivity(intent);
                }





            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast t1 = Toast.makeText(GPSActivity.this, "Connexion error", Toast.LENGTH_SHORT);
                t1.show();
            }
        });
    }
}
