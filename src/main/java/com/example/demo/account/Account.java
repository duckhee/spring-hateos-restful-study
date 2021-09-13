package com.example.demo.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String email;
	
	private String password;
	
	@Builder.Default
	/** 여러개의 enum 타입을 가질 수있다는 것을 알려주는 것 */
	@ElementCollection(fetch = FetchType.EAGER) // 값이 여려개일 수도 있다고 알려주는 것 @ElementCollection
	@Enumerated(value = EnumType.STRING)
	private Set<AccountRole> roles = new HashSet<>(); 
	
	
}
