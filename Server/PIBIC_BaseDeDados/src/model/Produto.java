package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @Column(name = "ID_PRODUTO", unique = true, nullable = false)
    private int id_usuario;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    @Column(name = "QTDE", nullable = false)
    private int qtde;

    @Column(name = "VALOR", nullable = false)
    private float valor;

    public Produto() {
	id_usuario = 0;
	descricao = "";
	qtde = 0;
	valor = 0.0F;
    }

    public Produto(int id_usuario, String descricao, int qtde, float valor) {
	this.id_usuario = id_usuario;
	this.descricao = descricao;
	this.qtde = qtde;
	this.valor = valor;
    }

    /*
     * ******************* GETTERS AND SETTERS **********************
     */

    /**
     * @return the id_usuario
     */
    public int getId_usuario() {
	return id_usuario;
    }

    /**
     * @param id_usuario
     *            the id_usuario to set
     */
    public void setId_usuario(int id_usuario) {
	this.id_usuario = id_usuario;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
	return descricao;
    }

    /**
     * @param descricao
     *            the descricao to set
     */
    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    /**
     * @return the qtde
     */
    public int getQtde() {
	return qtde;
    }

    /**
     * @param qtde
     *            the qtde to set
     */
    public void setQtde(int qtde) {
	this.qtde = qtde;
    }

    /**
     * @return the valor
     */
    public float getValor() {
	return valor;
    }

    /**
     * @param valor
     *            the valor to set
     */
    public void setValor(float valor) {
	this.valor = valor;
    }
}
