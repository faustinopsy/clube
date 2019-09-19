package br.senai.sp.informatica.clube.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.senai.sp.informatica.clube.model.validacao.Senha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Socio {
	@Id
	@Column(name="nome", length=15)
	private String nome;
	@Transient
	@JsonIgnore
	private String old_nome;
	private String endereco;
	private String email;
	private String telefone;
	@Senha(message="A senha deve ter 1 numero e 1 letra maisculas e 1 simbolo")
	private String senha;
	
	private boolean ativo;
	public boolean isAdministrador() {
		// TODO Auto-generated method stub
		return false;
	}
	public void setAdministrador(boolean endsWith) {
		// TODO Auto-generated method stub
		
	}
	public Object isHabilitado() {
		// TODO Auto-generated method stub
		return null;
	}
}
