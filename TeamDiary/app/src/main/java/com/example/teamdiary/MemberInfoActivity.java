package com.example.teamdiary;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberInfoActivity extends AppCompatActivity {

    private MemberAdapter memberAdapter;
    private ListView listView;
    private String ProjectName;
    private FirebaseDatabase firebaseDatabase;
    ArrayList<String> member = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_info);

        memberAdapter = new MemberAdapter();
        listView = findViewById(R.id.lv_meminfo);



        listView.setAdapter(memberAdapter);
        ProjectName = getIntent().getStringExtra("ProjectName");
        firebaseDatabase = FirebaseDatabase.getInstance();

        firebaseDatabase.getReference("project").orderByKey().addValueEventListener(UserInfoListUP1);



    }


    ValueEventListener UserInfoListUP1 = new ValueEventListener() {
        String TeamName;
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot data : dataSnapshot.getChildren()) {
                if (ProjectName.equals(data.getValue(ProjectData.class).getProjectName())) {
                    TeamName = data.getValue(ProjectData.class).getTeamName();
                    firebaseDatabase.getReference().child("team").orderByKey().addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot data2 : dataSnapshot.getChildren()) {
                                if (TeamName.equals(data2.getValue(TeamData.class).getTeamName())){

                                    member.add(data2.getValue(TeamData.class).getMember1());
                                    member.add(data2.getValue(TeamData.class).getMember2());
                                    member.add(data2.getValue(TeamData.class).getMember3());
                                    member.add(data2.getValue(TeamData.class).getMember4());
                                    member.add(data2.getValue(TeamData.class).getTeamMaster());
                                }
                            }
                            firebaseDatabase.getReference("user").orderByKey().addValueEventListener(UserInfoListUP2);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener UserInfoListUP2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot data: dataSnapshot.getChildren()){
                if(member.contains(data.getValue(UserData.class).getPhone())){
                    memberAdapter.addItem(data.getValue(UserData.class));
                    memberAdapter.notifyDataSetChanged();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
