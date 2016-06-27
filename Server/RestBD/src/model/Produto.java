/**
 * 
 */
package model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Thiago Dal Pont
 *
 */
@Entity
@Table(name = "produto")
public class Produto {
	@EmbeddedId
	private ProdutoPK pk;

	@Column(name = "nome_produto")
	private String nomeProduto = "";

	public ProdutoPK getPk() {
		return pk;
	}

	public void setPk(ProdutoPK pk) {
		this.pk = pk;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}
}