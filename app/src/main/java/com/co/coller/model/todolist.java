package com.co.coller.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class todolist {
    @SerializedName("id_todolist")
    @Expose
    private String idTodolist;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("nama_todolist")
    @Expose
    private String namaTodolist;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tgl_todolist")
    @Expose
    private String tglTodolist;

    public String getIdTodolist() {
        return idTodolist;
    }

    public void setIdTodolist(String idTodolist) {
        this.idTodolist = idTodolist;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNamaTodolist() {
        return namaTodolist;
    }

    public void setNamaTodolist(String namaTodolist) {
        this.namaTodolist = namaTodolist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTglTodolist() {
        return tglTodolist;
    }

    public void setTglTodolist(String tglTodolist) {
        this.tglTodolist = tglTodolist;
    }
}