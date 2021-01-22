package com.example.teamdiary;

public class DateData extends BaseData {
    private String Date;
    private String Title;
    private String Text;
    private String ProjectName;
    public DateData(){

    }

    public void setDate(String date) {
        Date = date;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setText(String text) {
        this.Text = text;
    }
    public void setProjectName(String ProjectName){
        this.ProjectName = ProjectName;
    }

    public DateData(String date, String title, String text, String ProjectName) {
        this.Date = date;
        this.Title = title;
        this.Text = text;
        this.ProjectName = ProjectName;
    }

    public String getDate() {
        return Date;
    }

    public String getTitle() {
        return Title;
    }

    public String getText() {
        return Text;
    }
    public String getProjectName(){
        return ProjectName;
    }

    public void setKVs(){
        super.addHash("Date", Date);
        super.addHash("Title", Title);
        super.addHash("Text", Text);
        super.addHash("ProjectName", ProjectName);
    }
}
