package ecjtu.husen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 今日的结果
 * @author 11785
 */
public class TodayResult<T> implements Serializable {
    private String title;
    private String tooltip;
    private String legend;
    private List<String> xAxis = new ArrayList<>();
    private List<String> yAxis = new ArrayList<>();
    private String seriesName;
    private String seriesType;
    private List<T> data = new ArrayList<>();
    private Integer xAxisSize;
    private Integer yAxisSize;
    private Integer dataSize;

    public Integer getxAxisSize() {
        return xAxisSize;
    }

    public void setxAxisSize(Integer xAxisSize) {
        this.xAxisSize = xAxisSize;
    }

    public Integer getyAxisSize() {
        return yAxisSize;
    }

    public void setyAxisSize(Integer yAxisSize) {
        this.yAxisSize = yAxisSize;
    }

    public Integer getDataSize() {
        return dataSize;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }

    public List<String> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<String> yAxis) {
        this.yAxis = yAxis;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TodayResult{" +
                "title='" + title + '\'' +
                ", tooltip='" + tooltip + '\'' +
                ", legend='" + legend + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", seriesType='" + seriesType + '\'' +
                '}';
    }
}
