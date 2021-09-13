package com.example.demo.config;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "my-app") // build할 때 properties를 자동완성할 수 있게 된다.
@Getter @Setter
public class AppProperties {

	@NotEmpty
	private String adminUserName;
	
	@NotEmpty
	private String adminUserPassword;
	
	@NotEmpty
	private String userName;
	
	@NotEmpty
	private String userPassword;
	
	@NotEmpty
	private String clientId;
	
	@NotEmpty
	private String clientSecret;
	
}
