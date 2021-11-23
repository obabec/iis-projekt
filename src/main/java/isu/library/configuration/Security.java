package isu.library.configuration;


import isu.library.model.service.user.MyUserDetailsService;
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

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET, "/book").permitAll()
                .antMatchers(HttpMethod.POST, "/book").hasAnyRole("ADMIN", "LIBRARIAN", "DISTRIBUTOR")
                .antMatchers(HttpMethod.GET, "/book/*").permitAll()
                .antMatchers(HttpMethod.POST, "/book/*").hasAnyRole("ADMIN", "LIBRARIAN", "DISTRIBUTOR")
                .antMatchers(HttpMethod.GET, "/libraries").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/reservationSummary").authenticated()
                .antMatchers(HttpMethod.GET, "/userUpdate").hasAnyRole("ADMIN", "LIBRARIAN", "DISTRIBUTOR", "READER")
                .antMatchers(HttpMethod.POST, "/userUpdate").hasRole("ADMIN")
                .antMatchers("/userDelete").hasRole("ADMIN")
                .antMatchers("/users").hasRole("ADMIN")
                .antMatchers("/reservation/createLoan").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/votes").authenticated()
                .antMatchers(HttpMethod.GET, "/deleteVote").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers(HttpMethod.GET, "/deleteLibrary").hasRole("ADMIN")
                .antMatchers("/library").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/reservations").hasAnyRole("ADMIN", "LIBRARIAN")
                .antMatchers("/orders").hasAnyRole("ADMIN", "LIBRARIAN", "DISTRIBUTOR")
                .antMatchers("/titles").hasAnyRole("ADMIN", "LIBRARIAN", "DISTRIBUTOR")
                .antMatchers("/title").hasAnyRole("ADMIN", "DISTRIBUTOR")
                .antMatchers("/author").hasAnyRole("ADMIN", "DISTRIBUTOR")
                .antMatchers("/authors").hasAnyRole("ADMIN", "DISTRIBUTOR")
                .and().formLogin().loginPage("/login").permitAll().failureUrl("/login?error=1")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll();
    }
}