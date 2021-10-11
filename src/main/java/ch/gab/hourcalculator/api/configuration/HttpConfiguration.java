package ch.gab.hourcalculator.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class HttpConfiguration extends WebSecurityConfigurerAdapter {
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().and()
                .antMatcher("/hourcalculator/api/**")
                .authorizeRequests()
                .anyRequest()
                .permitAll();
    }
}
