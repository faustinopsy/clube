package br.senai.sp.informatica.clube.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.informatica.clube.model.Socio;

public interface SocioRepo extends JpaRepository<Socio, String>{

}
