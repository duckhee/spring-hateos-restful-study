package com.example.demo.event;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;


@Component
public class EventValidator {


	public void validate(EventDto eventDto, Errors errors) {
		if((eventDto.getBasePrice() > eventDto.getMaxPrice()) && (eventDto.getMaxPrice() != 0)) {
			errors.rejectValue("basePrice", "wrongValue", "Base price is wrong.");
			errors.rejectValue("MaxPrice", "wrongValue", "Max price is wrong.");
		}
		
		//TODO endEventDateTime
		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		if(endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) || 
				endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime()) || 
				endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime())) {
			/** rejectValue를 하면 field Error에 들어가는 것이다. reject()를 하면 global Error에 들어간다. */
			errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime wrong.");
		}
		
		//TODO beginEventDateTime
		@SuppressWarnings("unused")
		LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
		
		
		//TODO CloseEventDateTime Enrollment
//		LocalDateTime closeEventDateTime = eventDto.getCloseEnrollmentDateTime();
		
		//TODO BeginEventDateTime Enrollment
//		LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
		
	}

}
