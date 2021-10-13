package ch.gab.hourcalculator.api.configuration;

import ch.gab.hourcalculator.api.configuration.filter.AuthenticationFilter;
import ch.gab.hourcalculator.api.configuration.filter.AuthorizationFilter;
import ch.gab.hourcalculator.api.service.api.IUserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder pwEncoder;

    @Autowired
    private IUserService userService;

    @Value("${gab.security.key}")
    private String KEY;

    public SecurityConfiguration(BCryptPasswordEncoder pwEncoder) {
        this.pwEncoder = pwEncoder;
    }

    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/users/new", "/users/login").permitAll()
                .and()
                .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(authenticationFilter())
                    .addFilter(new AuthorizationFilter(authenticationManager(), KEY))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(pwEncoder);
    }

    public AuthenticationFilter authenticationFilter() throws Exception {
        var auth = new AuthenticationFilter(authenticationManager(), KEY);
        auth.setFilterProcessesUrl("/login");
        return auth;
    }
}
