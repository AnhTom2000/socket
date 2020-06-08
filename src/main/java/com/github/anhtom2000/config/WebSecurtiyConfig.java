package com.github.anhtom2000.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description : TODO
 * @Author : Weleness
 * @Date : 2020/06/06
 */
@EnableWebSecurity
public class WebSecurtiyConfig extends WebSecurityConfigurerAdapter {

    private DbUserDetailsService dbUserDetailService;

    public WebSecurtiyConfig(DbUserDetailsService dbUserDetailService) {
        this.dbUserDetailService = dbUserDetailService;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.
                authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/control/**").hasRole("USER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/led/**").hasAnyRole("USER")
                .antMatchers("/police/**").hasAnyRole("USER")
                .antMatchers("/user/**").hasAnyRole("USER")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/control")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .rememberMe();
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(auth);
        auth.userDetailsService(dbUserDetailService);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
