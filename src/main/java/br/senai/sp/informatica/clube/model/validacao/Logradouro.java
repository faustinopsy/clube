package br.senai.sp.informatica.clube.model.validacao;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = LogradouroValidator.class)
public @interface Logradouro {
	int max() default 0;
	String message() default " ";
	
	Class<?>[] groups() default{ };
	Class<? extends Payload>[] payload() default{ };
	
}
