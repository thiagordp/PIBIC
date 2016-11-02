package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {

	/**
	 * Atributos
	 */
	@Id
	@Column(name = "ID_USUARIO", nullable = false)
	private int id_usuario;

	@Column(name = "NOME", nullable = false)
	private String nome;

	@Column(name = "ENDERECO", nullable = false)
	private String endereco;

	@Column(name = "CEP", nullable = false)
	private String cep;

	@Column(name = "CPF", nullable = false)
	private String cpf;

	@Column(name = "RG", nullable = false)
	private String rg;

	/**
	 * Construtores
	 */
	public Usuario() {
		this.id_usuario = 0;
		this.nome = this.endereco = this.cep = this.cpf = this.rg = "";
	}

	public Usuario(int id_tipo, String nome, String endereco, String cep, String cpf, String rg) {
		this.id_usuario = id_tipo;
		this.nome = nome;
		this.endereco = endereco;
		this.cep = cep;
		this.cpf = cpf;
		this.rg = rg;
	}

	/**
	 * Gets e sets ****************************
	 */

	/**
	 * @return the id_tipo
	 */
	public int getId_Usuario() {
		return id_usuario;
	}

	/**
	 * @param id_tipo
	 *            the id_tipo to set
	 */
	public void setId_tipo(int id_tipo) {
		this.id_usuario = id_tipo;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco() {
		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	/**
	 * @return the cep
	 */
	public String getCep() {
		return cep;
	}

	/**
	 * @param cep
	 *            the cep to set
	 */
	public void setCep(String cep) {
		this.cep = cep;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
		return rg;
	}

	/**
	 * @param rg
	 *            the rg to set
	 */
	public void setRg(String rg) {
		this.rg = rg;
	}
}
