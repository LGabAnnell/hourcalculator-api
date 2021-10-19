package ch.gab.hourcalculator.api.configuration;

import ch.gab.hourcalculator.api.configuration.filter.AuthenticationFilter;
import ch.gab.hourcalculator.api.configuration.filter.AuthorizationFilter;
import ch.gab.hourcalculator.api.service.api.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.Cookie;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder pwEncoder;

    @Autowired
    private IUserService userService;

    @Value("${gab.security.key}")
    private String KEY;

    @Autowired
    private ObjectMapper objectMapper;

    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                    .antMatchers("/users/new", "/login", "/post-time").permitAll()
                .and()
                    .logout()
                    .logoutSuccessHandler((req, res, auth) -> {
                        res.addCookie(new Cookie("my-cookie", null));
                        objectMapper.writeValue(res.getWriter(), "User is logged out!");
                    })
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
