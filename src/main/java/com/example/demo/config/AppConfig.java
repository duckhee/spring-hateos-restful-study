package com.example.demo.config;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRepository;
import com.example.demo.account.AccountRole;
import com.example.demo.account.AccountService;

@Configuration
public class AppConfig {

	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
		.setSourceNameTokenizer(NameTokenizers.UNDERSCORE)
		.setDestinationNameTokenizer(NameTokenizers.UNDERSCORE);
		return mapper;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	/** 어플릐케이션 실행 전에 테스트 유저를 만들어주기 위한 Runner */
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Autowired
			private AccountService accountService;
			
			@SuppressWarnings("unused")
			@Autowired
			private AccountRepository accountRepository;
			
			@Autowired
			private AppProperties appProperties;
			
			@Override
			public void run(ApplicationArguments args) throws Exception {
				// TODO Auto-generated method stub
				/** test user make */
				Account testAccount = Account.builder()
				.email(appProperties.getAdminUserName())
				.password(appProperties.getAdminUserPassword())
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
				Account testUser = Account.builder()
						.email(appProperties.getUserName())
						.password(appProperties.getUserPassword())
						.roles(Set.of(AccountRole.USER))
						.build();
				this.accountService.saveAccount(testAccount);
//				if(this.accountRepository.findByEmail(testAccount.getEmail()) == null) {					
//					this.accountService.saveAccount(testAccount);
//				}
				this.accountService.saveAccount(testUser);
//				if(this.accountRepository.findByEmail(testUser.getEmail()) == null) {
//					this.accountService.saveAccount(testUser);
//				}
			}
		};
	}
}
