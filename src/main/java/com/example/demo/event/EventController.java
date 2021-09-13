package com.example.demo.event;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.account.Account;
import com.example.demo.account.CurrentUser;
//import com.example.demo.account.AccountAdapter;
import com.example.demo.common.ErrorsResource;

// hateoas support make link
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

/**
 * Hateoas
 * Hypermedia As The English Of Application State
 * @author duckheewon
 *
 */

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {
	
	private final EventRepository eventRepository;
	
	private final ModelMapper modelMapper;
	
	private final EventValidator eventValidator;
	
	public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
		// TODO Auto-generated constructor stub
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	
	}

	
	//@PostMapping(value = "/api/events")
	@PostMapping
	public ResponseEntity<?> createEvent(@RequestBody @Valid EventDto events, Errors errors, @CurrentUser Account account){
		//URI crateUri =  linkTo(methodOn(EventController.class).createEvent(null)).slash("{id}").toUri();
		/** */
		
		if(errors.hasErrors()) {
			//return ResponseEntity.badRequest().build();
			/** Error를 body에 담아서 전송 하는 것 담기 위해서 ErrorSerializer를 구현 */
//			return ResponseEntity.badRequest().body(new ErrorsResource(errors));
//			return ResponseEntity.badRequest().body(errors);
			return badRequest(errors);
		}
		
		/** custom validator로 검증 실행 */
		eventValidator.validate(events, errors);
		
		if(errors.hasErrors()) {
			//return ResponseEntity.badRequest().build();
			/** Error를 body에 담아서 전송 하는 것 담기 위해서 ErrorSerializer를 구현 */
			//return ResponseEntity.badRequest().body(errors);
			return badRequest(errors);
		}
		
		EventDomain event = modelMapper.map(events, EventDomain.class);
		/** 무료인지 유료인지 설정하는 것 */
		event.update();
		event.setOwner(account);
		EventDomain newEvent = this.eventRepository.save(event);
		WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
		URI crateUri =  selfLinkBuilder.toUri();
		//return ResponseEntity.created(crateUri).build();
		/** 예전 소스 */
		//EventResource resource = new EventResource(newEvent);
		/** 현재 변경된 소스 이렇게 사용을 하는 것이 좋다. */
		EntityModel<EventDomain> resource = EventResource.of(newEvent);
		//resource.add(new Link(null));
		/** link insert */
		resource.add(linkTo(EventController.class).withRel("query-events"));
		resource.add(selfLinkBuilder.withRel("update-events"));
		resource.add(selfLinkBuilder.withSelfRel());
		resource.add(Link.of("/docs/index.html#resources-events-create").withRel("profile"));
		return ResponseEntity.created(crateUri).body(resource);
	}
	
	
	/** 
	 * @AuthenticationPrincipal User user를 사용하면 spring security의 user를 가져올 수 있다.
	 * expression=를 사용하면, 원하는 객체를 따로 끄내서 쓸 수 잇다. 
	 */
	@GetMapping
	public ResponseEntity<?> queryEvent(Pageable pageable, PagedResourcesAssembler<EventDomain> assembler, @CurrentUser Account user){
		
		Page<EventDomain> page = this.eventRepository.findAll(pageable);
		/** 페이지에 대한 링크를 자동으로 담아서 준다. */
		/** e-> new EventResource(e) 각각의 이벤트 페이지로 이동하는 링크 생성 */
		//var pagedResource = assembler.toModel(page);
		var pagedResource = assembler.toModel(page, e->EventResource.of(e));
		/** 사용자가 있으면, spring security의 값을 가지고 링크 생성 하는 것 */
		if(user != null) {
			pagedResource.add(linkTo(EventController.class).withRel("create-event"));
		}
		/** profile link 추가 */
		pagedResource.add(Link.of("/docs/index.html#resources-events-list").withRel("profile"));
		return ResponseEntity.ok().body(pagedResource);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getEvent(@PathVariable Long id, @CurrentUser Account account){
		Optional<EventDomain> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		EventDomain event = optionalEvent.get();
		EntityModel<EventDomain> eventResource = EventResource.of(event);
		eventResource.add(Link.of("/docs/index.html#resources-events-get").withRel("profile"));
		if(event.getOwner().equals(account)) {
			eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}
		return ResponseEntity.ok().body(eventResource);
		
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "{id}")
	public ResponseEntity<?> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDto eventDto, Errors errors, @CurrentUser Account account){
		Optional<EventDomain> eventDomain = eventRepository.findById(id);
		if(eventDomain.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		this.eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		EventDomain existEvent = eventDomain.get();
		this.modelMapper.map(eventDto, existEvent);
		EventDomain savedEvent = this.eventRepository.save(existEvent);
		EntityModel<EventDomain> eventResource = EventResource.of(savedEvent);
		eventResource.add(Link.of("/docs/index.html#resources-events-update").withRel("profile"));
		/** 관리자가 아니면 권한이 없다는 응답을 주는 것 */
		if(!existEvent.getOwner().equals(account)) {
			return new ResponseEntity(HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok(eventResource);
	}

	/** 에러를 전달하는 함수로 정의 중복이 있어서 refactoring */
	private ResponseEntity<?> badRequest(Errors errors) {
		return  ResponseEntity.badRequest().body(ErrorsResource.modelOf(errors));
//		return  ResponseEntity.badRequest().body(errors);
	}
}
