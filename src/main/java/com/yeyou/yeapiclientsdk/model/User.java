package com.yeyou.yeapiclientsdk.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    String name;
    public User(String name) {
        this.name = name;
    }

    public User() {
    }
}
