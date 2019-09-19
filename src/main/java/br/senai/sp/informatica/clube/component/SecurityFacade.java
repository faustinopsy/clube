package br.senai.sp.informatica.clube.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.senai.sp.informatica.clube.model.Socio;

@Component
public class SecurityFacade {

	public String getUserName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth !=null) {
			Socio user = (Socio)auth.getPrincipal();
			return user.getNome();
		}else {
			return null;
		}
	}
	
}
