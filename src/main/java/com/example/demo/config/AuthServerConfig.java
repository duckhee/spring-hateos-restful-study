package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.example.demo.account.AccountService;


/**
 * token을 발급하여 접근하는 OAUTH2 방식 설정 파일이다.
 * @author duckheewon
 *
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
	private AppProperties appProperties;
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// TODO Auto-generated method stub
		/** 현재 사용하고 있는 security password encoder를 설정해주어야한다. */
		/** client의 secret(password)을 확인할 때 사용된다. */
		security.passwordEncoder(passwordEncoder);
	}
	
	/** client의 ID를 설정 해주는 것이다. */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// TODO Auto-generated method stub
		/** inmemory 로 설정한 것 JDBC로 설정해서 database에 넣을 수 있다. */
//		clients.inMemory().withClient("myApp")
		clients.inMemory().withClient(appProperties.getClientId())
		/** 여기서 refresh_token은 이 토큰을 가지고 새로운 토큰을 발급 받는 grantType의 token이다. */
		.authorizedGrantTypes("password", "refresh_token")
		/** 나름대로 정의하기 */
		.scopes("read","write", "trust")
		/** 이 앱의 크릿을 설정하는 것 secret이니깐 인코딩을 해줘야한다. */
//		.secret(this.passwordEncoder.encode("pass"))
		.secret(this.passwordEncoder.encode(appProperties.getClientSecret()))
		/** access token의 기간설정 */
		.accessTokenValiditySeconds(10 * 60)
		/** refresh token의 기간 설정 */
		.refreshTokenValiditySeconds(6* 10 * 60);
	}
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// TODO Auto-generated method stub
		/** AuthenticationManager이랑 userdetail, Token store를 설정할 수 있다. */
		endpoints.authenticationManager(authenticationManager)
		.userDetailsService(accountService)
		.tokenStore(tokenStore);
	}
	
	
	

}
