package com.example.teamdiary;


import java.util.HashMap;
import java.util.Map;

public class DataAdd {

    public String projectName;
    public String title;
    public String text;
    public String img_name;

    public Map<String, Object> item = new HashMap<>();

    //데이터 업로드
    public DataAdd(String ProjectName, String title, String text, String img_name){
        this.projectName = ProjectName;
        this.title = title;
        this.text = text;
        this.img_name = img_name;
    }

    public DataAdd() {
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("project", projectName);
        result.put("title", title);
        result.put("text", text);
        result.put("img_name", img_name);

        return result;
    }

    public String getProjectName() {
        return projectName;
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
