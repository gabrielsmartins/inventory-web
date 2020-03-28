package br.com.inventory.web.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService autenticacaoService;

	@Autowired
	private TokenService tokenService;


	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}


	// Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		        .antMatchers(HttpMethod.POST, "/auth").permitAll()
		        .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
		        .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()
				.antMatchers(HttpMethod.GET, "/topicos").permitAll()
				.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
				.antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
				.anyRequest().authenticated().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new JwtTokenFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
	}

	// Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/actuator/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/swagger-ui/**");
        web.ignoring().antMatchers(HttpMethod.GET, "/topicos");
        web.ignoring().antMatchers(HttpMethod.GET, "/topicos/*");
        web.ignoring().antMatchers(HttpMethod.GET, "/h2-console/**");
		web.ignoring().antMatchers("/**.html", "/v2/api-docs", "/webjars/**","/configuration/**", "/swagger-resources/**");
	}

}
