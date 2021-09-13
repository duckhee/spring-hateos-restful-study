package com.example.demo.domain;

import static org.assertj.core.api.Assertions.assertThat;

//import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;


import com.example.demo.event.EventDomain;


public class EventTest {

	@DisplayName(value = "builder test")
	@Test
	public void builder() {
		EventDomain newEvent = EventDomain.builder()
				.name("Inflearn REST API")
				.description("REST API development with spring")
				.build();
		
		assertThat(newEvent).isNotNull();
	}
	
	@DisplayName(value = "java bean make setter getter TEST")
	@Test
	public void javaBean() {
		String name = "Inflearn REST API";
		String description = "REST API development with spring";
		EventDomain newEvent = new EventDomain();
		newEvent.setName(name);
		newEvent.setDescription(description);
		
		assertThat(newEvent.getName()).isEqualTo(name);
		assertThat(newEvent.getDescription()).isEqualTo(description);
	}

	@DisplayName(value = "이벤트 free 설정 테스트")
	@Test
	public void testFree() {
		// Given 
		EventDomain event = EventDomain.builder()
				.basePrice(0)
				.maxPrice(0)
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isFree()).isTrue();
		
		// Given 
		event = EventDomain.builder()
				.basePrice(100)
				.maxPrice(0)
				.build();
		// When
		event.update();
		
		assertThat(event.isFree()).isFalse();
		
		// Given 
		event = EventDomain.builder()
				.basePrice(0)
				.maxPrice(100)
				.build();
		// When
		event.update();
		
		assertThat(event.isFree()).isFalse();

	}
	
	@DisplayName(value = "온라인인지 오프라인인지 테스트")
	@Test
	public void testOffline() {
		// Given
		EventDomain event = EventDomain.builder()
				.location("city")
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isOffline()).isTrue();

		// Given
		event = EventDomain.builder()
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isOffline()).isFalse();
	}

	
/** 	
	static Stream<Arguments> arguments = Stream.of(
			  Arguments.of(0, 0, true), // null strings should be considered blank
			  Arguments.of(100, 0, false),
			  Arguments.of(0, 0, true)
			);

	@DisplayName(value = "이벤트 free 설정 테스트 - with params" )
	@Test
	@MethodSource("arguments")
	@ParameterizedTest
	public void testFree_wit_params(int basePrice, int maxPrice, boolean isFree) {
		// Given 
		EventDomain event = EventDomain.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();
		// When
		event.update();
		// Then
		assertThat(event.isFree()).isEqualTo(isFree);
		
	}
*/	
	
}
