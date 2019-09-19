package br.senai.sp.informatica.clube.filter;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.senai.sp.informatica.clube.model.Socio;
import br.senai.sp.informatica.clube.component.JwtTokenProvider;
import br.senai.sp.informatica.clube.service.SocioService;

public class JwtAutheticationFilter  extends OncePerRequestFilter{
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private SocioService socioService;
	
	private static final Logger logger = LoggerFactory.getLogger(JwtAutheticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		
			String jwt=getJwtFromRequest(request);
			
			if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
				
				String userId=tokenProvider.getUserIdFromJWT(jwt);
				
				Socio socio = socioService.getSocio(userId);
				
				UsernamePasswordAuthenticationToken autenticacao =
						new UsernamePasswordAuthenticationToken(socio, null, Collections.singletonList(
								socioService.getAutorizacoes(userId) ));
				
				autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
				SecurityContextHolder.getContext().setAuthentication(autenticacao);
			}
		}catch (Exception erro) {
			logger.error("Não foi possível registrar a Autenticação ", erro);
		}
		filterChain.doFilter(request, response);
		
	}
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
	
	
}
