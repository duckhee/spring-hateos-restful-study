package com.example.demo.configuration;

import org.junit.jupiter.api.Disabled;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import com.fasterxml.jackson.databind.ObjectMapper;


@Disabled // 테스트를 실행하지 않겠다는 annotation
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs  
@ActiveProfiles(value = "test")
@Import(RestDocsConfiguration.class) // 만든 빈 설정을 사용하도록 설정하는 것 다른 스프링 빈 파일을 가져와서 사용하는 방법
public class BaseControllerTest {
	
	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected ModelMapper modelMapper;

}
