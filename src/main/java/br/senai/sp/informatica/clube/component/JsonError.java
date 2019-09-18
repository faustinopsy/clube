package br.senai.sp.informatica.clube.component;

import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class JsonError {
	public static String build(BindingResult result) {
		return new StringBuilder("{\n" +
			// Obtém a lista dos atributos com erro
			result.getFieldErrors().stream()
				// Separa o nome do atributo e da mensagem de erro
				.map(erro -> "\"" + erro.getField() + "\" : \"" +
						            erro.getDefaultMessage() + "\"")
				// Agrupa todos dos erros separando por vírgula
				.collect(Collectors.joining(",\n"))
			+ "\n}").toString();
	}
}
