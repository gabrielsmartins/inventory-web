package br.com.inventory.web.config.security.configuration;

import br.com.inventory.web.config.security.filter.JwtTokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
                .anyRequest().permitAll().and().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new JwtTokenFilter(), BasicAuthenticationFilter.class);
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/**.html", "/v2/api-docs", "/webjars/**","/configuration/**", "/swagger-resources/**");
    }


}
