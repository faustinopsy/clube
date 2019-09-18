package br.senai.sp.informatica.clube.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Autorizacao {
	@Id
	@Column(name="nome", length=15)
	private String nome;
	private String perfil;
}