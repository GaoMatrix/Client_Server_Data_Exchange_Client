
package com.gao.client_server_data_exchange;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gao.client_server_data_exchange.service.ServiceRulesException;
import com.gao.client_server_data_exchange.service.UserService;
import com.gao.client_server_data_exchange.service.UserServiceImpl;

public class LoginActivity extends Activity {

    private static final int FLAG_LOGIN_SUCCESS = 1;
    // private static final int FLAG_LOGIN_FAILED = 2;

    private static final String MSG_LOGIN_ERROR = "登陆失败";
    private static final String MSG_LOGIN_SUCCESS = "登陆成功";
    public static final String MSG_LOGIN_FAILED = "登陆名|登陆密码出错";

    private EditText txtLoginName;
    private EditText txtLoginPassword;
    private Button btnLogin;
    private Button btnReset;
    private ProgressDialog mProgressDialog;

    private UserService mUserService = new UserServiceImpl();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initView();
        initListeners();

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
                case FLAG_LOGIN_SUCCESS:
                    showTip(MSG_LOGIN_SUCCESS);
                    break;
                default:
                    break;
            }
        }

    };

    private void showTip(String errorMsg) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        
    }

    private void initListeners() {
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final String loginName = txtLoginName.getText().toString().trim();
                final String loginPassword = txtLoginPassword.getText().toString().trim();
                
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(LoginActivity.this);
                }
                mProgressDialog.setTitle("请等待");
                mProgressDialog.setMessage("登陆中...");
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            mUserService.userLogin(loginName, loginPassword);
                            mHandler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
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
                            data.putSerializable("ErrorMsg", MSG_LOGIN_ERROR);
                            message.setData(data);
                            mHandler.sendMessage(message);
                        }
                    }
                });
                thread.start();
            }
        });
        btnReset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                txtLoginName.setText("");
                txtLoginPassword.setText("");
            }
        });
    }

    private void initView() {
        this.txtLoginName = (EditText) findViewById(R.id.txt_login);
        this.txtLoginPassword = (EditText) findViewById(R.id.txt_login_password);
        this.btnLogin = (Button) findViewById(R.id.btn_login);
        this.btnReset = (Button) findViewById(R.id.btn_reset);
    }

}
