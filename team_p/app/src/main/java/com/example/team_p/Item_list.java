package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Item_list extends AppCompatActivity {

    //파이어베이스 데이터베이스
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("test");
    //사용자 uid 받아오는 코드
    FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();
    TextView tv_uid;
    ListView item_list;
    Button btn_add;

    private ArrayAdapter<String> adapter;
    List<Object> array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        tv_uid = findViewById(R.id.tv_uid);
        item_list = findViewById(R.id.item_list);
        btn_add = findViewById(R.id.btn_add);

        tv_uid.setText("사용자 uid : " + UID.getUid()); //uid 테스트

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Item_add.class);
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

    }

    //데이터 베이스에 추가가 있을 경우, 리스트 뷰 갱신
    public void list_update(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adapter.clear();
                //데이터가 있는 수만큼 반복
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    //데이터 고유 키 값 가지고 와서 저장
                    String key = data.getKey();
                    Data_add data_add = data.getValue(Data_add.class);

                    //이부분 나준에 어댑터 구현 하고 나서 getItemID 로 고유 아이디 주면 될 것 같아요
                    adapter.add(key);
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
        Intent intent = new Intent(getApplicationContext(),Item_view.class);
        //인텐트에 Extra 값으로 데이터베이스 키 값을 넘긴다.
        intent.putExtra("Item_key",parent.getItemAtPosition(position).toString());
        startActivity(intent);

    }

}
