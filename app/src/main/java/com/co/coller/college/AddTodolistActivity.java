package com.co.coller.college;

import static com.co.coller.college.TodolistFragment.DETAIL_TODOLIST;
import static com.co.coller.college.TodolistFragment.ID_TODOLIST;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.co.coller.R;
import com.co.coller.api.api;
import com.co.coller.api.apiClient;
import com.co.coller.api.sharedPref;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTodolistActivity extends AppCompatActivity {

    EditText edDetailTodolist;
    Button btnSimpan, btnDelete, btnBack;
    api api;
    sharedPref sharedPref;
    String id_todolist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todolist);

        sharedPref = new sharedPref(this);
        Intent intent = getIntent();

        String detail_todolist = intent.getStringExtra(DETAIL_TODOLIST);
        id_todolist = intent.getStringExtra(ID_TODOLIST);

        edDetailTodolist = (EditText) findViewById(R.id.txt_detail_todolist);
        btnSimpan = (Button) findViewById(R.id.btn_simpan_todolist);
        btnDelete = (Button) findViewById(R.id.btn_hapus_todolist);
        btnBack = (Button) findViewById(R.id.button_x_todolist);

        if (id_todolist != null){
            edDetailTodolist.setText(detail_todolist);
            btnDelete.setVisibility(View.VISIBLE);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteTodolist();
                }
            });
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formCheck();
            }
        });
    }

    private void deleteTodolist() {
        api = apiClient.getClient().create(api.class);
        Call<JsonObject> deleteTodolist = api.deleteTodolist(id_todolist);

        deleteTodolist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful() && response.body() != null){
                    Log.i("onSuccess", response.body().toString());
                    startActivity(new Intent(AddTodolistActivity.this, CollegeActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void formCheck() {
        final String uid = sharedPref.getUid();
        final String detailTodolist = edDetailTodolist.getText().toString();

        if (TextUtils.isEmpty(detailTodolist)) {
            edDetailTodolist.setError("Please enter caption");
            edDetailTodolist.requestFocus();
            return;
        }

        if (id_todolist != null){
            updateTodolist(id_todolist, detailTodolist);
        } else {
            saveTodolist(uid, detailTodolist);
        }
    }

    private void updateTodolist(String id_todolist, String detailTodolist) {
        api = apiClient.getClient().create(api.class);
        Call<JsonObject> updateTodolist = api.updateTodolist(id_todolist, detailTodolist);

        updateTodolist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful() && response.body() != null){
                    Log.i("onSuccess", response.body().toString());
                    startActivity(new Intent(AddTodolistActivity.this, CollegeActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void saveTodolist(String uid, String detailTodolist) {
        api = apiClient.getClient().create(api.class);
        Call<JsonObject> saveTodolist = api.addTodolist(uid, detailTodolist);

        saveTodolist.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful() && response.body() != null){
                    Log.i("onSuccess", response.body().toString());
                    startActivity(new Intent(AddTodolistActivity.this, CollegeActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}