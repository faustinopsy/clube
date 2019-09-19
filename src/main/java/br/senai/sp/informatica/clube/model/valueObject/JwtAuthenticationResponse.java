package br.senai.sp.informatica.clube.model.valueObject;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType= "Bearer ";
	
	public JwtAuthenticationResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
		
	}
	
	
}
