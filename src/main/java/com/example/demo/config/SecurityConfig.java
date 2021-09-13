package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;


import com.example.demo.account.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	/** filter 설정 아예 security가 설정이 되지 않는 것이다. */
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		web.ignoring().mvcMatchers("/docs/index.html");
		/** 기본 static에 있는 것을 security를 하지 않게 설정 */
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		/** security에서 허용을 해주는 것이다. */
		/**
		http.authorizeHttpRequests()
		.mvcMatchers("docs/index.html").permitAll()
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();
		*/
		http.anonymous()
		.and()
		.formLogin()
		.and()
		.authorizeHttpRequests()
		.mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
		.anyRequest().authenticated();
		
	}
	
	
	
	@Bean
	public TokenStore tokenStore() {
		/** token을 메모리에 저장하기 위한 저장소 생성 */
		return new InMemoryTokenStore();
	}
	
	/** authorities를 다른 서버가 참조할 수 있도록 노출 시키기 위해 Bean 설정 */
	/** user 인증 정보를 가지고 있다. */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	/** authorities 재정의 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		super.configure(auth);
		auth.userDetailsService(accountService)
		.passwordEncoder(passwordEncoder); 
	}
	
}
