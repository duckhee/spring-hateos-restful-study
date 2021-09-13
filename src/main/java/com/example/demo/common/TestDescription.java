package com.example.demo.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.SOURCE) // 어디까지 가져다가 쓸 것인지 소스에서만 쓸 것인지 런타임에도 쓸 것인지 컴파일 타입에도 쓸 것인지 설정하는 것
public @interface TestDescription {

	String value() default "Testing";
}
