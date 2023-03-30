package com.housservice.housstock.Security;


import com.housservice.housstock.Security.services.MyUserDetailsService;
import com.housservice.housstock.filter.FilterAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static jdk.nashorn.internal.runtime.PropertyDescriptor.GET;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration @EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyUserDetailsService myUserDetailsService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v1/compte/login")
            .permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/v1/personnel/**").hasAnyAuthority("RH","Admin");
        http.authorizeRequests().antMatchers(GET,"/api/v1/client/**").hasAnyAuthority("commercial","Admin");
        http.authorizeRequests().antMatchers(GET,"/api/v1/fournisseur/**").hasAnyAuthority("commercial","Admin");
        http.authorizeRequests().antMatchers(GET,"/api/v1/commande/**").hasAnyAuthority("commercial","Admin");
        http.authorizeRequests().antMatchers(GET,"/api/v1/compte/**").hasAnyAuthority("Admin");

        http.addFilterBefore(new FilterAuthorization(),UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
