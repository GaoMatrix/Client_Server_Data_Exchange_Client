
package com.gao.client_server_data_exchange.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.gao.client_server_data_exchange.RegisterActivity;
import com.gao.client_server_data_exchange.entity.Student;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
            throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
        }
        String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        if (result.equals("success")) {
            
        } else {
            throw new ServiceRulesException(RegisterActivity.MSG_REGISTER_FAILED);
        }
    }

    @Override
    public void userRegister(String loginName, List<String> interesting) throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        String uri = "http://192.168.1.2:8080/Client_Server_Data_Exchange/register.do";
        HttpPost httpPost = new HttpPost(uri);
        //JAON数据的封装
        // Data = {"LoginName":"Tom", "Interesting":["game","music","sports"]}
        JSONObject object = new JSONObject();
        object.put("LoginName", loginName);
        JSONArray array = new JSONArray();
        if (null != interesting) {
            for (String string : interesting) {
                array.put(string);
            }
          }
        object.put("Interesting", array);
        NameValuePair parameter = new BasicNameValuePair("Data", object.toString());
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(parameter);
        
        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {//200
            throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
        }
       /* String result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        if (result.equals("success")) {
            
        } else {
            throw new ServiceRulesException(RegisterActivity.MSG_REGISTER_FAILED);
        }*/
        
        //从响应中取得服务器的返回结果
        String results = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        //解析JSON
        JSONObject jsonResults = new JSONObject(results);
        String result = jsonResults.getString("result");
        if (result.equals("success")) {
            
        } else {
            String errorMsg = jsonResults.getString("errorMsg");
            throw new ServiceRulesException(errorMsg);
        }
     }

    @Override
    public List<Student> getStudents() throws Exception {
        List<Student> studentList = new ArrayList<Student>();
        HttpClient httpClient = new DefaultHttpClient();
        String uri = "http://192.168.1.2:8080/Client_Server_Data_Exchange/getStudent.do";
        HttpGet httpGet = new HttpGet(uri);

        HttpResponse httpResponse = httpClient.execute(httpGet);

        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {// 200
            throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
        }
        String results = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        JSONArray array = new JSONArray(results);

        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonStudent = array.getJSONObject(i);
            Long id = Long.parseLong(jsonStudent.getString("id")); 
            String name = jsonStudent.getString("name");
            int age = jsonStudent.getInt("age");
            studentList.add(new Student(id, name, age));
        }

        return studentList;
    }

    @Override
    public Bitmap getImage() throws Exception {
        Bitmap bitmap = null;
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream in = null;

        try {
            url = new URL("http://192.168.1.2:8080/Client_Server_Data_Exchange/getImage.jpeg?id=1");
            urlConnection = (HttpURLConnection) url.openConnection();

            // 设置可以读取
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new ServiceRulesException("Server error");
            }

            in = urlConnection.getInputStream();
            if (null != in) {
                bitmap = BitmapFactory.decodeStream(in);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != urlConnection) {
                urlConnection.disconnect();
            }
        }
        return bitmap;
    }
        
        
        
        
        
        
        
        
        
        
}
