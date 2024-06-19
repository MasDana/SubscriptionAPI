package com.dana;

public class Result {
    private Object data;
    private String pesan;
    private int status;
    private boolean sukses;

    public Result(Object data, String pesan, int status, boolean sukses) {
        this.data = data;
        if (data != null) {
            this.pesan = pesan;
            this.status = status;
            this.sukses = sukses;
        } else {
            this.pesan = pesan;
            this.status = 400;
            this.sukses = false;
        }
    }

    public Result() {

    }
    public String getData() {
        if (data == null) return null;
        return data.toString();
    }

    public String getPesan() {
        return "\"" + pesan + "\"";
    }

    public int getStatus() {
        return status;
    }

    public boolean isSukses() {
        return sukses;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setPesan(String message) {
        this.pesan = pesan;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setSukses(boolean sukses) {
        sukses = sukses;
    }
}
