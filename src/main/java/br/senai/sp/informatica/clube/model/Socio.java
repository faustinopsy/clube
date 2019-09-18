package br.senai.sp.informatica.clube.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {
	@Id
	@Column(length = 15)
	private String nome;
	@Transient
	@JsonIgnore
	private String old_nome;
	private String endereco;
	private String email;
	private String telefone;
	@JsonIgnore
	private String senha;
	private boolean ativo;
}
