package com.example.team_p;

public class UserData extends BaseData{
    private String uid;
    private String name;
    private String email;
    private String school;

    public UserData(String uid, String name, String email, String school) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.school = school;
    }

    public void setKVs(){
        super.addHash("uid", uid);
        super.addHash("name", name);
        super.addHash("email", email);
        super.addHash("school", school);
    }
}
