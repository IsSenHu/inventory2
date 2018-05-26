package com.husen.vo;

public class Result<T> {
    private Integer code;
    private String des;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", des='" + des + '\'' +
                ", data=" + data +
                '}';
    }

    public Result(Integer code, String des, T data) {
        super();
        this.code = code;
        this.des = des;
        this.data = data;
    }

    public Result() {
        super();
    }
}
