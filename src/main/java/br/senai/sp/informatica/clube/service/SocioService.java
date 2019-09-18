package br.senai.sp.informatica.clube.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public void salvar(@Valid Socio socio) {
		Socio old_socio;
		
		if(!socio.getOld_nome().equalsIgnoreCase(socio.getNome())) {
			old_socio = getSocio(socio.getOld_nome());
			removeSocio(socio.getOld_nome());
		} else {
			old_socio = getSocio(socio.getNome());
		}
		 
		auth.save(new Autorizacao(socio.getNome(), "ROLE_USER"));
		
		if(old_socio != null) {
			socio.setSenha(old_socio.getSenha());
		} else {
			socio.setSenha(socio.getSenha());
		}
		
		repo.save(socio);
	}

	public List<Socio> getSocios() {
		return repo.findAll().stream()
				.collect(Collectors.toList());
	}	
	
	public Socio getSocio(String nome) {
		Socio socio = repo.findById(nome).orElse(null);
		
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

	private Autorizacao getAutorizacao(String nome) {
		return auth.findById(nome).orElse(null);
	}
}
