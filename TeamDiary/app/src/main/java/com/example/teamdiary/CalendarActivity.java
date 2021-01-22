package com.example.teamdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    //파이어베이스 데이터베이스
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private DataBaseManager databaseManager;
    CalendarView calendarView;
    ListView calendar_list;

    TextView tv_calendar_date;
    EditText et_calendar_title;
    EditText et_calendar_text;
    Button btn_calendar_add;
    private InputMethodManager imm;

    private String ProjectName;
    private String Date;
    private ArrayAdapter<String> adapter;
    List<Object> array = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        databaseManager = new DataBaseManager();
        Intent it = getIntent();
        ProjectName = it.getStringExtra("ProjectName");
        tv_calendar_date = findViewById(R.id.tv_calendar_date);
        et_calendar_title = findViewById(R.id.et_calendar_titile);
        et_calendar_text = findViewById(R.id.et_calendar_text);

        calendar_list = findViewById(R.id.calendar_list);
        adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>());
        calendar_list.setAdapter(adapter);
        imm = (InputMethodManager) this.getSystemService(SignupActivity.INPUT_METHOD_SERVICE);

        //calendar_list_update();

        setCal();
    }

    public void setCal(){
        calendarView = (CalendarView)findViewById(R.id.calender);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                adapter.clear();
                adapter.notifyDataSetChanged();
                Date = year+"-"+(month+1)+"-"+dayOfMonth;
                tv_calendar_date.setText(Date);
                databaseReference.getDatabase().getReference("res").child("sch").orderByKey().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data : dataSnapshot.getChildren()){
                            DateData temp = data.getValue(DateData.class);
                            if(ProjectName.equals(temp.getProjectName()) && Date.equals(temp.getDate())){
                                String title = temp.getTitle();
                                String text = temp.getText();
                                adapter.add(title);
                                adapter.add(text);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_calendar_add = findViewById(R.id.btn_calendar_add);
        btn_calendar_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_calendar_title.getText().toString();
                String text = et_calendar_text.getText().toString();
                DateData temp = new DateData(Date ,title, text, ProjectName);
                temp.setKVs();
                HashMapConvertor hashMapConvertor = new HashMapConvertor(temp.getKeys(), temp.getValues());
                databaseManager.writeNewSch(hashMapConvertor.getHashMap());
            }
        });
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.lo_calendar:
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
        }
    }
}
