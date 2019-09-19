package br.senai.sp.informatica.clube.controller;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.informatica.clube.component.JwtTokenProvider;
import br.senai.sp.informatica.clube.model.valueObject.JwtAuthenticationResponse;
import br.senai.sp.informatica.clube.model.valueObject.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AuthController.class);
	@PostMapping("/signin")
	public ResponseEntity<?> autentication(@RequestBody @Valid LoginRequest login){
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsername(),   login.getPassword()));
		logger.error(login.getUsername()+ " "+login.getPassword());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		//criar o token de autenticação
			String jwt= tokenProvider.generateToken(auth);
			
			logger.error("Token "+ jwt );
				return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
	
	
}
