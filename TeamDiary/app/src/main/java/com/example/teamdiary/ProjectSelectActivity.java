package com.example.teamdiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProjectSelectActivity extends AppCompatActivity {

    private String teamName;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String ProjectName;

    private LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectselect);

        Intent it = getIntent();
        teamName = it.getStringExtra("TeamName");
        linearLayout = findViewById(R.id.lo_projectlist);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("project").orderByKey().addValueEventListener(ProjectListUp);

    }

    ValueEventListener ProjectListUp = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            linearLayout.removeAllViews();
            int id = 0;
            ArrayList<String> temp1 = new ArrayList<>();
            for(DataSnapshot data: dataSnapshot.getChildren()){
                ProjectData temp2 = data.getValue(ProjectData.class);
                if(temp2.getTeamName().equals(teamName)){
                    temp1.add(data.getValue(ProjectData.class).getTeamName());
                    if(temp1.contains(teamName)){
                        Button btn = new Button(ProjectSelectActivity.this);
                        btn.setText(data.getValue(ProjectData.class).getProjectName());
                        btn.setLayoutParams(layoutParams);
                        btn.setId(id);
                        btn.setBackground(getResources().getDrawable(R.drawable.btn_box));
                        ProjectName = data.getValue(ProjectData.class).getProjectName();
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //이 부분에 메인 컨텐츠로 가도록 설정해줘야 함
                                Intent it = new Intent(ProjectSelectActivity.this, MainContentsActivity.class);
                                it.putExtra("ProjectName", ProjectName);
                                startActivity(it);
                            }
                        });
                        linearLayout.addView(btn);
                    }
                    id++;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_add_project:
                Intent intent = new Intent(this, ProjectAddActivity.class);
                intent.putExtra("data", teamName);
                startActivity(intent);
        }
    }
}
