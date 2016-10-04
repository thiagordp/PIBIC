package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "interacao")
public class Interacao {

    @Id
    @Column(name = "ID_INTERACAO", nullable = false)
    private int id_interacao;

    @Column(name = "DATA", nullable = false)
    private Date data;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO", nullable = false)
    private TipoInteracao tipo;

    @Column(name = "QTDE", nullable = true)
    private int qtde;

    @Column(name = "VALOR", nullable = true)
    private float valor;

    /**
     **************** Construtores ***************
     */
    public Interacao() {
	this.id_interacao = 0;
	this.data = new Date();
	this.produto = new Produto();
	this.usuario = new Usuario();
	this.tipo = new TipoInteracao();
    }

    public Interacao(int id_interacao, Date data, Produto produto, Usuario usuario, TipoInteracao tipo, int qtde, float valor) {
	this.id_interacao = id_interacao;
	this.data = data;
	this.produto = produto;
	this.usuario = usuario;
	this.tipo = tipo;
	this.qtde = qtde;
	this.valor = valor;
    }

    /**
     * ************* Getters and Setters *************
     */

    /**
     * @return the id_interacao
     */
    public int getId_interacao() {
	return id_interacao;
    }

    /**
     * @param id_interacao
     *            the id_interacao to set
     */
    public void setId_interacao(int id_interacao) {
	this.id_interacao = id_interacao;
    }

    /**
     * @return the data
     */
    public Date getData() {
	return data;
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(Date data) {
	this.data = data;
    }

    /**
     * @return the produto
     */
    public Produto getProduto() {
	return produto;
    }

    /**
     * @param produto
     *            the produto to set
     */
    public void setProduto(Produto produto) {
	this.produto = produto;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
	return usuario;
    }

    /**
     * @param usuario
     *            the usuario to set
     */
    public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
    }

    /**
     * @return the tipo
     */
    public TipoInteracao getTipo() {
	return tipo;
    }

    /**
     * @param tipo
     *            the tipo to set
     */
    public void setTipo(TipoInteracao tipo) {
	this.tipo = tipo;
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "Interacao [id_interacao=" + id_interacao + ", data=" + data + ", produto=" + produto.getDescricao() + ", usuario=" + usuario.getNome()
			+ ", tipo=" + tipo.getDescricao() + ", qtde=" + qtde + ", valor=" + valor + "]";
    }

}