package com.example.demo.common;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonComponent // objectmapper에 추가하는 것 
public class ErrorsSerializer extends JsonSerializer<Errors>{

	@Override
	public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		/** error의 시작을 알려주는 것 버전 업데이트로 인해서 Jackson 라이브러리가 Arrary를 먼저 만드는 것을 허용하지 않기 때문에 */
		/** Error가 여려개이니깐 Errors를 답아주기 위해서 배열의 시작을 알려주는 것 */
        gen.writeFieldName("errors");
		gen.writeStartArray();
//		gen.writeArrayFieldStart("errors");

		errors.getFieldErrors().forEach(error->{
			/** error를 json object로 만들어주는 것 */
			try {
				gen.writeStartObject();
				gen.writeStringField("field", error.getField());
				gen.writeStringField("objectName", error.getObjectName());
				gen.writeStringField("code", error.getCode());
				gen.writeStringField("defaultMessage", error.getDefaultMessage());
				Object rejectedValue = error.getRejectedValue(); // reject된 value를 가져오는 것
				if(rejectedValue != null) {
					gen.writeStringField("rejectedValue", rejectedValue.toString());
				}
				/** 배열의 경우 Object의 끝을 표시해줘야한다. */
				gen.writeEndObject();
				/** error를 json object로 만들어주는 것 */
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("loggin filed Error ::: {}", e.toString());
				
			}
		});
		
		errors.getGlobalErrors().forEach(error->{
			try {
				/** error를 json object로 만들어주는 것 */
				gen.writeStartObject();
				gen.writeStringField("objectName", error.getObjectName());
				gen.writeStringField("code", error.getCode());
				gen.writeStringField("defaultMessage", error.getDefaultMessage());
				/** error를 json object로 만들어주는 것 */
				gen.writeEndObject();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("loging :::: {}", e.toString());
					e.printStackTrace();
				}
				
		});
		
		/** Error가 여려개이니깐 Errors를 답아주기 위해서 배열의 끝을 알려주는 것 */
		gen.writeEndArray();
		
	}

}
