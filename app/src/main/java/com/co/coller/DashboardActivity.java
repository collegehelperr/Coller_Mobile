package com.co.coller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.co.coller.api.api;
import com.co.coller.api.apiClient;
import com.co.coller.api.sharedPref;
import com.co.coller.college.CollegeActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.owl93.dpb.CircularProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ImageButton btnProfil;
    TextView tvNama;
    sharedPref sharedPref;
    CircleImageView fotoProf;
    CardView btnSpinner, btnCollege;
    CircularProgressView circleGraph;
    float progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPref = new sharedPref(this);

        tvNama = (TextView) findViewById(R.id.tv_nama);
        btnProfil = (ImageButton) findViewById(R.id.btn_prof);
        fotoProf = (CircleImageView) findViewById(R.id.foto_profil_dashboard);
        btnSpinner = (CardView) findViewById(R.id.wheel_spinner);
        btnCollege = (CardView) findViewById(R.id.college_manage);
        circleGraph = (CircularProgressView) findViewById(R.id.graph_todolist);

        tvNama.setText("Hai, " + sharedPref.getName());

        String urlImg = "https://3907-20-119-63-129.ngrok.io" + sharedPref.getProfImg();
        Log.i("Url image", urlImg);

        Glide.with(this).load(urlImg).into(fotoProf);

        circleGraph.setVisibility(View.GONE);
        getPersentase();

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ProfilActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, CollegeActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void getPersentase() {
        final String uid = sharedPref.getUid();
        api api = apiClient.getClient().create(api.class);
        Call<JsonObject> getPersentase = api.getPersentase(uid);

        getPersentase.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()){
                    if (response.body() != null){
                        try {
                            JSONObject jObj = new JSONObject(new Gson().toJson(response.body()));
                            circleGraph.setProgress(Float.parseFloat(jObj.getString("Persentase")));
                            circleGraph.setVisibility(View.VISIBLE);
//                            progress = Float.parseFloat(jObj.getString("Persentase"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(DashboardActivity.this, "Persentase " + progress, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}