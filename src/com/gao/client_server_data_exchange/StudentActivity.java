package com.gao.client_server_data_exchange;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.gao.client_server_data_exchange.adapter.StudentAdapter;
import com.gao.client_server_data_exchange.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends Activity{
    private ListView mStudentListView;
    private List<Student> mStdentList;
    private StudentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);
        
        mStudentListView = (ListView) findViewById(R.id.lv_student);
        
        mStdentList = new ArrayList<Student>();
        mStdentList.add(new Student(100L, "Tom", 20));
        mStdentList.add(new Student(101L, "Jack", 21));
        mStdentList.add(new Student(102L, "Jim", 23));
        
        mAdapter = new StudentAdapter(this, R.layout.student_item, mStdentList);
        mStudentListView.setAdapter(mAdapter);
    }
}
