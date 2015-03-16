
package com.gao.client_server_data_exchange.service;

import com.gao.client_server_data_exchange.LoginActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class UserServiceImpl implements UserService {

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        //uri: http://localhost:8080/Client_Server_Data_Exchange/login.do?LoginName=tom&LoginPassword=123
        String uri = "http://192.168.1.2:8080/Client_Server_Data_Exchange/login.do?LoginName="
                + loginName + "&LoginPassword=" + loginPassword;
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse httpResponse = httpClient.execute(httpGet);
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
}
