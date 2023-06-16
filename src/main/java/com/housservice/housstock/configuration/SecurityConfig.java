package com.housservice.housstock.configuration;

import com.housservice.housstock.filter.CustomAuthenticationFilter;
import com.housservice.housstock.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration @EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private  final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    // laisser l'accès aux ressources
    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }



    // @Autowired
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//                CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
//        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//                http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.addFilter(customAuthenticationFilter);
//    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/user/login");
    http.csrf().disable();
    http.sessionManagement().sessionCreationPolicy(STATELESS);
//        http.authorizeRequests().antMatchers("/api/v1/user/login").permitAll();
//    http.authorizeRequests().antMatchers("/api/v1/role/**").hasAnyAuthority("ROLE_ADMIN");
//    http.authorizeRequests().antMatchers("/api/v1/compte/**").hasAnyAuthority("ROLE_ADMIN");
//    http.authorizeRequests().antMatchers("/api/v1/personnel/**").hasAnyAuthority("ROLE_ADMIN","ROLE_RH");
//    http.authorizeRequests().antMatchers("/api/v1/commandeClient/**").hasAnyAuthority("ROLE_COMMERCIALE","ROLE_ADMIN","ROLE_PRODUCTION","ROLE_DEVELOPPEMENT");
//    http.authorizeRequests().antMatchers("/api/v1/client/**").hasAnyAuthority("ROLE_COMMERCIALE","ROLE_DEVELOPPEMENT","ROLE_ADMIN");
//    http.authorizeRequests().antMatchers("/api/v1/fournisseur/**").hasAnyAuthority("ROLE_COMMERCIALE","ROLE_DEVELOPEMENT","ROLE_ADMIN");
//    http.authorizeRequests().antMatchers("/api/v1/nomenclature/**").hasAnyAuthority("ROLE_COMMERCIALE","ROLE_DEVELOPEMENT","ROLE_ADMIN","ROLE_PRODUCTION","ROLE_CONDUCTEUR_MACHINE");
//    http.authorizeRequests().antMatchers("/api/v1/ligneCommandeClient/**").hasAnyAuthority("ROLE_ADMIN","ROLE_PRODUCTION","ROLE_CONDUCTEUR_MACHINE");
//    http.authorizeRequests().antMatchers("/api/v1/machine/**").hasAnyAuthority("ROLE_PRODUCTION","ROLE_CONDUCTEUR_MACHINE","ROLE_ADMIN");
//    http.authorizeRequests().antMatchers("/api/v1/planificationOf/**").hasAnyAuthority("ROLE_PRODUCTION","ROLE_ADMIN","ROLE_CONDUCTEUR_MACHINE","ROLE_DEVELOPEMENT");
//    http.authorizeRequests().antMatchers("/api/v1/excel/**").hasAnyAuthority("ROLE_PRODUCTION","ROLE_ADMIN","ROLE_CONDUCTEUR_MACHINE","ROLE_DEVELOPEMENT","ROLE_COMMERCIALE","ROLE_RH");
//    http.authorizeRequests().antMatchers("/api/v1/etapeProduction/**").hasAnyAuthority("ROLE_PRODUCTION","ROLE_ADMIN","ROLE_CONDUCTEUR_MACHINE","ROLE_DEVELOPEMENT","ROLE_COMMERCIALE");
//    http.authorizeRequests().antMatchers("/api/v1/contactClient/**").hasAnyAuthority("ROLE_ADMIN","ROLE_COMMERCIALE");



    http.authorizeRequests().anyRequest().authenticated();
    http.addFilter(customAuthenticationFilter);
    http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
