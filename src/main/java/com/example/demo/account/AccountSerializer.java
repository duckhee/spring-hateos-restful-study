package com.example.demo.account;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class AccountSerializer extends JsonSerializer<Account>{

	@Override
	public void serialize(Account account, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		gen.writeStartObject();
		gen.writeNumberField("id", account.getId());
		gen.writeEndObject();
		
	}

}
