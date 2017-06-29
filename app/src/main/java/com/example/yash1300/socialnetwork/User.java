package com.example.yash1300.socialnetwork;

/**
 * Created by Yash 1300 on 26-06-2017.
 */

public class User {
    private String firstName, lastName, email, age, password;

    public User(String firstName, String lastName, String email, String age, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}
