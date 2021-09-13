package com.example.demo.event;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.demo.account.Account;
import com.example.demo.account.AccountSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Entity 위에는 Data 를 쓰지 않는다. 상호 참조로 인해 stack overflow가 일어날 수 있으므로
@Entity
@Getter
@Setter
@Builder // default로 모든 arg를 가지고 만들게 된다. 다른 패키지에서 객체를 만들기 애매해지므로 NoArgsConstructor와 getter setter를 추가해주는 것이다.
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class EventDomain {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private String description;
	
	private LocalDateTime beginEnrollmentDateTime;
	
	private LocalDateTime closeEnrollmentDateTime;
	
	private LocalDateTime beginEventDateTime;
	
	private LocalDateTime endEventDateTime;
	
	private String location;
	
	private int basePrice;
	
	private int maxPrice;
	
	private int limitOfErollment;
	
	private boolean offline;
	
	private boolean free;
	
	@Builder.Default
	/** enum 타입은 String 값으로 변경을 해서 저장하는 것이 좋다. */
	@Enumerated(value = EnumType.STRING) 
	private EventStatus eventStatus = EventStatus.DRAFT;

	@ManyToOne // 단반향 관계 설정
	@JsonSerialize(using = AccountSerializer.class) // 이정보를 Json으로 보낼 경우 사용할 Serializer 설정
	private Account owner;
	
	public void update() {
		if(this.basePrice == 0 && this.maxPrice == 0) {
			this.free = true;
		}else {
			this.free = false;
		}
		/** null 비교를 항상 먼저 해주는 것이 좋다. */
		if(this.location == null || this.location.isBlank()) {
			this.offline = false;
		}else {
			this.offline = true;
		}
	}
}
