package com.example.demo.controller;

import org.junit.jupiter.api.Disabled;

//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import java.time.LocalDateTime;
//
//import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.hateoas.MediaTypes;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

//import com.example.demo.event.EventDomain;
import com.example.demo.event.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@WebMvcTest
public class EventControllerSlicingTest {
	
	// 가짜 요청을 만들어서 서블렛에 보내고 응답을 확인할 수있는 테스트를 만들 수 있다. web과 관련된 빈들만 등록해서 사용하는 것이다. slice test라고 부른다.
	@SuppressWarnings("unused")
	@Autowired
	private MockMvc mockMvc;
	
	@SuppressWarnings("unused")
	@Autowired
	private ObjectMapper objectMapper;
	
	@SuppressWarnings("unused")
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean // 가짜 빈을 만들어주는 것 
	private EventRepository eventRepository;
	
	@DisplayName(value = "이벤트 생성 테스트")
	@Test
	public void createEvent() throws Exception {
//		EventDomain event = EventDomain.builder()
//				.name("spring")
//				.description("REST API Development with spring")
//				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
//				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
//				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
//				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
//				.basePrice(100)
//				.maxPrice(200)
//				.limitOfErollment(100)
//				.location("강남역 D2 팩토리")
//				.build();
//		
//		event.setId(1L);
//		
//		/** 반환할 때 @MockBean은 항상 null 값이 들어온다. 따라서 가짜 결과 값을 만들어 주는 것이다. */
//		Mockito.when(eventRepository.save(event)).thenReturn(event);
//		
//		mockMvc.perform(post("/api/events/")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaTypes.HAL_JSON) // hypertext application language
//				.content(objectMapper.writeValueAsString(event))) // json형태로 전달하는 것
//		.andDo(print()) // 어떤 데이터를 보내고 받았는지 알 수 있다.
//		.andExpect(status().isCreated())
//		.andExpect(jsonPath("id").exists())
//		.andExpect(header().exists(HttpHeaders.LOCATION))
//		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE));
	}
	
	@DisplayName(value = "이벤트 생성 테스트 - 올바르지 않은 값")
	@Test
	public void createEvent_with_wrong_input() throws Exception {
//		EventDomain event = EventDomain.builder()
//				.name("spring")
//				.description("REST API Development with spring")
//				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
//				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
//				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
//				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
//				.basePrice(100)
//				.maxPrice(200)
//				.limitOfErollment(100)
//				.location("강남역 D2 팩토리")
//				.free(true)
//				.offline(false)
//				.id(100L)
//				.build();
//		
//		//event.setId(1L);
//		
//		/** 반환할 때 @MockBean은 항상 null 값이 들어온다. 따라서 가짜 결과 값을 만들어 주는 것이다. */
//		Mockito.when(eventRepository.save(event)).thenReturn(event);
//		
//		mockMvc.perform(post("/api/events/")
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaTypes.HAL_JSON) // hypertext application language
//				.content(objectMapper.writeValueAsString(event))) // json형태로 전달하는 것
//		.andDo(print()) // 어떤 데이터를 보내고 받았는지 알 수 있다.
//		.andExpect(status().isBadRequest())
//		.andExpect(jsonPath("id").exists())
//		.andExpect(header().exists(HttpHeaders.LOCATION))
//		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
//		.andExpect(jsonPath("id").value(Matchers.not(100)))
//		.andExpect(jsonPath("free").value(Matchers.not(true)));
	}

}
