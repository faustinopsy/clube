package br.senai.sp.informatica.clube.component;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	@Value("${app.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwtExpirationInMs}")
	private int jwtExpirationInMs;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	public String generateToken(Authentication authentication) {
		
		User userPrincipal = (User)authentication.getPrincipal();
		Date agora = new Date();
		Date dataDeExpiracao = new Date(agora.getTime()+ jwtExpirationInMs);
		
		return Jwts.builder().setSubject(userPrincipal.getUsername())
				.setIssuedAt(agora).setExpiration(dataDeExpiracao)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserIdFromJWT(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();

	}
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parser()
			.setSigningKey(jwtSecret)
			.parseClaimsJws(token);
			return true;
		}catch(MalformedJwtException erro){
			logger.error("Token Invalido");
		}catch(ExpiredJwtException erro) {
			logger.error("Token Expirado");
		}catch(UnsupportedJwtException erro) {
			logger.error("Token irreconhecido");
		}catch(IllegalArgumentException erro) {
			logger.error("Token danificado");
		}
		return false;
		
		
	}
}
