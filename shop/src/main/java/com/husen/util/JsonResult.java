package com.husen.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ajax请求返回结果
 * @author 11785
 */
public class JsonResult<T> implements Serializable {

    private String name;
    private String result;
    private List<T> content = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "JsonResult{" +
                "name='" + name + '\'' +
                ", result='" + result + '\'' +
                '}';
    }

    public JsonResult(String name, String result, List<T> content) {
        super();
        this.name = name;
        this.result = result;
        this.content = content;
    }

    public JsonResult(String name, String result) {
        super();
        this.name = name;
        this.result = result;
    }

    public JsonResult() {
        super();
    }
}
