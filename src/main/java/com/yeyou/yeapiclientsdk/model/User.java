package com.yeyou.yeapiclientsdk.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    String name;
    long age;
    Pet pet;

    public User(String name, long age, Pet pet) {
        this.name = name;
        this.age = age;
        this.pet = pet;
    }

    public User() {
    }
}
