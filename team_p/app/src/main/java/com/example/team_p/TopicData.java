package com.example.team_p;

public class TopicData extends BaseData{
    private String title;
    private String desc;
    private String date;

    public TopicData(String title, String desc, String date) {
        this.title = title;
        this.desc = desc;
        this.date = date;
    }

    public void setKVs(){
        super.addHash("TopicName", title);
        super.addHash("Desc", desc);
        super.addHash("Date", date);
    }
}
