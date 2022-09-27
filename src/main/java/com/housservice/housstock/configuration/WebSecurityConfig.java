package com.housservice.housstock.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity // (1)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // (1)
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {  // (2)
//        http
//                .authorizeRequests()
//                .antMatchers("/", "/api/v1/article")
//                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()   .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
////                .anyRequest().authenticated() // (4)
////                .and()
////                .authorizeRequests()
////                .antMatchers("/", "/home")
////                .permitAll() // (3)
//                .anyRequest().authenticated() // (4)
//                .and()
//                .formLogin() // (5)
//                .loginPage("/login") // (5)
//                .permitAll()
//                .and()
//                .logout() // (6)
//                .permitAll()
//                .and()
//                .httpBasic(); // (7)
//    }
//}
