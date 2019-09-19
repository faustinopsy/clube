package br.senai.sp.informatica.clube.model.validacao;

import java.util.function.BiFunction;
import java.util.function.Predicate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SenhaValidator implements ConstraintValidator<Senha, String>{
	private Senha senha;
	
	
	BiFunction<String, Predicate<Integer>, Boolean> regra=(texto,condicao)-> texto.chars()
			.filter(letra -> condicao.test(letra))
			.findAny().isPresent();
			
			
	@Override
	public void initialize(Senha constraintAnnotation) {
		senha = constraintAnnotation;
	}
	
	
	@Override
	public boolean isValid(String senha, ConstraintValidatorContext context) {
		return senha.length() >=8 && 
			   regra.apply(senha, letra -> letra=='#' || letra =='&' || letra =='$' || letra=='%') &&
			   regra.apply(senha, Character::isUpperCase) &&
			   regra.apply(senha, Character::isDigit);
	}
}
