package com.example.demo.index;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.event.EventController;

import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
public class IndexController {
	
	
	@GetMapping(value = "/api")
	public RepresentationModel<?> index() {
		
		var index = new RepresentationModel<>();
		index.add(linkTo(EventController.class).withRel("events"));
		return index;
	}

}
