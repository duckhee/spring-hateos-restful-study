package com.example.demo.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.demo.account.AccountService;
import com.example.demo.config.AppProperties;
import com.example.demo.configuration.BaseControllerTest;

public class AuthServerTests extends BaseControllerTest{
	
	@SuppressWarnings("unused")
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AppProperties appProperties;
	
	@Test
	@DisplayName(value = "인증 토큰을 발급 받는 테스트")
	public void getAuthToken() throws Exception{
//		String username = "tester@co.kr";
//		String userPassword = "test";
//		Account testAccount = Account.builder()
//				.email(username)
//				.password(userPassword)
//				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//				.build();
//		this.accountService.saveAccount(testAccount);
		
//		String clientId = "myApp";
//		String clientSecret = "pass";
		
		/** 인증을 처리할 수 있는 서버가 등록이 되면, 기본적으로 /oauth/token이라는 핸들러가 등록이 된다. */
		this.mockMvc.perform(post("/oauth/token")
//				.with(httpBasic(clientId, clientSecret))
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
				.param("username", appProperties.getUserName())
				.param("password", appProperties.getUserPassword() )
				.param("grant_type", "password")
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("access_token").exists());
		 
	}

}
