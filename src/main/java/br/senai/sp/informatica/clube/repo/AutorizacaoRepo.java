package br.senai.sp.informatica.clube.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sp.informatica.clube.model.Autorizacao;

public interface AutorizacaoRepo extends JpaRepository<Autorizacao, String> {

}
