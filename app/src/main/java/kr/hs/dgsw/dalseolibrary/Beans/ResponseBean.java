package kr.hs.dgsw.dalseolibrary.Beans;

import com.google.gson.GsonBuilder;

public class ResponseBean<T> {
    private int status;
    private String message;
    private T data;

    public ResponseBean() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, ResponseBean.class);
    }
}
