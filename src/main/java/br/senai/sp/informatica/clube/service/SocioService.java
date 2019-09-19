package br.senai.sp.informatica.clube.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import br.senai.sp.informatica.clube.component.SecurityFacade;
import br.senai.sp.informatica.clube.model.Autorizacao;
import br.senai.sp.informatica.clube.model.Socio;
import br.senai.sp.informatica.clube.repo.AutorizacaoRepo;
import br.senai.sp.informatica.clube.repo.SocioRepo;

@Service
public class SocioService {
	@Autowired
	private SocioRepo repo;
	@Autowired
	private AutorizacaoRepo auth;
	@Autowired
	private SecurityFacade security;
	
	public void salvar(@Valid Socio socio) {
		Socio old_socio;
		
		if(!socio.getOld_nome().equalsIgnoreCase(socio.getNome())) {
			old_socio = getSocio(socio.getOld_nome());
			removeSocio(socio.getOld_nome());
		} else {
			old_socio = getSocio(socio.getNome());
		}
		 
		auth.save(new Autorizacao(socio.getNome(), 
				socio.isAdministrador() ?"ROLE_ADMIN" : "ROLE_USER"));
		
		if(old_socio != null) {
			socio.setSenha(old_socio.getSenha());
		} else {
			socio.setSenha(socio.getSenha());
		}
		
		repo.save(socio);
	}

	
	public List<Socio> getSocios() {
		return repo.findAll()
				.stream().filter(socio -> socio.isAtivo())
				.collect(Collectors.toList());
	}	
	
	private Socio atribuiPerfil(Socio socio) {
		Autorizacao autorizacao = getAutorizacao(socio.getNome());
		if(autorizacao !=null) {
			socio.setAdministrador(autorizacao.getPerfil().endsWith("ADMIN"));
		}else {
			socio.setAdministrador(false);
		}
		return socio;
	}
	public Socio getSocio(String nome) {
		Socio socio = repo.findById(nome).orElse(null);
		if(socio !=null) {
			socio= atribuiPerfil(socio);
		}
		return socio;
		
	}

	public boolean removeSocio(String nome) {
		Socio socio = getSocio(nome);
		if(socio != null) {
			
			Autorizacao autorizacao = getAutorizacao(nome);
			if(autorizacao != null)
				auth.delete(autorizacao); 
			
			repo.delete(socio);
			return true;
		} else {
			return false;
		}
	}

	public Autorizacao getAutorizacao(String nome) {
		return auth.findById(nome).orElse(null);
	}
	public GrantedAuthority getAutorizacoes(String nome) {
		Autorizacao autorizacao =getAutorizacao(nome);
		
		return autorizacao != null? () -> autorizacao.getPerfil() : null;
	}
}
