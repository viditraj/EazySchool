package com.education.School.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

//WebSecurityConfigurerAdapter -> Spring Security implementation class

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {

//Overriding Security function here we are giving unrestricted access to all the requests
//Removing Security Giving full public access

   /* @Override
    protected void configure(HttpSecurity http) throws Exception{
            http.authorizeRequests().anyRequest().permitAll(). //permitting all the requests without authorization
                    and().formLogin(). // enabling the form login
                    and().httpBasic(); //enabling http Basic
    } */

//Overriding Security function here we are DENYING access to all the requests
//Applying Security to DENY all type of access to any request

   /* @Override
    protected void configure(HttpSecurity http) throws Exception{
            http.authorizeRequests().anyRequest().denyAll(). //denying all the requests without authorization
                    and().formLogin(). // disabling the form login
                    and().httpBasic(); //disabling http Basic
    } */

//CUSTOM SECURITY ACCESS LOGIC

   @Override
    protected void configure(HttpSecurity http) throws Exception {
        //mvcMatchers is used to match the endpoint with the given expression and then trigger corresponding next action
        http.csrf().disable().
                authorizeRequests().
                mvcMatchers("/home").permitAll().
                mvcMatchers("/dashboard").authenticated().//step 3- dashboard is displayed step 4-on logout go to logout step 5
                mvcMatchers("/holidays/**").permitAll().
                mvcMatchers("/contacts").permitAll().
                mvcMatchers("/courses").permitAll().
                mvcMatchers("/about").permitAll().
                and().formLogin().loginPage("/login").//step1-loginPage step2-on successful login display dashboard
                defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll().
                and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll().
                //step 5- on logout display login page again and end session
                and().httpBasic();
    }


//ADDING USER LOGIN DETAILS
   @Override
    protected void configure(AuthenticationManagerBuilder auth ) throws Exception{
        //Using inMemoryAuthentication to tell Spring that these login details are valid and it should store them
        auth.inMemoryAuthentication()
                .withUser("user").password("1234").roles("USER")
                .and()
                .withUser("admin").password("4321").roles("USER","ADMIN")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
