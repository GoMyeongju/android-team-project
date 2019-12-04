package com.example.team_p;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class Data_add{

    public String uid;
    public String title;
    public String text;
    public String img_name;

    public Map<String, Object> item = new HashMap<>();

    //데이터 업로드
    public Data_add(String uid, String title, String text, String img_name){
        this.uid = uid;
        this.title = title;
        this.text = text;
        this.img_name = img_name;
    }

    public Data_add() {
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("title", title);
        result.put("text", text);
        result.put("img_name", img_name);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getimg_name() {
        return img_name;
    }

    public Map<String, Object> getItem() {
        return item;
    }
}
