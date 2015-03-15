
package com.gao.client_server_data_exchange.service;

import com.gao.client_server_data_exchange.LoginActivity;

public class UserServiceImpl implements UserService {

    @Override
    public void userLogin(String loginName, String loginPassword) throws Exception {
        Thread.sleep(3000);
        
        if (loginName.equals("tom") && loginPassword.equals("123")) {

        } else {
            throw new ServiceRulesException(LoginActivity.MSG_LOGIN_FAILED);
        }
    }
}
