package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class Item_view extends AppCompatActivity {

    //파이어베이스 스로리지 초기화
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    //StorageReference storageReference = storage.getReference();
    //파이어베이스 데이터베이스
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    //사용자 uid 받아오는 코드
    FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();

    String data_path = "test";
    String img_path = "imgs/";


    TextView tv_title_view;
    TextView tv_text_view;

    ImageView iv_img_view;

    Button btn_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        tv_title_view = findViewById(R.id.tv_title_view);
        tv_text_view = findViewById(R.id.tv_text_view);

        iv_img_view = findViewById(R.id.iv_img_view);

        btn_download = findViewById(R.id.btn_download);

        Intent intent = getIntent();
        //데이터베이스 데이터 키 값 ID를 받아옴.
        String item_key = intent.getStringExtra("Item_key");

        Toast.makeText(getApplicationContext(), item_key, Toast.LENGTH_SHORT).show();
        getData(item_key);


        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadimg(img_path);

            }
        });

    }

    public void getData(String item_key){

        //키 값을 기준으로 일치하는 데이터 가지고 옴.
        //이거 응용하면 검색도 가능
        databaseReference.child(data_path).orderByKey().equalTo(item_key).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                Data_add data_add = dataSnapshot.getValue(Data_add.class);
                tv_title_view.setText(data_add.title);
                tv_text_view.setText(data_add.text);

                img_path = img_path + data_add.img_name;

                //Toast.makeText(getApplicationContext(), storageReference.toString(), Toast.LENGTH_SHORT).show();
                getdownloadURI(img_path);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //스토리지 다운로드 URI 가져오기
    public void getdownloadURI(String imgname) {

        //이미지 경로
        StorageReference imgRef = storage.getReference();

       imgRef.child(imgname).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
           @Override
           public void onSuccess(Uri uri) {
               //Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
               Glide.with(getApplicationContext()).load(uri).into(iv_img_view);
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Toast.makeText(getApplicationContext(), "다운로드 경로 얻어오지 못 함. : " + e, Toast.LENGTH_LONG).show();

           }
       });
    }

    //휴대폰 로컬 영역에 저장하기
    public void downloadimg(String imgname) {
        //이미지 경로
        StorageReference imgRef = storage.getReference();
        try {
            //휴대폰 내에 경로 생성하여 jpg 형태로 이미지 저장.
            final File localFile = File.createTempFile("test_images", "jpg");
            imgRef.child(imgname).getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
