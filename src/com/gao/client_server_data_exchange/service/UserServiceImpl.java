
package com.gao.client_server_data_exchange.service;

import com.gao.client_server_data_exchange.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {
        //HttpGet 方法请求
        /*HttpClient httpClient = new DefaultHttpClient();
        //uri: http://localhost:8080/Client_Server_Data_Exchange/login.do?LoginName=tom&LoginPassword=123
        String uri = "http://192.168.1.2:8080/Client_Server_Data_Exchange/login.do?LoginName="
                + loginName + "&LoginPassword=" + loginPassword;
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);*/
        
        //HttpPost方法请求
        HttpClient httpClient = new DefaultHttpClient();
        String uri = "http://192.168.1.2:8080/Client_Server_Data_Exchange/login.do";
        HttpPost httpPost = new HttpPost(uri);
        NameValuePair paramLoginName = new BasicNameValuePair("LoginName", loginName);
        NameValuePair paramLoginPassword = new BasicNameValuePair("LoginPassword", loginPassword);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(paramLoginName);
        parameters.add(paramLoginPassword);
        httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        
        
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {//200
            throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
        }
        String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        if (result.equals("success")) {
            
        } else {
            throw new ServiceRulesException(LoginActivity.MSG_LOGIN_FAILED);
        }
    }

    @Override
    public void userRegister(String loginName, List<String> interesting) throws Exception {
        Thread.sleep(2000);
    }
}
