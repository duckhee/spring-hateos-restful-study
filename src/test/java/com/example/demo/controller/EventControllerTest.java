package com.example.demo.controller;


import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;

//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.account.Account;
import com.example.demo.account.AccountRepository;
import com.example.demo.account.AccountRole;
import com.example.demo.account.AccountService;
import com.example.demo.common.TestDescription;
import com.example.demo.config.AppProperties;
import com.example.demo.configuration.BaseControllerTest;
//import com.example.demo.configuration.RestDocsConfiguration;
import com.example.demo.event.EventDomain;
import com.example.demo.event.EventDto;
import com.example.demo.event.EventRepository;
import com.example.demo.event.EventStatus;
//import com.fasterxml.jackson.databind.ObjectMapper;


/** 직접 추가해주어야 한다. */
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.relaxedLinks;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
//import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
//import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;


//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs  
//@ActiveProfiles(value = "test")
//@Import(RestDocsConfiguration.class) // 만든 빈 설정을 사용하도록 설정하는 것 다른 스프링 빈 파일을 가져와서 사용하는 방법 
public class EventControllerTest extends BaseControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@Autowired
//	private ObjectMapper objectMapper;
//	
//	@Autowired
//	private ModelMapper modelMapper;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private EventRepository eventRepository;
	@SuppressWarnings("unused")
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AppProperties appProperties; 
	
	private EventDomain generateEvent(int i, Account account) {
		// TODO Auto-generated method stub
		EventDomain eventDomain = EventDomain.builder()
				.name("spring " + i)
				.description("REST API Development with spring " + i)
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfErollment(100)
				.location("강남역 D2 팩토리")
				.free(false)
				.offline(true)
				.eventStatus(EventStatus.DRAFT)
				.build();
		eventDomain.setOwner(account);
		return this.eventRepository.save(eventDomain);
	}
	
	/** test data generate */
	private EventDomain generateEvent(int i) {
		// TODO Auto-generated method stub
		EventDomain eventDomain = EventDomain.builder()
				.name("spring " + i)
				.description("REST API Development with spring " + i)
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfErollment(100)
				.location("강남역 D2 팩토리")
				.free(false)
				.offline(true)
				.eventStatus(EventStatus.DRAFT)
				.build();
		return this.eventRepository.save(eventDomain);
	}
	
	private Account createAccount() {
		Account account = Account.builder()
				.email(appProperties.getAdminUserName())
				.password(appProperties.getAdminUserPassword())
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		return this.accountRepository.save(account);
	}
	
	@SuppressWarnings("unused")
	private String getBearerToken(boolean needToCreateAccount) throws Exception {
		return "Bearer " + getAccessToken(needToCreateAccount );
	}
	
	private String getAccessToken(boolean needToCreateAccount) throws Exception{
		// TODO Auto-generated method stub
//		String username = "tester@co.kr";
//		String userPassword = "test";
//		Account testAccount = Account.builder()
//				.email(username)
//				.password(userPassword)
//				.email(appProperties.getUserName())
//				.password(appProperties.getUserPassword())
//				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
//				.build();
//		this.accountService.saveAccount(testAccount);
		
//		String clientId = "myApp";
//		String clientSecret = "pass";
		if(needToCreateAccount) {
			createAccount();
		}
		/** 인증을 처리할 수 있는 서버가 등록이 되면, 기본적으로 /oauth/token이라는 핸들러가 등록이 된다. */
		ResultActions resultActions = this.mockMvc.perform(post("/oauth/token")
				.with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
//				.param("username", username)
//				.param("password", userPassword)
				.param("username", appProperties.getUserName())
				.param("password", appProperties.getUserPassword())
				.param("grant_type", "password"));
		
		var responseBody = resultActions.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser parser = new Jackson2JsonParser();
		System.out.println("response ::: " + responseBody.toString());
		return parser.parseMap(responseBody).get("access_token").toString();
	}

	
	@AfterEach
	public void testDataDelete() {
		this.eventRepository.deleteAll();
		this.accountRepository.deleteAll();
	}

	@Test
	@TestDescription(value = "annotation test")
	public void annotaion_test() {
		
	}
	
	@DisplayName(value = "이벤트 생성 테스트")
	@Test
	public void createEvent() throws Exception {
		EventDto event = EventDto.builder()
				.name("spring")
				.description("REST API Development with spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfErollment(100)
				.location("강남역 D2 팩토리")
				.build();
		
		mockMvc.perform(post("/api/events/")
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법 
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON) // hypertext application language
				.content(objectMapper.writeValueAsString(event))) // json형태로 전달하는 것
		.andDo(print()) // 어떤 데이터를 보내고 받았는지 알 수 있다.
		.andExpect(status().isCreated())
		.andExpect(jsonPath("id").exists())
		.andExpect(header().exists(HttpHeaders.LOCATION))
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
		.andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
		.andExpect(jsonPath("free").value(false))
		.andExpect(jsonPath("offline").value(true))
		.andExpect(jsonPath("_links.self").exists())
//		.andExpect(jsonPath("_link.profile").exists())
		.andExpect(jsonPath("_links.query-events").exists())
		.andExpect(jsonPath("_links.update-events").exists())
		/** 첫번째 인자가 문서의 이름이다. */ 
		/** links 추가 시 링크도 문서에 추가가 된다. */
		.andDo(document("create-event", 
				/** links 추가 시 링크도 문서에 추가가 된다. */
				links(
						linkWithRel("self").description("link to self"),
						linkWithRel("query-events").description("link to query events"),
						linkWithRel("update-events").description("link to update an existing event"),
						linkWithRel("profile").description("link to profile")
				),
				/** 요청에 관한 헤더 정보를 문서에 추가해주기 위한 설정 */
				/** 요청 관련 문서화 */
				requestHeaders(
						headerWithName(HttpHeaders.ACCEPT).description("accept header"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("content Type header")
						),
				requestFields(
						fieldWithPath("name").description("name of new event").type(JsonFieldType.STRING),
						fieldWithPath("description").description("description of new event").type(JsonFieldType.STRING),
						fieldWithPath("beginEnrollmentDateTime").description("date time of begin new event").type(JsonFieldType.STRING),
						fieldWithPath("closeEnrollmentDateTime").description("date time of close new event").type(JsonFieldType.STRING),
						fieldWithPath("beginEventDateTime").description("date time of begin new event").type(JsonFieldType.STRING),
						fieldWithPath("endEventDateTime").description("date time of end new event").type(JsonFieldType.STRING),
						fieldWithPath("location").description("event location information").type(JsonFieldType.STRING),
						fieldWithPath("basePrice").description("base event price").type(JsonFieldType.NUMBER),
						fieldWithPath("maxPrice").description("max event price").type(JsonFieldType.NUMBER),
						fieldWithPath("limitOfErollment").description("max enrollment event").type(JsonFieldType.NUMBER)
						),
				/** 응답 관련 문서화 */
				responseHeaders(
						headerWithName(HttpHeaders.LOCATION).description("response location"),
						headerWithName(HttpHeaders.CONTENT_TYPE).description("HAL Json")
						),
				/** 응답 중에 안 쓴게 있어도 실패가 안 일어난다. 아래 서술한 것만 만드는 것이 relaxed 키워드가 붙은 함수이다. */
				relaxedResponseFields(
						fieldWithPath("id").description("event id").type(JsonFieldType.NUMBER),
						fieldWithPath("name").description("name of new event").type(JsonFieldType.STRING),
						fieldWithPath("description").description("description of new event").type(JsonFieldType.STRING),
						fieldWithPath("beginEnrollmentDateTime").description("date time of begin new event").type(JsonFieldType.STRING),
						fieldWithPath("closeEnrollmentDateTime").description("date time of close new event").type(JsonFieldType.STRING),
						fieldWithPath("beginEventDateTime").description("date time of begin new event").type(JsonFieldType.STRING),
						fieldWithPath("endEventDateTime").description("date time of end new event").type(JsonFieldType.STRING),
						fieldWithPath("location").description("event location information").type(JsonFieldType.STRING),
						fieldWithPath("basePrice").description("base event price").type(JsonFieldType.NUMBER),
						fieldWithPath("maxPrice").description("max event price").type(JsonFieldType.NUMBER),
						fieldWithPath("limitOfErollment").description("max enrollment event").type(JsonFieldType.NUMBER),
						fieldWithPath("free").description("event is free or not").type(JsonFieldType.BOOLEAN),
						fieldWithPath("offline").description("event is offline or online").type(JsonFieldType.BOOLEAN),
						fieldWithPath("eventStatus").description("event state response").type(JsonFieldType.STRING),
						/** _link 를 더해주는 것 */
						/** json의 특정 값이 리턴될 수 있고 안될 수 있는 결과라면.. 예를 들어, response에 description 필드가 있을 경우, 없을 경우를 하나의 문서로 만들기 위해서는 optional()을 더해준다. */
				        fieldWithPath("_links.self.href").type(JsonFieldType.STRING).description("my href").optional(),
				        fieldWithPath("_links.query-events.href").type(JsonFieldType.STRING).description("my href").optional(),
				        fieldWithPath("_links.update-event.href").type(JsonFieldType.STRING).description("my href").optional(),
				        fieldWithPath("_links.profile.href").type(JsonFieldType.STRING).description("my href").optional()

						)
				
				
				
				));
	}
	


	@DisplayName(value = "이벤트 생성 테스트 - 올바르지 않은 값 입력 값이 많을 경우")
	@Test
	public void createEvent_bad_request() throws Exception {
		EventDomain event = EventDomain.builder()
				.name("spring")
				.description("REST API Development with spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 27, 14,  21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfErollment(100)
				.location("강남역 D2 팩토리")
				.free(true)
				.offline(false)
				.id(100L)
				.build();
		
		//event.setId(1L);		
		
		mockMvc.perform(post("/api/events/")
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON) // hypertext application language
				.content(objectMapper.writeValueAsString(event))) // json형태로 전달하는 것
		.andDo(print()) // 어떤 데이터를 보내고 받았는지 알 수 있다.
		.andExpect(status().isBadRequest()); //properties에 설절되어 있는 것 때문에 BadRequest로 응답한다.
		
	}
	
	@DisplayName(value = "이벤트 생성 테스트 - 필요한 입력값이 없음")
	@Test
	public void createEvent_Bad_Request_Empty_input() throws Exception {
		
		EventDto eventEmptyDto = new EventDto();
		
		mockMvc.perform(post("/api/events")
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(eventEmptyDto))
				)
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
	
	
	@DisplayName(value = "이벤트 생성 테스트 - 잚못된 입력값 입력")
	@Test
	public void createEvent_Bad_Request_with_wrong_input() throws Exception {
		
		EventDto eventDto = EventDto.builder()
				.name("spring")
				.description("REST API Development with spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 26, 14,  21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 25, 14,  21))
				.beginEventDateTime(LocalDateTime.of(2018, 11, 23, 14,  21))
				.endEventDateTime(LocalDateTime.of(2018, 11, 21, 14,  21))
				.basePrice(10000)
				.maxPrice(200)
				.limitOfErollment(100)
				.location("강남역 D2 팩토리")
				.build();
		
		mockMvc.perform(post("/api/events")
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
				)
		.andDo(print())
		.andExpect(jsonPath("errors[0].objectName").exists())
//		.andExpect(jsonPath("$[0].field").exists()) // 에러가 발생한 필드
		.andExpect(jsonPath("errors[0].defaultMessage").exists()) //에러의 기본 메세지는 무엇인지 
		.andExpect(jsonPath("errors[0].code").exists()) // 에러 코드가 무엇인지 
//		.andExpect(jsonPath("$[0].rejectValue").exists()); // 어디서 에러가 발생하게 되었는지
		.andExpect(jsonPath("_links.index").exists()) // 에러 발생 시에 index로 이동하는 링크 있는지 확인 
		.andExpect(status().isBadRequest());

	}
	
	@DisplayName(value = "30개의 이벤트를 10개씩 두번쨰 페이지 조회하기")
	@Test
	public void queryEvents() throws Exception {
		// Given
		IntStream.range(0, 30).forEach(this::generateEvent);
		this.mockMvc.perform(get("/api/events")
				.param("page", "1")
				.param("size", "10")
				.param("sort", "name,DESC"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("page").exists())
		.andExpect(jsonPath("_embedded.eventDomainList[0]._links.self").exists())
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.profile").exists())
		.andDo(document("query-events",
				/** link description */
				links(
						linkWithRel("self").description("link to self"),
						linkWithRel("profile").description("link to self"),
						linkWithRel("prev").description("link to self"),
						linkWithRel("next").description("link to self"),
						linkWithRel("last").description("link to self"),
						linkWithRel("first").description("link to self")
						),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("HAL JSON")
						) 
				
				));
	}
	
	@DisplayName(value = "30개의 이벤트를 10개씩 두번쨰 페이지 조회하기 - 인증 정보 주는 경우")
	@Test
	public void queryEvents_with_authecation() throws Exception {
		// Given
		/** 권한을 꺼내는 방법 */
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		/** spring Security의 User로 받을 수 있다. */
//		User prinicpal = (User)authentication.getPrincipal(); 
		IntStream.range(0, 30).forEach(this::generateEvent);
		this.mockMvc.perform(get("/api/events")
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.param("page", "1")
				.param("size", "10")
				.param("sort", "name,DESC"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("page").exists())
		.andExpect(jsonPath("_embedded.eventDomainList[0]._links.self").exists())
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.profile").exists())
		.andExpect(jsonPath("_links.create-event").exists())
		.andDo(document("query-events",
				/** link description */
				relaxedLinks(
						linkWithRel("self").description("link to self"),
						linkWithRel("profile").description("link to self"),
						linkWithRel("prev").description("link to self"),
						linkWithRel("next").description("link to self"),
						linkWithRel("last").description("link to self"),
						linkWithRel("first").description("link to self")
						),
				responseHeaders(
						headerWithName(HttpHeaders.CONTENT_TYPE).description("HAL JSON")
						) 
				
				));
	}
	
	
	@DisplayName(value = "이벤트 하나를 선택해서 조회하기")
	@Test
	public void getEvent() throws Exception {
		Account account = createAccount();
		
		EventDomain eventDomain = this.generateEvent(100, account);
		mockMvc.perform(get("/api/events/{id}", eventDomain.getId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("name").exists())
		.andExpect(jsonPath("id").exists())
		.andExpect(jsonPath("_links.self").exists())
		.andExpect(jsonPath("_links.profile").exists())
		.andDo(print())
		.andDo(document("get-an-event"));
		
	}
	
	@DisplayName(value = "없는 상태의 이벤트 요청 시 404 응답")
	@Test
	public void getEvent_empty_id_call() throws Exception {
		mockMvc.perform(get("/api/events/{id}", 2000))
		.andExpect(status().isNotFound())
		.andDo(print())
		.andDo(document("get-an-event-wrong"));
		
	}
	
	
	@DisplayName(value = "정상적으로 이벤트를 수정하기")
	@Test
	public void updateEvent() throws Exception{
		// Given
		Account account = this.createAccount();
		EventDomain eventDomain = this.generateEvent(100, account);
		EventDto eventDto = this.modelMapper.map(eventDomain, EventDto.class);
		String eventName = "Update Event";
		eventDto.setName(eventName);
		
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", eventDomain.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
				.accept(MediaTypes.HAL_JSON_VALUE)
				)
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("name").value(eventName))
		.andExpect(jsonPath("_links.self").exists())
		.andDo(document("update-event"));
		
	}
	
	@DisplayName(value = "입력값이 잘못된 이벤트를 수정하기")
	@Test
	public void updateEvent_with_wrong() throws Exception{
		// Given
		EventDomain eventDomain = this.generateEvent(100);
		EventDto eventDto = modelMapper.map(eventDomain, EventDto.class); 
		String eventName = "Update Event";
		eventDto.setName(eventName);
		eventDto.setBasePrice(20000000);
		eventDto.setMaxPrice(1000);
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", eventDomain.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
				.accept(MediaTypes.HAL_JSON_VALUE)
				)
		.andDo(print())
		.andExpect(status().isBadRequest());
		
	}
	
	@DisplayName(value = "입력값이 없는 경우 이벤트를 수정하기")
	@Test
	public void updateEvent_with_empty() throws Exception{
		// Given
		EventDomain eventDomain = this.generateEvent(100);
		EventDto eventDto = new EventDto();
		String eventName = "Update Event";
		eventDto.setName(eventName);
		
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", eventDomain.getId())
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
				.accept(MediaTypes.HAL_JSON_VALUE)
				)
		.andDo(print())
		.andExpect(status().isBadRequest());
		
	}
	
	

	@DisplayName(value = "존재하지 않는 이벤트를 수정하기")
	@Test
	public void updateEvent_with_not_event() throws Exception{
		// Given
		EventDomain eventDomain = this.generateEvent(100);
		EventDto eventDto = modelMapper.map(eventDomain, EventDto.class); 
		String eventName = "Update Event";
		eventDto.setName(eventName);
		eventDto.setBasePrice(20000000);
		eventDto.setMaxPrice(1000);
		//when & then
		this.mockMvc.perform(put("/api/events/{id}", 30123120)
				.header(HttpHeaders.AUTHORIZATION, "Bearer" + getAccessToken(false)) // 인증 정보를 넘겨주는 방법
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(eventDto))
				.accept(MediaTypes.HAL_JSON_VALUE)
				)
		.andDo(print())
		.andExpect(status().isNotFound());
		
	}
	
}
