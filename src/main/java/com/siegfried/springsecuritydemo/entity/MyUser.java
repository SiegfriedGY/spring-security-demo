package com.siegfried.springsecuritydemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class MyUser {
    private Integer id;
    private String username;
    private String password;
}
