package isu.library.configuration;


import isu.library.model.entity.Person;
import isu.library.model.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.sql.Date;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Person korbi = new Person("Tomas", "Korbar", new Date(0), "ADMIN", "korbonaut", bCryptPasswordEncoder.encode("12345678"));
        //userDetailsService.registerNewUserAccount(korbi);
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/book").permitAll()
                .antMatchers(HttpMethod.POST, "/book").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/book/*").permitAll()
                .antMatchers(HttpMethod.POST, "/book/*").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/libraries").hasRole("ADMIN")
                .antMatchers("/library").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/reservations").hasAnyRole("ADMIN", "LIBRARIAN")
                .and().formLogin().loginPage("/login").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
    }

    @Bean
    public BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}