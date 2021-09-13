package com.example.demo.account;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
//@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
public class AccountServiceTests {

	

	@Autowired
	private AccountService accountService;
	
	@SuppressWarnings("unused")
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@DisplayName(value = "account service test")
	@Test
	public void findByUsername() {
		// GIVEN 
		String username = "test@co.kr";
		String password = "1234";
		Account account = Account.builder()
				.email(username)
				.password(password)
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		@SuppressWarnings("unused")
		Account newAccount = this.accountService.saveAccount(account);
		
		// WHEN
		UserDetailsService userDetailsService = (UserDetailsService) accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		//Then
		//assertThat(userDetails.getPassword()).isEqualTo(password);
		assertThat(this.passwordEncoder.matches(password, userDetails.getPassword()));
		
	}
	
	@DisplayName(value = "wrong user find")
	@Test
	public void findByUsername_failed() {
		String wrongname = "cacdscd";
		
		assertThrows(UsernameNotFoundException.class, ()->{
			accountService.loadUserByUsername(wrongname);
		});
		
		try {
			accountService.loadUserByUsername(wrongname);
			fail("support to be failed");
		}catch (UsernameNotFoundException e) {
			// TODO: handle exception
			assertThat(e.getMessage()).containsSequence(wrongname);
		}
	}
	
}
