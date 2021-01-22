package com.example.teamdiary;

public class ProjectData extends BaseData {
    private String TeamName;
    private String ProjectName;
    private String Desc;

    public ProjectData(){

    }

    public ProjectData(String teamName, String projectName, String Desc) {
        this.TeamName = teamName;
        this.ProjectName = projectName;
        this.Desc = Desc;
    }

    public void setKVs(){
        super.addHash("TeamName", TeamName);
        super.addHash("ProjectName", ProjectName);
        super.addHash("Desc", Desc);
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public String getDesc() {
        return Desc;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }
}
