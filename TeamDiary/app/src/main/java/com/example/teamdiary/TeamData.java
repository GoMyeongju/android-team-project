package com.example.teamdiary;

public class TeamData extends BaseData{
    private String TeamName;
    private String TeamMaster;
    private String Member1;
    private String Member2;
    private String Member3;
    private String Member4;


    public TeamData(){

    }

    public TeamData(String TeamName, String teamMaster, String member1, String member2, String member3, String member4) {
        this.TeamName = TeamName;
        this.TeamMaster = teamMaster;
        this.Member1 = member1;
        this.Member2 = member2;
        this.Member3 = member3;
        this.Member4 = member4;
    }

    public void setKVc(){
        super.addHash("TeamName", TeamName);
        super.addHash("TeamMaster", TeamMaster);
        super.addHash("Member1", Member1);
        super.addHash("Member2", Member2);
        super.addHash("Member3", Member3);
        super.addHash("Member4", Member4);
    }

    public void setTeamName(String TeamName) {
        this.TeamName = TeamName;
    }

    public void setTeamMaster(String TeamMaster) {
        this.TeamMaster = TeamMaster;
    }

    public void setMember1(String member1) {
        this.Member1 = member1;
    }

    public void setMember2(String member2) {
        this.Member2 = member2;
    }

    public void setMember3(String member3) {
        this.Member3 = member3;
    }

    public void setMember4(String member4) {
        this.Member4 = member4;
    }

    public String getTeamName() {
        return TeamName;
    }

    public String getTeamMaster() {
        return TeamMaster;
    }

    public String getMember1() {
        return Member1;
    }

    public String getMember2() {
        return Member2;
    }

    public String getMember3() {
        return Member3;
    }

    public String getMember4() {
        return Member4;
    }
}
