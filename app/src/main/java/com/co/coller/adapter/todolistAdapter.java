package com.co.coller.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.co.coller.R;
import com.co.coller.api.api;
import com.co.coller.api.apiClient;
import com.co.coller.model.todolist;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class todolistAdapter extends RecyclerView.Adapter<todolistAdapter.ViewHolder> {

    ArrayList<todolist> listTodolist;
    private View.OnClickListener mOnItemClicklistener;

    public todolistAdapter(ArrayList<todolist> listTodolist) {
        this.listTodolist = listTodolist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_todolist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDetailTodolist.setText(listTodolist.get(position).getNamaTodolist());

        if(listTodolist.get(position).getStatus().equals("0")){
            holder.cbStatus.setChecked(false);
        } else {
            holder.cbStatus.setChecked(true);
        }

        holder.cbStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){
                    setStatus(listTodolist.get(holder.getAdapterPosition()).getIdTodolist(), "1");
                } else {
                    setStatus(listTodolist.get(holder.getAdapterPosition()).getIdTodolist(), "0");
                }
            }
        });
    }

    private void setStatus(String idTodolist, String status) {
        api api = apiClient.getClient().create(api.class);
        Call<JsonObject> updateStatus = api.updateStatusTodolist(idTodolist, status);

        updateStatus.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("Responsestring", response.body().toString());
                if (response.isSuccessful() && response.body() != null){
                    Log.i("onSuccess", response.body().toString());
                } else {
                    Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listTodolist.size();
    }

    public void setOnItemClicklistener(View.OnClickListener itemClicklistener){
        mOnItemClicklistener = itemClicklistener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDetailTodolist;
        CheckBox cbStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDetailTodolist = itemView.findViewById(R.id.detail_todolist);
            cbStatus = itemView.findViewById(R.id.cb_todolist);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClicklistener);
        }
    }
}
