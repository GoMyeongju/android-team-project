package com.example.teamdiary;

public class UserData extends BaseData{
    private String email;
    private String name;
    private String school;
    private String uid;
    private String phone;

    public UserData(){

    }

    public UserData(String uid, String name, String email, String school, String phone) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.school = school;
        this.phone = phone;
    }

    public void setKVs(){
        super.addHash("uid", uid);
        super.addHash("name", name);
        super.addHash("email", email);
        super.addHash("school", school);
        super.addHash("phone", phone);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getUid() {
        return uid;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }
}
