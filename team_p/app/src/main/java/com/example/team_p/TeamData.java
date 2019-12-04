package com.example.team_p;

public class TeamData extends BaseData{
    private String teamId;
    private String teamMaster;
    private String[] member;

    public TeamData(String teamName, String teamMaster, String[] member) {
        this.teamId = teamName;
        this.teamMaster = teamMaster;
        this.member = member;
    }

    public void setKVc(){
        super.addHash("TeamName", teamId);
        super.addHash("TeamMaster", teamMaster);
        super.addHash("Member1", member[0]);
        super.addHash("Member2", member[1]);
        super.addHash("Member3", member[2]);
        super.addHash("Member4", member[3]);
    }
}
