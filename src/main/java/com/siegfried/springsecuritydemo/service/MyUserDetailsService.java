package com.siegfried.springsecuritydemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.siegfried.springsecuritydemo.entity.MyUser;
import com.siegfried.springsecuritydemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("myUserDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<MyUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        MyUser myUser = userMapper.selectOne(wrapper);

        if (myUser == null) {
            throw new UsernameNotFoundException("username not exist");
        }

        List<GrantedAuthority> auths = AuthorityUtils
                .commaSeparatedStringToAuthorityList("role1, manager, ROLE_ba");  // 注意，如果要添加role,则一定要加 ROLE_，因为底层做了一个封装

        return new User(username, new BCryptPasswordEncoder().encode(myUser.getPassword()), auths);
    }
}
