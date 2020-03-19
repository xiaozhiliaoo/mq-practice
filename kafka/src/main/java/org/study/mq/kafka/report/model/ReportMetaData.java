package org.study.mq.kafka.report.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportMetaData implements Serializable {

    private String title;

    private List<ReportClickData> clickData = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ReportClickData> getClickData() {
        return clickData;
    }

    public void setClickData(List<ReportClickData> clickData) {
        this.clickData = clickData;
    }

    @Override
    public String toString() {
        return "ReportMetaData{" +
                "title='" + title + '\'' +
                ", clickData=" + clickData +
                '}';
    }
}
