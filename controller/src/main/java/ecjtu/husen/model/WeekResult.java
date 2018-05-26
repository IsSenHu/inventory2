package ecjtu.husen.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 本周的结果
 * @author 11785
 */
public class WeekResult<T> implements Serializable {
    private String title;
    private List<String> legend;
    private List<Series<T>> series = new ArrayList<>();
    private Integer legendSize;

    public Integer getLegendSize() {
        return legendSize;
    }

    public void setLegendSize(Integer legendSize) {
        this.legendSize = legendSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public List<Series<T>> getSeries() {
        return series;
    }

    public void setSeries(List<Series<T>> series) {
        this.series = series;
    }

    @Override
    public String toString() {
        return "WeekResult{" +
                "title='" + title + '\'' +
                ", legend=" + legend +
                ", series=" + series +
                '}';
    }
}
