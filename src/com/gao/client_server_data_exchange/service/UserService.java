
package com.gao.client_server_data_exchange.service;

import com.gao.client_server_data_exchange.entity.Student;

import java.util.List;

public interface UserService {
    public void userLogin(String loginName, String loginPassword) throws Exception;

    public void userRegister(String loginName, List<String> interesting) throws Exception;

    public List<Student> getStudents() throws Exception;
}
