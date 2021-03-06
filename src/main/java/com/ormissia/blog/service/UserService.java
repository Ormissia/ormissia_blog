package com.ormissia.blog.service;

import com.ormissia.blog.pojo.User;

import java.util.HashMap;

/**
 * @Author 宋奕锟
 * @Date: Create in 2020/9/24 15:04
 */
public interface UserService {

    //用户登录接口
    HashMap<String,String> userLogin(String username, String password);

    //通过用户名获取用户信息
    User selectUserByUsername(String username);
}
