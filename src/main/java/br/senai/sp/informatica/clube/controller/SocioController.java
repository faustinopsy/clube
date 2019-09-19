 package br.senai.sp.informatica.clube.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import br.senai.sp.informatica.clube.component.JsonError;
import br.senai.sp.informatica.clube.model.Autorizacao;
import br.senai.sp.informatica.clube.model.Socio;
import br.senai.sp.informatica.clube.service.SocioService;

@Controller
@RequestMapping("/api")
public class SocioController {
	@Autowired
	private SocioService socioService;
	
	@Secured("ROLE_ADMIN")
	@PostMapping("/salvaSocio")
	public ResponseEntity<Object> salvaUsuario(@RequestBody @Valid Socio socio, BindingResult result) {
		if(result.hasErrors()) {
			return ResponseEntity.unprocessableEntity()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(JsonError.build(result));
		} else {
			socioService.salvar(socio);
			return ResponseEntity.ok().build();
		}
	}
	
	@RequestMapping("/listaSocio")
	public ResponseEntity<List<Socio>> listaSocio() {
		return ResponseEntity.ok(socioService.getSocios());
	}
	@Secured("ROLE_ADMIN")
	@GetMapping("/editaSocio/{nome}")
	public ResponseEntity<Object> editaSocio(@PathVariable("nome") String nome) {
		Socio socio = socioService.getSocio(nome);
		
		if(socio != null) {
			socio.setOld_nome(socio.getNome());
			return ResponseEntity.ok(socio);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@Secured("ROLE_ADMIN")
	@PostMapping("/removeSocio/{nome}")
	public ResponseEntity<Object> removeUsuario(@PathVariable("nome") String nome) {
		if(socioService.removeSocio(nome)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@RequestMapping("leAutorizacoes/{nome}")
	public ResponseEntity<Autorizacao> getAutorizacoes(@PathVariable("nome") String nome){
		
		return ResponseEntity.ok(socioService.getAutorizacao(nome));
	}
}
