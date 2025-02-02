package com.backend.greenride.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tb_motorista")
public class Motorista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O atributo nome é Obrigatório!")
	@Size(max = 100)
	private String nome;

	@NotBlank(message = "O atributo usuario não deve ser nulo")
	@Size(max = 50, message = "O usuario não deve ser maior que 50 caracteres")
	@Schema(example = "email@email.com.br")
	@Email
	private String motorista;

	@Size(min = 8,  message = "O senha não deve ser menor que 8 e maioque 12 caracteres")
	@NotBlank(message = "O atributo senha não deve ser nulo")
	private String senha;

	private String foto;

	@NotBlank(message = "Colocar o modelo do carro")
	@Size(max = 100)
	private String carro;

	@NotBlank(message = "O atributo cor é Obrigatório!")
	@Size(max = 100)
	private String cor;

	@NotBlank(message = "O atributo placa é Obrigatório!")
	@Size(max = 7)
	private String placa;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "motorista", cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("motorista")
	private List<Corrida> corrida;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMotorista() {
		return motorista;
	}

	public void setMotorista(String motorista) {
		this.motorista = motorista;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCarro() {
		return carro;
	}

	public void setCarro(String carro) {
		this.carro = carro;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public List<Corrida> getCorrida() {
		return corrida;
	}

	public void setCorrida(List<Corrida> corrida) {
		this.corrida = corrida;
	}

	
	
}
