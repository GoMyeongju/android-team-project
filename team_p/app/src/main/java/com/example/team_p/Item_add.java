package com.example.team_p;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class Item_add extends AppCompatActivity {

    //파이어베이스 스로리지 초기화
    FirebaseStorage storage = FirebaseStorage.getInstance();
    //파이어베이스 데이터베이스
    private DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference();
    //사용자 uid 받아오는 코드
    FirebaseUser UID = FirebaseAuth.getInstance().getCurrentUser();

    private final int GET_GALLERY_IMAGE = 200;
    //private static final int REQUEST_CODE = 0;

    EditText et_title;
    EditText et_text;
    Button btn_add_img;
    ImageView iv_img;
    TextView tv_img_name;
    Button btn_add;


    //스토리지 경로에 들어갈 이미지 이름
    String imgname;
    //선택된 이미지 경로
    Uri selectedImgUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        et_title = findViewById(R.id.et_title);
        et_text = findViewById(R.id.et_text);
        iv_img = findViewById(R.id.iv_img);
        tv_img_name = findViewById(R.id.tv_img_name);

        //사진 선택 버튼 이벤트 처리
        btn_add_img = findViewById(R.id.btn_add_img);
        btn_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭 시 휴대폰 갤러리로 들어가 사진 선택할 수 있도록 함 - 두 가지 방법이 있고 화면이 서로 다름

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, REQUEST_CODE);

            }
        });

        //저장 버튼 이벤트 처리
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = et_title.getText().toString();
                String text = et_text.getText().toString();


                //공백이 있거나 사진이 선택되지 않은 경우
                if (imgname == null || title == "" || text == "") {
                    Toast.makeText(getApplicationContext(), "양식을 지켜주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    //스토리지에 업로드 및 데이터베이스에 저장
                    uploadStorage(selectedImgUri);

                }


            }
        });


    }

    //이미지 이름 가지고 오는 함수(경로 끝에 있는 이름만 빼내서 저장하기 위함
    private String getImgnameURI(Uri contentURI) {
        String imgname;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            imgname = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            //이미지 저장 결로 내에서 제일 마지막 이미지 명만 추출
            // 폴더명/이미지 형식으로 가지고 오기때문에 따로 추출하지 않으면 스토리지 경로에 문제 생김
            int idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            imgname = cursor.getString(idx);
            cursor.close();
        }
        return imgname;
    }

    //사진 선택 후 처리 -  화면 갱신
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //정상적으로 사진이 선택되었을 때
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImgUri = data.getData();
            iv_img.setImageURI(selectedImgUri);

            //이미지 경로 가지고 오기
            imgname = getImgnameURI(selectedImgUri);
            //경로 출력
            tv_img_name.setText(imgname);

        }

//        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
//            try{
//                InputStream inputStream = getContentResolver().openInputStream(data.getData());
//
//                Bitmap img = BitmapFactory.decodeStream(inputStream);
//                inputStream.close();
//
//                iv_img.setImageBitmap(img);
//            }catch(Exception e)
//            {
//                return;
//            }
//
//        }
        //사진을 선택하지 않았을 경우
        else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_SHORT).show();
        }

    }


    //스토리지 레퍼런스 생성
    public StorageReference createRef() {

        //스토리지 경로
        String StoragePath = "imgs/";
        //스토리지 레퍼런스 생성
        final StorageReference storageReference = storage.getReference().child(StoragePath);
        return storageReference;
    }

    //스토리지 업로드 및 업로드 완료 후 데이터 베이스 저장
    public void uploadStorage(Uri selectedImgUri) {

        //자식 레퍼런스 생성 - 이미지 이름으로
        StorageReference imgRef = createRef().child(imgname);
        String uid = UID.getUid();
        String title = et_title.getText().toString();
        String text = et_text.getText().toString();
        String img_name = tv_img_name.getText().toString();

        //업로드 태스크
        UploadTask uploadTask = imgRef.putFile(selectedImgUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                //데이터베이스에 저장
                uploadDatabase(uid, title, text, img_name);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });


    }


    //데이터 베이스에 데이터 업로드
    public void uploadDatabase(String uid, String title, String text, String img_uri){


        String key = databaseReference.child("test").push().getKey();
        Data_add data_add = new Data_add(uid, title, text, img_uri);
        Map<String, Object> uploadData = data_add.toMap();

        Map<String, Object> childupdate = new HashMap<>();
        childupdate.put("/test/" + key, uploadData);

        databaseReference.updateChildren(childupdate);
        Toast.makeText(getApplicationContext(), "데이터 저장 성공", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Item_list.class);
        startActivity(intent);


    }
}

