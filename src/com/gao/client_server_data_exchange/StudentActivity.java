
package com.gao.client_server_data_exchange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.gao.client_server_data_exchange.adapter.StudentAdapter;
import com.gao.client_server_data_exchange.entity.Student;
import com.gao.client_server_data_exchange.service.ServiceRulesException;
import com.gao.client_server_data_exchange.service.UserService;
import com.gao.client_server_data_exchange.service.UserServiceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends Activity {
    protected static final String MSG_STUDENT_ERROR = "加载数据错误";;
    private static final int FLAG_STUDENT_SUCCESS = 1;
    
    private ListView mStudentListView;
    private List<Student> mStdentList;
    private StudentAdapter mAdapter;
    private ProgressDialog mProgressDialog;
    private UserService mUserService = new UserServiceImpl();

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
            switch (msg.what) {
                case 0:
                    String errorMsg = (String) msg.getData().getSerializable("ErrorMsg");
                    showTip(errorMsg);
                    break;
               
                 case FLAG_STUDENT_SUCCESS: 
                     loadDataListView();
                 break;
                 
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        mStudentListView = (ListView) findViewById(R.id.lv_student);

        mStdentList = new ArrayList<Student>();
        /*
         * mStdentList.add(new Student(100L, "Tom", 20)); mStdentList.add(new
         * Student(101L, "Jack", 21)); mStdentList.add(new Student(102L, "Jim",
         * 23));
         */

        mAdapter = new StudentAdapter(this, R.layout.student_item, mStdentList);
        mStudentListView.setAdapter(mAdapter);

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(StudentActivity.this);
        }
        mProgressDialog.setTitle("请等待");
        mProgressDialog.setMessage("登陆中...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mStdentList = mUserService.getStudents();
                    mHandler.sendEmptyMessage(FLAG_STUDENT_SUCCESS);
                } catch (ServiceRulesException e) {
                    e.printStackTrace();
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", e.getMessage());
                    message.setData(data);
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putSerializable("ErrorMsg", MSG_STUDENT_ERROR);
                    message.setData(data);
                    mHandler.sendMessage(message);
                }
            }
        }).start();

    }

    protected void loadDataListView() {
        mAdapter = new StudentAdapter(this, R.layout.student_item, mStdentList);
        mStudentListView.setAdapter(mAdapter);
    }

    private void showTip(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();

    }
}
