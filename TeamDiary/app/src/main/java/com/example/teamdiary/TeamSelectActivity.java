package com.example.teamdiary;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TeamSelectActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String uid;

    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

    private String userPhone;
    private String teamName;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamselect);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("user").orderByKey().addValueEventListener(TeamNameSelection);

        linearLayout = findViewById(R.id.lo_teamlist);
    }
    //현재 접속된 유저의 이름을 찾아내는 이벤트 리스너
    ValueEventListener TeamNameSelection = new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            uid = firebaseAuth.getCurrentUser().getUid();
            for(DataSnapshot data: dataSnapshot.getChildren()){
                UserData temp = data.getValue(UserData.class);
                if(temp.getUid().equals(uid)){
                    userPhone = temp.getPhone();
                    databaseReference.getDatabase().getReference().child("team").
                            orderByKey().addValueEventListener(TeamListUp);
                    break;
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    };
    //현재 접속된 유저가 속한 팀을 찾아주는 이벤트 리스너
    ValueEventListener TeamListUp = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            linearLayout.removeAllViews();
            int id = 0;
            for(DataSnapshot data: dataSnapshot.getChildren()){
                ArrayList<String> temp = new ArrayList<>();
                temp.add(data.getValue(TeamData.class).getTeamMaster());
                temp.add(data.getValue(TeamData.class).getTeamName());
                temp.add(data.getValue(TeamData.class).getMember1());
                temp.add(data.getValue(TeamData.class).getMember2());
                temp.add(data.getValue(TeamData.class).getMember3());
                temp.add(data.getValue(TeamData.class).getMember4());
                if(temp.contains(userPhone)){
                    Button btn = new Button(TeamSelectActivity.this);
                    btn.setText(data.getValue(TeamData.class).getTeamName());
                    btn.setLayoutParams(layoutParams);
                    btn.setId(id);
                    btn.setBackground(getResources().getDrawable(R.drawable.btn_box));
                    teamName = data.getValue(TeamData.class).getTeamName();
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it = new Intent(TeamSelectActivity.this, ProjectSelectActivity.class);
                            it.putExtra("TeamName", teamName);
                            startActivity(it);
                        }
                    });
                    linearLayout.addView(btn);
                }
                id++;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void onClick(View v){;
        switch (v.getId()){
            case R.id.btn_teamadd:
                Intent intent = new Intent(this, TeamAddActivity.class);
                intent.putExtra("data", userPhone);
                startActivity(intent);
        }
    }
}
