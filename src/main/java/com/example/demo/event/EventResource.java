package com.example.demo.event;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

//import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


// bean serializer를 가지고 JSON 변환을 하는 것이다.
public class EventResource extends EntityModel<EventDomain> { // 이것이 정석? 
//public class EventResource extends RepresentationModel<EventResource>{
	
	@JsonUnwrapped // json 형태에 객체 이름을 제거하는 방법 JSON 변환 시 현재 Resource의 이름을 제거해준다.
	private EventDomain eventDomain;
	
	
	public EventResource(EventDomain eventDomain) {
		this.eventDomain = eventDomain;
	}

	public EventDomain getEvent() {
		return eventDomain;
	}
	
	/** 자기자신의 링크를 만들어주기 위한 베이스 URL을 가져오기 */
    private static WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class);

    /** profile link 추가 및 HAL-JSON 형태로 만들어주기 위한 링크 추가 */
    public static EntityModel<EventDomain> of(EventDomain event, String profile){
        List<Link> links = getSelfLink(event);
        /** profile 링크를 입력 받은 것을 추가 */
        links.add(Link.of(profile, "profile"));
        /** HAL-JSON 형태의 객체 반환 */
        return EntityModel.of(event, links);
    }
    
    /** link 추가 및 HAL-JSON 형태로 만들어주기 위한 링크 추가 */
    public static EntityModel<EventDomain> of(EventDomain event){
    	/** 링크를 여러개 만들기 위한 링크 리스트의 형태의 객체에 링크 추가 */
        List<Link> links = getSelfLink(event);
        /** HAL-JSON 형태의 객체 반환 */
        return EntityModel.of(event, links);
    }

    /** 링크를 만들어주는 것 */
    private static List<Link> getSelfLink(EventDomain event) {
        selfLinkBuilder.slash(event.getId());
        List<Link> links = new ArrayList<>();
        links.add(selfLinkBuilder.withSelfRel());
        return links;
    }

    public static URI getCreatedUri(EventDomain event) {
        return selfLinkBuilder.slash(event.getId()).toUri();
    }
}
	
