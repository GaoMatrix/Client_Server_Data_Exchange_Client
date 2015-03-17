
package com.gao.client_server_data_exchange.entity;

import java.io.Serializable;

public class Student implements Serializable {

    private static final long serialVersionUID = -7548275287234898693L;

    private Long id;
    private String name;
    private int age;

    public Student() {
    }

    public Student(long id, String name, int age) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
