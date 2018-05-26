package ecjtu.husen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11785
 */
public class Series<T> implements Serializable {
    private String name;
    private String type;
    private String stack;
    private List<T> data = new ArrayList<>();
    private Integer ataSize;

    public Integer getAtaSize() {
        return ataSize;
    }

    public void setAtaSize(Integer ataSize) {
        this.ataSize = ataSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStack() {
        return stack;
    }

    public void setStack(String stack) {
        this.stack = stack;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Series{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", stack='" + stack + '\'' +
                ", data=" + data +
                '}';
    }
}
