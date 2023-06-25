package com.housservice.housstock.Security;


import com.housservice.housstock.Security.services.MyUserDetailsService;
import com.housservice.housstock.filter.FilterAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



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
        http.authorizeRequests()
                .antMatchers("/api/v1/compte/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/personnel/**").hasAnyAuthority("Monitrice","Responsable RH", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/personnel/**").hasAnyAuthority("Responsable RH", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/client/**").hasAnyAuthority("Commercial", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/client/**").hasAnyAuthority("Commercial", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/fournisseur/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/fournisseur/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/commande/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/commande/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/compte/**").hasAnyAuthority("Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/compte/**").hasAnyAuthority("Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/machine/**").hasAnyAuthority("Conducteur machine","Responsable de production", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/machine/**").hasAnyAuthority("Responsable de production", "Admin")
                .antMatchers(HttpMethod.DELETE, "/api/v1/machine/**").hasAnyAuthority("Responsable de production", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/profile/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/v1/profile/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/commandeClient/**").hasAnyAuthority("Commercial", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/commandeClient/**").hasAnyAuthority("Commercial", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/listeMatiere/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/listeMatiere/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/affectation/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/affectation/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/affectation/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/affectation/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/affectationFrs/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/affectationFrs/**").hasAnyAuthority("Responsable d achat", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/planificationOf/**").hasAnyAuthority("Responsable de production", "Admin")
                .antMatchers(HttpMethod.PUT, "/api/v1/planificationOf/**").hasAnyAuthority("Responsable de production", "Admin")
                .antMatchers(HttpMethod.GET, "/api/v1/planifiermachine/**").hasAnyAuthority("Conducteur machine")
                .antMatchers(HttpMethod.PUT, "/api/v1/planifiermachine/**").hasAnyAuthority("Conducteur machine")
                .antMatchers(HttpMethod.GET, "/api/v1/Atelier/**").hasAnyAuthority("Monitrice")
                .antMatchers(HttpMethod.PUT, "/api/v1/Atelier/**").hasAnyAuthority("Monitrice")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
                    // Handle access denied response
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Access Denied");
                });

        http.addFilterBefore(new FilterAuthorization(), UsernamePasswordAuthenticationFilter.class);
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
