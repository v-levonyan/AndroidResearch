package com.androidexample.listview;

/**
 * Created by vahan on 6/17/16.
 */
public class Person implements java.io.Serializable {
    private String name;
    private String lastName;
    private String email;
    private String company;
    private String jobTitle;
    private String age;

    public Person(String[] datas) {
        this.name = datas[0];
        this.lastName = datas[1];
        this.email = datas[2];
        this.company = datas[3];
        this.jobTitle = datas[4];
        this.age = datas[5];
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getAge() {
        return age;
    }
}


