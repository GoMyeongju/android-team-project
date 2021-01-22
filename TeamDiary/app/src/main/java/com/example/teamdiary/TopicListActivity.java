package com.example.teamdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TopicListActivity extends AppCompatActivity {

    //파이어베이스 데이터베이스
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("test");
    //사용자 uid 받아오는 코드
    FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();
    TextView tv_uid;
    ListView item_list;
    Button btn_add;
    Button btn_test;
    private String projectName;
    private ArrayAdapter<String> adapter;
    List<Object> array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        tv_uid = findViewById(R.id.tv_uid);
        item_list = findViewById(R.id.item_list);
        btn_add = findViewById(R.id.btn_add);

        projectName = getIntent().getStringExtra("ProjectName");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopicAddActivity.class);
                intent.putExtra("projectName", projectName);
                startActivity(intent);
            }
        });

        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>());
        item_list.setAdapter(adapter);

        list_update();

        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                list_click(parent, position);
            }
        });

        item_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                //안내 창 띄우는 코드
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());     // 여기서 this는 Activity의 this
                // 여기서 부터는 알림창의 속성 설정
                builder.setTitle("알림")        // 제목 설정
                        .setMessage("글을 삭제하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String title = parent.getItemAtPosition(position).toString();
                                delete_data(title);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                builder.show();    // 알림창 띄우기

                return false;
            }
        });



        /*btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main_content.class);
                startActivity(intent);
            }
        });*/

    }

    //데이터 베이스에 추가가 있을 경우, 리스트 뷰 갱신
    public void list_update(){
        databaseReference.orderByChild("project").equalTo(projectName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adapter.clear();
                //데이터가 있는 수만큼 반복
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    //데이터 고유 키 값 가지고 와서 저장
                    String key = data.getKey();
                    DataAdd data_add = data.getValue(DataAdd.class);
                    System.out.println(data_add.getProjectName());
                    //이부분 나준에 어댑터 구현 하고 나서 getItemID 로 고유 아이디 주면 될 것 같아요
                    adapter.add(data_add.projectName +"'s Topic");
                    //adapter.add(data_add.title);
                    //Log.d("getFirebaseDatabase", "key: " + key);
                }
                adapter.notifyDataSetChanged();
                item_list.setSelection(adapter.getCount() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //리스트 클릭 이벤트 처리
    public void list_click(AdapterView<?> parent, int position){

        //리스트 뷰 안에 글자 가지고 와서 확인
        Toast.makeText(getApplicationContext(), "클릭" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

        //데이터베이스 업로드 키 값 가지고 와서 인텐트로 넘김
        Intent intent = new Intent(getApplicationContext(),TopicInfo.class);
        //인텐트에 Extra 값으로 데이터베이스 키 값을 넘긴다.
        intent.putExtra("Item_key",parent.getItemAtPosition(position).toString());
        startActivity(intent);

    }

    public void delete_data(String title){
        databaseReference.child("test").child(title).removeValue();
        list_update();
    }


}
