package io.springlab.SpringbootSecurityInMemoryDB.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("abc").password("{noop}abc").authorities("ADMIN");
        auth.inMemoryAuthentication().withUser("bca").password("{noop}bca").authorities("EMPLOYEE");
        auth.inMemoryAuthentication().withUser("cab").password("{noop}cab").authorities("CLERK");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //URL and Access type
        http.authorizeRequests()
                .antMatchers("/home").permitAll()
                .antMatchers("/welcome").authenticated()
                .antMatchers("/admin").hasAuthority("ADMIN")
                .antMatchers("/emp").hasAuthority("EMPLOYEE")
                .antMatchers("/clerk").hasAuthority("CLERK")
        //LoginForm Details
                .and()
                .formLogin()
                .defaultSuccessUrl("/welcome",true)
        //LogoutForm Details
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        //Exception Details
                .and()
                .exceptionHandling()
                .accessDeniedPage("/no-access");
    }
}
