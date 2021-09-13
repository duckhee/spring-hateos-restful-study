package com.example.demo.index;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.example.demo.configuration.RestDocsConfiguration;
import com.example.demo.configuration.BaseControllerTest;


//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Import(value = RestDocsConfiguration.class)
public class IndexControllerTest extends BaseControllerTest{
	
//	@Autowired
//	private MockMvc mockMvc;
	
	@Test
	@DisplayName(value = "index에 링크 확인 테스트 ")
	public void index() throws Exception {
		mockMvc.perform(get("/api/"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("_links.events").exists());
	}

}
