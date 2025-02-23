package com.example.demo.common;



import org.springframework.hateoas.EntityModel;
import org.springframework.validation.Errors;

import com.example.demo.index.IndexController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ErrorsResource extends EntityModel<Errors> {
	

	public static EntityModel<Errors> modelOf(Errors errors) {
		EntityModel<Errors> errorsModel = EntityModel.of(errors);
		errorsModel.add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
		return errorsModel;
    }

}
