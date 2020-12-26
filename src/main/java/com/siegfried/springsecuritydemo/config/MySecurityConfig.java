package com.siegfried.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService myUserDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(password());
    }

    @Bean
    public PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.logout().logoutUrl("/logout")
                .logoutSuccessUrl("/test/logout").permitAll();

        http.formLogin()
                .loginPage("/login.html")           // 登录页设置
                .loginProcessingUrl("/user/login")  // 登录访问路径，即controller
                .defaultSuccessUrl("/success.html").permitAll()    // 登陆成功后跳转的路径
                .and().authorizeRequests()
                    .antMatchers("/","/test/hello","/user/login").permitAll()
                // 基于权限和角色进行访问，4个方法
//                .antMatchers("/test/index").hasAuthority("admin")
//                .antMatchers("/test/index").hasAnyAuthority("admin, manager")
//                .antMatchers("/test/index").hasRole("sale")
                .antMatchers("/test/index").hasAnyRole("sale, dev, ba")
                .anyRequest().authenticated()
                .and().csrf().disable();

        // 自定义无权限访问页面（不是用户名密码错误，而是登录后，无访问权限，默认是一个403页面，There was an unexpected error (type=Forbidden, status=403).）
        http.exceptionHandling().accessDeniedPage("/unauthorized.html");
    }
}
