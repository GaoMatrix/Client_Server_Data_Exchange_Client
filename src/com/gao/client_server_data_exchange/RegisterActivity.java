package com.gao.client_server_data_exchange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.gao.client_server_data_exchange.service.ServiceRulesException;
import com.gao.client_server_data_exchange.service.UserService;
import com.gao.client_server_data_exchange.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends Activity{
    private static final int FLAG_REGISTER_SUCCESS = 1;
    // private static final int FLAG_LOGIN_FAILED = 2;

    private static final String MSG_REGISTER_ERROR = "註冊失败";
    private static final String MSG_REGISTER_SUCCESS = "注册成功";
    public static final String MSG_REGISTER_FAILED = "注册名|注册密码出错";
    public static final String MSG_SERVER_ERROR = "服务器错误";
    
    private EditText txtLoginName;
    private CheckBox chkGame;
    private CheckBox chkMusic;
    private CheckBox chkSports;
    private Button btnRegister;
    
    private ProgressDialog mProgressDialog;

    private UserService mUserService = new UserServiceImpl();
    

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initView();
        initListeners();
    }

    private void initView() {
        this.txtLoginName = (EditText) findViewById(R.id.txt_login_name);
        this.chkGame = (CheckBox) findViewById(R.id.chk_game);
        this.chkSports = (CheckBox) findViewById(R.id.chk_sports);
        this.chkMusic = (CheckBox) findViewById(R.id.chk_music);
        this.btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void initListeners() {
        this.btnRegister.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                final String loginName = txtLoginName.getText().toString();
                final List<String> interesting = new ArrayList<String>();
                if (chkGame.isChecked()) {
                    interesting.add(chkGame.getText().toString());
                }
                if (chkMusic.isChecked()) {
                    interesting.add(chkMusic.getText().toString());
                }
                if (chkSports.isChecked()) {
                    interesting.add(chkSports.getText().toString());
                }
                
                
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(RegisterActivity.this);
                }
                mProgressDialog.setTitle("请等待");
                mProgressDialog.setMessage("登陆中...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            mUserService.userRegister(loginName, interesting);
                            mHandler.sendEmptyMessage(FLAG_REGISTER_SUCCESS);
                        } catch (ServiceRulesException e) { //用户名和密码不正确
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
                            data.putSerializable("ErrorMsg", MSG_REGISTER_ERROR);
                            message.setData(data);
                            mHandler.sendMessage(message);
                        }
                    }
                });
                thread.start();
            
            }
        });
    }

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
                case FLAG_REGISTER_SUCCESS:
                    showTip(MSG_REGISTER_SUCCESS);
                    break;
                default:
                    break;
            }
        }

    };

    private void showTip(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
