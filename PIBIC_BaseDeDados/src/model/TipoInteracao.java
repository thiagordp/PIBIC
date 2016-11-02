package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_interacao")
public class TipoInteracao {

    /**
     * Atributos
     */
    @Id
    @Column(name = "ID_TIPO", nullable = false)
    private int id_tipo;

    @Column(name = "DESCRICAO", nullable = false)
    private String descricao;

    /**
     * Construtores
     */

    public TipoInteracao() {
	id_tipo = 0;
	descricao = "";
    }

    public TipoInteracao(int id_tipo, String descricao) {
	this.id_tipo = id_tipo;
	this.descricao = descricao;
    }

    /**
     * Gets e sets
     */

    /**
     * @return the id_tipo
     */
    public int getId_tipo() {
	return id_tipo;
    }

    /**
     * @param id_tipo
     *            the id_tipo to set
     */
    public void setId_tipo(int id_tipo) {
	this.id_tipo = id_tipo;
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
}
