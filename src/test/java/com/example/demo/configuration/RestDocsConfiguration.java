package com.example.demo.configuration;

import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;


/**
 * Json 형태를 보기 쉽게 하기 위해서 설정을 위한 파일 
 * @author duckheewon
 *
 */
@TestConfiguration
public class RestDocsConfiguration {

	@Bean
	public RestDocsMockMvcConfigurationCustomizer restDocsMockMvcConfigurationCustomizer() {
//		return new RestDocsMockMvcConfigurationCustomizer() {
//			
//			@Override
//			public void customize(MockMvcRestDocumentationConfigurer configurer) {
//				// TODO Auto-generated method stub
//				configurer.operationPreprocessors()
//				.withRequestDefaults(prettyPrint())
//				.withResponseDefaults(prettyPrint());
//			}
//		};
		/** Json을 보기 좋은 형태로 설정 */
		return configurer -> configurer.operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint());
	}
}
